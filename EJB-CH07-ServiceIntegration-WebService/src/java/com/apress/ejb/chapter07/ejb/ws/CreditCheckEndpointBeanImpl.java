/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb.ws;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author PC
 */
@Stateless
@WebService(endpointInterface = "CreditCheckEndpointBean")
public class CreditCheckEndpointBeanImpl implements CreditCheckEndpointBean, CreditCheckEndpointBeanLocal{

    @Override
    public boolean validateCC(String cc) {
        return true;
    }
    
}
