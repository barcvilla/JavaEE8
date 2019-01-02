/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.CartItem;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.CustomerOrder;
import com.apress.ejb.entities.Distributor;
import com.apress.ejb.entities.Individual;
import com.apress.ejb.entities.OrderItem;
import com.apress.ejb.entities.Wine;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
@Stateless(name = "OrderProcessFacade")
public class OrderProcessFacadeBean {
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;
    
    public OrderProcessFacadeBean(){}
    
    public Object mergeEntity(Object entity)
    {
        return em.merge(entity);
    }
    
    public Object persistEntity(Object entity)
    {
         em.persist(entity);
         return entity;
    }
    
    public Object refreshEntity(Object entity)
    {
        em.refresh(entity);
        return entity;
    }
    
    public void removeEntity(Object entity)
    {
        em.remove(em.merge(entity));
    }
    
    public void createNewOrder(CustomerOrder customerOrder)
    {
        persistEntity(customerOrder);
    }
    
    public String processOrder(List<CartItem> cartItems)
    {
        return null;
    }
    
    public String processOrder(Customer customer)
    {
        String processStatus = null;
        if(!em.contains(customer))
        {
            customer = em.find(Customer.class, customer.getId());
        }
        
        if(customer instanceof Individual)
        {
            CustomerOrder order = new CustomerOrder();
            order.setCreationDate(new Timestamp(System.currentTimeMillis()));
            try
            {
                Iterator cartIter = customer.getCartItemList().iterator();
                List<CartItem> cartItems = new ArrayList<CartItem>();
                while(cartIter.hasNext())
                {
                    CartItem cItem = (CartItem)cartIter.next();
                    OrderItem oItem = new OrderItem();
                    int qty = cItem.getQuantity();
                    oItem.setQuantity(qty);
                    oItem.setOrderDate(new Timestamp(System.currentTimeMillis()));
                    oItem.setWine(cItem.getWine());
                    Wine tempWine = cItem.getWine();
                    Float d = tempWine.getRetailPrice();
                    Float price = d * cItem.getQuantity();
                    oItem.setPrice(price);
                    order.addOrderItem(oItem);
                    cartItems.add(cItem);
                }
                em.persist(order);
                
                customer.addCustomerOrder(order);
                for(CartItem cartItem : cartItems)
                {
                    customer.removeCartItem(cartItem);
                }
                customer = em.merge(customer);
                
                PurchaseOrder po = new PurchaseOrder();
                po.setCustomer(customer);
                po.setCustomerOrder(order);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            processStatus = "Purchase Order sent for processing to the process queue";
        }
        else if(customer instanceof Distributor)
        {
            if("PREFERRED".equals(((Distributor)customer).getMemberStatus()))
            {
                processStatus = "Distributor credit check approved";
            }
        }
        else
        {
            processStatus = " Invalid Credit Card number or credit check failed";
        }
        return processStatus;
    }
    
}
