/*
 * 中软万维 数金终端
 */

package com.dfans.vo;

import java.sql.Date;

public class StudentVO {
	

		private java.lang.Integer qid;
		private java.lang.String qchName;
		private java.lang.String qpyName;
		private java.lang.String qcardNo;
		private java.lang.String qreferenceNo;
		private java.lang.String qremarks;
		private java.lang.String qvoucherUrl;
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
		
		public java.lang.String getQchName() {
			return this.qchName;
		}
		
		public void setQchName(java.lang.String value) {
			this.qchName = value;
		}
		
		public java.lang.String getQpyName() {
			return this.qpyName;
		}
		
		public void setQpyName(java.lang.String value) {
			this.qpyName = value;
		}
		
		public java.lang.String getQcardNo() {
			return this.qcardNo;
		}
		
		public void setQcardNo(java.lang.String value) {
			this.qcardNo = value;
		}
		
		public java.lang.String getQreferenceNo() {
			return this.qreferenceNo;
		}
		
		public void setQreferenceNo(java.lang.String value) {
			this.qreferenceNo = value;
		}
		
		public java.lang.String getQremarks() {
			return this.qremarks;
		}
		
		public void setQremarks(java.lang.String value) {
			this.qremarks = value;
		}
		
		public java.lang.String getQvoucherUrl() {
			return this.qvoucherUrl;
		}
		
		public void setQvoucherUrl(java.lang.String value) {
			this.qvoucherUrl = value;
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
					.append(tableAlias+".ChName, \n")
					.append(tableAlias+".PyName, \n")
					.append(tableAlias+".CardNo, \n")
					.append(tableAlias+".ReferenceNo, \n")
					.append(tableAlias+".Remarks, \n")
					.append(tableAlias+".VoucherUrl, \n")
					.append(tableAlias+".CreatedTime, \n")
					.append(tableAlias+".UpdateTime \n");
			aFields = aFieldsBuf.toString();
			return aFields;
		}

}

