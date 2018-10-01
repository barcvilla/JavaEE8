/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author PC
 */
@Entity
@Table(name ="customer")
@NamedQueries({
    @NamedQuery(name="Customer.findAll", query = "select c from Customer c"),
    @NamedQuery(name = "Customer.findByEmail", query = "select c from Customer c where c.email= :email"),
    @NamedQuery(name = "Customer.findById", query = "select c from Customer c where c.id= :id"),
    @NamedQuery(name = "Customer.findOrderById", query = "select c from Customer c left join c.customerOrders co where c.id= :id")})
public class Customer implements Serializable{
    @Basic //aplicable a tipos de datos simples de java
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(length = 400)
    private String name;
    
    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<CustomerOrder> customerOrders;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "shipping_address", referencedColumnName = "id")
    private Address shippingAddress;
    
    private String email;
    
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "billing_address", referencedColumnName = "id")
    private Address billingAddress;
    
    public Customer()
    {
        customerOrders = new ArrayList<CustomerOrder>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder)
    {
        if(customerOrders == null)
        {
            customerOrders = new ArrayList<CustomerOrder>();
        }
        
        customerOrders.add(customerOrder);
        customerOrder.setCustomer(this);
        return customerOrder;
    }
    
    public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder)
    {
        getCustomerOrders().remove(customerOrder);
        customerOrder.setCustomer(null);
        return customerOrder;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
