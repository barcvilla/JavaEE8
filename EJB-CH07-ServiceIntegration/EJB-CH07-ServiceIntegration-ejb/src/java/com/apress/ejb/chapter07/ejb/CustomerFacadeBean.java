/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Stateless session bean que provee metodos del negocio para consultar
 * informacion del cliente por email y operaciones CRUD. Este session bean permite al cliente registrarse el mismo
 * como miembro de la app.
 * Este Facade es injectada con un EntityManager via la anotacion @PersistenceContext que es
 * utilizado para realizar las operaciones CRUD
 *
 * @author PC
 */
@Stateless
public class CustomerFacadeBean {

    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    public void removeCustomer(Customer customer) {
        customer = em.find(Customer.class, customer.getId());
        em.remove(customer);
    }

    public List<Customer> getCustomerFindAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    public Customer getCustomerFindById(Integer id) {
        return em.find(Customer.class, id);
    }

    public Customer getCustomerFindByEmail(String email) {
        return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();
    }

    public Customer registerCustomer(Customer customer) {
        return persistEntity(customer);
    }
}
