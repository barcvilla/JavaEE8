/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.test;

import com.apress.ejb.entities.Customer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PC
 */
public class Test {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ConsolaJpqlPU");
    private static final EntityManager em = emf.createEntityManager();
    
    public static void main(String[] args) {
        String jpql = "select c from Customer c";
        List<Customer> customers = em.createQuery(jpql).getResultList();
        for(Customer customer : customers)
        {
            System.out.println(customer.getName());
        }
    }
}
