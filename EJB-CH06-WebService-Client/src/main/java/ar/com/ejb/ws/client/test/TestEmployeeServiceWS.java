/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.ejb.ws.client.test;

import ar.com.ejb.ws.client.Employee;
import ar.com.ejb.ws.client.EmployeeServiceImplService;
import ar.com.ejb.ws.client.EmployeeServiceWS;
import java.util.List;

/**
 *
 * @author PC
 */
public class TestEmployeeServiceWS {
    public static void main(String[] args) {
        EmployeeServiceWS employeeService = new EmployeeServiceImplService().getEmployeeServiceImplPort();
        System.out.println("Ejecutando Servicio Listar Employee WS");
        List<Employee> employees = employeeService.listarEmployees();
        for(Employee e : employees)
        {
            System.out.println("Id: " + e.getId()+" name: "+e.getFirstName()+" last name: "+e.getLastName()+" email: "
                    +e.getEmail()+" address: "+e.getHomeAddress().getStreet1()+ " "+e.getHomeAddress().getCity());
        }
        System.out.println("Fin de Servicio Listar Employees");
    }
}
