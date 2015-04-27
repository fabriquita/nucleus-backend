package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface ResourceRepository extends PagingAndSortingRepository<Resource, Long> {
   
}
