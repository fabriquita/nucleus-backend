package org.fabriquita.nucleus.controllers;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.services.GroupService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupService groupService;

    RestTemplate restTemplate = new RestTemplate();

    String baseUrl;

    @Value("${local.server.port}")
    int port;

    @Test
    public void allTest() {
        baseUrl = "http://localhost:" + port + "/user/";

        // Clear all the data

        userRepository.deleteAll();

        // Create a root user with PUT request

        Group rootGroup = groupService.add("Root", null, null, null);

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Root");
        map.put("group_id", rootGroup.getId());

        restTemplate.put(baseUrl, map);

        // Get the root user

        Map<String, Object> user = (Map<String, Object>) restTemplate
                .getForObject(baseUrl, Vector.class).firstElement();
        User root = new User();
        root.setName((String) user.get("name"));
        root.setId(new Long((Integer) user.get("id")));

        Group group = new Group();
        Map<String, Object> mp = (Map<String, Object>) user.get("group");
        group.setId(new Long(mp.get("id").toString()));
        group.setName((String) mp.get("name"));
        group.setLevel(new Long(mp.get("level").toString()));
        root.setGroup(group);

        // Get root by id

        User rootUser = restTemplate.getForObject(baseUrl + root.getId(),
                User.class);
        assertEquals(root.getName(), rootUser.getName());

        // Update it

        String nameToUpdate = "Root++";
        Map<String, Object> updateData = new HashMap<String, Object>();
        updateData.put("name", nameToUpdate);
        User updatedUser = restTemplate.postForObject(baseUrl + root.getId(),
                updateData, User.class);
        assertEquals(nameToUpdate, updatedUser.getName());

        // Delete it

        restTemplate.delete(baseUrl + root.getId());
        User deletedUser = restTemplate.getForObject(baseUrl + root.getId(),
                User.class);
        assertNull(deletedUser);

    }

}
