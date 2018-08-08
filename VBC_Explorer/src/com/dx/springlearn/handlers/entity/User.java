package com.dx.springlearn.handlers.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable{
   private Integer id;
   private String username;
   private String password;
   private String phone;
   private Date creationTime;
   private String state;
   private BigDecimal balance;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
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
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public Date getCreationTime() {
	return creationTime;
}
public void setCreationTime(Date creationTime) {
	this.creationTime = creationTime;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public BigDecimal getBalance() {
	return balance;
}
public void setBalance(BigDecimal balance) {
	this.balance = balance;
}
@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((balance == null) ? 0 : balance.hashCode());
	result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result + ((state == null) ? 0 : state.hashCode());
	result = prime * result + ((username == null) ? 0 : username.hashCode());
	return result;
}
@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (balance == null) {
		if (other.balance != null)
			return false;
	} else if (!balance.equals(other.balance))
		return false;
	if (creationTime == null) {
		if (other.creationTime != null)
			return false;
	} else if (!creationTime.equals(other.creationTime))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (password == null) {
		if (other.password != null)
			return false;
	} else if (!password.equals(other.password))
		return false;
	if (phone == null) {
		if (other.phone != null)
			return false;
	} else if (!phone.equals(other.phone))
		return false;
	if (state == null) {
		if (other.state != null)
			return false;
	} else if (!state.equals(other.state))
		return false;
	if (username == null) {
		if (other.username != null)
			return false;
	} else if (!username.equals(other.username))
		return false;
	return true;
}
@Override
public String toString() {
	return "User [id=" + id + ", username=" + username + ", password=" + password + ", phone=" + phone
			+ ", creationTime=" + creationTime + ", state=" + state + ", balance=" + balance + "]";
}
public User(Integer id, String username, String password, String phone, Date creationTime, String state,
		BigDecimal balance) {
	super();
	this.id = id;
	this.username = username;
	this.password = password;
	this.phone = phone;
	this.creationTime = creationTime;
	this.state = state;
	this.balance = balance;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}
   
   
}
