package com.dfans.common.fileupload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FileAttach{
	
	@JsonProperty("id")
	private java.lang.Long id;
	@JsonProperty("name")
	private java.lang.String name;
	@JsonProperty("path")
	private java.lang.String path;
	@JsonProperty("attachType")
	private int attachType;
	
	public FileAttach(){}
	
	public FileAttach(String name,String path,int attachType)
	{
		this.name = name;
		this.path = path;
		this.attachType = attachType;
	}
	
	@JsonIgnore		
	public void setId(java.lang.Long value) {
		this.id = value;
	}
	
	@JsonIgnore
	public java.lang.Long getId() {
		return this.id;
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
	public void setPath(java.lang.String value) {
		this.path = value;
	}
	
	@JsonIgnore
	public java.lang.String getPath() {
		return this.path;
	}
	@JsonIgnore
	public int getAttachType() {
		return attachType;
	}
	@JsonIgnore
	public void setAttachType(int attachType) {
		this.attachType = attachType;
	}
	
}

