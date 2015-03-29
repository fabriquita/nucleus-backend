package org.fabriquita.nucleus.popdb;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.services.GroupService;
import org.fabriquita.nucleus.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RolePopDB {

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;

    public void popDB() {
        String roleName1 = "Role 1";
        String roleName2 = "Role 2";
        Group group1 = groupService.list().get(0);
        Group group2 = groupService.list().get(1);
        Role role1 = roleService.add(roleName1, group1.getId());
        Role role2 = roleService.add(roleName2, group2.getId());
        System.out.println(role1.toMap());
        System.out.println(role2.toMap());
    }

}
