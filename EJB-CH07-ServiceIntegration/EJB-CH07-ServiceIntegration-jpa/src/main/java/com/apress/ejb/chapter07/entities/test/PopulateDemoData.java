/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.entities.test;

import com.apress.ejb.chapter07.entities.Address;
import com.apress.ejb.chapter07.entities.BusinessContact;
import com.apress.ejb.chapter07.entities.CartItem;
import com.apress.ejb.chapter07.entities.Customer;
import com.apress.ejb.chapter07.entities.CustomerOrder;
import com.apress.ejb.chapter07.entities.Distributor;
import com.apress.ejb.chapter07.entities.Individual;
import com.apress.ejb.chapter07.entities.InventoryItem;
import com.apress.ejb.chapter07.entities.OrderItem;
import com.apress.ejb.chapter07.entities.Supplier;
import com.apress.ejb.chapter07.entities.Wine;
import com.apress.ejb.chapter07.entities.WineItem;
import com.apress.ejb.chapter07.util.Ini;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopulateDemoData {

  private JavaServiceFacade facade;
  
  static
  {
      Ini.loadProperties();
  }

  public static void main(String[] args) {
    
    PopulateDemoData.resetData("Chapter07-WineAppUnit-ResourceLocal", System.out);
  }

  public static void resetData(String persistenceUnit, PrintStream out) {
    PopulateDemoData pdd = null;
    try {
      pdd = new PopulateDemoData(persistenceUnit);

      out.println("Reporting existing data...");
      pdd.showDataCount(out);

      out.println("Removing data...");
      pdd.removeAllDemoData(out);

      out.println("Reporting data after removal...");
      pdd.showDataCount(out);

      out.println("Populating data...");
      pdd.populateDemoCustomer();
      pdd.populateWines();

      out.println("Reporting final data...");
      pdd.showDataCount(out);
    } finally {
      if (pdd != null) {
        pdd.releaseEntityManager();
      }
    }
  }

  private PopulateDemoData(String persistenceUnit) {
    facade = new JavaServiceFacade(persistenceUnit);
  }

  private void removeAllDemoData(PrintStream out) {
    removeAll(OrderItem.class, out);
    removeAll(CustomerOrder.class, out);
    removeAll(Individual.class, out);
    removeAll(Distributor.class, out);
    removeAll(Supplier.class, out);
    removeAll(InventoryItem.class, out);
    removeAll(CartItem.class, out);
    removeAll(Wine.class, out);
  }

  private <T> void removeAll(Class<T> entityClass, PrintStream out) {
    int i = 0;
    for (T entity : facade.findAll(entityClass)) {
      facade.removeEntity(entity);
    }
    out.println("Removed " + i + " " + entityClass.getSimpleName() + " instances");
  }

  private Customer populateDemoCustomer() {
    Address a = new Address("Redwood Shores", "CA", "200 Oracle Pkwy", null, "94065");
    Individual i = new Individual("James", "Brown", "800.888.8000", Ini.TO_EMAIL_ADDRESS, a, a, "04/14", "123");
    facade.persistEntity(i);

    return i;
  }

  private InventoryItem populateWines() {
    InventoryItem ii = null;
    for (int i = 0; i < 6; i++) {
      Wine w = new Wine("USA", "Fine Wine - ranked #" + i, "Yerba Buena " + i, 90, "Napa Valley", new Float(10 + i), "Zinfandel", 2000 + i);
      facade.persistEntity(w);
      ii = new InventoryItem(10 + i, w, new java.util.Date(System.currentTimeMillis()), new Float(1 + i));
      facade.persistEntity(ii);
    }
    for (int i = 4; i < 10; i++) {
      Wine w = new Wine("France", "Fine Wine - ranked #" + i, "Chateau Brown " + i, 90, "Loire Valley ", new Float(10 + i), "Zinfandel", 2000 + i);
      facade.persistEntity(w);
      ii = new InventoryItem(10 + i, w, new java.util.Date(System.currentTimeMillis()), new Float(1 + i));
      facade.persistEntity(ii);
    }

    return ii;
  }

  public void showDataCount(PrintStream out) {
    out.println(facade.getCount(Address.class) + " Addresses found");
    out.println(facade.getCount(BusinessContact.class) + " Business Contacts found");
    out.println(facade.getCount(CustomerOrder.class) + " Customer Orders found");
    out.println(facade.getCount(Wine.class) + " Wines found");
    out.println(facade.getCount(WineItem.class) + " Wine Items found");
  }

  private void releaseEntityManager() {
    if (facade != null) {
      facade.close();
    }
  }
}