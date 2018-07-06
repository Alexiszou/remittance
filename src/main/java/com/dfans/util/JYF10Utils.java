package com.dfans.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JYF10Utils {

	public static List<Map> getStandardData(List<Map> data,String col,String name,String type)
	{
		List<Map> ret=new ArrayList();
		String[] tCol=col.split(",");
		String[] tName=name.split(",");
		for(int i=0;i<data.size();i++)
		{
			Map m=new HashMap();
			for(int k=1;k<tCol.length;k++)
			{
				if(data.get(i).get(tCol[k])==null)
				{
					m.put(tName[k],"--");
				}
				else
				{
					m.put(tName[k],data.get(i).get(tCol[k]));
				}
			}
			Map n=new HashMap();
			System.out.println(type);
			n.put(data.get(i).get(type).toString(),m);
			ret.add(n);
		}
		return ret;
	}
	
	public static List<Map> getStandardData1(List<Map> data,String col,String name,String type)
	{
		List<Map> ret=new ArrayList();
		String[] tCol=col.split(",");
		String[] tName=name.split(",");
		
		for(int i=1;i<tName.length;i++)
		{
			Map m=new LinkedHashMap();
			for(int k=0;k<data.size();k++)
			{
				if(data.get(k).get(tCol[i])==null)
				{
					m.put(data.get(k).get(type).toString(),"--");
				}
				else
				{
					m.put(data.get(k).get(type).toString(),data.get(k).get(tCol[i]));
				}
			}
			Map n=new HashMap();
			n.put(tName[i],m);
			ret.add(n);
		}
		return ret;
	}

}
