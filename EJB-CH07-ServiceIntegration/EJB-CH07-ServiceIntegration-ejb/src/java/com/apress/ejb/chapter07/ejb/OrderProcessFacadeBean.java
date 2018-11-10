/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.ejb.client.CreditCheckEndpointBean;
import com.apress.ejb.chapter07.ejb.client.CreditService;
import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.Distributor;
import com.apress.ejb.chapter07.entities.Individual;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Wine;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.ws.WebServiceRef;

/**
 * OrderProcessFacadeBean actua como un coordiandor entre los servicios de order processing y credit service. Este componente
 * procesa los cart items y crea un Purchase Order que puede ser consumido por el servicio order processing.
 * 
 * Este Stateless session bean provee metodos de negocio que son invocados por
 * el ShoppingCartBean para someter un pedido y otros metodos que interactuan
 * los servicios de credit y procesamiento de pedidos.
 *
 * @author PC
 */
@Stateless(name = "OrderProcessFacade", mappedName = "OrderProcessFacade")
public class OrderProcessFacadeBean {

    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;

    @Resource(mappedName = "poTopicConnectionFactory")
    private TopicConnectionFactory poTopicCF;

    @Resource(mappedName = "PurchaseOrderTopic")
    private Topic poTopic;

    @WebServiceRef(type = CreditService.class)
    CreditService service;

    public Object mergeEntity(Object entity) {
        return em.merge(entity);
    }

    public Object persistEntity(Object entity) {
        em.persist(entity);
        return entity;
    }

    public void createNewOrder(CustomerOrder newOrder) {
        persistEntity(newOrder);
    }

    private boolean performCreditCheck(Individual individual) {
        String ccnum = individual.getCcNum().toString();
        CreditCheckEndpointBean creditService = service.getCreditCheckEndpointBeanPort();
        return creditService.creditCheck(ccnum);
    }
    
    /**
     * ShoppingCartBean llama este metodo de negocio cuando una orden es sometida por la app client. Este metodo tiene un 
     * parametro, el cual es una entidad de tipo Customer. El shoppin cart del cliente esta contenido en la list cartItems,
     * cada miembro de esta lista hace referencia a un wine y una cantidad adicionada al cart por el cliente mientra compra.
     * Al inicio, el metodo verifica si la entidad recibida esta administrada por el entity manager, si no lo es, se llama al
     * metodo merge() del entity manager el cual se encarga de recuperar un objeto Customer managed
     * 
     * Una vez que se obtiene el customer, se verifica su el objeto customer es de tipo Individual o Distributor. Para los de tipo
     * Individual se hace una llamada al metodo performCreditCheck() para verificar el credito de la tarjeta. Si la tarjeta
     * resulta ser invalida, se envia el mensaje Tarjeta de credito invalida al client app via ShoppingCartBean. 
     * En el caso que el objeto Customer sea de tipo Distributor, la propiedad memberStatus es verificada, y cualquier otro valor
     * distinto a APPROVED se rechaza.
     * 
     * Si el cliente es aprovado, el procesamiento continua, se crea un nuevo objeto CustomerOrder, este nuevo pedido es poblado
     * con la coleccion de objetos CartItem asociado al cliente y encontrado en su propiedad cartItems. Por cada cartItem un
     * objeto OrderItem es creado y se captura la cantidad de cada wine y el precio total para el wine en el cartItem. El precio
     * de cada OrderItem es calculado utilizando el retail price disponible en cada entidad wine.
     * Una vez que todos los cartItems han sido procesados y se han creado los respectivos OrderItems, la nueva CustomerOrder
     * que contiene a los OrderItems es persistido. El metodo removeCartItem es invocado para eliminar cada cartItem dentro
     * del objeto Customer.
     * 
     * La clase utilitaria PurchaseOrder no es una entidad, el cual asocia un objeto Customer con un CustomerOrder especifico.
     * Esta instancia PurchaseOrder es pasado como un argumento al metodo sendPOtoMDB(), enviando el purchase order al 
     * processing service. Una vez que la llamada es hecha, el proceso es asyncrono, y un mensaje es enviado de regreso al
     * client app diciendo que el pedido ha sido enviado para su procesamiento.
     * 
     * @param customer
     * @return 
     */
    public String processOrder(Customer customer) {
        String processStatus = null;
        if (!em.contains(customer)) {
            customer = em.merge(customer);
        }

        if (customer instanceof Individual) {
            if (!performCreditCheck((Individual) customer)) {
                processStatus = "Invalid credit card number or credit check failed";
            }
        } else if (customer instanceof Distributor) {
            if (!"APPROVED".equals(((Distributor) customer).getMemberStatus())) {
                processStatus = "Distributor credit check rejected";
            }
        }

        if (processStatus == null) {
            CustomerOrder order = new CustomerOrder();
            order.setCreationDate(new Timestamp(System.currentTimeMillis()));
            em.persist(order);

            List<CartItem> cartItems = customer.getCartItemList();
            if (cartItems != null) {
                List<CartItem> tempCartItems = new ArrayList<CartItem>();
                for (CartItem cItem : cartItems) {
                    OrderItem oItem = new OrderItem();
                    int qty = cItem.getQuantity();
                    oItem.setQuantity(qty);
                    oItem.setOrderDate(new Timestamp(System.currentTimeMillis()));
                    oItem.setWine(cItem.getWine());
                    Wine tempWine = cItem.getWine();
                    Float d = tempWine.getRetailPrice();
                    Float price = d * cItem.getQuantity();
                    oItem.setPrice(price);
                    order.addOrderItem(oItem);
                    tempCartItems.add(cItem);
                }
                for (CartItem cartItem : tempCartItems) {
                    customer.removeCartItem(cartItem);
                    em.remove(cartItem);
                }
            }
            customer.addCustomerOrder(order);
            PurchaseOrder po = new PurchaseOrder();
            po.setCustomerOrder(order);
            sendPOtoMDB(po);
            processStatus = "Purchase Order sent for processing to the process queue";
        }
        return processStatus;
    }
    
    /**
     * Este metodo hace uso del recurso JMS inyectado para un topic connection factory y un topic. Una conexion a un topic
     * connection factory es creado y la conexion es iniciada. Una vez que la conexion esta disponible, una session es
     * creada y un MessageProducer es creado con un topic. Un objeto ObjectMessage es creado para tomar un objeto
     * PurchaseOrder y el MessageProducer es usado para enviar el PurchaseOrder al topic
     * @param po 
     */
    private void sendPOtoMDB(PurchaseOrder po) {
        //send PO to MDB now
        Connection connection = null;
        Session session = null;
        try {
            connection = poTopicCF.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(poTopic);
            ObjectMessage objMessage = session.createObjectMessage();
            objMessage.setObject(po);
            producer.send(objMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    Logger.getLogger(OrderProcessFacadeBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(OrderProcessFacadeBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
