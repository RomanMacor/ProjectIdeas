/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import roman.cwk.entity.Organization;
import roman.cwk.entity.UserGroup;
import roman.cwk.sessionbeans.OrganizationFacade;
import roman.cwk.sessionbeans.UserGroupFacade;

/**
 *
 * @author Roman Macor
 */
@ManagedBean
@RequestScoped
public class RegistrationBean {

    //all the organization will fall to this group
    private static final String USER_GROUP = "organization";
    
    private Organization organization;
    private UserGroup userGroup;
    private String repeatPassword;
    @EJB
    private OrganizationFacade ejbOrgFacade;
    @EJB
    private UserGroupFacade ejbGroupOrgFacade;

    /**
     * Creates a new instance of RegistrationBean
     */
    public RegistrationBean() {
    }

    public Organization getOrganization() {
        if (organization == null) {
            organization = new Organization();
        }
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

//    public UserGroup getUserGroup() {
//        if (userGroup == null) {
//            userGroup = new UserGroup();
//        }
//        return userGroup;
//    }
//
//    public void setUserGroup(UserGroup userGroup) {
//        this.userGroup = userGroup;
//    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
    public String register(){
        
        userGroup = new UserGroup();
        userGroup.setGroupName(USER_GROUP);
        userGroup.setOrganizationName(organization.getOrganizationName());
        
        ejbOrgFacade.create(organization);
        ejbGroupOrgFacade.create(userGroup);
        
        //TODO change to confirmation
        return "confirmation";
    }
}
