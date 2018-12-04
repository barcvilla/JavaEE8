/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

@Entity
@NamedQueries({@NamedQuery(name = "Address.findAll", query = "select o from Address o")})
public class Address implements Serializable {
  @Column(length = 4000)
  private String city;
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String state;
  @Column(length = 4000)
  private String street1;
  @Column(length = 4000)
  private String street2;
  @Version
  private Integer version;
  @Column(name = "ZIP_CODE")
  private String zipCode;

  public Address() {
  }

  public Address(String city, String state, String street1, String street2, String zipCode) {
    setCity(city);
    setState(state);
    setStreet1(street1);
    setStreet2(street2);
    setZipCode(zipCode);
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getStreet2() {
    return street2;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }
}

