/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import roman.cwk.entity.Project;

/**
 *
 * @author Roman Macor
 */
@ManagedBean
@RequestScoped
public class ProjectBean {
    private Project project;
    /**
     * Creates a new instance of ProjectBean
     */
    public ProjectBean() {
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
    public String create(){
        return "confirmation";
    }
}
