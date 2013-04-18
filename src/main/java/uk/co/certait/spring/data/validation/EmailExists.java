package uk.co.certait.spring.data.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailExistsValidator.class)
public @interface EmailExists {
	String message() default "Email doesn't exists";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
