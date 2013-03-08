/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Terrorhunt
 */
public abstract class BaseBean {

    public void addErrorMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
        context.addMessage(null, facesMessage);
    }

    public String getLogedUsername() {
        String userName = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        return userName;
    }

    public String logout() {
        HttpSession session;
        session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
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
}
