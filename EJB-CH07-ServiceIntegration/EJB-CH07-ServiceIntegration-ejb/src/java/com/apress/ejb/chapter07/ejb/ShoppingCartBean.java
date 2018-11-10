/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.Wine;
import java.sql.Timestamp;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * El ShoppingCartBean administra los items del carrito (cart items) para un cliente registrado que se ha
 * autenticado en la aplicacion. El ShoppingCartBean es un session bean stateful y este mantiene un registro de los
 * cart items (wines y quantity) que son adicionados y eliminados del shopping cart. Finalmente, el componente 
 * shopping cart transfiere la informacion del pedido al componente order-processing.
 * @author PC
 */
@Stateful(name = "ShoppingCart")
public class ShoppingCartBean implements ShoppingCartLocal{
    
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA", type = PersistenceContextType.EXTENDED)
    private EntityManager em;
    
    private Customer customer;
    
    @EJB
    private CustomerFacadeBean customerFacade;
    
    @EJB
    private OrderProcessFacadeBean orderProcessFacade;

    @Override
    public void addWineItem(Wine wine, int quantity) {
        CartItem cartItem = new CartItem(quantity, wine);
        customer.addCartItem(cartItem);
    }

    @Override
    public void addWineItem(Wine wine) {
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(20);
        cartItem.setWine(wine);
        cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        customer.addCartItem(cartItem);
    }
    
    public void addCartItemsTemporarily()
    {
        List<Wine> wines = em.createNamedQuery("Wine.findAll").getResultList();
        for(Wine wine : wines)
        {
            final CartItem cartItem = new CartItem();
            cartItem.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            cartItem.setQuantity(20);
            cartItem.setWine(wine);
            customer.addCartItem(cartItem);
        }
    }

    @Override
    public Customer findCustomer(String email) {
        customer = customerFacade.getCustomerFindByEmail(email);
        return customer;
    }

    @Override
    public String sendOrderToOPC() {
        String result = null;
        try
        {
            orderProcessFacade.processOrder(customer);
            result = "Your order has been submitted -  you will be notied about the status via email";
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            result = "An error occured while processing your order, Please contact Customer Service";
        }
        
        return result;
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public List<Wine> getWineFindAll() {
        return em.createNamedQuery("Wine.findAll", Wine.class).getResultList();
    }

    @Override
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).getResultList();
    }

    @Override
    public List<Wine> getWineFindByVarietal(String varietal) {
        return em.createNamedQuery("Wine.findByVarietal", Wine.class).getResultList();
    }

    @Override
    public List<Wine> getWineFindByYear(Integer year) {
        return em.createNamedQuery("Wine.findByYear", Wine.class).getResultList();
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
        cartItem = em.find(CartItem.class, cartItem);
        em.remove(cartItem);
    }

    @Override
    public List<CartItem> getCartItems() {
        return customer.getCartItemList();
    }

    @Override
    public void removeWine(Wine wine) {
        wine = em.find(Wine.class, wine);
        em.remove(wine);
    }

    @Override
    public void removeWineItem(CartItem cartItem) {
        customer.removeCartItem(cartItem);
    }
    
}
