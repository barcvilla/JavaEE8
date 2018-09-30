/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.service;

import com.apress.ejb.dao.OrderManagementDao;
import com.apress.ejb.entities.CustomerOrder;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class OrderManagementServiceImpl implements OrderManagementService{
    @EJB
    private OrderManagementDao orderManagementDao;

    @Override
    public List<CustomerOrder> getCustomerOrderFindAll() {
        return orderManagementDao.getCustomerOrderFindAll();
    }

    @Override
    public CustomerOrder getCustomerOrderFindById(int id) {
        return orderManagementDao.getCustomerOrderFindById(id);
    }
    
    
}
