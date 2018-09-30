/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.service;

import com.apress.ejb.entities.Customer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CustomerService {
    public void addCustomer(Customer customer);
    public Customer updateCustomer(Customer customer);
    public void removeCustomer(Customer customer);
    public List<Customer> getCustomerFindAll();
    public Customer getCustomerFindById(int id);
    public Customer getCustomerFindByEmail(String email);
    public Customer getCustomerFindByIdWithOrders(int id);
}
