/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb.ws;

import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CreditCheckEndpointBeanLocal {
    public boolean validateCC(String cc);
}
