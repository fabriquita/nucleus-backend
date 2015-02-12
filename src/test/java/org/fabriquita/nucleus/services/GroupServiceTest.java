package org.fabriquita.nucleus.services;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class GroupServiceTest {

    @Autowired
    GroupService groupService;

    @Test
    public void allTest() {
        String name1 = "group1";
        String name2 = "group2";
        Group group1 = groupService.add(name1, null, null, null);
        Group group1_2 = groupService.get(group1.getId());
        Assert.assertEquals(group1.getId(), group1_2.getId());
        Group group2 = groupService.add(name2, group1.getId(), null, null);
        Assert.assertEquals(group1.getId(), group2.getParent().getId());
        String newName2 = "newGroup2";
        group2 = groupService.update(group2.getId(), newName2, group2
                .getParent().getId(), null, null);
        Assert.assertEquals(group2.getName(), newName2);
        groupService.delete(group2.getId());
        Group group2_2 = groupService.get(group2.getId());
        Assert.assertNull(group2_2);
        groupService.delete(group1.getId());
        Group group1_3 = groupService.get(group1.getId());
        Assert.assertNull(group1_3);
    }

}
