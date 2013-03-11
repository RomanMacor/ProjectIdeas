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
import roman.cwk.sessionbeans.OrganizationFacade;
import roman.cwk.sessionbeans.ProjectFacade;

/**
 * EJB, project service (business logic)
 *
 * @author Roman Macor
 */
@Stateless
public class ProjectService {

    @EJB
    private ProjectFacade ejbProjectFacade;
    @EJB
    private OrganizationFacade ejbOrganizationFacade;
    private String projectCurrentTitle = "";

    /**
     * Create new project in database
     *
     * @param project project to be created in database
     * @param organizationName name of the organization that created project
     * @return created project
     * @throws BusinessException if project with the same title already exists
     */
    public Project create(Project project, String organizationName) throws BusinessException {

        Organization organization = ejbOrganizationFacade.find(organizationName);
        project.setOrganization(organization);
        //Checks if project with such name already exists
        if (ejbProjectFacade.projectExist(project.getTitle())) {
            throw new BusinessException("Project with title " + project.getTitle()
                    + " already exists");
        }
        ejbProjectFacade.create(project);

        return project;
    }

    /**
     * Get projects that contain search string in title
     *
     * @param searchString search string
     * @return list of projects that fit the search criteria
     */
    public List<Project> getProjectsByName(String searchString) {
        return ejbProjectFacade.searchByName(searchString);
    }

    /**
     * Get projects for organization with specified name
     *
     * @param organizationName organization name
     * @return list of projects that were created by the organization
     * @throws BusinessException if no user is logged in
     */
    public List<Project> getProjectsForUser(String organizationName) throws BusinessException {
        if (organizationName == null || organizationName.equals("")) {
            throw new BusinessException("No user loged in");
        }
        return ejbProjectFacade.findProjectsForUser(organizationName);
    }

    /**
     * Prepare detail page for project.
     *
     * @param id id of project
     * @return project with specified id
     * @throws BusinessException if project with specified id doesn't exist
     */
    public Project prepareDetail(Long id) throws BusinessException {
        Project project = ejbProjectFacade.find(id);
        if (project == null) {
            throw new BusinessException("Project not found");
        }
        return project;
    }

    /**
     * Deletes project.
     *
     * @param projectId id of project that is to be deleted.
     * @throws BusinessException if project with such id doesn't exist
     */
    public void detele(Long projectId) throws BusinessException {
        Project project = ejbProjectFacade.find(projectId);
        if (project == null) {
            throw new BusinessException("Project not found");
        }
        ejbProjectFacade.remove(project);
    }

    /**
     * Prepare project edit page.
     *
     * @param projectId id of project which is to be edited.
     * @return project which is to be edited
     * @throws BusinessException if project with such id doesn't exist
     */
    public Project prepareEdit(Long projectId) throws BusinessException {
        Project project = ejbProjectFacade.find(projectId);
        if (project == null) {
            throw new BusinessException("Project not found");
        }
        //Storing project title so that check whether it has changed during edit could be made
        projectCurrentTitle = project.getTitle();
        return project;
    }

    /**
     * Update the project in database.
     *
     * @param project project to be updated
     * @return project to be updated
     * @throws BusinessException if project with such title already exists
     */
    public Project edit(Project project) throws BusinessException {
        //Checks if project with such name already exists, 
        // only if the name is not the same as before edit
        if (!project.getTitle().equals(projectCurrentTitle)
                && ejbProjectFacade.projectExist(project.getTitle())) {
            throw new BusinessException("Project with title " + project.getTitle()
                    + " already exists");
        }
        ejbProjectFacade.edit(project);
        return project;
    }
}
