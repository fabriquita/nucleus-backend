package org.fabriquita.nucleus.repositories;

import javax.transaction.Transactional;

import org.fabriquita.nucleus.models.User;
import org.springframework.data.repository.PagingAndSortingRepository;

@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    public User findByName(String name);
    public User findByUserNameAndPassword(String userName, String password);
    public User findByEmailAndPassword(String email, String password);
}
