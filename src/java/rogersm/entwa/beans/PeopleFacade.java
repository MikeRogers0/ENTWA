/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rogersm.entwa.beans;

import rogersm.entwa.entities.People;
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
public class PeopleFacade extends AbstractFacade<People> {
    @PersistenceContext(unitName = "ENTWAPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public PeopleFacade() {
        super(People.class);
    }
    
    /**
     *
     * @param type
     * @return
     */
    public List findByType(String type) {
        TypedQuery <People> query = em.createNamedQuery("People.findByType", People.class).setParameter("type", type); 
        List<People> results = query.getResultList(); 
        return  results;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public List findById(Integer id) {
        TypedQuery <People> query = em.createNamedQuery("People.findById", People.class).setParameter("id", id); 
        List<People> results = query.getResultList(); 
        return  results;
    }
    
    /**
     *
     * @param email
     * @param password
     * @return
     */
    public List findByEmailAndPassword(String email, String password) {
        List <People> results = em.createNamedQuery("People.findByEmailAndPassword", People.class).setParameter("email", email).setParameter("password", password).getResultList(); 
        return results;
    }
}
