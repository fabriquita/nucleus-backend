package org.fabriquita.nucleus.services;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    public List<Group> list() {
        return Lists.newLinkedList(groupRepository.findAll());
    }

    public Group get(Long id) {
        return groupRepository.findOne(id);
    }

    public Group add(String name, Long parentId) {
        Group group = new Group();
        Group parent = null;
        if (parentId != null) {
            parent = groupRepository.findOne(parentId);
        }
        group.setName(name);
        if (parent == null) {
            group.setLevel(0L);
        } else {
            group.setLevel(parent.getLevel() + 1);
        }
        group.setParent(parent);
        return groupRepository.save(group);
    }

    public Group update(Long id, String name, Long parentId) {
        Group group = groupRepository.findOne(id);
        Group parent = null;
        if (parentId != null) {
            parent = groupRepository.findOne(parentId);
        }
        group.setName(name);
        if (parent == null) {
            group.setLevel(0L);
        } else {
            group.setLevel(parent.getLevel() + 1);
        }
        group.setParent(parent);
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        Group group = groupRepository.findOne(id);
        groupRepository.delete(group);
    }

}
