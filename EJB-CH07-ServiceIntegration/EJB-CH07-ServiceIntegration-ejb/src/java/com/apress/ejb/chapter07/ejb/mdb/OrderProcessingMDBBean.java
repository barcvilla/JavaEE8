/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb.mdb;

import com.apress.ejb.chapter07.ejb.PurchaseOrder;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.InventoryItem;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.entities.test.PopulateDemoData;
import com.apress.ejb.chapter07.util.Ini;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * OrderProcessing es un MDB (Message Driven Beans). La idea de tener un MDB en
 * esta app es mostrar algunos procesamientos dentro de una aplicacion
 * empresarial puede ser hecho de una manera asincrona, y como se puede trabajar
 * con el EntityManager, Session beans, y otros MDB desde un MDB
 *
 * @author PC
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")},
        mappedName = "PurchaseOrderTopic")
public class OrderProcessingMDBBean implements MessageListener {

    private PurchaseOrder po;
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;
    @Resource(mappedName = "StatusMessageTopicConnectionFactory")
    private TopicConnectionFactory statusMessageTopicCF;
    @Resource(mappedName = "StatusMessageTopic")
    private Topic statusTopic;

    /**
     * onMessage procesa el mensaje entrante con la ayuda del metodo utilitario
     * processOrder().
     *
     * @param message
     */
    public void onMessage(Message message) {
        try {
            // verificamos si el mensaje recibido es un objeto de tipo ObjectMessage
            if (message instanceof ObjectMessage) {
                ObjectMessage objMessage = (ObjectMessage) message;
                // recuperamos el objeto
                Object obj = objMessage.getObject();
                // verificamos si el objeto es de tipo purchaseOrder
                if (obj instanceof PurchaseOrder) {
                    // typecast a PurchaseOrder
                    po = (PurchaseOrder) obj;
                    processOrder(po);
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param po 
     */
    private void processOrder(PurchaseOrder po) {
        // Obtenemos el customer y el CustomerOrder
        Customer customer = po.getCustomer();
        CustomerOrder order = po.getCustomerOrder();
        
        //Procesamos cada linea del CustomerOrder
        for (OrderItem oItem : order.getOrderItemList()) {
            Wine wine = oItem.getWine();
            int qty = oItem.getQuantity();
            // actualizamos el stock de vinos en la base de datos
            deductInventory(wine, qty);
        }
        
        //Envio del status del pedido actualizado al cliente via email. 
        //Ini.loadProperties();
        String from = Ini.FROM_EMAIL_ADDRESS;
        // recuperamose el email de cliente y el id del pedido
        String to = customer.getEmail();
        //construimos el cuerpo del email
        String content
                = "Your order has been processed. "
                + "If you have questions call Beginning EJB Wine Store "
                + "Application with order id # "
                + po.getCustomerOrder().getId().toString();
        // enviamos el mensaje
        sendStatus(from, to, content);
    }
    
    /**
     * Hacemos uso del EntityManager inyectado y llamamos al query name Inventory.findItemByItem definido en la entidad
     * InventoryItem. Una vez que se recupera el inventario para un wine especifico, la cantidad es actualizada usando
     * el metodo setter setQuantity(). El inventory item es administrado,asi, sus modificaciones estan sincronizadas con la
     * base de datos
     * @param tempWine
     * @param deductQty 
     */
    private void deductInventory(Wine tempWine, int deductQty) {
        InventoryItem iItem
                = em.createNamedQuery("InventoryItem.findItemByWine",
                        InventoryItem.class).setParameter("wine", tempWine).getSingleResult();
        int newQty = iItem.getQuantity() - deductQty;
        iItem.setQuantity(newQty);
    }
    
    /**
     * Este metodo hace uso del recursos inyectado JSM para un topic connection factory y un topic. Una conexion a un topic
     * connection factory es creado y la conexion es iniciada. Una vez que una conexion esta disponible, una session es
     * creada, y un MessageProducer que contiene el topic es creado. Un objeto Message es creado, y el JMSType es 
     * configurado a MailMessage. Despues de eso, una serie de llamadas al setStringProperty() sobre el objeto Message
     * para poder crear las secciones to, from, subject y content del email. Una vez que todas las propiedades estas
     * configuradas, el message es enviado al message topic que sera procesado por el email service.
     * @param from
     * @param to
     * @param content 
     */
    private void sendStatus(String from, String to, String content) {
        try {
            System.out.println("Before status TopicCF connection");
            Connection connection = statusMessageTopicCF.createConnection();
            System.out.println("Created connection");
            connection.start();
            System.out.println("Started connection");
            System.out.println("Starting Topic Session");
            Session topicSession
                    = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageProducer publisher = topicSession.createProducer(statusTopic);
            System.out.println("created producer");
            MapMessage message = topicSession.createMapMessage();
            message.setStringProperty("from", from);
            message.setStringProperty("to", to);
            message.setStringProperty("subject", "Status of your wine order");
            message.setStringProperty("content", content);
            System.out.println("before send");
            publisher.send(message);
            System.out.println("after send");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
