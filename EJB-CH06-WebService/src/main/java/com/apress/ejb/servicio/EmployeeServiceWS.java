/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.servicio;

import com.apress.ejb.entities.Employee;
import com.apress.ejb.entities.FullTimeEmployee;
import com.apress.ejb.entities.PartTimeEmployee;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author PC
 */
@WebService
public interface EmployeeServiceWS {
    @WebMethod
    public List<Employee> listarEmployees();
    
    @WebMethod
    public List<FullTimeEmployee> listarFullTimeEmployees();
    
    @WebMethod
    public List<PartTimeEmployee> listarPartTimeEmployees();
}
