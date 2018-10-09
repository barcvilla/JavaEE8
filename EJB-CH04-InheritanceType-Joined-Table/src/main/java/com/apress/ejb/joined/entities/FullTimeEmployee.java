/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.joined.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "ch04_join_ft_employee")
@NamedQueries({@NamedQuery(name = "FullTimeEmployee.findAll", query = "select fte from FullTimeEmployee fte")})
public class FullTimeEmployee extends Employee implements Serializable{
    private static final long serialVersionUID = -7301681120809804802L;
    
    @Column(name = "annual_salary")
    private double annualSalary;
    
    /**
     * Como ya hemos especificado el mapeo, este campo puede simplemente referencia al campo manager usando mappedBy = "manager"
     * De esta forma, ambos campos relacionados son mapeados a la misma llave foranea y un join table no es necesario.
     * cascade = {CascadeType.PERSIST, CascadeType.MERGE} indica que cualquier operacion persist o merge realizada en esta
     * entidad, Employee, debe tambien aplicarse a cualquier instancia referenciada por este campo de relacion.
     * Por ejemplo, Si una  nueva instancia de empleado es creada y asignada a FullTimeEmpoyee como su manager, el acto de
     * persistir la instancia del Empleado por el EntityManager.persist() tambien causara que cualquier instancia de referencia
     * de FullTimeEmployee sea persistida tambien, si ellos no han sido ya persistido.
     */
    @OneToMany(mappedBy = "manager", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Employee> managedEmployees;
    
    public FullTimeEmployee(){}

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public List<Employee> getManagedEmployees() {
        return managedEmployees;
    }

    public void setManagedEmployees(List<Employee> managedEmployees) {
        this.managedEmployees = managedEmployees;
    }
    
    public Employee addEmployee(Employee employee)
    {
        getManagedEmployees().add(employee);
        employee.setManager(this);
        return employee;
    }
    
    public Employee removeEmployee(Employee employee)
    {
        getManagedEmployees().remove(employee);
        employee.setManager(null);
        return employee;
    }
}
