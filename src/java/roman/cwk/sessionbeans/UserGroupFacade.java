/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.sessionbeans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import roman.cwk.entity.UserGroup;

/**
 * DAO, EJB user group facade
 *
 * @author Roman Macor
 */
@Stateless
public class UserGroupFacade extends AbstractFacade<UserGroup> {

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
    public UserGroupFacade() {
        super(UserGroup.class);
    }
}
