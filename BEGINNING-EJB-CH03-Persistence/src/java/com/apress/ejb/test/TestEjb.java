/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.test;

import com.apress.ejb.service.CustomerServiceRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author PC
 */
public class TestEjb {
    public static void main(String[] args) {
        System.out.println("Llamando al EJB desde el Cliente");
        try
        {
            Context jndi = new InitialContext();
            CustomerServiceRemote customerService = 
            (CustomerServiceRemote)jndi.lookup("java:global/BEGINNING-EJB-CH03-Persistence/CustomerServiceImpl!com.apress.ejb.service.CustomerServiceRemote");
            
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
        }
    }
}
