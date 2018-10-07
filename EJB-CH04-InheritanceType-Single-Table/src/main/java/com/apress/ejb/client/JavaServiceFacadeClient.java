/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.client;

import com.apress.ejb.entities.Address;
import com.apress.ejb.entities.FullTimeEmployee;
import com.apress.ejb.entities.PartTimeEmployee;
import com.apress.ejb.service.JavaServiceFacade;
import java.util.List;

/**
 *
 * @author PC
 */
public class JavaServiceFacadeClient {
    
    static final JavaServiceFacade javaServiceFacade = new JavaServiceFacade();
    
    /**
     * Eliminamos cualquier dato previo. Debido a las reglas de cascada en Person.homeAddress, remover una persona
     * removera el address tambien
     */
    public static void deleteData()
    {
        try
        {
            
            for(PartTimeEmployee partTimeEmployee : (List<PartTimeEmployee>)javaServiceFacade.getPartTimeEmployeeFindAll())
            {
                javaServiceFacade.removePartTimeEmployee(partTimeEmployee);
            }
            
            for(FullTimeEmployee fullTimeEmployee : (List<FullTimeEmployee>)javaServiceFacade.getFullTimeEmployeeFindAll())
            {
                javaServiceFacade.removeFullTimeEmployee(fullTimeEmployee);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Creamos instancias de FullTimeEmployee, PartTimeEmployee, con su objeto Address, y los guardamos en la BD
     */
    public static void createFullAndPartTimeEmployees()
    {
        try
        {
            Address address = new Address();
            address.setCity("San Mateo");
            address.setState("CA");
            address.setStreet1("1301 Aswood Ct");
            address.setZipCode("94402");
            javaServiceFacade.persistEntity(address);
            
            FullTimeEmployee fullTimeEmployee = new FullTimeEmployee();
            fullTimeEmployee.setAnnualSalary(1000D);
            fullTimeEmployee.setDepartment("HQ");
            fullTimeEmployee.setEmail("x@y.com");
            fullTimeEmployee.setFirstName("Brian");
            fullTimeEmployee.setLastName("Jones");
            fullTimeEmployee.setHomeAddress(address);
            fullTimeEmployee = javaServiceFacade.persistEntity(fullTimeEmployee);
            
            address.setCity("San Francisco");
            address.setState("CA");
            address.setStreet1("53 Surrey St");
            address.setZipCode("94131");
            javaServiceFacade.persistEntity(address);
            
            PartTimeEmployee partTimeEmployee = new PartTimeEmployee();
            partTimeEmployee.setHourlyWage(100D);
            partTimeEmployee.setDepartment("SALES");
            partTimeEmployee.setEmail("a@b.com");
            partTimeEmployee.setFirstName("David");
            partTimeEmployee.setLastName("Holmes");
            partTimeEmployee.setHomeAddress(address);
            partTimeEmployee.setManager(fullTimeEmployee);
            javaServiceFacade.persistEntity(partTimeEmployee);
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        deleteData();
        //createFullAndPartTimeEmployees();
    }
}
