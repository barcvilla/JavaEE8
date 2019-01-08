/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.view;

import com.apress.ejb.beans.ShoppingCartLocal;
import com.apress.ejb.entities.Customer;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;

/**
 *
 * @author PC
 */
@Named(value = "login")
@SessionScoped
public class Login implements Serializable {

    String email;
    Customer customer;
    @EJB
    ShoppingCartLocal shoppingCart;
    
    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String processLogin()
    {
        String navigation = null;
        customer = (Customer)shoppingCart.findCustomer(email);
        if(customer != null)
        {
            navigation = "winehome";
        }
        else
        {
            navigation = "register";
        }
        return navigation;
    }

    public ShoppingCartLocal getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCartLocal shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
    
}
