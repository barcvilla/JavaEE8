/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.mappedsupperclass.service;

import com.apress.ejb.mappedsupperclass.entities.Address;
import com.apress.ejb.mappedsupperclass.entities.Employee;
import com.apress.ejb.mappedsupperclass.entities.FullTimeEmployee;
import com.apress.ejb.mappedsupperclass.entities.PartTimeEmployee;
import java.util.List;

/**
 *
 * @author PC
 */
public class JavaServiceFacadeClient {

    public static void main(String[] args) {
        try {
            final JavaServiceFacade javaServiceFacade = new JavaServiceFacade();

            //-----------------------------------------------------------------------------------
            //  Clear out any previous test data. Due to “cascade” settings on the
            //  “Person.homeAddress” relationship field, removing a Person will remove its
            //  Address as well.
            //-----------------------------------------------------------------------------------
            for (PartTimeEmployee parttimeemployee : (List<PartTimeEmployee>) javaServiceFacade.getPartTimeEmployeeFindAll()) {
                javaServiceFacade.removePartTimeEmployee(parttimeemployee);
            }
            for (FullTimeEmployee fulltimeemployee : (List<FullTimeEmployee>) javaServiceFacade.getFullTimeEmployeeFindAll()) {
                javaServiceFacade.removeFullTimeEmployee(fulltimeemployee);
            }

            //-----------------------------------------------------------------------------------
            //  Create FullTimeEmployee and PartTimeEmployee instances, along with their Address
            //  objects, and persist them in the database.
            //-----------------------------------------------------------------------------------
            
            Address add = new Address();
            add.setCity("San Mateo");
            add.setState("CA");
            add.setStreet1("1301 Ashwood Ct");
            add.setZipCode("94402");
            javaServiceFacade.persistEntity(add);

            FullTimeEmployee ft = new FullTimeEmployee();
            ft.setAnnualSalary(1000D);
            ft.setDepartment("HQ");
            ft.setEmail("x@y.com");
            ft.setFirstName("Brian");
            ft.setLastName("Jones");
            ft.setHomeAddress(add);
            ft = javaServiceFacade.persistEntity(ft);

            add = new Address();
            add.setCity("San Francisco");
            add.setState("CA");
            add.setStreet1("53 Surrey St");
            add.setZipCode("94131");
            javaServiceFacade.persistEntity(add);

            final PartTimeEmployee pt = new PartTimeEmployee();
            pt.setHourlyWage(100D);
            pt.setDepartment("SALES");
            pt.setEmail("a@b.com");
            pt.setFirstName("David");
            pt.setLastName("Holmes");
            pt.setHomeAddress(add);
            pt.setManager(ft);
            javaServiceFacade.persistEntity(pt);

            //-----------------------------------------------------------------------------------
            //  Retrieve the entities through their type-specific JPQL queries and print them out
            //-----------------------------------------------------------------------------------
            System.out.println("\nEmployees:\n");
            for (Employee employee : (List<Employee>) javaServiceFacade.getEmployeeFindAll()) {
                printEmployee(employee);
            }
            System.out.println("\nPartTimeEmployees:\n");
            for (PartTimeEmployee parttimeemployee : (List<PartTimeEmployee>) javaServiceFacade.getPartTimeEmployeeFindAll()) {
                printPartTimeEmployee(parttimeemployee);
            }
            System.out.println("\nFullTimeEmployees:\n");
            for (FullTimeEmployee fulltimeemployee : (List<FullTimeEmployee>) javaServiceFacade.getFullTimeEmployeeFindAll()) {
                printFullTimeEmployee(fulltimeemployee);
            }
            System.out.println("\nAddresses:\n");
            for (Address address : (List<Address>) javaServiceFacade.getAddressFindAll()) {
                printAddress(address);
            }
            
            //-----------------------------------------------------------------
            // Demostracion de Relaciones Polimorficas usando JPQL
            //-----------------------------------------------------------------
            String query = "select o from Employee o where o.homeAddress.city = :city";
            String city = "San Mateo";
            List<Employee> listEmployees = javaServiceFacade.queryCityEmployee(query, city);
            for(Employee emp : listEmployees)
            {
                System.out.println(emp.getFirstName());
                System.out.println(emp.getLastName());
            }
            
            // USING THE CRITERIA QUERY API
            List<Address> addresses = javaServiceFacade.getAddressFindByCity(city);
            for(Address address : addresses)
            {
                System.out.println("Id: " + address.getId() + " city: " + address.getCity());
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printEmployee(Employee employee) {
        System.out.println("dept = " + employee.getDepartment());
        System.out.println("email = " + employee.getEmail());
        System.out.println("manager = " + employee.getManager());
        System.out.println("firstName = " + employee.getFirstName());
        System.out.println("id = " + employee.getId());
        System.out.println("lastName = " + employee.getLastName());
        System.out.println("version = " + employee.getVersion());
        System.out.println("homeAddress = " + employee.getHomeAddress());
    }

    private static void printFullTimeEmployee(FullTimeEmployee fulltimeemployee) {
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

    private static void printPartTimeEmployee(PartTimeEmployee parttimeemployee) {
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

    private static void printAddress(Address address) {
        System.out.println("city = " + address.getCity());
        System.out.println("id = " + address.getId());
        System.out.println("state = " + address.getState());
        System.out.println("street1 = " + address.getStreet1());
        System.out.println("street2 = " + address.getStreet2());
        System.out.println("version = " + address.getVersion());
        System.out.println("zipCode = " + address.getZipCode());
    }
}
