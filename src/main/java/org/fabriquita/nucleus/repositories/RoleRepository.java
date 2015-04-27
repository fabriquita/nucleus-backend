package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {
    public Role findByName(String name);
}
