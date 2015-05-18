package org.fabriquita.nucleus.services;

import java.util.LinkedList;
import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.User;
import org.fabriquita.nucleus.repositories.GroupRepository;
import org.fabriquita.nucleus.repositories.UserRepository;
import org.fabriquita.nucleus.shiro.ShiroSecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    public List<Group> list() {
        return Lists.newLinkedList(groupRepository.findAll());
    }

    public Group get(Long id) {
        return groupRepository.findByIdAndIdIn(id, getVisibilityGroupsIds());
    }

    public Group add(String name, Long parentId, String description) {
        Group group = new Group();
        Group parent = null;
        if (parentId != null) {
            parent = groupRepository.findOne(parentId);
            if (!isVisibleDownAndGroup(getVisibilityGroup(), parent)) {
                parent = null;
            }
        }
        group.setName(name);
        group.setDescription(description);
        if (parent == null) {
            group.setLevel(0L);
        } else {
            group.setLevel(parent.getLevel() + 1);
        }
        group.setParent(parent);
        group.setActive(true);
        return groupRepository.save(group);
    }

    public Group update(Long id, String name, Long parentId, String description, Boolean active) {
        Group group = groupRepository.findOne(id);
        Group parent = null;
        if (parentId != null) {
            parent = groupRepository.findOne(parentId);
            if (!isVisibleDownAndGroup(getVisibilityGroup(), parent)) {
                parent = null;
            }
        }
        if (parent != null) {
            group.setLevel(parent.getLevel() + 1);
            group.setParent(parent);
        }
        if (name != null) {
            group.setName(name);
        }
        if (description != null) {
            group.setDescription(description);
        }
        if (active != null) {
          group.setActive(active);
        }
        return groupRepository.save(group);
    }

    public void delete(Long id) {
        Group group = groupRepository.findByIdAndIdIn(id, getVisibilityGroupsIds());
        group.setActive(false);
        groupRepository.save(group);
    }

    public Page<Group> list(Integer page, Integer size) {
        PageRequest pageRequest = new PageRequest(page, size);
        return groupRepository.findByIdIn(pageRequest, getVisibilityGroupsIds());
    }

    public List<Group> getDownGroups(Long id) {
        Group group = groupRepository.findOne(id);
        return getDownGroups(group);
    }

    public List<Group> getDownGroups(Group group) {
        List<Group> groups = new LinkedList<>();
        for (Group childGroup : group.getChildren()) {
            groups.add(childGroup);
            groups.addAll(getDownGroups(childGroup));
        }
        return groups;
    }

    public List<Group> getDownGroupsAndGroup(Group group) {
        List<Group> groups = getDownGroups(group);
        groups.add(group);
        return groups;
    }

    public List<Group> getUpGroups(Long id) {
        Group group = groupRepository.findOne(id);
        return getUpGroups(group);
    }

    public List<Group> getUpGroups(Group group) {
        List<Group> groups = new LinkedList<>();
        Group parent = group.getParent();
        if (parent != null) {
            groups.add(parent);
            groups.addAll(getUpGroups(parent));
        }
        return groups;
    }

    public List<Group> getUpGroupsAndGroup(Group group) {
        List<Group> groups = getUpGroups(group);
        groups.add(group);
        return groups;
    }

    public boolean isVisibleUp(Group currentGroup, Group group) {
        for (Group visibleGroup : getUpGroups(currentGroup)) {
            if (visibleGroup.getId() == group.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleUpAndGroup(Group currentGroup, Group group) {
        if (currentGroup.getId() == group.getId()) {
            return true;
        }
        return isVisibleUp(currentGroup, group);
    }

    public boolean isVisibleDown(Group currentGroup, Group group) {
        for (Group visibleGroup : getDownGroups(currentGroup)) {
            if (visibleGroup.getId() == group.getId()) {
                return true;
            }
        }
        return false;
    }

    public boolean isVisibleDownAndGroup(Group currentGroup, Group group) {
        if (currentGroup == null) {
            return false;
        }
        if (currentGroup.getId() == group.getId()) {
            return true;
        }
        return isVisibleDown(currentGroup, group);
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
        List<Group> groups = getDownGroupsAndGroup(getVisibilityGroup());
        return groups;
    }

    private List<Long> getVisibilityGroupsIds() {
        List<Long> ids = new LinkedList<>();
        for (Group group : getVisibilityGroups()) {
            ids.add(group.getId());
        }
        return ids;
    }
}
