/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.barcvilla.ejb;

import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.entities.test.PopulateDemoData;
import com.apress.ejb.chapter07.util.Ini;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Order Processor Bean Context Management Transaction
 * En este Ejb Stateless Session Bean declaramos el comportamiento Transactional. 
 * @author PC
 */
@Stateless(name = "OrderProcessorCMT")
public class OrderProcessorCMTBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Chapter08-TransactionSamples-JTA")
    private EntityManager em;
    
    /**
     * Eliminamos el cliente existente con el email wineapp@yahoo.com y cualquier vino existente con el coutry : United States
     * El contenedor EJB se encargara que este trabajo se realice en un contexto transactional.
     */
    public String initialize()
    {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("Removed ");
        int i = 0;
        
        /**
         * Filtramos los datos eliminando cualquier cliente con email: wineapp@yahoo.com o si esta definido en
         * el archivo de proppiedades user.properties.
         */
        Ini.loadProperties();
        for(Customer customer : getCustomerFindByEmail(Ini.TO_EMAIL_ADDRESS))
        {
            em.remove(customer);
            i++;
        }
        strBuf.append(i);
        strBuf.append(" wine(s)");
        return strBuf.toString();
    }
    
    /**
     * Creamos un CustomerOrder desde los items del Customer's cart. Creamos una nueva entidad CustomerOrder y luego
     * creamos una nueva entidad OrderItem para cada cartItem encontrado en el Customer's cart.
     * 
     * Usamos Context Management Transaction con el atributo por defecto REQUIRED, si este metodo es invocado sin un
     * contexto de transaccion, una nueva transaccion sera creada por el contenedor EJB sobre la invocacion del metodo
     * y commit sobre la terminacion satisfactoria del metodo
     * @return un mensaje de status (texto plano)
     */
    
    /**
     * select o from Customer o where o.email = :email
     * Container Transaction Attribute Not Supported: El contenedor EJB invoca el metodo con un contexto de transaccion
     * no especificado. Si un contexto de transaccion esta disponible cuando el metodo es llamado, entonces el
     * contenedor dis-asocia la transaccion del hilo actual antes de invocar el metodo, y luego re-asocia la transaccion
     * con el hilo
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Customer> getCustomerFindByEmail(String email)
    {
        return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getResultList();
    }
    
    /**
     * select object(wime) from Wine where wine.country = :country
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Wine> getWineFindByCountry(String country)
    {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }
}
