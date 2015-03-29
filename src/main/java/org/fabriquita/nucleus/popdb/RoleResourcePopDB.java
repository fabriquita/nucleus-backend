package org.fabriquita.nucleus.popdb;

import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.services.ResourceService;
import org.fabriquita.nucleus.services.RoleResourceService;
import org.fabriquita.nucleus.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleResourcePopDB {

    @Autowired
    RoleResourceService roleResourceService;

    @Autowired
    ResourceService resourceService;
    
    @Autowired
    RoleService roleService;
    
    public void popDB() {
        for (Resource resource : resourceService.list()) {
            for (Role role : roleService.list()) {
                if (resource.getGroup().getId() == role.getGroup().getId()) {
                    RoleResource roleResource = roleResourceService.add(role.getId(), resource.getId(), "crudx");
                    System.out.println(roleResource.toMap());
                }
            }
        }
    }

}
