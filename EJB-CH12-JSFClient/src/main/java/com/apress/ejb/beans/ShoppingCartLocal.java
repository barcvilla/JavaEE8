/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.CartItem;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.Wine;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author PC
 */
@Local
public interface ShoppingCartLocal {
    void addWineItem(Wine wine, int quantity);
    
    void addWineItem(Wine wine);
    
    Customer findCustomer(String email);
    
    List<CartItem> getAllCartItems(Customer customer);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<CartItem> getCartItemFindAll();
    
    Customer getCustomer();
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindAll();
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByCountry(String country);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByVarietal(String varietal);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByYear(Integer year);
    
    <T> T mergeEntity(T entity);
    
    <T> T persistEntity(T entity);
    
    void removeCartItem(CartItem cartItem);
    
    void removeWine(Wine wine);
    
    void removeWineItem(CartItem cartItem);
    
    void setCustomer(Customer customer);
    
    String sendOrderToOPC();
}
