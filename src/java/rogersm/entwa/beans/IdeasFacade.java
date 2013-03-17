/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rogersm.entwa.beans;

import rogersm.entwa.entities.Ideas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author MikeRogers
 */
@Stateless
public class IdeasFacade extends AbstractFacade<Ideas> {
    @PersistenceContext(unitName = "ENTWAPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IdeasFacade() {
        super(Ideas.class);
    }
    
    public List findByStatus(String status) {
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.findByStatus", Ideas.class).setParameter("status", status); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
    
    public List findByPerson(Integer id) {
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.findByPerson", Ideas.class).setParameter("submitter", id); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
    
    public List searchByTitle(String search){
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.searchByTitle", Ideas.class).setParameter("title", search); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
}
