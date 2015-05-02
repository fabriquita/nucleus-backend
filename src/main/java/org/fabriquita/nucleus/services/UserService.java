package org.fabriquita.nucleus.services;

import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    RoleRepository roleRepository;

    public List<User> list() {
        return Lists.newLinkedList(userRepository.findAll());
    }

    public User get(Long id) {
        return userRepository.findOne(id);
    }

    public User add(String firstName, String lastName, String userName, String password, Long groupId, Long roleId, String email, Boolean archived) {
        User user = new User();
        Group group = null;
        Role role = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        if (roleId != null) {
            role = roleRepository.findOne(roleId);
        }
        String hashedPassword = new Sha256Hash(password).toString();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(hashedPassword);
        user.setGroup(group);
        user.setRole(role);
        user.setEmail(email);
        user.setArchived(archived);
        return userRepository.save(user);
    }

    public User update(Long id, String firstName, String lastName, String userName, String password, String email, Long groupId, Long roleId, Boolean archived) {
        User user = userRepository.findOne(id);
        Group group = null;
        Role role = null;
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        if (userName != null) {
            user.setUserName(userName);
        }
        if (password != null) {
            String hashedPassword = new Sha256Hash(password).toString();
            user.setPassword(hashedPassword);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if(groupId != null){
            group = groupRepository.findOne(groupId);
        }
        if(group != null){
            user.setGroup(group);
        }
        if (roleId != null) {
            role = roleRepository.findOne(roleId);
        }
        if (role != null) {
            user.setRole(role);
        }
        if (archived != null) {
          user.setArchived(archived);
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findOne(id);
        user.setArchived(true);
        userRepository.save(user);
    }

    public User getLogin(String name, String password) {
        User user = userRepository.findByUserNameAndPassword(name, password);
        if(user == null) {
            user = userRepository.findByEmailAndPassword(name, password);
        }
        if (user != null) {
            user.setLastLogin();
            userRepository.save(user);
        }
        return user;
    }

    public Page<User> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return userRepository.findAll(pageRequest);
    }

}
