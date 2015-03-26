package org.fabriquita.nucleus.controllers;

import java.util.HashMap;
import java.util.Map;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.services.GroupService;
import org.fabriquita.nucleus.services.ResourceService;
import org.fabriquita.nucleus.services.RoleResourceService;
import org.fabriquita.nucleus.services.RoleService;
import org.fabriquita.nucleus.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class AuthControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleResourceService roleResourceService;

    RestTemplate restTemplate = new RestTemplate();

    String baseUrl;

    @Value("${local.server.port}")
    int port;

    @Test
    public void allTest() {
        baseUrl = "http://localhost:" + port + "/";

        // Create group, user, roles and resources
        Group rootGroup = groupService.add("Root", null, null);
        Role adminRole = roleService.add("admin", rootGroup.getId());
        Resource userResource = resourceService.add("user", rootGroup.getId());
        RoleResource adminUserRoleResource = roleResourceService.add(adminRole.getId(), userResource.getId(), "crudx");
        User rootUser = userService.add("root", "none", rootGroup.getId(), adminRole.getId());

        // Login
        Map<String, Object> map = new HashMap<>();
        map.put("user", rootUser.getName());
        map.put("password", rootUser.getPassword());

        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "login", map, String.class);
        System.out.println(response);

    }

}
