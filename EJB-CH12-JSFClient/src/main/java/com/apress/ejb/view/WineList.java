/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.view;

import com.apress.ejb.beans.SearchFacadeLocal;
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
import javax.faces.application.Application;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "WineList")
@SessionScoped
public class WineList implements Serializable {
    
    public WineList() {
    }
    
    @EJB
    private SearchFacadeLocal searchFacade;
    List<Wine> wineList = new ArrayList<Wine>();
    private HtmlDataTable dataTable1;
    
    public String findAllWines()
    {
        if(searchFacade == null)
        {
            return "gohome";
        }
        else
        {
            wineList = searchFacade.getWineFindAll();
            return "allwines";
        }
    }
    
    public String searchByCountry()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression wineYear = expressionFactory.createValueExpression(eLContext, "#{SearchWines.Country}", String.class);
        String country = (String)wineYear.getValue(eLContext);
        if(searchFacade == null)
        {
            return "gohome";
        }
        else
        {
            wineList = searchFacade.getWineFindByCountry(country);
            return "success";
        }
    }
    
    public String searchByVarietal()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression wineYear = expressionFactory.createValueExpression(eLContext, "#{SearchWines.varietal}", String.class);
        String varietal =  (String)wineYear.getValue(eLContext);
        if(searchFacade == null)
        {
            return "gohome";
        }
        else
        {
            wineList = searchFacade.getWineFindByVarietal(varietal);
            return "success";
        }
    }
    
    public String searchByYear()
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression wineYear = expressionFactory.createValueExpression(eLContext, "#{SearchWines.year}", String.class);
        String year = (String)wineYear.getValue(eLContext);
        if(searchFacade == null)
        {
            return "gohome";
        }
        else
        {
            wineList = searchFacade.getWineFindByYear(new Integer(year));
            return "success";
        }
    }
    
    public String invokeAddToCart()
    {
        Wine addWine = (Wine)this.dataTable1.getRowData();
        FacesContext ctx = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = ctx.getApplication().getExpressionFactory();
        ELContext eLContext = ctx.getELContext();
        ValueExpression valueExpression = expressionFactory.createValueExpression(eLContext, "#{JSFShoppingCart.selectedWine}", Wine.class);
        valueExpression.setValue(eLContext, addWine);
        return "addtocart";
    }
    
    public void setWinesList(List<Wine> winesList)
    {
        this.wineList = winesList;
    }
    
    public List<Wine> getWinesList()
    {
        return wineList;
    }
    
    public void setDataTable1(HtmlDataTable dataTable1)
    {
        this.dataTable1 = dataTable1;
    }
    
    public HtmlDataTable getDataTable1()
    {
        return dataTable1;
    }
}
