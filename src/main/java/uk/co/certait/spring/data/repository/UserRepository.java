package uk.co.certait.spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.certait.spring.data.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findByEmailAddress(String emailAddress);
}
