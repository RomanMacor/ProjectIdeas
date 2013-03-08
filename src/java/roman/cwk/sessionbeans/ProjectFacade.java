/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import roman.cwk.entity.Project;

/**
 *
 * @author Roman Macor
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {

    @PersistenceContext(unitName = "ProjectIdeasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectFacade() {
        super(Project.class);
    }

    public void test() {
        em.find(Project.class, 5);
    }

    public List<Project> findProjectsForUser(String organizationName) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE c.organization.organizationName = :name");
        query.setParameter("name", organizationName);
        return query.getResultList();
    }

    public boolean projectExist(String name) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE c.title = :name");
        query.setParameter("name", name);
        return !(query.getResultList().isEmpty());
        
    }
    public List<Project> searchByName(String searchString) {
       Query query = em.createQuery("SELECT c FROM Project c WHERE upper(c.title) like :searchString");
       query.setParameter("searchString", "%" + searchString.toUpperCase() + "%");
       return query.getResultList();
    }
}
