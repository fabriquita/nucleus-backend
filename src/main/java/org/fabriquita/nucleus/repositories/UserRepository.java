package org.fabriquita.nucleus.repositories;

import java.util.List;

import org.fabriquita.nucleus.models.Group;
import org.fabriquita.nucleus.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    public User findByFirstName(String firstName);
    public User findByUserName(String userName);
    public User findByEmail(String email);
    public User findByUserNameAndPassword(String userName, String password);
    public User findByEmailAndPassword(String email, String password);
    public List<User> findByGroupIn(List<Group> groups);
    public Page<User> findByGroupIn(Pageable pageable, List<Group> groups);
    public User findByIdAndGroupIn(Long id, List<Group> groups);
}
