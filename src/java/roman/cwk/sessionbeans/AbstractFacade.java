/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Abstract class, provides CRUD methods for entities.
 *
 * @author Roman Macor
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    /**
     * Constructor to be called by child class. It is used to specified entity
     * class.
     *
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    /**
     * Insert specified entity instance into database.
     *
     * @param entity instance of entity that is to be inserted
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * Update entity in database.
     *
     * @param entity entity that is to be updated
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * Delete entity from database
     *
     * @param entity entity that is to be deleted
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * Retrieve object from database
     *
     * @param id id of object that is to be retrieved
     * @return retrieved object
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * Retrieve all entities from database
     *
     * @return List of entities
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * Retrieve all entities in database in specified range
     *
     * @param range array containing min and max
     * @return List of entities
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
     * Get count of entities in database.
     *
     * @return count of entities in database
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
