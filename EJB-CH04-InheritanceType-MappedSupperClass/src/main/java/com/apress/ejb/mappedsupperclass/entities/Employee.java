/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.mappedsupperclass.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "select o from Employee o")})
@Table(name = "ch04_ms_employee")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee extends Person implements Serializable {

    private static final long serialVersionUID = -8529011412038476148L;
    @Column(length = 400)
    private String department;
    @Column(length = 400)
    private String email;
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
