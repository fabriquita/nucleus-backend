package org.fabriquita.nucleus.repositories;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    public Role findByName(String name);

    public Page<Role> findByGroupIn(Pageable pageable, List<Group> groups);
    public Role findByIdAndGroupIn(Long id, List<Group> groups);
}
