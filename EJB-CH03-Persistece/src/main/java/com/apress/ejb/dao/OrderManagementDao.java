/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.CustomerOrder;
import java.util.List;

/**
 *
 * @author PC
 */
public interface OrderManagementDao {
    public List<CustomerOrder> getCustomerOrderFindAll();
    public CustomerOrder getCustomerOrderFindById(int id);
}
