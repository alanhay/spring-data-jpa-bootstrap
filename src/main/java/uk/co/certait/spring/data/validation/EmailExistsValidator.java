package uk.co.certait.spring.data.validation;

import javax.persistence.NoResultException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import uk.co.certait.spring.data.domain.QUser;
import uk.co.certait.spring.data.repository.UserRepository;

public class EmailExistsValidator implements ConstraintValidator<EmailExists, Object> {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(EmailExists constraint) {
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = NoResultException.class)
	public boolean isValid(Object emailAddress, ConstraintValidatorContext context){
		boolean valid = false;

		if (emailAddress != null) {
			try {
				valid = userRepository.findOne(QUser.user.emailAddress.eq((String) emailAddress)) != null;
			}
			catch (NoResultException nex) {
				valid = false;
			}
		}

		return valid;
	}
}
