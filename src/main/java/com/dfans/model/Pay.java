/*
 * 中软万维 数金终端
 */


package com.dfans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Pay{
	
	/**
	 * id
	 */
	@JsonProperty("id")
	private java.lang.Integer id;

	/**
	 * 费用-人民币
	 */
	@JsonProperty("priceCn")
	private Float priceCn;

	/**
	 * 费用-其他
	 */
	@JsonProperty("priceEn")
	private Float priceEn;

	/**
	 * 付款方式 1.银联，2其他--待定
	 */
	@JsonProperty("payStyle")
	private java.lang.Integer payStyle;

	/**
	 * 付款人姓名
	 */
	@JsonProperty("payUser")
	private java.lang.String payUser;

	/**
	 * 付款人联系电话
	 */
	@JsonProperty("payTel")
	private java.lang.Integer payTel;

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

	/**
	 * endDate
	 */
	@JsonProperty("endDate")
	private String endDate;


	public Pay(){}
	@JsonIgnore
	public void setID(java.lang.Integer value) {
		this.id = value;
	}
	
	@JsonIgnore
	public java.lang.Integer getID() {
		return this.id;
	}
	@JsonIgnore
	public void setPriceCN(Float value) {
		this.priceCn = value;
	}
	
	@JsonIgnore
	public Float getPriceCN() {
		return this.priceCn;
	}
	@JsonIgnore
	public void setPriceEN(Float value) {
		this.priceEn = value;
	}
	
	@JsonIgnore
	public Float getPriceEN() {
		return this.priceEn;
	}
	@JsonIgnore
	public void setPayStyle(java.lang.Integer value) {
		this.payStyle = value;
	}
	
	@JsonIgnore
	public java.lang.Integer getPayStyle() {
		return this.payStyle;
	}
	@JsonIgnore
	public void setPayUser(java.lang.String value) {
		this.payUser = value;
	}
	
	@JsonIgnore
	public java.lang.String getPayUser() {
		return this.payUser;
	}
	@JsonIgnore
	public void setPayTel(java.lang.Integer value) {
		this.payTel = value;
	}
	
	@JsonIgnore
	public java.lang.Integer getPayTel() {
		return this.payTel;
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
	public Float getPriceCn() {
		return priceCn;
	}
	@JsonIgnore
	public void setPriceCn(Float priceCn) {
		this.priceCn = priceCn;
	}
	@JsonIgnore
	public Float getPriceEn() {
		return priceEn;
	}
	@JsonIgnore
	public void setPriceEn(Float priceEn) {
		this.priceEn = priceEn;
	}
	@JsonIgnore
	public String getEndDate() {
		return endDate;
	}
	@JsonIgnore
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}

