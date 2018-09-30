/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.service;

import com.apress.ejb.dao.AddressDao;
import com.apress.ejb.entities.Address;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

/**
 *
 * @author PC
 */
@Stateless
public class AddressServiceImpl implements AddressService{
    
    @EJB
    private AddressDao addressDao;
    @Resource
    private SessionContext context;
    
    @Override
    public void addAddress(Address address) {
        try
        {
            addressDao.addAddress(address);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
    }

    @Override
    public Address updateAddress(Address address) {
        Address ad =  null;
        try
        {
            ad =  addressDao.updateAddress(address);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
        return ad;
    }

    @Override
    public void removeAddress(Address address) {
        try
        {
            addressDao.removeAddress(address);
        }
        catch(Throwable t)
        {
            context.setRollbackOnly();
            t.printStackTrace();
        }
    }

    @Override
    public List<Address> getAddressFindAll() {
        return addressDao.getAddressFindAll();
    }
    
}
