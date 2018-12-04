/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.barcvilla.ejb;

import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.entities.test.PopulateDemoData;
import com.apress.ejb.chapter07.util.Ini;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Order Processor Bean Context Management Transaction En este Ejb Stateless
 * Session Bean declaramos el comportamiento Transactional.
 *
 * @author PC
 */
@Stateless(name = "OrderProcessorCMT")
public class OrderProcessorCMTBean {

    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Chapter08-TransactionSamples-JTA")
    private EntityManager em;

    /**
     * Eliminamos el cliente existente con el email wineapp@yahoo.com y
     * cualquier vino existente con el coutry : United States El contenedor EJB
     * se encargara que este trabajo se realice en un contexto transactional.
     */
    public String initialize() {
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
        strBuf.append(" wine(s)");
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
    public CustomerOrder createCustomerOrder(Customer customer) {
        return createCustomerOrderUsingSupports(customer);
    }

    /**
     * Si un contexto de transaccion esta disponible, este es usado por el
     * metodo, si no hay un contexto de transaccion disponible, entonces el
     * contenedor invoca el metodo sin un contexto de transaccion
     *
     * @param customer
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CustomerOrder createCustomerOrderUsingSupports(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("OrderProcessingBean.createCustomerOrder():  Customer not specified");
        }

        if (!em.contains(customer)) {
            customer = em.merge(customer);
        }

        final CustomerOrder customerOrder = new CustomerOrder();
        customer.addCustomerOrder(customerOrder);

        final Timestamp orderDate = new Timestamp(System.currentTimeMillis());
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

            //eliminamos el CartItem
            customer.removeCartItem(cartItem);
        }
        return persistEntity(customerOrder);
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }
    
    public <T> T mergeEntity(T entity)
    {
        return em.merge(entity);
    }
    
    public <T> void removeEntity(T entity)
    {
        em.remove(em.merge(entity));
    }
    
    public <T> List<T> findAll(Class<T> entityClass)
    {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
    
    public <T> List<T> findAllByRange(Class<T> entityClass, int[] range)
    {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * select o from Customer o where o.email = :email Container Transaction
     * Attribute Not Supported: El contenedor EJB invoca el metodo con un
     * contexto de transaccion no especificado. Si un contexto de transaccion
     * esta disponible cuando el metodo es llamado, entonces el contenedor
     * dis-asocia la transaccion del hilo actual antes de invocar el metodo, y
     * luego re-asocia la transaccion con el hilo
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Customer> getCustomerFindByEmail(String email) {
        return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getResultList();
    }

    /**
     * select object(wime) from Wine where wine.country = :country
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }
}
