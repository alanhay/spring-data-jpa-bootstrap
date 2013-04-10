package uk.co.certait.spring.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import uk.co.certait.spring.data.domain.User;

import com.mysema.query.types.Predicate;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QueryDslPredicateExecutor<User> {
	
	public User findOne(Predicate predicate);

	public List<User> findAll(Predicate predicate);
	
	public Page<User> findAll(Predicate predicate, Pageable p);
}
