/*
 * 中软万维 数金终端
 */

package com.dfans.vo;

import java.sql.Date;

public class OrderVO {
	

		private String qorderNo;
		private java.lang.Integer quserId;
		private java.lang.Integer qschoolId;
		private java.lang.Integer qstudentId;
		private java.lang.Integer qpayId;
		private java.util.Date qcreatedTimeBegin;
		private java.util.Date qcreatedTimeEnd;
		private java.util.Date qupdateTimeBegin;
		private java.util.Date qupdateTimeEnd;

		public String getQorderNo() {
			return this.qorderNo;
		}
		
		public void setQorderNo(String value) {
			this.qorderNo = value;
		}
		
		public java.lang.Integer getQuserId() {
			return this.quserId;
		}
		
		public void setQuserId(java.lang.Integer value) {
			this.quserId = value;
		}
		
		public java.lang.Integer getQschoolId() {
			return this.qschoolId;
		}
		
		public void setQschoolId(java.lang.Integer value) {
			this.qschoolId = value;
		}
		
		public java.lang.Integer getQstudentId() {
			return this.qstudentId;
		}
		
		public void setQstudentId(java.lang.Integer value) {
			this.qstudentId = value;
		}
		
		public java.lang.Integer getQpayId() {
			return this.qpayId;
		}
		
		public void setQpayId(java.lang.Integer value) {
			this.qpayId = value;
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
					.append(tableAlias+".OrderNo, \n")
					.append(tableAlias+".UserID, \n")
					.append(tableAlias+".SchoolID, \n")
					.append(tableAlias+".StudentID, \n")
					.append(tableAlias+".PayID, \n")
					.append(tableAlias+".CreatedTime, \n")
					.append(tableAlias+".UpdateTime \n");
			aFields = aFieldsBuf.toString();
			return aFields;
		}

}

