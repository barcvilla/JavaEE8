/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.test;

import com.apress.ejb.entities.Customer;
import com.apress.ejb.service.CustomerService;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author PC
 */
public class TestLocalEjb {
    @EJB
    private static CustomerService customerService;
    
    public static void main(String[] args) {
        List<Customer> customers = customerService.getCustomerFindAll();
        for(Customer customer : customers)
        {
            System.out.println(customer.getName());
        }
    }
}
