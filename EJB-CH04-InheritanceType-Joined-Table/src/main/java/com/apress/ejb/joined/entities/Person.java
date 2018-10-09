/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.joined.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 *
 * @author PC
 */
@Entity
@Table(name = "ch04_join_person")
@NamedQueries({@NamedQuery(name = "Person.findAll", query = "select p from Person p")})
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements Serializable{
    private static final long serialVersionUID = 5291172566067954515L;
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "first_name", length = 400)
    private String firstName;
    
    @Column(name = "last_name", length = 400)
    private String lastName;
    
    /**
     * Debido a que no hay un campo correspondiente en la entidad Address que referencie a Person, esta es una relacion
     * uni-direccional. CasacadeType.ALL indica que todas las operacion realizadas sobre la entidad Persona se aplicaran
     * tambien al hombre homeAddress de tipo Address.
     */
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "home_address", referencedColumnName = "id")
    private Address homeAddress;
    
    @Version
    private Integer version;
    
    public Person()
    {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    
}
