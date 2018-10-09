/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.joined.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@NamedQueries({@NamedQuery(name = "PartTimeEmployee.findAll", query = "select pte from PartTimeEmployee pte")})
@Table(name = "ch04_join_pt_employee")
public class PartTimeEmployee extends Employee implements Serializable{
    private static final long serialVersionUID = 4017999239159878209L;
    
    @Column(name = "hourly_wage")
    private double hourlyWage;
    
    public PartTimeEmployee(){}

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
    
    
}
