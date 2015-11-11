package org.fabriquita.nucleus.services;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.NCollection;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.NCollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class NCollectionService {
    
    @Autowired
    NCollectionRepository nCollectionRepository;
    
    @Autowired
    GroupRepository groupRepository;
    
    public Page<NCollection> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        //TODO: check group visibility
        return nCollectionRepository.findAll(pageRequest);
    }
    
    public NCollection add(String name, Long groupId) {
        //NCollection nCollection = new NCollection();
        NCollection nCollection = new NCollection();
        Group group = null;
        if (groupId != null) {
            //TODO: this method should be into the service instead of the repository
            group = groupRepository.findOne(groupId);
            //TODO: check group visibility
        }
        nCollection.setName(name);
        nCollection.setGroup(group);
        // set an collection as active by default
        nCollection.setActive(true);
        return nCollectionRepository.save(nCollection);
    }
}
