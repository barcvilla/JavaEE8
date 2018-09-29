/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.CustomerOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class OrderManagementDaoImpl implements OrderManagementDao{
    
    @PersistenceContext(name = "persistence")
    EntityManager em;
    
    @Override
    public List<CustomerOrder> getCustomerOrderFindAll() {
        return em.createNamedQuery("CustomerOrder.findAll", CustomerOrder.class).getResultList();
    }

    @Override
    public CustomerOrder getCustomerOrderFindById(int id) {
        return em.createNamedQuery("CustomerOrder.findById", CustomerOrder.class).setParameter("id", id).getSingleResult();
    }
    
}
