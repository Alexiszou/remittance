/*
 * 中软万维 数金终端
 */

package com.dfans.vo;

public class SchoolVO {

	private java.lang.Integer qid;
	private java.lang.String qaccountName;
	private java.lang.Integer qaccountNo;
	private java.lang.String qchName;
	private java.lang.String qenName;
	private java.lang.String qcountry;
	private java.lang.String qlogoUrl;
	private java.lang.String qlocation;
	private java.lang.String qsortCode;
	private java.lang.String qibanno;
	private java.lang.String qbic;
	private java.lang.String qbankAddress;
	private java.lang.String qreferencing;
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

	public java.lang.String getQaccountName() {
		return this.qaccountName;
	}

	public void setQaccountName(java.lang.String value) {
		this.qaccountName = value;
	}

	public java.lang.Integer getQaccountNo() {
		return this.qaccountNo;
	}

	public void setQaccountNo(java.lang.Integer value) {
		this.qaccountNo = value;
	}

	public java.lang.String getQchName() {
		return this.qchName;
	}

	public void setQchName(java.lang.String value) {
		this.qchName = value;
	}

	public java.lang.String getQenName() {
		return this.qenName;
	}

	public void setQenName(java.lang.String value) {
		this.qenName = value;
	}

	public java.lang.String getQcountry() {
		return this.qcountry;
	}

	public void setQcountry(java.lang.String value) {
		this.qcountry = value;
	}

	public java.lang.String getQlogoUrl() {
		return this.qlogoUrl;
	}

	public void setQlogoUrl(java.lang.String value) {
		this.qlogoUrl = value;
	}

	public java.lang.String getQlocation() {
		return this.qlocation;
	}

	public void setQlocation(java.lang.String value) {
		this.qlocation = value;
	}

	public java.lang.String getQsortCode() {
		return this.qsortCode;
	}

	public void setQsortCode(java.lang.String value) {
		this.qsortCode = value;
	}

	public java.lang.String getQibanno() {
		return this.qibanno;
	}

	public void setQibanno(java.lang.String value) {
		this.qibanno = value;
	}

	public java.lang.String getQbic() {
		return this.qbic;
	}

	public void setQbic(java.lang.String value) {
		this.qbic = value;
	}

	public java.lang.String getQbankAddress() {
		return this.qbankAddress;
	}

	public void setQbankAddress(java.lang.String value) {
		this.qbankAddress = value;
	}

	public java.lang.String getQreferencing() {
		return this.qreferencing;
	}

	public void setQreferencing(java.lang.String value) {
		this.qreferencing = value;
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
	 * 
	 * @param tableAlias
	 * @return
	 */
	public String getAliasFields(String tableAlias) {
		String aFields;
		StringBuffer aFieldsBuf = new StringBuffer();
		aFieldsBuf.append(" ").append(tableAlias + ".ID, \n").append(tableAlias + ".AccountName, \n")
				.append(tableAlias + ".AccountNo, \n").append(tableAlias + ".ChName, \n")
				.append(tableAlias + ".EnName, \n").append(tableAlias + ".Country, \n")
				.append(tableAlias + ".LogoUrl, \n").append(tableAlias + ".Location, \n")
				.append(tableAlias + ".SortCode, \n").append(tableAlias + ".IBANNo, \n").append(tableAlias + ".BIC, \n")
				.append(tableAlias + ".BankAddress, \n").append(tableAlias + ".Referencing, \n")
				.append(tableAlias + ".CreatedTime, \n").append(tableAlias + ".UpdateTime \n");
		aFields = aFieldsBuf.toString();
		return aFields;
	}

}
