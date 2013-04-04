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
	
	public static BooleanExpression userHasEmailAddress(String emailAddress){
		return emailAddress == null ? QUser.user.emailAddress.isNull() : QUser.user.emailAddress.eq(emailAddress);
	}
	
	public static BooleanExpression userIsActive(){
		return QUser.user.deleted.eq(false);
	}
}
