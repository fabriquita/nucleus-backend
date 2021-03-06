package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class ResourceService {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupService groupService;

    public List<Resource> list() {
        return Lists.newLinkedList(resourceRepository.findAll());
    }

    public Resource get(Long id) {
        return resourceRepository.findByIdAndGroupIn(id, getVisibilityGroups());
    }

    public Resource add(String name, String description, Long groupId) {
        Resource resource = new Resource();
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
            if (!groupService.isVisibleUpAndDownAndGroup(getVisibilityGroup(), group)) {
                return null;
            }
        }
        resource.setName(name);
        resource.setDescription(description);
        resource.setGroup(group);
        return resourceRepository.save(resource);
    }

    public Resource update(Long id, String name, String description, Long groupId) {
        Resource resource = resourceRepository.findOne(id);
        Group group = null;
        if (name != null) {
            resource.setName(name);
        }
        if (description != null) {
            resource.setDescription(description);
        }
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
            if (!groupService.isVisibleUpAndDownAndGroup(getVisibilityGroup(), group)) {
                return null;
            }
        }
        if (group != null) {
            resource.setGroup(group);
        }
        return resourceRepository.save(resource);
    }

    public void delete(Long id) {
        Resource resource = resourceRepository.findByIdAndGroupIn(id, getVisibilityGroups());
        resourceRepository.delete(resource);
    }

    public Page<Resource> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return resourceRepository.findByGroupIn(pageRequest, getVisibilityGroups());
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

    public List<Group> getVisibilityGroups() {
        List<Group> groups = groupService.getUpAndDownGroupsAndGroup(getVisibilityGroup());
        return groups;
    }

}
