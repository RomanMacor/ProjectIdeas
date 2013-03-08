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
    private String organizationCurrentName;
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
        return organization;
    }

    public Organization edit(Organization organization) throws BusinessException {
         //Checks if Organization with such name already exists, 
        // only if the name is not the same as before edit
        if(!organizationCurrentName.equals(organization.getOrganizationName()) &&
                ejbOrgFacade.OrganizationExist(organization.getOrganizationName())){
            throw new BusinessException("Organization with name " +
                    organization.getOrganizationName() + " already exist");
        }
        ejbOrgFacade.edit(organization);
        return organization;
    }
}
