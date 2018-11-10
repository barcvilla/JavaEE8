/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.client;

import com.apress.ejb.entities.Employee;
import com.apress.ejb.servicio.EmployeeServiceRemote;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author PC
 */
public class EmployeeServiceClient {
       
    private static void printEmployee(Employee employee)
    {
        System.out.println("Department :" + employee.getDepartment());
        System.out.println("Email: " + employee.getEmail());
        System.out.println("Manager: " + employee.getManager());
        System.out.println("First Name: " + employee.getFirstName());
        System.out.println("Id: " + employee.getId());
        System.out.println("Last Name: " + employee.getLastName());
        System.out.println("Version: " + employee.getVersion());
        System.out.println("Home Address: " + employee.getHomeAddress());
    }
    
    public static void main(String[] args) 
    { 
        try
        {
            
            Context jndi = new InitialContext();
            EmployeeServiceRemote employeeService = (EmployeeServiceRemote)
                    jndi.lookup("java:global/EJB-CH06-WebService/EmployeeServiceImpl!com.apress.ejb.servicio.EmployeeServiceRemote");
            
            for(Employee employee : (List<Employee>) employeeService.listarEmployees())
            {
                printEmployee(employee);
            }
        }
        catch(NamingException ex)
        {
            ex.printStackTrace();
        }
    }
}
