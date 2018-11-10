/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import java.io.Serializable;

/**
 *
 * @author PC
 */
public class PurchaseOrder implements Serializable{
    @SuppressWarnings("compatibility:6196334823835706012")
    private static final long serialVersionUID = -5933882909612696630L;
    
    private Customer customer;
    private CustomerOrder customerOrder;

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
