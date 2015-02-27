package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Resource;
import org.fabriquita.nucleus.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class ResourceService {

    @Autowired
    ResourceRepository resourceRepository;

    public List<Resource> list() {
        return Lists.newLinkedList(resourceRepository.findAll());
    }

    public Resource get(Long id) {
        return resourceRepository.findOne(id);
    }

    public Resource add(String permissions) {
        Resource resource = new Resource();
        resource.setPermissions(permissions);
        return resourceRepository.save(resource);
    }

    public Resource update(Long id, String permissions) {
        Resource resource = resourceRepository.findOne(id);
        if (permissions != null) {
            resource.setPermissions(permissions);
        }
        return resourceRepository.save(resource);
    }

    public void delete(Long id) {
        Resource resource = resourceRepository.findOne(id);
        resourceRepository.delete(resource);
    }

}
