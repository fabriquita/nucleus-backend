package org.fabriquita.nucleus.repositories;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.RoleResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResource, Long> {

    public RoleResource findByIdAndGroupIn(Long id, List<Group> groups);
    public Page<RoleResource> findByGroupIn(Pageable pageable, List<Group> groups);
}
