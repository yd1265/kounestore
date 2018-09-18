package com.bookstore.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bookstore.domain.security.Authority;
import com.bookstore.domain.security.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,updatable=false)
private long id;
private String username;
private String password;
private String firstName;
private String lastName;
@Column(name="email",nullable=false,updatable=false)
private String email;
private String phone;
private boolean enabled=true;

@OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch=FetchType.EAGER)

@JsonIgnore
private Set<UserRole> userRoles=new HashSet<UserRole>();

@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
private List<UserShipping> userShippingList;


@OneToMany(cascade=CascadeType.ALL, mappedBy="user")
private List<UserPayment> userPaymentList;

public User() {
	
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
public Set<UserRole> getUserRoles() {
	return userRoles;
}
public void setUserRoles(Set<UserRole> userRoles) {
	this.userRoles = userRoles;
}

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
  Set<GrantedAuthority> authorites=new HashSet<GrantedAuthority>();
	  userRoles.forEach(user->authorites.add(new Authority(user.getRole().getName())));
	return authorites;
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
public List<UserShipping> getUserShippingList() {
	return userShippingList;
}
public void setUserShippingList(List<UserShipping> userShippingList) {
	this.userShippingList = userShippingList;
}
public List<UserPayment> getUserPaymentList() {
	return userPaymentList;
}
public void setUserPaymentList(List<UserPayment> userPaymentList) {
	this.userPaymentList = userPaymentList;
}


}
