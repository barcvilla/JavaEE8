/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter04.tableperclass.service;

import com.apress.ejb.chapter04.tableperclass.Address;
import com.apress.ejb.chapter04.tableperclass.Employee;
import com.apress.ejb.chapter04.tableperclass.FullTimeEmployee;
import com.apress.ejb.chapter04.tableperclass.PartTimeEmployee;
import com.apress.ejb.chapter04.tableperclass.Person;
import java.util.List;

/**
 *
 * @author PC
 */
public class JavaServiceFacadeClient {
  static final JavaServiceFacade javaServiceFacade = new JavaServiceFacade();
  
  public static void main(String[] args) {
        //deleteData();
        createFullAndPartTimeEmployees();
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
  private static void printEmployee(Employee employee)
  {
    System.out.println("dept = " + employee.getDepartment());
    System.out.println("email = " + employee.getEmail());
    System.out.println("manager = " + employee.getManager());
    System.out.println("firstName = " + employee.getFirstName());
    System.out.println("id = " + employee.getId());
    System.out.println("lastName = " + employee.getLastName());
    System.out.println("version = " + employee.getVersion());
    System.out.println("homeAddress = " + employee.getHomeAddress());
  }

  private static void printFullTimeEmployee(FullTimeEmployee fulltimeemployee)
  {
    System.out.println("annualSalary = " + fulltimeemployee.getAnnualSalary());
    System.out.println("managedEmployees = " + fulltimeemployee.getManagedEmployees());
    System.out.println("dept = " + fulltimeemployee.getDepartment());
    System.out.println("email = " + fulltimeemployee.getEmail());
    System.out.println("manager = " + fulltimeemployee.getManager());
    System.out.println("firstName = " + fulltimeemployee.getFirstName());
    System.out.println("id = " + fulltimeemployee.getId());
    System.out.println("lastName = " + fulltimeemployee.getLastName());
    System.out.println("version = " + fulltimeemployee.getVersion());
    System.out.println("homeAddress = " + fulltimeemployee.getHomeAddress());
  }

  private static void printPartTimeEmployee(PartTimeEmployee parttimeemployee)
  {
    System.out.println("hourlyWage = " + parttimeemployee.getHourlyWage());
    System.out.println("dept = " + parttimeemployee.getDepartment());
    System.out.println("email = " + parttimeemployee.getEmail());
    System.out.println("manager = " + parttimeemployee.getManager());
    System.out.println("firstName = " + parttimeemployee.getFirstName());
    System.out.println("id = " + parttimeemployee.getId());
    System.out.println("lastName = " + parttimeemployee.getLastName());
    System.out.println("version = " + parttimeemployee.getVersion());
    System.out.println("homeAddress = " + parttimeemployee.getHomeAddress());
  }

  private static void printPerson(Person person)
  {
    System.out.println("firstName = " + person.getFirstName());
    System.out.println("id = " + person.getId());
    System.out.println("lastName = " + person.getLastName());
    System.out.println("version = " + person.getVersion());
    System.out.println("homeAddress = " + person.getHomeAddress());
  }

  private static void printAddress(Address address)
  {
    System.out.println("city = " + address.getCity());
    System.out.println("id = " + address.getId());
    System.out.println("state = " + address.getState());
    System.out.println("street1 = " + address.getStreet1());
    System.out.println("street2 = " + address.getStreet2());
    System.out.println("version = " + address.getVersion());
    System.out.println("zipCode = " + address.getZipCode());
  }
}
