/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import roman.cwk.entity.Organization;

/**
 * DAO, EJB organization facade
 *
 * @author Roman Macor
 */
@Stateless
public class OrganizationFacade extends AbstractFacade<Organization> {

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
    public OrganizationFacade() {
        super(Organization.class);
    }

    /**
     * Checks whether organization exists
     *
     * @param name organization name
     * @return true if organization exists
     */
    public boolean OrganizationExist(String name) {
        Query query = em.createQuery("SELECT c FROM Project c WHERE c.title = :name");
        query.setParameter("name", name);
        return !(query.getResultList().isEmpty());
    }
}
