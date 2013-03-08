/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        return em.createQuery("SELECT c FROM Project c WHERE c.organization.organizationName = :name")
                .setParameter("name", organizationName)
                .getResultList(); 
    }

    public boolean projectExist(String name) {
        return !(em.createQuery("SELECT c FROM Project c WHERE c.title = :name")
                .setParameter("name", name)
                .getResultList()
                .isEmpty());
    }
}
