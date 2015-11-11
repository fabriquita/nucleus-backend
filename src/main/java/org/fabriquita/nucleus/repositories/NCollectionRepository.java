package org.fabriquita.nucleus.repositories;

import org.fabriquita.nucleus.models.NCollection;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NCollectionRepository extends PagingAndSortingRepository<NCollection, Long> {

}
