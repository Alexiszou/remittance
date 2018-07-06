package com.dfans.controller;

public class PageVO {
	private int page = 1;// 当前第几页
	private int rows = 15;// 每页显示记录数
	private String timeBegin; // 搜索开始时间
	private String timeEnd; // 搜索结束时间
	private String key; // 搜索关键字
	

	public PageVO() {
	}

	public PageVO(int page, int rows, String timeBegin, String timeEnd, String key) {
		super();
		this.page = page;
		this.rows = rows;
		this.timeBegin = timeBegin;
		this.timeEnd = timeEnd;
		this.key = key;
	}

	public int getPage() {
		return page;
	}

	public void setPageIndex(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getTimeBegin() {
		return timeBegin;
	}

	public void setTimeBegin(String timeBegin) {
		this.timeBegin = timeBegin;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
