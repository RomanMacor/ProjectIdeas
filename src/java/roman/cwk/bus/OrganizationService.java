/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.bus;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import roman.cwk.entity.Organization;
import roman.cwk.entity.Project;
import roman.cwk.entity.UserGroup;
import roman.cwk.sessionbeans.OrganizationFacade;
import roman.cwk.sessionbeans.ProjectFacade;
import roman.cwk.sessionbeans.UserGroupFacade;

/**
 *
 * @author Terrorhunt
 */
@Stateless
public class OrganizationService {

    private static final String USER_GROUP = "organization";
    private UserGroup userGroup;
    @EJB
    private OrganizationFacade ejbOrgFacade;
    @EJB
    private UserGroupFacade ejbGroupOrgFacade;
    @EJB
    private ProjectFacade ejbProjectFacade;
    private String organizationCurrentName ="";
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

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
        
        //To check whether the name was changed durring edit
        organizationCurrentName = organization.getOrganizationName();
        return organization;
    }

    public Organization prepareEditOrganization(String organizationName) throws BusinessException {
        Organization organization;
        organization = ejbOrgFacade.find(organizationName);
        if (organization == null) {
            throw new BusinessException("Organization not found");
        }
        organizationCurrentName = organizationName;
        return organization;
    }

    public Organization edit(Organization organization) {
        ejbOrgFacade.edit(organization);
        return organization;
    }
    public Organization changePassword(String currentPassword, Organization organization) throws BusinessException{
        String actualCurrentPassword = ejbOrgFacade.find(organization.getOrganizationName()).getPassword();
        if(!actualCurrentPassword.equals(currentPassword)){
            throw new BusinessException("Invalid current password");
        }
        ejbOrgFacade.edit(organization);
        return organization;
    }
}
