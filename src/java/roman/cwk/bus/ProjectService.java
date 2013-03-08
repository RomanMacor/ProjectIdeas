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
 *
 * @author Terrorhunt
 */
@Stateless
public class ProjectService {

    @EJB
    private ProjectFacade ejbProjectFacade;
    @EJB
    private OrganizationFacade ejbOrganizationFacade;
    private String projectCurrentTitle;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

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

    public List<Project> getProjectsByName(String searchString) {
        return ejbProjectFacade.searchByName(searchString);
    }

    public List<Project> getProjectsForUser(String organizationName) throws BusinessException {
        if (organizationName == null || organizationName.equals("")) {
            throw new BusinessException("No user loged in");
        }
        return ejbProjectFacade.findProjectsForUser(organizationName);
    }

    public Project prepareDetail(Long id) throws BusinessException {
        Project project = ejbProjectFacade.find(id);
        if (project == null) {
            throw new BusinessException("Project not found");
        }
        return project;
    }

    public void detele(Long projectId) throws BusinessException {
        Project project = ejbProjectFacade.find(projectId);
        if(project == null){
            throw new BusinessException("Project not found");
        }
        ejbProjectFacade.remove(project);
    }
    public Project prepareEdit(Long projectId) throws BusinessException {
        Project project = ejbProjectFacade.find(projectId);
        if(project == null){
            throw new BusinessException("Project not found");
        }
        //Storing project title so that check whether it has changed during edit could be made
        projectCurrentTitle = project.getTitle();
        return project;
    }
    public Project edit(Project project) throws BusinessException{
        //Checks if project with such name already exists, 
        // only if the name is not the same as before edit
        if (!project.getTitle().equals(projectCurrentTitle) &&
                ejbProjectFacade.projectExist(project.getTitle())) {
            throw new BusinessException("Project with title " + project.getTitle()
                    + " already exists");
        }
        ejbProjectFacade.edit(project);
        return project;
    }
}
