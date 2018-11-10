/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.servicio;

import com.apress.ejb.dao.EmployeeDao;
import com.apress.ejb.entities.Employee;
import com.apress.ejb.entities.FullTimeEmployee;
import com.apress.ejb.entities.PartTimeEmployee;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author PC
 */
@Stateless
@WebService(endpointInterface = "com.apress.ejb.servicio.EmployeeServiceWS")
public class EmployeeServiceImpl implements EmployeeServiceWS, EmployeeServiceLocal, EmployeeServiceRemote{
    
    @Resource
    private SessionContext contexto;
    
    @EJB
    private EmployeeDao employeeDao;

    @Override
    public List<Employee> listarEmployees() {
        return employeeDao.listarEmployees();
    }

    @Override
    public List<FullTimeEmployee> listarFullTimeEmployees() {
        return employeeDao.listarFullTimeEmployees();
    }

    @Override
    public List<PartTimeEmployee> listarPartTimeEmployees() {
        return employeeDao.listarPartTimeEmployees();
    }
    
}
