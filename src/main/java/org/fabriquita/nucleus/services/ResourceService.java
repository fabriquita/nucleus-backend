package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class ResourceService {

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    GroupRepository groupRepository;
    
    public List<Resource> list() {
        return Lists.newLinkedList(resourceRepository.findAll());
    }

    public Resource get(Long id) {
        return resourceRepository.findOne(id);
    }

    public Resource add(String name, Long groupId) {
        Resource resource = new Resource();
        Group group = null;
        if(groupId != null){
            group = groupRepository.findOne(groupId);
        }
        resource.setName(name);
        resource.setGroup(group);
        return resourceRepository.save(resource);
    }

    public Resource update(Long id, String name, Long groupId) {
        Resource resource = resourceRepository.findOne(id);
        Group group = null;
        if ( name != null ){
            resource.setName(name);
        }
        if(groupId != null) {
            group = groupRepository.findOne(groupId);
        }
        if( group != null ){
            resource.setGroup(group);
        }
        return resourceRepository.save(resource);
    }

    public void delete(Long id) {
        Resource resource = resourceRepository.findOne(id);
        resourceRepository.delete(resource);
    }

}
