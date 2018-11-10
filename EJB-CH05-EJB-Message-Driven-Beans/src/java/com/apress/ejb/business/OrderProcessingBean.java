/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.business;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

/**
 *
 * @author PC
 */
@Stateless(name = "OrderProcessing")
public class OrderProcessingBean {
    public OrderProcessingBean(){}
    
    @Resource(mappedName = "StatusMessageTopicConnectionFactory")
    private TopicConnectionFactory statusMesssageTopicCF;
    
    @Resource(mappedName = "StatusMessageTopic")
    private Topic statusTopic;
    
    public String SendOrderStatus()
    {
        String from = "ceva_19@hotmail.com";
        String to = "barcvilla@gmail.com";
        String content = "Your order has been processed. " + "If you have question" + " call EJB Application with Order id: PO123456789";
        
        try
        {
            System.out.println("Before status TopicCF Connection");
            Connection connection = statusMesssageTopicCF.createConnection();
            System.out.println("Created connection");
            connection.start();
            System.out.println("started connection");
            System.out.println("Starting Topic Session");
            Session topicSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            MessageProducer publisher =  topicSession.createProducer(statusTopic);
            System.out.println("created producer");
            MapMessage message = topicSession.createMapMessage();
            message.setStringProperty("from", from);
            message.setStringProperty("to", to);
            message.setStringProperty("subject", "status of you wine order");
            message.setStringProperty("content", content);
            System.out.println("before send");
            publisher.send(message);
            System.out.println("after send");
        }
        catch(JMSException e)
        {
            e.printStackTrace();
        }
        
        return "Created a MapMessage and sent it to StatusTopic";
    }
}
