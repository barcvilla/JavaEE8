/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.dao;

import com.apress.ejb.entities.Address;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless
public class AddressDaoImpl implements AddressDao{
    @PersistenceContext(unitName = "rrhhpersistence")
    EntityManager em;
    
    @Override
    public void addAddress(Address address) {
        em.persist(address);
    }
    
    @Override
    public Address updateAddress(Address address) {
        return em.merge(address);
    }

    @Override
    public void removeAddress(Address address) {
        address = em.find(Address.class, address.getId());
        em.remove(address);
    }

    @Override
    public List<Address> getAddressFindAll() {
        return em.createNamedQuery("Address.findAll", Address.class).getResultList();
    }

}
