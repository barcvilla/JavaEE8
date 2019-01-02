/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.CustomerOrder;
import java.io.Serializable;

/**
 *
 * @author PC
 */
public class PurchaseOrder implements Serializable{
    private Customer customer;
    private CustomerOrder customerOrder;
    
    public PurchaseOrder(){}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }
    
    
}
