package org.fabriquita.nucleus.services;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.NCollection;
import org.fabriquita.nucleus.models.NEntity;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.NCollectionRepository;
import org.fabriquita.nucleus.repositories.NEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class NEntityService {
    
    @Autowired
    NEntityRepository nEntityRepository;
    
    @Autowired
    NCollectionRepository nCollectionRepository;
    
    @Autowired
    GroupRepository groupRepository;
    
    public Page<NEntity> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        //TODO: check group visibility
        return nEntityRepository.findAll(pageRequest);
    }
    
    public NEntity add(String name, String content, Long nCollectionId, Long groupId) {
        NEntity nEntity = new NEntity();
        Group group = null;
        NCollection nCollection = null;
        if (nCollectionId != null) {
            //TODO: this method should be into the service instead of the repository
            nCollection = nCollectionRepository.findOne(nCollectionId);
        }
        if (groupId != null) {
            //TODO: this method should be into the service instead of the repository
            group = groupRepository.findOne(groupId);
            //TODO: check group visibility
        }
        nEntity.setName(name);
        nEntity.setContent(content);
        nEntity.setNCollection(nCollection);
        nEntity.setGroup(group);
        // set an entity as active by default
        nEntity.setActive(true);
        return nEntityRepository.save(nEntity);
    }
}
