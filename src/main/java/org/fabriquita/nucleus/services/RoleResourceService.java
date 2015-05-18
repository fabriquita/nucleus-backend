package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.fabriquita.nucleus.repositories.RoleResourceRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class RoleResourceService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;
    
    @Autowired
    RoleResourceRepository roleResourceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    ResourceService resourceService;

    public List<RoleResource> list() {
        return Lists.newLinkedList(roleResourceRepository.findAll());
    }

    public RoleResource get(Long id) {
        return roleResourceRepository.findByIdAndGroupIn(id, getVisibilityGroups());
    }

    public RoleResource add(Long roleId, Long resourceId, String permissions) {
        RoleResource roleResource = new RoleResource();
        Role role = roleRepository.findByIdAndGroupIn(roleId, getVisibilityGroups());
        Resource resource = resourceRepository.findByIdAndGroupIn(resourceId, resourceService.getVisibilityGroups());
        roleResource.setRole(role);
        roleResource.setResource(resource);
        if (permissions != null) {
            roleResource.setCreate(permissions.contains("c"));
            roleResource.setRead(permissions.contains("r"));
            roleResource.setUpdate(permissions.contains("u"));
            roleResource.setDelete(permissions.contains("d"));
            roleResource.setExecute(permissions.contains("x"));
        }
        return roleResourceRepository.save(roleResource);
    }

    public RoleResource update(Long id, String permissions) {
        RoleResource roleResource = roleResourceRepository.findByIdAndGroupIn(id, getVisibilityGroups());
        if (permissions != null) {
            roleResource.setCreate(permissions.contains("c"));
            roleResource.setRead(permissions.contains("r"));
            roleResource.setUpdate(permissions.contains("u"));
            roleResource.setDelete(permissions.contains("d"));
            roleResource.setExecute(permissions.contains("x"));
        }
        return roleResourceRepository.save(roleResource);
    }

    public void delete(Long id) {
        RoleResource roleResource = roleResourceRepository.findByIdAndGroupIn(id, getVisibilityGroups());
        roleResourceRepository.delete(roleResource);
    }

    public Page<RoleResource> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return roleResourceRepository.findByGroupIn(pageRequest, getVisibilityGroups());
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

}
