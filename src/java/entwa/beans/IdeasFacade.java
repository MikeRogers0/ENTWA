/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entwa.beans;

import entwa.entities.Ideas;
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
}
