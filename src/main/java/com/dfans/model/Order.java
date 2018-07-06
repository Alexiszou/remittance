/*
 * 中软万维 数金终端
 */

package com.dfans.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Order {

	/**
	 * 订单编号
	 */
	@JsonProperty("orderNo")
	private String orderNo;

	/**
	 * 关联-账户ID
	 */
	@JsonProperty("userId")
	private java.lang.Integer userId;

	/**
	 * 关联-学校ID
	 */
	@JsonProperty("schoolId")
	private java.lang.Integer schoolId;

	/**
	 * 关联-学生ID
	 */
	@JsonProperty("studentId")
	private java.lang.Integer studentId;

	/**
	 * 关联-支付信息ID
	 */
	@JsonProperty("payId")
	private java.lang.Integer payId;

	/**
	 * 解压缩地址
	 */
	@JsonProperty("orderSeq")
	private String orderSeq;

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

	public Order() {
	}

	@JsonIgnore
	public void setOrderNo(String value) {
		this.orderNo = value;
	}

	@JsonIgnore
	public String getOrderNo() {
		return this.orderNo;
	}

	@JsonIgnore
	public void setUserID(java.lang.Integer value) {
		this.userId = value;
	}

	@JsonIgnore
	public java.lang.Integer getUserID() {
		return this.userId;
	}

	@JsonIgnore
	public void setSchoolID(java.lang.Integer value) {
		this.schoolId = value;
	}

	@JsonIgnore
	public java.lang.Integer getSchoolID() {
		return this.schoolId;
	}

	@JsonIgnore
	public void setStudentID(java.lang.Integer value) {
		this.studentId = value;
	}

	@JsonIgnore
	public java.lang.Integer getStudentID() {
		return this.studentId;
	}

	@JsonIgnore
	public void setPayID(java.lang.Integer value) {
		this.payId = value;
	}

	@JsonIgnore
	public java.lang.Integer getPayID() {
		return this.payId;
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
	public String getOrderSeq() {
		return orderSeq;
	}

	@JsonIgnore
	public void setOrderSeq(String orderSeq) {
		this.orderSeq = orderSeq;
	}

}
