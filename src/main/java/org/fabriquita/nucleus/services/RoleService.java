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

    public Role add(String name, Long groupId) {
        Role role = new Role();
        Group group = null;
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        role.setName(name);
        role.setGroup(group);
        return roleRepository.save(role);
    }

    public Role update(Long id, String name, Long groupId) {
        Role role = roleRepository.findOne(id);
        Group group = null; 
        if (name != null) {
            role.setName(name);
        }
        if (groupId != null) {
            group = groupRepository.findOne(groupId);
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

}
