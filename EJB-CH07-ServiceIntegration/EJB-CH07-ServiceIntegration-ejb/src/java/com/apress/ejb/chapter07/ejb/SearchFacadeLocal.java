/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.Wine;
import java.util.List;
import javax.ejb.Local;

/**
 * 
 * @author PC
 */
@Local
public interface SearchFacadeLocal {
    List<Wine> getWineFindAll();
    List<Wine> getWineFindByCountry(String country);
    List<Wine> getWineFindByVarietal(String varietal);
    List<Wine> getWineFindByYear(Integer year);
    <T> T mergeEntity(T entity);
    <T> T persistEntity(T entity);
    Object queryRange(String jpqlStmt, int firstResult, int maxResults);
    void removeWie(Wine wine);
}
