package uk.co.certait.spring.data.repository;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.certait.spring.data.domain.Address;
import uk.co.certait.spring.data.domain.Gender;
import uk.co.certait.spring.data.domain.QUser;
import uk.co.certait.spring.data.domain.Role;
import uk.co.certait.spring.data.domain.User;

public class UserRepositoryTest extends AbstractBaseDatabaseTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void testLoadUser() {
		User user = repository.findOne(1l);
		Assert.assertNotNull(user);
		Assert.assertEquals("Jim", user.getForename());
		Assert.assertEquals("Jones", user.getSurname());
		Assert.assertEquals("jim@jones.net", user.getEmailAddress());
		Assert.assertEquals(createDate(15, Calendar.JANUARY, 1970), user.getDateOfBirth());
		Assert.assertEquals(Gender.M, user.getGender());
		Assert.assertEquals(1, user.getRoles().size());
		Assert.assertEquals(createDate(9, Calendar.JANUARY, 2013), user.getRegistrationDate());
	}

	@Test
	public void testPersistUser() {
		User user = new User();
		user.setForename("Alan");
		user.setSurname("Smith");
		user.setGender(Gender.M);
		user.setDateOfBirth(createDate(12, Calendar.AUGUST, 1970));
		user.setEmailAddress("alan@smith.net");
		user.setPassword("12345678");
		user.setPhoneNumber("013146654321");

		Address address = new Address();
		address.setLineOne("Add Line One");
		address.setTown("Edinburgh");
		address.setPostCode("EH1 2HG");
		user.setAddress(address);
		user.setRegistrationDate(createDate(15, Calendar.APRIL, 2013));

		user.addRole(new Role("role_1"));
		user.addRole(new Role("role_2"));

		repository.save(user);

		flushPersistenceContext();
		clearPersistenceContext();

		user = repository.findOne(user.getId());
		Assert.assertNotNull(user);
		Assert.assertEquals("Alan", user.getForename());
		Assert.assertEquals("Smith", user.getSurname());
		Assert.assertEquals(2, user.getRoles().size());
	}

	@Test
	public void testFindByEmailAddress() {
		User user = repository.findOne(QUser.user.emailAddress.eq("jack@hamilton.net"));
		Assert.assertNotNull(user);
		Assert.assertEquals("Jack", user.getForename());
		Assert.assertEquals("Hamilton", user.getSurname());
	}

	@Test
	public void testFindByGender() {
		List<User> users = repository.findAll(QUser.user.gender.eq(Gender.M));
		Assert.assertEquals(4, users.size());

		users = repository.findAll(QUser.user.gender.eq(Gender.F));
		Assert.assertEquals(2, users.size());
	}

	@Test
	public void testFindByCity() {

		List<User> users = repository.findAll(QUser.user.address.town.eq("Edinburgh"));
		Assert.assertEquals(2, users.size());

		users = repository.findAll(QUser.user.address.town.eq("Stirling"));
		Assert.assertEquals(1, users.size());
	}

	@Test
	public void testFindByGenderAndCity() {
		List<User> users = repository.findAll(QUser.user.address.town.eq("Glasgow").and(QUser.user.gender.eq(Gender.M)));
		Assert.assertEquals(2, users.size());

		users = repository.findAll(QUser.user.address.town.eq("Glasgow").and(QUser.user.gender.eq(Gender.F)));
		Assert.assertEquals(1, users.size());
	}

	@Override
	public String[] getDataSetPaths() {
		return new String[] { "/data/users.xml", "/data/user_roles.xml" };
	}
}
