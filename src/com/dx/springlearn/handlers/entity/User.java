package com.dx.springlearn.handlers.entity;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId; // ID
	private String phoneNumber; // 电话号码 | 用户名
	private String password; // 密码
	private String gender; // 性别
	private String inviteCode; // 邀请码，默认为 123456789
	private String statusCode; // 状态码，默认为 1
	private String registerTime; // 注册时间
	private String lastLoginTime; // 上次登录时间
	private String realName; // 真是姓名
	private String idCard; // 身份证号
	private String level; // 等级
	private String inviter; // 邀请人

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getInviter() {
		return inviter;
	}

	public void setInviter(String inviter) {
		this.inviter = inviter;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", phoneNumber=" + phoneNumber + ", password=" + password + ", gender="
				+ gender + ", inviteCode=" + inviteCode + ", statusCode=" + statusCode + ", registerTime="
				+ registerTime + ", lastLoginTime=" + lastLoginTime + ", realName=" + realName + ", idCard=" + idCard
				+ ", level=" + level + ", inviter=" + inviter + "]";
	}

	public User(Integer userId, String phoneNumber, String password, String gender, String inviteCode, String statusCode,
			String registerTime, String lastLoginTime, String realName, String idCard, String level, String inviter) {
		super();
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.gender = gender;
		this.inviteCode = inviteCode;
		this.statusCode = statusCode;
		this.registerTime = registerTime;
		this.lastLoginTime = lastLoginTime;
		this.realName = realName;
		this.idCard = idCard;
		this.level = level;
		this.inviter = inviter;
	}

	public User(String phoneNumber, String password, String inviteCode, String registerTime, String inviter) {
		super();
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.inviteCode = inviteCode;
		this.registerTime = registerTime;
		this.inviter = inviter;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
