/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.barcvilla.ejb;

import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.InventoryItem;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.util.Ini;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;
import javax.transaction.Status;
import javax.transaction.SystemException;

/**
 * Stateful Bean Management Transaction - En este caso, en lugar de confiar en el container para manejar la demarcacion de
 * transaccion en los limites del metodo. Mostramos como demarcar una transaccion explicitamente,
 * No hay un requerimiento que ud utilice Bean Management Transaction cuando utiliza un stateful session bean y de hecho
 * esta opcion tipicamente no es usada.
 * Al ser un Stateful Session Bean le permite al enterprise bean retener el estado desde la invocacion de un cliente al siguiente.
 * En este caso, el estado es el contexto de persistencia y la transaccion asociada la cual debe sobrevivir a traves de multiples
 * invocaciones de metodos. La declaracion Bean Managed Transaction significa que el contenedor no deberia automaticamente
 * interponerse en los limites del metodo para demarcar la transaccion. 
 */
@Stateful(name = "OrderProcessorBMT")
@TransactionManagement(TransactionManagementType.BEAN)
@Interceptors(OrderProcessorBMTBeanTxnInterceptor.class)
public class OrderProcessorBMTBean {

    @Resource
    SessionContext sessionContext;
    /**
     * PersistenceContextType.EXTENDED permite persistir una transaccion a la siguiente, y permite a las entidades asociadas
     * permanecer administradas incluso luego que la transaccion en la cual ellas fueron creadas hayan terminado.
     * El objeto UserTransaction disponible a traves de la propiedad sessionContext
     */
    @PersistenceContext(unitName = "Chapter08-TransactionSamples-JTA", type = PersistenceContextType.EXTENDED)
    private EntityManager em;
    
    /**
     * Eliminamos un Customer existente con email: wineapp@yahoo.com y cualquier Wine existente con country 'United States'
     */
    public String initialize() throws HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("Removed ");
        int i = 0;

        /**
         * Filtramos los datos eliminando cualquier cliente con email:
         * wineapp@yahoo.com o si esta definido en el archivo de proppiedades
         * user.properties.
         */
        Ini.loadProperties();
        for (Customer customer : getCustomerFindByEmail(Ini.TO_EMAIL_ADDRESS)) {
            em.remove(customer);
            i++;
        }
        strBuf.append(i);
        strBuf.append(" Customer(s) and ");
        
        // Eliminamos cualquier Wine existente con Country 'United State'
        i = 0;
        for(Wine wine : getWineFindByCountry("United States"))
        {
            em.remove(wine);
            i++;
        }
        strBuf.append(i);
        strBuf.append(" Wine(s)");
        
        // Aplicamos los cambios, commiting  las operaciones de removal entity
        commitTransaction();
        return strBuf.toString();
    }

    /**
     * Creamos un CustomerOrder desde los items del Customer's cart. Creamos una
     * nueva entidad CustomerOrder y luego creamos una nueva entidad OrderItem
     * para cada cartItem encontrado en el Customer's cart.
     *
     * Usamos Context Management Transaction con el atributo por defecto
     * REQUIRED, si este metodo es invocado sin un contexto de transaccion, una
     * nueva transaccion sera creada por el contenedor EJB sobre la invocacion
     * del metodo y commit sobre la terminacion satisfactoria del metodo
     *
     * @return un mensaje de status (texto plano)
     */
    public CustomerOrder createCustomerOrder(Customer customer) throws Exception {
        if (customer == null) {
            throw new IllegalArgumentException("OrderProcessingBean.createCustomerOrder():  Customer not specified");
        }
        // Nos aseguramos que estamos trabajando con un objeto Customer managed
        customer = em.find(Customer.class, customer.getId());

        CustomerOrder customerOrder = new CustomerOrder();
        customer.addCustomerOrder(customerOrder);

        final Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        // Clonamos CartItem List, luego removemos las entradas al CartItem de la instancia Customer
        // Sin ocasionar un ConcurrentModificationException en el iterator
        final List<CartItem> cartItemList = new ArrayList(customer.getCartItemList());
        for (CartItem cartItem : cartItemList) {
            // creamos un nuevo OrderItem para este CartItem
            final OrderItem orderItem = new OrderItem();
            orderItem.setOrderDate(orderDate);
            orderItem.setPrice(cartItem.getWine().getRetailPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setStatus("Order Created");
            orderItem.setWine(cartItem.getWine());
            customerOrder.addOrderItem(orderItem);

            //eliminamos el CartItem. Notar que el flag 'orphanRemoval' asegurara que el CartItem es removido de la BD
            // una vez que esta (CartItem) esta disasociado desde el Customer.
            customer.removeCartItem(cartItem);
        }
        // La regla de Cascada sobre Customer causara que CustomerOrder sea persistida cuando el Customer es merged
        em.merge(customer);
        return customerOrder;
    }

    @ExcludeClassInterceptors
    public void commitTransaction() throws HeuristicMixedException, HeuristicRollbackException, RollbackException, SystemException {
        final UserTransaction txn = sessionContext.getUserTransaction();
        if (txn.getStatus() == Status.STATUS_ACTIVE) {
            txn.commit();
        }
    }

    @ExcludeClassInterceptors
    public void rollbackTransaction() throws SystemException {
        final UserTransaction txn = sessionContext.getUserTransaction();
        if (txn.getStatus() == Status.STATUS_ACTIVE) {
            txn.rollback();
        }
    }

    @ExcludeClassInterceptors
    public boolean isTransactionDirty() throws SystemException {
        final UserTransaction txn = sessionContext.getUserTransaction();
        return Boolean.valueOf(txn.getStatus() == Status.STATUS_ACTIVE);
    }

    @ExcludeClassInterceptors
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    public <T> void removeEntity(T entity) {
        em.remove(em.merge(entity));
    }

    @ExcludeClassInterceptors
    public <T> List<T> findAll(Class<T> entityClass) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }

    @ExcludeClassInterceptors
    public <T> List<T> findAllByRange(Class<T> entityClass, int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * <code>select o from Customer o where o.email = :email</code>
     */
    @ExcludeClassInterceptors
    public List<Customer> getCustomerFindByEmail(String email) {
        return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getResultList();
    }

    /**
     * <code>select object(wine) from Wine wine where wine.year = :year</code>
     */
    @ExcludeClassInterceptors
    public List<Wine> getWineFindByYear(Integer year) {
        return em.createNamedQuery("Wine.findByYear", Wine.class).setParameter("year", year).getResultList();
    }

    /**
     * <code>select object(wine) from Wine wine where wine.country = :country</code>
     */
    @ExcludeClassInterceptors
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }

    /**
     * <code>select object(wine) from Wine wine where wine.varietal = :varietal</code>
     */
    @ExcludeClassInterceptors
    public List<Wine> getWineFindByVarietal(String varietal) {
        return em.createNamedQuery("Wine.findByVarietal", Wine.class).setParameter("varietal", varietal).getResultList();
    }

    /**
     * <code>select o from InventoryItem o where o.wine = :wine</code>
     */
    @ExcludeClassInterceptors
    public List<InventoryItem> getInventoryItemFindItemByWine(Object wine) {
        return em.createNamedQuery("InventoryItem.findItemByWine", InventoryItem.class).setParameter("wine", wine).getResultList();
    }
}

/**
 * Interceptor Class sirve para interponerse en cada metodo que aplica cambios a traves del EntityManager para iniciar 
 * automaticamente una transaccion. Este patron es similar al modelo transaccional SQL, donde una transaccion es iniciada
 * implicitamente en cada operacion DML como INSERT, UPDATE o DELETE. 
 * El cliente es responsable solo del llamado a COMMIT o ROLLBACK para concluir la transaccion, pero no hay una llamada
 * explicita al inicio de la transaccion. 
 * 
 */
class OrderProcessorBMTBeanTxnInterceptor {

    public OrderProcessorBMTBeanTxnInterceptor() {
    }

    @AroundInvoke
    Object beginTrans(InvocationContext invocationContext) throws Exception {
        final OrderProcessorBMTBean orderProcessorBMTBean = (OrderProcessorBMTBean) invocationContext.getTarget();
        final UserTransaction txn = orderProcessorBMTBean.sessionContext.getUserTransaction();
        if (txn.getStatus() == Status.STATUS_NO_TRANSACTION) {
            txn.begin();
        }
        return invocationContext.proceed();
    }
}
