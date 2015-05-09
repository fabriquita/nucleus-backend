package org.fabriquita.nucleus.services;

import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
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

    @Autowired
    GroupService groupService;

    public List<User> list() {
        return Lists.newLinkedList(userRepository.findAll());
    }

    public User get(Long id) {
        return userRepository.findByIdAndGroupIn(id, getVisibilityGroups());
    }

    public User add(String firstName, String lastName, String userName, String password, Long groupId, Long roleId, String email) {
        User user = new User();
        Group group = null;
        Role role = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
            if (!groupService.isVisibleDownAndGroup(getVisibilityGroup(), group)) {
                return null;
            }
        }
        if (roleId != null) {
            role = roleRepository.findOne(roleId);
            if (!groupService.isVisibleDownAndGroup(getVisibilityGroup(), role.getGroup())) {
                role = null;
            }
        }
        String hashedPassword = new Sha256Hash(password).toString();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(hashedPassword);
        user.setGroup(group);
        user.setRole(role);
        user.setEmail(email);
        user.setActive(true);
        return userRepository.save(user);
    }

    public User update(Long id, String firstName, String lastName, String userName, String password, String email, Long groupId, Long roleId, Boolean active) {
        User user = userRepository.findByIdAndGroupIn(id, getVisibilityGroups());
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
            if (!groupService.isVisibleDownAndGroup(getVisibilityGroup(), group)) {
                group = null;
            }
        }
        if(group != null){
            user.setGroup(group);
        }
        if (roleId != null) {
            role = roleRepository.findOne(roleId);
            if (!groupService.isVisibleDownAndGroup(getVisibilityGroup(), role.getGroup())) {
                role = null;
            }
        }
        if (role != null) {
            user.setRole(role);
        }
        if (active != null) {
          user.setActive(active);
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        User user = userRepository.findByIdAndGroupIn(id, getVisibilityGroups());
        user.setActive(false);
        userRepository.save(user);
    }

    public User getLogin(String name, String password) {
        User user = userRepository.findByUserNameAndPassword(name, password);
        if(user == null) {
            user = userRepository.findByEmailAndPassword(name, password);
        }
        if (user != null) {
            if (!user.getActive()) {
                user = null;
            } else {
                user.setLastLogin();
                userRepository.save(user);
            }
        }
        return user;
    }

    public Page<User> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return userRepository.findByGroupIn(pageRequest, getVisibilityGroups());
    }

    private Group getVisibilityGroup() {
        User currentUser = userRepository.findOne(ShiroSecurityUtils.getCurrentUserId());
        if (currentUser == null) {
            if (ShiroSecurityUtils.isPopdb()) {
                return groupRepository.findOne(1L);
            }
            return null;
        }
        return currentUser.getGroup();
    }

    private List<Group> getVisibilityGroups() {
        List<Group> groups = groupService.getDownGroupsAndGroup(getVisibilityGroup());
        return groups;
    }

    public User getByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
