/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.joined.service;

import com.apress.ejb.joined.entities.Address;
import com.apress.ejb.joined.entities.Employee;
import com.apress.ejb.joined.entities.FullTimeEmployee;
import com.apress.ejb.joined.entities.PartTimeEmployee;
import com.apress.ejb.joined.entities.Person;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
public class JavaServiceFacade {
    private final EntityManager em;
    
    public JavaServiceFacade()
    {
        /**
         * Para soportar un no ambiente JavaEE, evitamos injection y creamos un EntityManagerFactory
         */
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04-PersistenceIISamples-Joined");
        em = emf.createEntityManager();
    }
    
    /**
     * Todos los cambios que han sido realizados a las entidades administradas en el contexto de persistencia
     * son aplicados a la BD y commited
     */
    private void commitTransaction()
    {
        final EntityTransaction entityTransaction = em.getTransaction();
        if(!entityTransaction.isActive())
        {
            entityTransaction.begin();
        }
        entityTransaction.commit();
    }
    
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults)
    {
        Query query = em.createQuery(jpqlStmt);
        if(firstResult > 0)
        {
            query = query.setFirstResult(firstResult);
        }
        if(maxResults > 0)
        {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
    
    public <T> T persistEntity(T entity)
    {
        em.persist(entity);
        commitTransaction();
        return entity;
    }
    
    public <T> T mergeEntity(T entity)
    {
        entity = em.merge(entity);
        commitTransaction();
        return entity;
    }
    
    public void removeEmployee(Employee employee)
    {
        employee = em.find(Employee.class, employee.getId());
        em.remove(employee);
        commitTransaction();
    }
    
    public List<Employee> getEmployeeFindAll()
    {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }
    
    public void removeFullTimeEmployee(FullTimeEmployee fullTimeEmployee)
    {
        fullTimeEmployee =  em.find(FullTimeEmployee.class, fullTimeEmployee.getId());
        em.remove(fullTimeEmployee);
        commitTransaction();
    }
    
    public List<FullTimeEmployee> getFullTimeEmployeeFindAll()
    {
        return em.createNamedQuery("FullTimeEmployee.findAll", FullTimeEmployee.class).getResultList();
    }
    
    public void removePartTimeEmployee(PartTimeEmployee partTimeEmployee)
    {
        partTimeEmployee = em.find(PartTimeEmployee.class, partTimeEmployee.getId());
        em.remove(partTimeEmployee);
        commitTransaction();
    }
    
    public List<PartTimeEmployee> getPartTimeEmployeeFindAll()
    {
        return em.createNamedQuery("PartTimeEmployee.findAll", PartTimeEmployee.class).getResultList();
    }
    
    public void removePerson(Person person)
    {
        person = em.find(Person.class, person.getId());
        em.remove(person);
        commitTransaction();
    }
    
    public List<Person> getPersonFindAll()
    {
        return em.createNamedQuery("Person.findAll", Person.class).getResultList();
    }
    
    public void removeAddress(Address address)
    {
        address = em.find(Address.class, address.getId());
        em.remove(address);
        commitTransaction();
    }
    
    public List<Address> getAddressFindAll()
    {
        return em.createNamedQuery("Address.findAll", Address.class).getResultList();
    }
}
