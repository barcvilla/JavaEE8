/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CustomerFacadeLocal {
    public Object queryByRange(String jpqlSmt, int firstResult, int maxResult);
    public <T> T persistEntity(T entity);
    public <T> T mergeEntity(T entity);
    public void removeCustomer(Customer customer);
    public List<Customer> getCustomerFindAll();
    public Customer getCustomerFindById(BigDecimal id);
    public Customer getCustomerFindByEmail(String email);
    public Customer registerCustomer(Customer customer);
}
