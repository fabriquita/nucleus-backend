package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.User;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
	public User findByName(String name);
}
