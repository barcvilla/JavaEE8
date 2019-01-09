/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.view;

import com.apress.ejb.beans.ShoppingCartLocal;
import com.apress.ejb.entities.CartItem;
import com.apress.ejb.entities.Customer;
import com.apress.ejb.entities.Individual;
import com.apress.ejb.entities.Wine;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "JSFShoppingCart")
@SessionScoped
public class JSFShoppingCart implements Serializable {

    public JSFShoppingCart() {
    }

    Wine selectedWine;
    String quantity;
    @EJB
    ShoppingCartLocal shoppingCart;
    List<CartItem> cartItems = new ArrayList<CartItem>();

    public String addToCart() {
        Integer qty = new Integer(quantity);
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        //verificamos si el cliente ya se ha loggeado
        ValueExpression customerBinding = expressionFactory.createValueExpression(eLContext, "#{Login.customer}", Customer.class);
        Customer cust = (Customer) customerBinding.getValue(eLContext);
        if (cust == null) {
            return "success";
        } else {
            ValueExpression shoppingCartBinding = expressionFactory.createValueExpression(eLContext, "#{Login.shoppingCart}", ShoppingCartLocal.class);
            shoppingCart = (ShoppingCartLocal) shoppingCartBinding.getValue(eLContext);
            shoppingCart.addWineItem(selectedWine, qty.intValue());
            return "success";
        }
    }

    public void setSelectedWine(Wine selectedWine) {
        this.selectedWine = selectedWine;
    }

    public Wine getSelectedWine() {
        return selectedWine;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
    
    public List<CartItem> getCartItems()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression customerBinding = expressionFactory.createValueExpression(eLContext, "#{Login.customer}", Customer.class);
        Individual customer = (Individual)customerBinding.getValue(eLContext);
        ValueExpression shoppingCartBinding = expressionFactory.createValueExpression(eLContext, "#{Login.shoppingCart}", ShoppingCartLocal.class);
        shoppingCart = (ShoppingCartLocal)shoppingCartBinding.getValue(eLContext);
        return shoppingCart.getAllCartItems(customer);
    }
    
    public String ProcessOrder()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression shoppingCartBinding = expressionFactory.createValueExpression(eLContext, "#{Login.shoppingCart}", ShoppingCartLocal.class);
        shoppingCart = (ShoppingCartLocal)shoppingCartBinding.getValue(eLContext);
        shoppingCart.sendOrderToOPC();
        return "success";
    }
}
