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
 * DAO, EJB project facade
 *
 * @author Roman Macor
 */
@Stateless
public class ProjectFacade extends AbstractFacade<Project> {

    @PersistenceContext(unitName = "ProjectIdeasPU")
    private EntityManager em;

    /**
     * Get entity manager
     *
     * @return entity manager
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Constructor, calls parent constructor and sets entity class
     */
    public ProjectFacade() {
        super(Project.class);
    }

    /**
     * Get project that has specified organization name
     *
     * @param organizationName organization name
     * @return List of projects that belong to organization
     */
    public List<Project> findProjectsForUser(String organizationName) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE c.organization.organizationName = :name");
        query.setParameter("name", organizationName);
        return query.getResultList();
    }

    /**
     * Checks whether project with given name exist
     *
     * @param name name of project
     * @return true if project exist
     */
    public boolean projectExist(String name) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE c.title = :name");
        query.setParameter("name", name);
        return !(query.getResultList().isEmpty());

    }

    /**
     * Get projects that contain search string in the title
     *
     * @param searchString search string
     * @return List of projects that fit search criteria
     */
    public List<Project> searchByName(String searchString) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE upper(c.title) like :searchString");
        query.setParameter("searchString", "%" + searchString.toUpperCase() + "%");
        return query.getResultList();
    }
}
