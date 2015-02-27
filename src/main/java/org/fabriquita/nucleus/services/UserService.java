package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

    public List<User> list() {
        return Lists.newLinkedList(userRepository.findAll());
    }

    public User get(Long id) {
        return userRepository.findOne(id);
    }

    public User add(String name, Long groupId) {
        User user = new User();
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        user.setName(name);
        user.setGroup(group);
        return userRepository.save(user);
    }

    public User update(Long id, String name, Long groupId) {
        User user = userRepository.findOne(id);
        if (name != null) {
            user.setName(name);
        }
        Group group = null;
        if(groupId != null){
            group = groupRepository.findOne(groupId);
        }
        if(group != null){
            user.setGroup(group);
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findOne(id);
        userRepository.delete(user);
    }

}
