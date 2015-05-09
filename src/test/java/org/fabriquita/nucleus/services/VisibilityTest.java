package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.Application;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class VisibilityTest {

    @Autowired
    GroupService groupService;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testVisibilityFunctions() {
        Group group1 = groupService.get(1L);
        Group group2 = groupService.get(2L);
        Group group4 = groupService.get(4L);
        List<Group> group1groupsup = groupService.getUpGroups(group1);
        group1groupsup.add(group1);
        List<Group> group2groupsup = groupService.getUpGroups(group2);
        group2groupsup.add(group2);
        List<Group> group4groupsup = groupService.getUpGroups(group4);
        group4groupsup.add(group4);
        List<Group> group1groupsdown = groupService.getDownGroups(group1);
        group1groupsdown.add(group1);
        List<Group> group2groupsdown = groupService.getDownGroups(group2);
        group2groupsdown.add(group2);
        List<Group> group4groupsdown = groupService.getDownGroups(group4);
        group4groupsdown.add(group4);
        System.out.println("Group 1 users down:");
        for (User u : userRepository.findByGroupIn(group1groupsdown)) {
            System.out.println(u.getId());
        }
        System.out.println("Group 2 users down:");
        for (User u : userRepository.findByGroupIn(group2groupsdown)) {
            System.out.println(u.getId());
        }
        System.out.println("Group 4 users down:");
        for (User u : userRepository.findByGroupIn(group4groupsdown)) {
            System.out.println(u.getId());
        }
        System.out.println("Group 1 users up:");
        for (User u : userRepository.findByGroupIn(group1groupsup)) {
            System.out.println(u.getId());
        }
        System.out.println("Group 2 users up:");
        for (User u : userRepository.findByGroupIn(group2groupsup)) {
            System.out.println(u.getId());
        }
        System.out.println("Group 4 users up:");
        for (User u : userRepository.findByGroupIn(group4groupsup)) {
            System.out.println(u.getId());
        }
    }
}
