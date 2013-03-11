/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 * Entity class for organizations that create and manage project ideas. Contains
 * getters and setters methods
 *
 * @author Roman Macor
 */
@Entity
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    private String organizationName;
    @NotNull
    private String password;
    private String address;
    private String contactName;
    private String phoneNumber;
    private String email;
    @OneToMany(mappedBy = "organization")
    private Collection<Project> projects;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public void setProjects(Collection<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (organizationName != null ? organizationName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Organization)) {
            return false;
        }
        Organization other = (Organization) object;
        if ((this.organizationName == null && other.organizationName != null) || (this.organizationName != null && !this.organizationName.equals(other.organizationName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "roman.cwk.entity.Organization[ name=" + organizationName + " ]";
    }
}
