package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.models.Role;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.fabriquita.nucleus.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ResourceRepository resourceRepository;
    
    public List<Role> list() {
        return Lists.newLinkedList(roleRepository.findAll());
    }

    public Role get(Long id) {
        return roleRepository.findOne(id);
    }

    public Role add(String name) {
        Role role = new Role();
        role.setName(name);
        return roleRepository.save(role);
    }

    public Role update(Long id, String name) {
        Role role = roleRepository.findOne(id);
        if (name != null) {
            role.setName(name);
        }
        return roleRepository.save(role);
    }

    public void delete(Long id) {
        Role role = roleRepository.findOne(id);
        roleRepository.delete(role);
    }

    public Role addResource(Long id, Long resourceId) {
        Role role = roleRepository.findOne(id);
        Resource resource = resourceRepository.findOne(resourceId);
        role.getResources().add(resource);
        return roleRepository.save(role);
    }

}
