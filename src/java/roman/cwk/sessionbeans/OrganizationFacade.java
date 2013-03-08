/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import roman.cwk.entity.Organization;

/**
 *
 * @author Roman Macor
 */
@Stateless
public class OrganizationFacade extends AbstractFacade<Organization> {
    @PersistenceContext(unitName = "ProjectIdeasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizationFacade() {
        super(Organization.class);
    }
    public boolean OrganizationExist(String name) {
        return !(em.createQuery("SELECT c FROM Project c WHERE c.title = :name")
                .setParameter("name", name)
                .getResultList()
                .isEmpty());
    }
    
}
