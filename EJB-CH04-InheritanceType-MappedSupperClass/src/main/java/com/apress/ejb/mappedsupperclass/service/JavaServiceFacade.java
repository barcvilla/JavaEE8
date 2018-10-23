/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.mappedsupperclass.service;

import com.apress.ejb.mappedsupperclass.entities.Address;
import com.apress.ejb.mappedsupperclass.entities.Employee;
import com.apress.ejb.mappedsupperclass.entities.FullTimeEmployee;
import com.apress.ejb.mappedsupperclass.entities.PartTimeEmployee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author PC
 */
public class JavaServiceFacade {

    private final EntityManager em;

    public JavaServiceFacade() {
        //  To support an non-JavaEE environment, we avoid injection and create an EntityManagerFactory
        //  for the desired persistence unit.  From this factory we then create the EntityManager.
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Chapter04-PersistenceIISamples-MappedSuperclass");
        em = emf.createEntityManager();
    }

    /**
     * All changes that have been made to the managed entities in the
     * persistence context are applied to the database and committed.
     */
    private void commitTransaction() {
        final EntityTransaction entityTransaction = em.getTransaction();
        if (!entityTransaction.isActive()) {
            entityTransaction.begin();
        }
        entityTransaction.commit();
    }

    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }
    
    // USING POLYMORPHIC JPQL QUERIES
    public List<Employee> queryCityEmployee(String jpqlStmt, String city)
    {
        TypedQuery<Employee> query = em.createQuery(jpqlStmt, Employee.class);
        return query.setParameter("city", city).getResultList();
    }
    
    //USING THE QUERY CRITERIA API
    public List<Address> getAddressFindByCity(String city)
    {
        //definimos una consulta que retorna objetos de tipo Address
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Address> criteriaQuery = criteriaBuilder.createQuery(Address.class);
        Root<Address> address = criteriaQuery.from(Address.class);
        
        // Add the Select  clause
        criteriaQuery.select(address);
        
        //definimos un predicado en la clausula WHERE para comparar la propiedad city con el valor del parametro
        ParameterExpression<String> p = criteriaBuilder.parameter(String.class, "city");
        criteriaQuery.where(criteriaBuilder.equal(address.get("city"), p));
        
        //enlazamos el parametro city
        TypedQuery<Address> query = em.createQuery(criteriaQuery);
        query.setParameter("city", city);
        
        // retornamos el query result como una List<Address>
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        commitTransaction();
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        entity = em.merge(entity);
        commitTransaction();
        return entity;
    }

    public void removeEmployee(Employee employee) {
        employee = em.find(Employee.class, employee.getId());
        em.remove(employee);
        commitTransaction();
    }

    /**
     * <
     * code>select o from Employee o</code>
     */
    public List<Employee> getEmployeeFindAll() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    public void removeFullTimeEmployee(FullTimeEmployee fullTimeEmployee) {
        fullTimeEmployee = em.find(FullTimeEmployee.class, fullTimeEmployee.getId());
        em.remove(fullTimeEmployee);
        commitTransaction();
    }

    /**
     * <
     * code>select o from FullTimeEmployee o</code>
     */
    public List<FullTimeEmployee> getFullTimeEmployeeFindAll() {
        return em.createNamedQuery("FullTimeEmployee.findAll", FullTimeEmployee.class).getResultList();
    }

    public void removePartTimeEmployee(PartTimeEmployee partTimeEmployee) {
        partTimeEmployee = em.find(PartTimeEmployee.class, partTimeEmployee.getId());
        em.remove(partTimeEmployee);
        commitTransaction();
    }

    /**
     * <
     * code>select o from PartTimeEmployee o</code>
     */
    public List<PartTimeEmployee> getPartTimeEmployeeFindAll() {
        return em.createNamedQuery("PartTimeEmployee.findAll", PartTimeEmployee.class).getResultList();
    }

    public void removeAddress(Address address) {
        address = em.find(Address.class, address.getId());
        em.remove(address);
        commitTransaction();
    }

    /**
     * <
     * code>select o from Address o</code>
     */
    public List<Address> getAddressFindAll() {
        return em.createNamedQuery("Address.findAll", Address.class).getResultList();
    }
}
