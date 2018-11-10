/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.Wine;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface ShoppingCartLocal {
    void addWineItem(Wine wine, int quantity);
    void addWineItem(Wine wine);
    Customer findCustomer(String email);
    String sendOrderToOPC();
    Customer getCustomer();
    List<Wine> getWineFindAll();
    List<Wine> getWineFindByCountry(String country);
    List<Wine> getWineFindByVarietal(String varietal);
    List<Wine> getWineFindByYear(Integer year);
    <T> T mergeEntity(T entity);
    <T> T persistEntity(T entity);
    void removeCartItem(CartItem cartItem);
    List<CartItem> getCartItems();
    void removeWine(Wine wine);
    void removeWineItem(CartItem cartItem);
}
