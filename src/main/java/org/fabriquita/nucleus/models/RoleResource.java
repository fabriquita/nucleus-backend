package org.fabriquita.nucleus.models;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "role_resource0")
public class RoleResource extends Mappable {

    public static final String CREATE  = "c";
    public static final String READ    = "r";
    public static final String UPDATE  = "u";
    public static final String DELETE  = "d";
    public static final String EXECUTE = "x";

    @Id
    @GeneratedValue
    @Column(name = "role_resource_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    Role role;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    Resource resource;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    Group group;

    @Column(name = "create0", nullable = false)
    Boolean create;
    @Column(name = "read0", nullable = false)
    Boolean read;
    @Column(name = "update0", nullable = false)
    Boolean update;
    @Column(name = "delete0", nullable = false)
    Boolean delete;
    @Column(name = "execute0", nullable = false)
    Boolean execute;

    @Column(nullable = false)
    Boolean active;

    public RoleResource() {
        active = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        this.group = role.getGroup();
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public Boolean getExecute() {
        return execute;
    }

    public void setExecute(Boolean execute) {
        this.execute = execute;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Group getGroup() {
        return group;
    }

    public List<String> getRoleResourcePermissions() {
        List<String> permissions = new LinkedList<>();
        String permissionBase = resource.getName() + ":";
        if (this.create && this.read && this.update && this.delete
                && this.execute) {
            permissions.add(permissionBase + "*");
        } else {
            if (this.create) {
                permissions.add(permissionBase + CREATE);
            }
            if (this.read) {
                permissions.add(permissionBase + READ);
            }
            if (this.update) {
                permissions.add(permissionBase + UPDATE);
            }
            if (this.delete) {
                permissions.add(permissionBase + DELETE);
            }
            if (this.execute) {
                permissions.add(permissionBase + EXECUTE);
            }
        }
        return permissions;
    }

    @Override
    public String[] mappableFields() {
        return super.mappableFields();
    }

    @Override
    public Object mappableId() {
        return this.id;
    }

}
