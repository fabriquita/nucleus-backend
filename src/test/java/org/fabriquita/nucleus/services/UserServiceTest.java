package org.fabriquita.nucleus.services;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void allTest() {
        String name1 = "user1";
        User user1 = userService.add(name1, null);
        User user2 = userService.get(user1.getId());
        Assert.assertEquals(user1.getId(), user2.getId());
    }

}
