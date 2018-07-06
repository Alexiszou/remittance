/*
 * 中软万维 数金终端
 */

package com.dfans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

	/**
	 * 主键
	 */
	@JsonProperty("id")
	private Long id;

	/**
	 * 邮箱地址
	 */
	@JsonProperty("email")
	private java.lang.String email;

	/**
	 * 来源 0 web 1 微信
	 */
	@JsonProperty("source")
	private String Source;

	/**
	 * 用户名
	 */
	@JsonProperty("name")
	private java.lang.String name;

	/**
	 * 电话号码
	 */
	@JsonProperty("telphone")
	private String telphone;

	/**
	 * 验证码
	 */
	@JsonProperty("captcha")
	private String captcha;

	/**
	 * 验证token
	 */
	@JsonProperty("ctoken")
	private String ctoken;

	/**
	 * 账号密码
	 */
	@JsonProperty("password")
	private java.lang.String password;

	/**
	 * 修改-原账号密码
	 */
	@JsonProperty("oldPassword")
	private java.lang.String oldPassword;

	/**
	 * 修改-新账号密码
	 */
	@JsonProperty("newPassword")
	private java.lang.String newPassword;

	/**
	 * createdTime
	 */
	@JsonProperty("createdTime")
	private java.util.Date createdTime;

	/**
	 * updateTime
	 */
	@JsonProperty("updateTime")
	private java.util.Date updateTime;

	public User() {
	}

	@JsonIgnore
	public void setID(Long value) {
		this.id = value;
	}

	@JsonIgnore
	public java.lang.Long getID() {
		return this.id;
	}

	@JsonIgnore
	public void setEmail(java.lang.String value) {
		this.email = value;
	}

	@JsonIgnore
	public java.lang.String getEmail() {
		return this.email;
	}

	@JsonIgnore
	public void setName(java.lang.String value) {
		this.name = value;
	}

	@JsonIgnore
	public java.lang.String getName() {
		return this.name;
	}

	@JsonIgnore
	public void setTelphone(String value) {
		this.telphone = value;
	}

	@JsonIgnore
	public String getTelphone() {
		return this.telphone;
	}

	@JsonIgnore
	public void setPassword(java.lang.String value) {
		this.password = value;
	}

	@JsonIgnore
	public java.lang.String getPassword() {
		return this.password;
	}

	@JsonIgnore
	public void setCreatedTime(java.util.Date value) {
		this.createdTime = value;
	}

	@JsonIgnore
	public java.util.Date getCreatedTime() {
		return this.createdTime;
	}

	@JsonIgnore
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	@JsonIgnore
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	@JsonIgnore
	public String getCaptcha() {
		return captcha;
	}

	@JsonIgnore
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	@JsonIgnore
	public String getCtoken() {
		return ctoken;
	}

	@JsonIgnore
	public void setCtoken(String ctoken) {
		this.ctoken = ctoken;
	}

	@JsonIgnore
	public java.lang.String getOldPassword() {
		return oldPassword;
	}

	@JsonIgnore
	public void setOldPassword(java.lang.String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@JsonIgnore
	public java.lang.String getNewPassword() {
		return newPassword;
	}

	@JsonIgnore
	public void setNewPassword(java.lang.String newPassword) {
		this.newPassword = newPassword;
	}

	@JsonIgnore
	public String getSource() {
		return Source;
	}

	@JsonIgnore
	public void setSource(String source) {
		Source = source;
	}

}
