package uk.co.certait.spring.data.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(name = "forename", nullable = false, length = 25)
	private String forename;

	@Column(name = "surname", nullable = false, length = 30)
	private String surname;

	@Column(name = "emailAddress", nullable = false, length = 50)
	private String emailAddress;

	@Column(name = "gender", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "date_of_birth", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	
	@Column
	private String test;

	@Embedded
	private Address address;

	public String getForename() {
		return forename;
	}

	public void setForename(String forename) {
		this.forename = forename;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getName() {
		return forename + surname;
	}

	public String getNameReversed() {
		return surname + ", " + forename;
	}
}
