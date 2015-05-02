package org.fabriquita.nucleus.popdb;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.services.GroupService;
import org.fabriquita.nucleus.services.RoleService;
import org.fabriquita.nucleus.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserPopDB {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    GroupService groupService;

    public void popDB() {
        String firstName1 = "admin";
        String lastName1 = "admin";
        String userName1 = "admin";
        String userPassword1 = "admin";
        String email1 = "admin@nucleus.com";
        String firstName2 = "user";
        String lastName2 = "user";
        String userName2 = "user";
        String userPassword2 = "user";
        String email2 = "user@nucleus.com";
        Group group1 = groupService.list().get(0);
        Group group2 = groupService.list().get(1);
        Role role1 = roleService.list().get(0);
        Role role2 = roleService.list().get(1);
        User user1 = userService.add(firstName1, lastName1, userName1, userPassword1, group1.getId(), role1.getId(), email1, false);
        User user2 = userService.add(firstName2, lastName2, userName2, userPassword2, group2.getId(), role2.getId(), email2, true);
        System.out.println(user1.toMap());
        System.out.println(user2.toMap());
    }

}
