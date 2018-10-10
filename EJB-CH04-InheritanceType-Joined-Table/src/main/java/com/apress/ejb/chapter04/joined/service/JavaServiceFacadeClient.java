/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter04.joined.service;

import com.apress.ejb.joined.entities.Address;
import com.apress.ejb.joined.entities.Employee;
import com.apress.ejb.joined.entities.FullTimeEmployee;
import com.apress.ejb.joined.entities.PartTimeEmployee;
import com.apress.ejb.joined.entities.Person;
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
    
    //print employees
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
    
    public static void printFullTimeEmployee(FullTimeEmployee fullTimeEmployee)
    {
        System.out.println("Annual Saalry: " + fullTimeEmployee.getAnnualSalary());
        System.out.println("Managed Employees: " + fullTimeEmployee.getManagedEmployees());
        System.out.println("Department: " + fullTimeEmployee.getDepartment());
        System.out.println("Email: " + fullTimeEmployee.getEmail());
        System.out.println("Manager: " + fullTimeEmployee.getManager());
        System.out.println("Firt Name: " + fullTimeEmployee.getFirstName());
        System.out.println("Id: " + fullTimeEmployee.getId());
        System.out.println("Last Name: " + fullTimeEmployee.getLastName());
        System.out.println("Version: " + fullTimeEmployee.getVersion());
        System.out.println("Home Address: " + fullTimeEmployee.getHomeAddress());
    }
    
    public static void printPartTimeEmployee(PartTimeEmployee partTimeEmployee)
    {
        System.out.println("HourlyWage: " + partTimeEmployee.getHourlyWage());
        System.out.println("Department: " + partTimeEmployee.getDepartment());
        System.out.println("Email: " + partTimeEmployee.getEmail());
        System.out.println("Manager: " + partTimeEmployee.getManager());
        System.out.println("Firt Name: " + partTimeEmployee.getFirstName());
        System.out.println("Id: " + partTimeEmployee.getId());
        System.out.println("Last Name: " + partTimeEmployee.getLastName());
        System.out.println("Version: " + partTimeEmployee.getVersion());
        System.out.println("Home Address: " + partTimeEmployee.getHomeAddress());
    }
    
    public static void printPerson(Person person)
    {
        System.out.println("First Name: " + person.getFirstName());
        System.out.println("Id: " + person.getId());
        System.out.println("Last Name: " + person.getLastName());
        System.out.println("Version: " + person.getVersion());
        System.out.println("Home Address: " + person.getHomeAddress());
    }
    
    public static void printAddress(Address address)
    {
        System.out.println("City: " + address.getCity());
        System.out.println("Id: " + address.getId());
        System.out.println("State: " + address.getState());
        System.out.println("Street 1: " + address.getStreet1());
        System.out.println("Street 2: " + address.getStreet2());
        System.out.println("Version: " + address.getVersion());
        System.out.println("zipCode: " + address.getZipCode());
    }
    
    public static void main(String[] args) {
        deleteData();
        //createFullAndPartTimeEmployees();
        /**
        try
        {
            System.out.println("\nPersons: \n");
            for (Person person : (List<Person>) javaServiceFacade.getPersonFindAll()) {
                printPerson(person);
            }

            System.out.println("\nEmployees: \n");
            for (Employee employee : (List<Employee>) javaServiceFacade.getEmployeeFindAll()) {
                printEmployee(employee);
            }

            System.out.println("\nPartTimeEmployees: \n");
            for (PartTimeEmployee partTimeEmployee : (List<PartTimeEmployee>) javaServiceFacade.getPartTimeEmployeeFindAll()) {
                printPartTimeEmployee(partTimeEmployee);
            }

            System.out.println("\nFullTimeEmployees: \n");
            for (FullTimeEmployee fullTimeEmployee : (List<FullTimeEmployee>) javaServiceFacade.getFullTimeEmployeeFindAll()) {
                printFullTimeEmployee(fullTimeEmployee);
            }

            System.out.println("\nAddress: \n");
            for (Address address : (List<Address>) javaServiceFacade.getAddressFindAll()) {
                printAddress(address);
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        */
    }
}
