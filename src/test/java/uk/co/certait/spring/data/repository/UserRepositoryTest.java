package uk.co.certait.spring.data.repository;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.certait.spring.data.domain.Gender;
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
	}

	@Test
	public void testPersistUser() {
		User user = new User();
		user.setForename("Alan");
		user.setSurname("Smith");
		user.setGender(Gender.M);
		user.setDateOfBirth(createDate(12, Calendar.AUGUST, 1970));
		user.setEmailAddress("alan@smith.net");

		repository.save(user);
		clearPersistenceContext();

		user = repository.findOne(user.getId());
		Assert.assertNotNull(user);
		Assert.assertEquals("Alan", user.getForename());
		Assert.assertEquals("Smith", user.getSurname());
	}

	@Test
	public void testFindByEmailAddress() {
		User user = repository.findByEmailAddress("jack@hamilton.net");
		Assert.assertNotNull(user);
		Assert.assertEquals("Jack", user.getForename());
		Assert.assertEquals("Hamilton", user.getSurname());
	}

	@Override
	public String[] getDataSetPaths() {
		return new String[] { "/data/users.xml" };
	}
}
