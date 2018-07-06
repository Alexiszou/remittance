/*
 * 中软万维 数金终端
 */

package com.dfans.vo;

import java.sql.Date;

public class PayVO {
	

		private java.lang.Integer qid;
		private Long qpriceCn;
		private Long qpriceEn;
		private java.lang.Integer qpayStyle;
		private java.lang.String qpayUser;
		private java.lang.Integer qpayTel;
		private java.util.Date qcreatedTimeBegin;
		private java.util.Date qcreatedTimeEnd;
		private java.util.Date qupdateTimeBegin;
		private java.util.Date qupdateTimeEnd;

		public java.lang.Integer getQid() {
			return this.qid;
		}
		
		public void setQid(java.lang.Integer value) {
			this.qid = value;
		}
		
		public Long getQpriceCn() {
			return this.qpriceCn;
		}
		
		public void setQpriceCn(Long value) {
			this.qpriceCn = value;
		}
		
		public Long getQpriceEn() {
			return this.qpriceEn;
		}
		
		public void setQpriceEn(Long value) {
			this.qpriceEn = value;
		}
		
		public java.lang.Integer getQpayStyle() {
			return this.qpayStyle;
		}
		
		public void setQpayStyle(java.lang.Integer value) {
			this.qpayStyle = value;
		}
		
		public java.lang.String getQpayUser() {
			return this.qpayUser;
		}
		
		public void setQpayUser(java.lang.String value) {
			this.qpayUser = value;
		}
		
		public java.lang.Integer getQpayTel() {
			return this.qpayTel;
		}
		
		public void setQpayTel(java.lang.Integer value) {
			this.qpayTel = value;
		}
		
		public java.util.Date getQcreatedTimeBegin() {
			return this.qcreatedTimeBegin;
		}
		
		public void setQcreatedTimeBegin(java.util.Date value) {
			this.qcreatedTimeBegin = value;
		}
		
		public java.util.Date getQcreatedTimeEnd() {
			return this.qcreatedTimeEnd;
		}
		
		public void setQcreatedTimeEnd(java.util.Date value) {
			this.qcreatedTimeEnd = value;
		}
		
		public java.util.Date getQupdateTimeBegin() {
			return this.qupdateTimeBegin;
		}
		
		public void setQupdateTimeBegin(java.util.Date value) {
			this.qupdateTimeBegin = value;
		}
		
		public java.util.Date getQupdateTimeEnd() {
			return this.qupdateTimeEnd;
		}
		
		public void setQupdateTimeEnd(java.util.Date value) {
			this.qupdateTimeEnd = value;
		}
		
	
		/**
		 * 获取以tableAlias为Table别名的字段序列 
		 * @param tableAlias
		 * @return
		 */
		public String getAliasFields(String tableAlias){
			String aFields;
			StringBuffer aFieldsBuf = new StringBuffer();
			aFieldsBuf.append(" ")
					.append(tableAlias+".ID, \n")
					.append(tableAlias+".PriceCN, \n")
					.append(tableAlias+".PriceEN, \n")
					.append(tableAlias+".PayStyle, \n")
					.append(tableAlias+".PayUser, \n")
					.append(tableAlias+".PayTel, \n")
					.append(tableAlias+".CreatedTime, \n")
					.append(tableAlias+".UpdateTime \n");
			aFields = aFieldsBuf.toString();
			return aFields;
		}

}

