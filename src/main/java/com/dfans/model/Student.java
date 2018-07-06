/*
 * 中软万维 数金终端
 */

package com.dfans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

	/**
	 * 学生主键
	 */
	@JsonProperty("id")
	private java.lang.Integer id;

	/**
	 * 学生姓名
	 */
	@JsonProperty("chName")
	private java.lang.String chName;

	/**
	 * 姓名拼音
	 */
	@JsonProperty("pyName")
	private java.lang.String pyName;

	/**
	 * 身份证号
	 */
	@JsonProperty("cardNo")
	private java.lang.String cardNo;

	/**
	 * 学号
	 */
	@JsonProperty("referenceNo")
	private java.lang.String referenceNo;

	/**
	 * 备注
	 */
	@JsonProperty("remarks")
	private java.lang.String remarks;

	/**
	 * 身份证号地址
	 */
	@JsonProperty("cardNoUrl")
	private java.lang.String cardNoUrl;

	/**
	 * 护照地址 用
	 */
	@JsonProperty("passportUrl")
	private java.lang.String passportUrl;

	/**
	 * 留学凭证地址 用|区分
	 */
	@JsonProperty("voucherUrl")
	private java.lang.String voucherUrl;

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

	public Student() {
	}

	@JsonIgnore
	public void setID(java.lang.Integer value) {
		this.id = value;
	}

	@JsonIgnore
	public java.lang.Integer getID() {
		return this.id;
	}

	@JsonIgnore
	public void setChName(java.lang.String value) {
		this.chName = value;
	}

	@JsonIgnore
	public java.lang.String getChName() {
		return this.chName;
	}

	@JsonIgnore
	public void setPyName(java.lang.String value) {
		this.pyName = value;
	}

	@JsonIgnore
	public java.lang.String getPyName() {
		return this.pyName;
	}

	@JsonIgnore
	public void setCardNo(java.lang.String value) {
		this.cardNo = value;
	}

	@JsonIgnore
	public java.lang.String getCardNo() {
		return this.cardNo;
	}

	@JsonIgnore
	public void setReferenceNo(java.lang.String value) {
		this.referenceNo = value;
	}

	@JsonIgnore
	public java.lang.String getReferenceNo() {
		return this.referenceNo;
	}

	@JsonIgnore
	public void setRemarks(java.lang.String value) {
		this.remarks = value;
	}

	@JsonIgnore
	public java.lang.String getRemarks() {
		return this.remarks;
	}

	@JsonIgnore
	public void setVoucherUrl(java.lang.String value) {
		this.voucherUrl = value;
	}

	@JsonIgnore
	public java.lang.String getVoucherUrl() {
		return this.voucherUrl;
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
	public java.lang.String getCardNoUrl() {
		return cardNoUrl;
	}

	@JsonIgnore
	public void setCardNoUrl(java.lang.String cardNoUrl) {
		this.cardNoUrl = cardNoUrl;
	}

	@JsonIgnore
	public java.lang.String getPassportUrl() {
		return passportUrl;
	}

	@JsonIgnore
	public void setPassportUrl(java.lang.String passportUrl) {
		this.passportUrl = passportUrl;
	}

}
