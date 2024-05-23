package com.example.demo.domain;

import java.sql.Date;

public class User {

	private String userId;
	private String password;
	private String name;
	private String nickname;

	/*
	private String email;  // test@test.com
	private Integer sex;  // 0: man, 1: woman, 2: secret
	private String birthday;  // 1999-12-01
	private String phoneNumber;  // 010-1234-1234
	*/
	
	private String email="test@test.com";  
	private Integer sex=1;  
	private String birthday="2024-04-01";  
	private String phoneNumber="010-1234-1234"; 
	
	private Date regDate;  // 2024-03-31 14:20:31
	
	/*
	 * CREATE TABLE users (
		user_id VARCHAR2(20) NOT NULL,
		password VARCHAR2(20) NOT NULL,
		name VARCHAR2(20) NOT NULL,
		nickname VARCHAR2(20) NOT NULL,
		email VARCHAR2(30) NOT NULL,
		sex VARCHAR2(1) NOT NULL,
		birthday DATE NOT NULL,
		phone_number VARCHAR2(13) NOT NULL,
		reg_date DATE NOT NULL,
		PRIMARY KEY(user_id)
	);
	 * 
	 */
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", nickname=" + nickname
				+ ", email=" + email + ", sex=" + sex + ", birthday=" + birthday + ", phoneNumber=" + phoneNumber
				+ ", regDate=" + regDate + "]";
	}
}
