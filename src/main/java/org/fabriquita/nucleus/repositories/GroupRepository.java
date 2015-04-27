package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Group;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {
    public Group findByName(String name);

    public void delete(Group group);
}
