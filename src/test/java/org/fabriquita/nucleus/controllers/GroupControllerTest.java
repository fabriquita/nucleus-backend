package org.fabriquita.nucleus.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.junit.Test;
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
public class GroupControllerTest {

    @Autowired
    GroupRepository repository;

    RestTemplate restTemplate = new RestTemplate();

    String baseUrl;

    @Value("${local.server.port}")
    int port;

    @Test
    public void allTest() {
        baseUrl = "http://localhost:" + port + "/group/";

        // Create a root group with PUT request

        Map<String, Object> map = new HashMap<>();
        map.put("name", "Root");
        restTemplate.put(baseUrl, map);

        // Get the root Group

        Map<String, Object> group = (Map<String, Object>) restTemplate
                .getForObject(baseUrl, Vector.class).firstElement();

        Group root = new Group();
        root.setName((String) group.get("name"));
        root.setId(new Long((Integer) group.get("id")));
        root.setLevel(new Long((Integer) group.get("level")));

        // Get root by id

        Group rootGroup = restTemplate.getForObject(baseUrl + root.getId(),
                Group.class);
        assertEquals(root.getName(), rootGroup.getName());

        // Update it

        String nameToUpdate = "Root++";
        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("name", nameToUpdate);
        Group updatedGroup = restTemplate.postForObject(baseUrl + root.getId(),
                mp, Group.class);
        assertEquals(nameToUpdate, updatedGroup.getName());

        // Delete it

        restTemplate.delete(baseUrl + root.getId());
        Group deletedGroup = restTemplate.getForObject(baseUrl + root.getId(),
                Group.class);
        assertEquals(deletedGroup.getActive(), "true");
    }

}
