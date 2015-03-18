package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.RoleResource;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface RoleResourceRepository extends CrudRepository<RoleResource, Long> {

}
