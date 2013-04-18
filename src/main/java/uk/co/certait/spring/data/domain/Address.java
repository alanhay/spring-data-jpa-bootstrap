package uk.co.certait.spring.data.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Embeddable
public class Address {

	@NotEmpty
	@Length(min = 2, max = 40)
	@Column(name = "address_line_one", nullable = false, length = 40)
	private String lineOne;

	@Column(name = "address_line_two", nullable = true, length = 40)
	private String lineTwo;

	@NotEmpty
	@Length(min = 2, max = 30)
	@Column(name = "town", nullable = false, length = 30)
	@Index(name="user_town_index")
	private String town;

	@NotEmpty
	@Length(min=4, max=8)
	@Column(name = "post_code", nullable = false, length = 10)
	private String postCode;

	public String getLineOne() {
		return lineOne;
	}

	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}

	public String getLineTwo() {
		return lineTwo;
	}

	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode != null ? postCode.toUpperCase() : null;
	}
}
