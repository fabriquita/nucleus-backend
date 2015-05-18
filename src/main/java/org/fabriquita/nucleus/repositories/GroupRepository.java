package org.fabriquita.nucleus.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {
    public Group findByName(String name);

    public void delete(Group group);

    public Page<Group> findByIdIn(Pageable pageable, List<Long> ids);
    public Group findByIdAndIdIn(Long id, List<Long> ids);
}
