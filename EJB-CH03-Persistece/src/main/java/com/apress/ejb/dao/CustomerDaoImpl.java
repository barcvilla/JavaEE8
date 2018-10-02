/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class CustomerDaoImpl implements CustomerDao{
    // Usamos container injection para obtener una instancia del EntityManager
    @PersistenceContext(unitName = "rrhhpersistence")
    EntityManager em;
    
    @Override
    public void addCustomer(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return em.merge(customer);
    }

    @Override
    public void removeCustomer(Customer customer) {
        customer = em.find(Customer.class, customer.getId());
        em.remove(customer);
    }

    @Override
    public List<Customer> getCustomerFindAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    @Override
    public Customer getCustomerFindById(int id) {
        return em.createNamedQuery("Customer.findById", Customer.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public Customer getCustomerFindByEmail(String email) {
        return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();
    }
}
