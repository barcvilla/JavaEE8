/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apress.ejb.chapter07.ejb;

import com.apress.ejb.chapter07.entities.Wine;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Search Facade Component
 * Este Stateless session bean provee metodos de negocio que le permite a la app cliente consultar informacion de la entidad Wine.
 * o permite buscar vinos mediantes diversos criterios como year, country, varietal. Este es un componente stateless session bean
 * y este retorna una lista de wines.
 * 
 * @author PC
 */
@Stateless(name = "SearchFacade")
public class SearchFacadeBean implements SearchFacadeLocal{
    
    @PersistenceContext(unitName = "Chapter07-WineAppUnit-JTA")
    private EntityManager em;

    @Override
    public List<Wine> getWineFindAll() {
        return em.createNamedQuery("Wine.findAll", Wine.class).getResultList();
    }

    @Override
    public List<Wine> getWineFindByCountry(String country) {
        return em.createNamedQuery("Wine.findByCountry", Wine.class).setParameter("country", country).getResultList();
    }

    @Override
    public List<Wine> getWineFindByVarietal(String varietal) {
        return em.createNamedQuery("Wine.findByVarietal", Wine.class).setParameter("varietal", varietal).getResultList();
    }

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

    @Override
    public Object queryRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createNamedQuery(jpqlStmt);
        if(firstResult > 0)
        {
            query = query.setFirstResult(firstResult);
        }
        if(maxResults > 0)
        {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    @Override
    public void removeWie(Wine wine) {
        wine = em.find(Wine.class, wine.getId());
        em.remove(wine);
    }
}
