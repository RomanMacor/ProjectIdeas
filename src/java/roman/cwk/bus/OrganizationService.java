/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.bus;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import roman.cwk.entity.Organization;
import roman.cwk.entity.UserGroup;
import roman.cwk.sessionbeans.OrganizationFacade;
import roman.cwk.sessionbeans.UserGroupFacade;

/**
 * EJB, organization service (business logic)
 *
 * @author Roman Macor
 */
@Stateless
public class OrganizationService {

    private static final String USER_GROUP = "organization";
    private UserGroup userGroup;
    @EJB
    private OrganizationFacade ejbOrgFacade;
    @EJB
    private UserGroupFacade ejbGroupOrgFacade;

    /**
     * Create organization in database.
     *
     * @param organization organization to be created in database
     * @return created organization
     * @throws BusinessException when name of the organization already exists in
     * database
     */
    public Organization register(Organization organization) throws BusinessException {

        if (ejbOrgFacade.OrganizationExist(organization.getOrganizationName())) {
            throw new BusinessException("Organization with name "
                    + organization.getOrganizationName() + " already exists");
        }
        userGroup = new UserGroup();
        userGroup.setGroupName(USER_GROUP);
        userGroup.setOrganizationName(organization.getOrganizationName());

        ejbOrgFacade.create(organization);
        ejbGroupOrgFacade.create(userGroup);

        return organization;
    }

    /**
     * Prepare edit view.
     *
     * @param organizationName name of organization that is to be edited.
     * @return organization that is to be edited
     * @throws BusinessException if organization doesn't exist
     */
    public Organization prepareEditOrganization(String organizationName) throws BusinessException {
        Organization organization;
        organization = ejbOrgFacade.find(organizationName);
        if (organization == null) {
            throw new BusinessException("Organization not found");
        }
        return organization;
    }

    /**
     * Update organization in database.
     *
     * @param organization organization to be updated
     * @return organization to be updated
     */
    public Organization edit(Organization organization) {
        ejbOrgFacade.edit(organization);
        return organization;
    }

    /**
     * Changes the password
     *
     * @param currentPassword current password
     * @param organization organization (with new password set)
     * @return organization with changed password
     * @throws BusinessException if current password doesn't match with the one
     * in the database
     */
    public Organization changePassword(String currentPassword, Organization organization) throws BusinessException {
        String actualCurrentPassword = ejbOrgFacade.find(organization.getOrganizationName()).getPassword();
        if (!actualCurrentPassword.equals(currentPassword)) {
            throw new BusinessException("Invalid current password");
        }
        ejbOrgFacade.edit(organization);
        return organization;
    }
}
