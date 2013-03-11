/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roman.cwk.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Entity class for user group. For security restrains purposes. Contains
 * getters and setters methods.
 *
 * @author Roman Macor
 */
@Entity
public class UserGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @NotNull
    private String organizationName;
    @NotNull
    private String groupName;

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupName != null ? groupName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserGroup)) {
            return false;
        }
        UserGroup other = (UserGroup) object;
        if ((this.groupName == null && other.groupName != null) || (this.groupName != null && !this.groupName.equals(other.groupName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "roman.cwk.entity.UserGroup[ id=" + groupName + " ]";
    }
}
