package com.dfans.utils;

import java.util.ArrayList;
import java.util.List;

public class MessageResult {
	
	private int total = 0;//数据记录数
	
	private List rows = new ArrayList();//数据记录
	
	//当前页
	private int currentPage = 0;
	//总页数
	private int pageCount = 0;
	
	public MessageResult()
	{
		
	}
	
	public MessageResult(int total, List rows, int currentPage, int pageCount) {
		super();
		this.total = total;
		this.rows = rows;
		this.currentPage = currentPage;
		this.pageCount = pageCount;
	}

	public MessageResult(int count, List list) {
		super();
		this.total = count;
		this.rows = list;
	}


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
}
