package com.bookstore.domain.security;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.bookstore.domain.User;

@Entity
public class PasswordResetToken {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private static final int EXPIRATION=60*24;
	@OneToOne(targetEntity=User.class,fetch=FetchType.EAGER)
	@JoinColumn(name="user_id",nullable=false)
	private  User user;
	
	private String token;
	
	private Date expiredDate;

	
	public PasswordResetToken(){
		
	}
	public PasswordResetToken(User user, String token) {
		super();
		this.user = user;
		this.token = token;
		this.expiredDate=calculateDate(EXPIRATION);
	}

	@SuppressWarnings("static-access")
	private Date calculateDate(int expiredtimeMinutes) {
		Calendar calendar=Calendar.getInstance();
		  calendar.setTimeInMillis(new java.util.Date().getTime());
		  calendar.add(calendar.MINUTE, expiredtimeMinutes);
		return new Date(calendar.getTime().getTime());
	}
	
	
	public void updateToken(final String token){
		this.token=token;
		this.expiredDate=calculateDate(EXPIRATION);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}

	public static int getExpiration() {
		return EXPIRATION;
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", user=" + user + ", token="
				+ token + ", expiredDate=" + expiredDate + "]";
	}
	
	
	
}
