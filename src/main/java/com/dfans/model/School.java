/*
 * 中软万维 数金终端
 */

package com.dfans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class School {

	/**
	 * 学校主键
	 */
	@JsonProperty("id")
	private java.lang.Integer id;

	/**
	 * 收款账户
	 */
	@JsonProperty("accountName")
	private java.lang.String accountName;

	/**
	 * 收款账号
	 */
	@JsonProperty("accountNo")
	private String accountNo;

	/**
	 * 学校中文名
	 */
	@JsonProperty("chName")
	private java.lang.String chName;

	/**
	 * 学校英文名
	 */
	@JsonProperty("enName")
	private java.lang.String enName;

	/**
	 * 学校国家
	 */
	@JsonProperty("country")
	private java.lang.String country;

	/**
	 * 学校标志
	 */
	@JsonProperty("logoUrl")
	private java.lang.String logoUrl;

	/**
	 * 位置
	 */
	@JsonProperty("location")
	private java.lang.String location;

	/**
	 * 银行代号
	 */
	@JsonProperty("sortCode")
	private java.lang.String sortCode;

	/**
	 * 国际银行账号
	 */
	@JsonProperty("ibanno")
	private java.lang.String ibanno;

	/**
	 * 银行识别码
	 */
	@JsonProperty("bic")
	private java.lang.String bic;

	/**
	 * 0-系统添加，1-用户添加
	 */
	@JsonProperty("ctFlag")
	private Integer ctFlag;
	
	
	/**
	 * 银行地址
	 */
	@JsonProperty("bankAddress")
	private java.lang.String bankAddress;

	/**
	 * 备注
	 */
	@JsonProperty("referencing")
	private java.lang.String referencing;

	/**
	 * 银行名称
	 */
	@JsonProperty("bankName")
	private java.lang.String bankName;

	
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

	public School() {
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
	public void setAccountName(java.lang.String value) {
		this.accountName = value;
	}

	@JsonIgnore
	public java.lang.String getAccountName() {
		return this.accountName;
	}

	@JsonIgnore
	public void setAccountNo(String value) {
		this.accountNo = value;
	}

	@JsonIgnore
	public String getAccountNo() {
		return this.accountNo;
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
	public void setEnName(java.lang.String value) {
		this.enName = value;
	}

	@JsonIgnore
	public java.lang.String getEnName() {
		return this.enName;
	}

	@JsonIgnore
	public void setCountry(java.lang.String value) {
		this.country = value;
	}

	@JsonIgnore
	public java.lang.String getCountry() {
		return this.country;
	}

	@JsonIgnore
	public void setLogoUrl(java.lang.String value) {
		this.logoUrl = value;
	}

	@JsonIgnore
	public java.lang.String getLogoUrl() {
		return this.logoUrl;
	}

	@JsonIgnore
	public void setLocation(java.lang.String value) {
		this.location = value;
	}

	@JsonIgnore
	public java.lang.String getLocation() {
		return this.location;
	}

	@JsonIgnore
	public void setSortCode(java.lang.String value) {
		this.sortCode = value;
	}

	@JsonIgnore
	public java.lang.String getSortCode() {
		return this.sortCode;
	}

	@JsonIgnore
	public void setIBANNo(java.lang.String value) {
		this.ibanno = value;
	}

	@JsonIgnore
	public java.lang.String getIBANNo() {
		return this.ibanno;
	}

	@JsonIgnore
	public void setBIC(java.lang.String value) {
		this.bic = value;
	}

	@JsonIgnore
	public java.lang.String getBIC() {
		return this.bic;
	}

	@JsonIgnore
	public void setBankAddress(java.lang.String value) {
		this.bankAddress = value;
	}

	@JsonIgnore
	public java.lang.String getBankAddress() {
		return this.bankAddress;
	}

	@JsonIgnore
	public void setReferencing(java.lang.String value) {
		this.referencing = value;
	}

	@JsonIgnore
	public java.lang.String getReferencing() {
		return this.referencing;
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
	public java.lang.Integer getId() {
		return id;
	}

	@JsonIgnore
	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public java.lang.String getIbanno() {
		return ibanno;
	}

	@JsonIgnore
	public void setIbanno(java.lang.String ibanno) {
		this.ibanno = ibanno;
	}

	@JsonIgnore
	public java.lang.String getBic() {
		return bic;
	}

	@JsonIgnore
	public void setBic(java.lang.String bic) {
		this.bic = bic;
	}

	@JsonIgnore
	public Integer getCtFlag() {
		return ctFlag;
	}

	@JsonIgnore
	public void setCtFlag(Integer ctFlag) {
		this.ctFlag = ctFlag;
	}

	@JsonIgnore
	public java.lang.String getBankName() {
		return bankName;
	}

	@JsonIgnore
	public void setBankName(java.lang.String bankName) {
		this.bankName = bankName;
	}

}
