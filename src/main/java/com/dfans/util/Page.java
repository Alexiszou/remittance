package com.dfans.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Page {
	static int totalPage;		    //总页数
	static int showCount; 			//每页显示记录数
	static int totalResult = 0 ;   //总记录数
	static int start;			    //当前页
	static int pageStart;	        //分页开始索引
	private static Map<String, Object> map = new HashMap<String, Object>();
	
	public static Map<String, Object> getStart(@SuppressWarnings("rawtypes") List<Map> totalResultList ,String rowStr,String StartPage ){
		@SuppressWarnings("unchecked")
		
		Map<String, String> resultMap = totalResultList.get(0);
		Set<String> set = resultMap.keySet();
		Iterator<String> it = set.iterator();
		
		while(it.hasNext()) {
			totalResult =Integer.parseInt(String.valueOf(resultMap.get(it.next())));
		}
		String rowString = rowStr;
		
		if(rowString==null)
			showCount=10;
		else 
			showCount =Integer.parseInt(rowString);
		
		if(totalResult%showCount==0)
			totalPage = totalResult/showCount;
		else
			totalPage = totalResult/showCount+1;
		String StartIndex = StartPage;
		if(StartIndex==null||Integer.parseInt(StartIndex)==0)
			start=0;
		else
			start = Integer.parseInt(StartIndex)-1;
		pageStart = start * showCount;
		map.put("start", pageStart);
		map.put("rows", showCount);
		return map;
	}
	
}
