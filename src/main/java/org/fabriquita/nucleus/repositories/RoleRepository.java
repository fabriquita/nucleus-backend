package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Role;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findByName(String name);
}
