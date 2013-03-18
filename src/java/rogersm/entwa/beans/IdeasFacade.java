package rogersm.entwa.beans;

import rogersm.entwa.entities.Ideas;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * Idea Facade
 * 
 * @author 447955
 */
@Stateless
public class IdeasFacade extends AbstractFacade<Ideas> {
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
    public IdeasFacade() {
        super(Ideas.class);
    }
    
    /**
     *
     * Finds the idea by it's status, so i can stop pending status showing on homepage.
     * 
     * @param status
     * @return
     */
    public List findByStatus(String status) {
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.findByStatus", Ideas.class).setParameter("status", status); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
    
    /**
     * 
     * Finds idea by who is assoicated with them.
     * 
     * @param id
     * @return
     */
    public List findByPerson(Integer id) {
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.findByPerson", Ideas.class).
                setParameter("submitter", id).
                setParameter("student", id).
                setParameter("moderator", id).
                setParameter("organisation", id); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
    
    /**
     *
     * Searches the titles of the entities and returns the relvant ones.
     * 
     * @param search
     * @return
     */
    public List searchByTitle(String search){
        TypedQuery <Ideas> query = em.createNamedQuery("Ideas.searchByTitle", Ideas.class)
                .setParameter("title", search); 
        List<Ideas> results = query.getResultList(); 
        return  results;
    }
}
