package uk.co.certait.spring.data.repository;

import java.util.List;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import uk.co.certait.spring.data.domain.User;

import com.mysema.query.types.Predicate;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, QueryDslPredicateExecutor<User> {

	public User findByEmailAddress(String emailAddress);

	public List<User> findAll(Predicate predicate);
}
