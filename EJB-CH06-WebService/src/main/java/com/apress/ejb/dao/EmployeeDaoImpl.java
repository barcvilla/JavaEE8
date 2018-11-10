/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.Employee;
import com.apress.ejb.entities.FullTimeEmployee;
import com.apress.ejb.entities.PartTimeEmployee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeDaoImpl implements EmployeeDao{
    
    @PersistenceContext(unitName = "persistence") // llamamos a nuestro Persistance Unit del archivo persistance.xml
    EntityManager em; // inyectamos el Persistance Unit a em de tipo EntityManager

    @Override
    public List<Employee> listarEmployees() {
        return em.createNamedQuery("Employee.findAll", Employee.class).getResultList();
    }

    @Override
    public List<FullTimeEmployee> listarFullTimeEmployees() {
        return em.createNamedQuery("FullTimeEmployee.findAll", FullTimeEmployee.class).getResultList();
    }

    @Override
    public List<PartTimeEmployee> listarPartTimeEmployees() {
        return em.createNamedQuery("PartTimeEmployee.findAll", PartTimeEmployee.class).getResultList();
    }
    
}
