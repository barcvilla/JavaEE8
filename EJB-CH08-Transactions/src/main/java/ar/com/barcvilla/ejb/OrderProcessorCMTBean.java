/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.barcvilla.ejb;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Order Processor Bean Context Management Transaction
 * @author PC
 */
@Stateless(name = "OrderProcessorCMT")
public class OrderProcessorCMTBean {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Chapter08-TransactionSamples-JTA")
    private EntityManager em;
    
    
}
