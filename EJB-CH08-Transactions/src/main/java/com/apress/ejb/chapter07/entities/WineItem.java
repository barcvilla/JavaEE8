/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({@NamedQuery(name = "WineItem.findAll", query = "select o from WineItem o")})
@Table(name = "WINE_ITEM")
public abstract class WineItem implements Serializable {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private int quantity;
  private Integer version;
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "WINE_ID")
  private Wine wine;

  public WineItem() {
  }

  public WineItem(int quantity, Wine wine) {
    this.setQuantity(quantity);
    this.setWine(wine);
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Wine getWine() {
    return wine;
  }

  public void setWine(Wine wine) {
    this.wine = wine;
  }
}
