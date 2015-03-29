package org.fabriquita.nucleus.popdb;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class GroupPopDB {

    @Autowired
    GroupService groupService;

    public void popDB() {
        String groupName1 = "Group 1";
        String groupDesc1 = "Group 1 Description";
        String groupName2 = "Group 2";
        String groupDesc2 = "Group 1 Description";
        Group group1 = groupService.add(groupName1, null, groupDesc1);
        Group group2 = groupService.add(groupName2, group1.getId(), groupDesc2);
        System.out.println(group1.toMap());
        System.out.println(group2.toMap());
    }

}
