package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.fabriquita.nucleus.repositories.RoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;
    
    @Autowired
    RoleResourceRepository roleResourceRepository;
    
    @Autowired
    GroupRepository groupRepository;
    
    public List<Role> list() {
        return Lists.newLinkedList(roleRepository.findAll());
    }

    public Role get(Long id) {
        return roleRepository.findOne(id);
    }

    public Role add(String name, String description, Long groupId) {
        Role role = new Role();
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        role.setName(name);
        role.setDescription(description);
        role.setGroup(group);
        return roleRepository.save(role);
    }

    public Role update(Long id, String name, String description, Long groupId) {
        Role role = roleRepository.findOne(id);
        Group group = null; 
        if (name != null) {
            role.setName(name);
        }
        if (description != null) {
            role.setDescription(description);
        }
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        if(group != null){
            role.setGroup(group);
        }
        return roleRepository.save(role);
    }
    
    public Role addRoleResource(Long roleId, Long resourceId, String permissions){
        Role role = roleRepository.findOne(roleId);
        Resource resource = resourceRepository.findOne(resourceId);
        RoleResource roleResource = new RoleResource();
        roleResource.setRole(role);
        roleResource.setResource(resource);
        roleResource.setCreate(permissions.contains("c"));
        roleResource.setRead(permissions.contains("r"));
        roleResource.setUpdate(permissions.contains("u"));
        roleResource.setDelete(permissions.contains("d"));
        roleResource.setExecute(permissions.contains("e"));
        roleResourceRepository.save(roleResource);
        return role;
    }

    public void delete(Long id) {
        Role role = roleRepository.findOne(id);
        roleRepository.delete(role);
    }

    public Page<Role> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return roleRepository.findAll(pageRequest);
    }

}
