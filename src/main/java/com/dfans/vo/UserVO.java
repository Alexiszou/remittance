/*
 * 中软万维 数金终端
 */

package com.dfans.vo;

public class UserVO {
	

		private java.lang.Integer qid;
		private java.lang.String qemail;
		private java.lang.String qname;
		private String qtelphone;
		private java.lang.String qpassword;
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
		
		public java.lang.String getQemail() {
			return this.qemail;
		}
		
		public void setQemail(java.lang.String value) {
			this.qemail = value;
		}
		
		public java.lang.String getQname() {
			return this.qname;
		}
		
		public void setQname(java.lang.String value) {
			this.qname = value;
		}
		
		public String getQtelphone() {
			return this.qtelphone;
		}
		
		public void setQtelphone(String value) {
			this.qtelphone = value;
		}
		
		public java.lang.String getQpassword() {
			return this.qpassword;
		}
		
		public void setQpassword(java.lang.String value) {
			this.qpassword = value;
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
					.append(tableAlias+".Email, \n")
					.append(tableAlias+".Name, \n")
					.append(tableAlias+".Telphone, \n")
					.append(tableAlias+".PassWord, \n")
					.append(tableAlias+".CreatedTime, \n")
					.append(tableAlias+".UpdateTime \n");
			aFields = aFieldsBuf.toString();
			return aFields;
		}

}

