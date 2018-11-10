/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.mdb;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Message Driven Beans Class que escuchara el Message Topic y procesara el mensaje entrante. El mensaje recibido contendra
 * detalles para el cliente y sera utilizado para enviar una notificacion via email al cliente con respecto al status de su pedido
 * EJB Container utiliza la configuracion a nivel de clase para configurar el bean y enlazarlo al JMS provider apropiado.
 * propertyValue = javax.jms.Topic indica que el MDB estara escuchando un Topic y no un Queue
 * mappedName especificamos el nombre del destino fisico del topic
 * @author PC
 */
@MessageDriven(activationConfig = 
        {@ActivationConfigProperty(propertyName = "destinationName", propertyValue = "StatusMessageTopic"),
         @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")},
        mappedName = "StatusMessageTopic")
public class StatusMailerBean implements MessageListener{
    @Resource(name = "mail/wineappMail")
    private javax.mail.Session ms;
    /**
     * Metodo invocado por el EJB Container cuando llega un mensaje a la cola/topic el cual el MDB esta escuchando
     * Este metodo onMessage() contiene la logica de negocio de como procesar el mensaje entrante. 
     * @param message parametro de tipo Java Message Service
     */
    @Override
    public void onMessage(Message message)
    {
        try
        {
            /**
             * verificamos si message es de tipo MapMessage y obtenmos la info del cliente desde el mensaje, creamos un
             * mensaje email sobre el estado del pedido, y luego enviamos un email al cliente
             */
            if(message instanceof MapMessage)
            {
                MapMessage orderMessage = (MapMessage)message;
                String from = orderMessage.getStringProperty("from");
                String to = orderMessage.getStringProperty("to");
                String subject = orderMessage.getStringProperty("subject");
                String content = orderMessage.getStringProperty("context");
                // construimos el email
                javax.mail.Message msg = new MimeMessage(ms);
                msg.setFrom(new InternetAddress(from));
                InternetAddress[] address = {new InternetAddress(to)};
                msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new java.util.Date());
                msg.setContent(content, "text/html");
                System.out.println("MDB: Sending Message...");
                Transport.send(msg);
                System.out.println("MDB: Message Sent");
            }
            else
            {
                System.out.println("Invalid message");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
