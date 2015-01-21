package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.Group;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface GroupRepository extends CrudRepository<Group, Long> {
	public Group findByName(String name);
	public void delete(Group group);
}
