package org.fabriquita.nucleus.repositories;

import org.fabriquita.nucleus.models.NEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NEntityRepository extends  PagingAndSortingRepository<NEntity, Long> {
    
}
