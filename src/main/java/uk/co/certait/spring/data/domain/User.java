package uk.co.certait.spring.data.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

	@NotEmpty
	@Length(min = 2, max = 25)
	@Column(name = "forename", nullable = false, length = 25)
	private String forename;

	@NotEmpty
	@Length(min = 2, max = 30)
	@Column(name = "surname", nullable = false, length = 30)
	private String surname;

	@NotEmpty
	@Length(min = 8, max = 50)
	@Column(name = "emailAddress", nullable = false, length = 50, unique = true)
	private String emailAddress;

	@NotEmpty
	@Length(min = 10, max = 15)
	@Column(name = "phone_number", nullable = false, length = 15)
	private String phoneNumber;

	@NotNull
	@Column(name = "gender", nullable = false, length = 1)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@Past
	@Column(name = "date_of_birth", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@NotEmpty
	@Length(min = 8)
	@Column(name = "password", nullable = false)
	private String password;

	@Transient
	private String passwordConfirmation;

	@Column(name = "password_expired", nullable = false)
	private boolean passwordExpired;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "registration_date", nullable = false)
	public Date registrationDate;

	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	@Valid
	@Embedded
	private Address address;

	@ElementCollection
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"user_id", "name" }))
	private Set<Role> roles;

	public User() {
		roles = new HashSet<Role>();
		gender = Gender.M;

		dateOfBirth = new Date();
	}

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber != null ? phoneNumber.replaceAll(" ", "") : null;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	public boolean isPasswordExpired() {
		return passwordExpired;
	}

	public void setPasswordExpired(boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public String getName() {
		return forename + " " + surname;
	}

	public String getNameReversed() {
		return surname + ", " + forename;
	}

	@Override
	public Set<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	@Override
	public String getUsername() {
		return emailAddress;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
