/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import roman.cwk.entity.Project;
import roman.cwk.sessionbeans.ProjectFacade;

/**
 *
 * @author Roman Macor
 */
@ManagedBean
@RequestScoped
public class ProjectBean {

    private Project project;
    @EJB
    private ProjectFacade ejbFacade;

    /**
     * Creates a new instance of ProjectBean
     */
    public ProjectBean() {
    }

    public Project getProject() {
        if (project == null) {
            project = new Project();
        }
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String create() {
        ejbFacade.create(project);
        return "confirmation";
    }
}
