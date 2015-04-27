package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.RoleResource;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface RoleResourceRepository extends PagingAndSortingRepository<RoleResource, Long> {

}
