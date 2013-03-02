/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

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
    
}
