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

/**
 *
 * @author PC
 */
public interface EmployeeDao {
    public List<Employee> listarEmployees();
    public List<FullTimeEmployee> listarFullTimeEmployees();
    public List<PartTimeEmployee> listarPartTimeEmployees();
}
