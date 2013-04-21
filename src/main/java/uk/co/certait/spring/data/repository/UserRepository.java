package uk.co.certait.spring.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import uk.co.certait.spring.data.domain.User;

import com.mysema.query.types.Predicate;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, QueryDslPredicateExecutor<User> {

	public User findOne(Predicate predicate);

	public List<User> findAll(Predicate predicate);

	public Page<User> findAll(Predicate predicate, Pageable p);

	@Query("select distinct u.surname from User u where deleted = 0 and u.surname like ?1 order by u.surname")
	public List<String> findUniqueUserSurnames(String term, Pageable pageable);

	@Query("select distinct u.address.town from User u where deleted = 0 and u.address.town like  ?1 order by u.address.town")
	public List<String> findUniqueUserLocations(String term, Pageable pageable);
}
