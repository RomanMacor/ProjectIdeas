/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.managedbeans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import roman.cwk.bus.BusinessException;
import roman.cwk.bus.ProjectService;
import roman.cwk.entity.Project;

/**
 * Controller, managed bean for project ideas. Adds error messages to the view
 * if necessary.
 *
 * @author Roman Macor
 */
@ManagedBean
//@RequestScoped
@SessionScoped
public class ProjectBean extends BaseBean {

    private Project project;
    private String searchString = "";
    @EJB
    private ProjectService ejbProjectService;

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

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    /**
     * Create new project according to user input.
     *
     * @return String that redirects to index page for logged users, or empty
     * string if the page can not be prepared.
     */
    public String create() {
        String organizationName = this.getLogedUsername();
        try {
            this.project = ejbProjectService.create(project, organizationName);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
            return "";
        }
        return "/project/index";
    }

    /**
     * Get project for user that is logged in.
     *
     * @return List of projects that belongs to the logged user.
     */
    public List getProjectsForLogedUser() {
        String organizationName = this.getLogedUsername();
        List projects = new ArrayList();
        try {
            projects = ejbProjectService.getProjectsForUser(organizationName);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
        }
        return projects;
    }

    /**
     * Prepare show project detail page.
     *
     * @param id id of project idea which detail is to be shown
     * @return String that redirects to the detail page, or empty string if the
     * page can not be prepared.
     */
    public String prepareDetail(Long id) {
        try {
            project = ejbProjectService.prepareDetail(id);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
            return "";
        }
        return "detail";
    }

    /**
     * Prepare create new project page.
     *
     * @return string that redirects to the create new project page, or empty
     * string if the page can not be prepared.
     */
    public String prepareCreate() {
        project = new Project();
        return "/project/create";
    }

    /**
     * Deletes project idea.
     *
     * @param projectId id of idea that is to be deleted.
     * @return string that redirects to index for logged user page.
     */
    public String detele(Long projectId) {
        try {
            ejbProjectService.detele(projectId);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
        }
        return "/project/index";
    }

    /**
     * Prepares edit project idea page.
     *
     * @param projectId id of the project that is to be edited
     * @return String that redirect to edit project idea page, or empty string
     * if the page can not be prepared.
     */
    public String prepareEdit(Long projectId) {
        try {
            this.project = ejbProjectService.prepareEdit(projectId);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
            return "";
        }
        return "/project/edit";
    }

    /**
     * Edit project idea
     *
     * @return String that redirect to the index page for logged users, or empty
     * string if the page can not be prepared.
     */
    public String edit() {
        try {
            project = ejbProjectService.edit(project);
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectBean.class.getName()).log(Level.SEVERE, null, ex);
            addErrorMessage(ex.getMessage());
            return "";
        }
        return "/project/index";
    }

    /**
     * Sets searchString to empty string.
     *
     * @return String that redirects to home page.
     */
    public String prepareList() {
        searchString = "";
        return "/index";
    }

    /**
     * Get projects that contain search String in the title
     *
     * @return List of projects which contains the searchString in the title
     */
    public List<Project> getProjectsByName() {
        return ejbProjectService.getProjectsByName(searchString);
    }

    /**
     * Refresh index page.
     *
     * @return String that redirect to index page.
     */
    public String searchByName() {
        return "/index";
    }
}
