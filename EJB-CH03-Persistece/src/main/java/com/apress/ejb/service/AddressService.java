/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.service;

import com.apress.ejb.entities.Address;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface AddressService {
    public void addAddress(Address address);
    public Address updateAddress(Address address);
    public void removeAddress(Address address);
    public List<Address> getAddressFindAll();
}
