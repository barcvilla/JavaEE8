/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.Wine;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author PC
 */
@Remote
public interface SearchFacade {
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindAll();
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByCountry(String country);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByVarietal(String varietal);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    List<Wine> getWineFindByYear(Integer year);
    
    <T> T mergeEntity(T entity);
    
    <T> T persistEntity(T entity);
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    Object queryByRange(String jpqlStmt, int firstResult, int maxResult);
    
    void removeWine(Wine wine);
}
