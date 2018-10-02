/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.CustomerOrder;
import java.util.List;

/**
 *
 * @author PC
 */
public interface CustomerDao {
    public void addCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public void removeCustomer(Customer customer);
    public List<Customer> getCustomerFindAll();
    public Customer getCustomerFindById(int id);
    public Customer getCustomerFindByEmail(String email);
}
