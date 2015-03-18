package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Resource;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ResourceRepository extends CrudRepository<Resource, Long> {
   
}
