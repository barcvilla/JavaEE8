/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.view;

import com.apress.ejb.beans.CustomerFacadeLocal;
import com.apress.ejb.entities.Address;
import com.apress.ejb.entities.Individual;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author PC
 */
@Named(value = "NewCustomer")
@RequestScoped
public class NewCustomer {

    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String streetOne;
    private String streetTwo;
    private String city;
    private String state;
    private String zipCode;
    private String ccnum;
    private String ccexpDate;
    
    @EJB
    CustomerFacadeLocal customerFacade;

    public NewCustomer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStreetOne() {
        return streetOne;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCcnum() {
        return ccnum;
    }

    public void setCcnum(String ccnum) {
        this.ccnum = ccnum;
    }

    public String getCcexpDate() {
        return ccexpDate;
    }

    public void setCcexpDate(String ccexpDate) {
        this.ccexpDate = ccexpDate;
    }
    
    public String AddNewCustomer()
    {
        Individual customer = new Individual();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setPhone(phone);
        customer.setEmail(email);
        
        Address address = new Address();
        address.setStreet1(streetOne);
        address.setStreet2(streetTwo);
        address.setState(state);
        address.setCity(city);
        address.setZipCode(zipCode);
        
        customer.setDefaultBillingAddress(address);
        customer.setCcNum(ccnum);
        customer.setCcExpDate(ccexpDate);
        
        if(customerFacade != null)
        {
            customerFacade.registerCustomer(customer);
        }
        
        return "success";
    }
}
