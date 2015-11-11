package org.fabriquita.nucleus.popdb;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.services.GroupService;
import org.fabriquita.nucleus.services.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ResourcePopDB {

    @Autowired
    ResourceService resourceService;

    @Autowired
    GroupService groupService;

    public void popDB() {
        String[] resources = {
                "user",
                "role",
                "resource",
                "group",
                "roleResource",
                "collection",
                "entity"
        };
        Group group1 = groupService.list().get(0);
        for (String resourceName : resources) {
            Resource resource = resourceService.add(resourceName, resourceName, group1.getId());
            System.out.println(resource.toMap());
        }
    }

}
