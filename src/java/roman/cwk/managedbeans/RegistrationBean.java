/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import roman.cwk.entity.Organization;
import roman.cwk.entity.UserGroup;
import roman.cwk.sessionbeans.OrganizationFacade;
import roman.cwk.sessionbeans.UserGroupFacade;

/**
 *
 * @author Roman Macor
 */
@ManagedBean
//@RequestScoped
@SessionScoped
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

    public String register() {

        userGroup = new UserGroup();
        userGroup.setGroupName(USER_GROUP);
        userGroup.setOrganizationName(organization.getOrganizationName());

        ejbOrgFacade.create(organization);
        ejbGroupOrgFacade.create(userGroup);

        //TODO change to confirmation
        return "confirmation";
    }

    public String logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();
        return "/index";
    }

    public boolean isLoggedIn() {
        String userName = this.getLogedUsername();
        if (userName == null) {
            return false;
        } else {
            return true;
        }
    }

    public String getLogedUsername() {
        String userName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return userName;
    }

    public String prepareEditOrganization() {
        String organizationName = this.getLogedUsername();
        organization = ejbOrgFacade.find(organizationName);
        return "/project/edit_organization";
    }
    public String edit(){
        ejbOrgFacade.edit(organization);
        return "/project/index";
    }
}
