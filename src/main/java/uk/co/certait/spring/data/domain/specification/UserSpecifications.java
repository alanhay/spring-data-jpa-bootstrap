package uk.co.certait.spring.data.domain.specification;

import uk.co.certait.spring.data.domain.Gender;
import uk.co.certait.spring.data.domain.QUser;

import com.mysema.query.types.expr.BooleanExpression;

public class UserSpecifications {

	public static BooleanExpression userHasGender(Gender gender) {
		return QUser.user.gender.eq(gender);
	}

	public static BooleanExpression userLivesInCity(String city) {
		return QUser.user.address.town.eq(city);
	}
}
