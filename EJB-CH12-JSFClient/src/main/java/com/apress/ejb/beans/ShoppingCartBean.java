/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.CartItem;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.Wine;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 *
 * @author PC
 */
@Stateful(name = "ShoppingCart")
public class ShoppingCartBean implements ShoppingCartLocal{
    
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA", type = PersistenceContextType.EXTENDED)
    private EntityManager em;
    private Customer customer;
    
    @EJB
    private CustomerFacadeLocal customerFacade;
    @EJB
    private OrderProcessFacadeBean orderProcessFacade;
    
    public ShoppingCartBean(){}
    
    
    
    @Override
    public void addWineItem(Wine wine, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        wine = em.find(Wine.class, wine.getId());
        cartItem.setWine(wine);
        cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        
        if(em.contains(customer))
        {
            customer.addCartItem(cartItem);
        }
        else
        {
            customer = em.find(Customer.class, customer.getId());
            customer.addCartItem(cartItem);
        }
        
        customer = em.merge(customer);
    }

    @Override
    public void addWineItem(Wine wine) {
        customer = em.find(Customer.class, customer.getId());
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(20);
        wine = em.find(Wine.class, wine.getId());
        cartItem.setWine(wine);
        cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        customer.addCartItem(cartItem);
        customer = em.merge(customer);
    }

    @Override
    public Customer findCustomer(String email) {
        Customer cust =  customerFacade.getCustomerFindByEmail(email);
        setCustomer(cust);
        return cust;
    }

    @Override
    public List<CartItem> getAllCartItems(Customer customer) {
        if(em.contains(customer))
        {
            return customer.getCartItemList();
        }
        else
        {
            customer = em.find(Customer.class, customer.getId());
            return customer.getCartItemList();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<CartItem> getCartItemFindAll() {
        return em.createNamedQuery("CartItem.findAll", CartItem.class).getResultList();
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindAll() {
        return em.createNamedQuery("Wine.findAll", Wine.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByVarietal(String varietal) {
        return em.createNamedQuery("Wine.findByVarietal", Wine.class).setParameter("varietal", varietal).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByYear(Integer year) {
        return em.createNamedQuery("Wine.findByYear", Wine.class).setParameter("year", year).getResultList();
    }

    @Override
    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public void removeCartItem(CartItem cartItem) {
        cartItem = em.find(CartItem.class, cartItem.getId());
        em.remove(cartItem);
    }

    @Override
    public void removeWine(Wine wine) {
        wine = em.find(Wine.class, wine.getId());
        em.remove(wine);
    }

    @Override
    public void removeWineItem(CartItem cartItem) {
        if(!em.contains(customer))
        {
            customer =  em.find(Customer.class, customer.getId());
        }
        customer.removeCartItem(cartItem);
        customer = em.merge(customer);
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public void addCartItemsTemporarily()
    {
        customer = em.merge(customer);
        List<Wine> wines = em.createNamedQuery("findAllWine").getResultList();
        for(Wine wine : wines)
        {
            CartItem cartItem = new CartItem();
            cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            cartItem.setQuantity(20);
            cartItem.setWine(wine);
            customer.addCartItem(cartItem);
            em.merge(customer);
        }
    }
    
}
