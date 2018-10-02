/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.service;

import com.apress.ejb.dao.CustomerDao;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.CustomerOrder;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class CustomerServiceImpl implements CustomerService{
    
    @EJB
    private CustomerDao customerDao;
    
    @Resource
    private SessionContext context;

    @Override
    public void addCustomer(Customer customer) {
        try
        {
            customerDao.addCustomer(customer);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        Customer cust = null;
        try
        {
            cust = customerDao.updateCustomer(customer);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
        return cust;
    }

    @Override
    public void removeCustomer(Customer customer) {
        try
        {
            customerDao.removeCustomer(customer);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
    }

    @Override
    public List<Customer> getCustomerFindAll() {
        return customerDao.getCustomerFindAll();
    }

    @Override
    public Customer getCustomerFindById(int id) {
        return customerDao.getCustomerFindById(id);
    }

    @Override
    public Customer getCustomerFindByEmail(String email) {
        return customerDao.getCustomerFindByEmail(email);
    }
}
