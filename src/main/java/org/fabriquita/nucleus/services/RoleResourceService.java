package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.models.RoleResource;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.fabriquita.nucleus.repositories.RoleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<RoleResource> list() {
        return Lists.newLinkedList(roleResourceRepository.findAll());
    }

    public RoleResource get(Long id) {
        return roleResourceRepository.findOne(id);
    }

    public RoleResource add(Long roleId, Long resourceId, String permissions) {
        RoleResource roleResource = new RoleResource();
        Role role = roleRepository.findOne(roleId);
        Resource resource = resourceRepository.findOne(resourceId);
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
        RoleResource roleResource = roleResourceRepository.findOne(id);
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
        RoleResource roleResource = roleResourceRepository.findOne(id);
        roleResourceRepository.delete(roleResource);
    }

}
