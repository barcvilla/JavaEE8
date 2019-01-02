/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.beans;

import com.apress.ejb.entities.Wine;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
@Stateless(name = "SearchFacade")
public class SearchFacadeBean implements SearchFacadeLocal{
    
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;
    
    public SearchFacadeBean(){}
    
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindAll() {
        return em.createNamedQuery("Wine.findAll", Wine.class).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByVarietal(String varietal) {
        return em.createNamedQuery("Wine.findByVarietal", Wine.class).setParameter("varietal", varietal).getResultList();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public List<Wine> getWineFindByYear(Integer year) {
        return em.createNamedQuery("Wine.findByYear", Wine.class).setParameter("year", year).getResultList();
    }

    @Override
    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    @Override
    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @Override
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResult) {
        Query query = em.createNamedQuery(jpqlStmt);
        if(firstResult > 0)
        {
            query = query.setFirstResult(firstResult);
        }
        if(maxResult > 0)
        {
            query = query.setMaxResults(maxResult);
        }
        return query.getResultList();
    }

    @Override
    public void removeWine(Wine wine) {
        wine = em.find(Wine.class, wine.getId());
        em.remove(wine);
    }
    
}
