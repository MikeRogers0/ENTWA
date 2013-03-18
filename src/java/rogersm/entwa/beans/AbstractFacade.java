package rogersm.entwa.beans;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * 
 * The Abstract Facade, which is a starting place for the other DAO to talk to the data service.
 * 
 * @param <T> 
 * @author 447955
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    /**
     *
     * Gets the entity class and assigns it somewhere.
     * 
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     * 
     * Creates entity
     * 
     * @param entity
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * 
     * Updates entity
     * 
     * @param entity
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * 
     * Deletes Entity
     * 
     * @param entity
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     *
     * Looks up enity in DB based on ID.
     * 
     * @param id
     * @return
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     *
     * Lists all the entites
     * 
     * @return
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     *
     * Finds a certain amount of entities, which is useful for pagnation.
     * 
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     *
     * Counts the total amount of entities.
     * 
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
}
