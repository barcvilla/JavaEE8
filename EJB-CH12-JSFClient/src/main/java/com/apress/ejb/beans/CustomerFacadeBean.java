/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.Customer;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Stateless session bean que provee metodos del negocio para consultar
 * informacion del cliente por email y operaciones CRUD. Este session bean permite al cliente registrarse el mismo
 * como miembro de la app.
 * Este Facade es injectada con un EntityManager via la anotacion @PersistenceContext que es
 * utilizado para realizar las operaciones CRUD
 *
 * @author PC
 */
@Stateless(name = "CustomerFacade")
public class CustomerFacadeBean implements CustomerFacadeLocal{
    
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;
    
    public CustomerFacadeBean(){}

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlSmt, int firstResult, int maxResult) {
        Query query = em.createNamedQuery(jpqlSmt);
        if(firstResult > 0)
        {
            query = query.setFirstResult(firstResult);
        }
        if(maxResult > 0)
        {
            query = query.setMaxResults(maxResult);
        }
        
        return query.getResultList();
    }

    @Override
    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public void removeCustomer(Customer customer) {
        customer = em.find(Customer.class, customer.getId());
        em.remove(customer);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Customer> getCustomerFindAll() {
        return em.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Customer getCustomerFindById(BigDecimal id) {
        return em.find(Customer.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Customer getCustomerFindByEmail(String email) {
        try
        {
            return em.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getSingleResult();
        }
        catch(NoResultException e)
        {
            return null;
        }
    }

    @Override
    public Customer registerCustomer(Customer customer) {
        return persistEntity(customer);
    }
    
}
