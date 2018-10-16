/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter04.tableperclass;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "ch04_tpc_employee")
@NamedQueries({@NamedQuery(name = "Employee.findAll", query = "select e from Employee e")})
public abstract class Employee extends Person implements Serializable{
    private static final long serialVersionUID = -8529011412038476148L;
    @Column(length = 400)
    private String department;
    
    @Column(length = 400)
    private String email;
    
    /**
     * manager es de tipo FullTimeEmployee y mapea la columna manager. La columna manager pasa a ser una llave foranea que
     * referencia a la tabla mapeada por la entidad FullTypeEmployee.
     * La entidad al otro lado (FullTimeEmployee) de esta relacion bi-direccional, contiene el campo managedEmployees
     */
    @ManyToOne
    @JoinColumn(name = "manager")
    private FullTimeEmployee manager;
    
    public Employee(){}

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FullTimeEmployee getManager() {
        return manager;
    }

    public void setManager(FullTimeEmployee manager) {
        this.manager = manager;
    }
}
