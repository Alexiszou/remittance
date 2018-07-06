package com.dfans.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * 字符串相关方法
 *
 */
public class StringUtil {

	public static String getUserId(HttpServletRequest request)
	{
		String userId="1";
		Cookie [] cookies = request.getCookies();  
		for(int i=0;cookies!=null && i<cookies.length;i++){  
            String cname = cookies[i].getName();  
            String cvalue = cookies[i].getValue(); 
            if(cname.equals("userId")){
            	userId=cvalue;
            }
        }  
		
		return userId;
	}
	public static String[] StrList(String valStr){
	    int i = 0;
	    String TempStr = valStr;
	    String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
	    valStr = valStr + ",";
	    while (valStr.indexOf(',') > 0)
	    {
	        returnStr[i] = valStr.substring(0, valStr.indexOf(','));
	        valStr = valStr.substring(valStr.indexOf(',')+1 , valStr.length());
	        
	        i++;
	    }
	    return returnStr;
	}
	
    public static String GBK2Unicode(String str) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char chr1 = str.charAt(i);
  
            if (!isNeedConvert(chr1)) {
                result.append(chr1);
                continue;
            }
  
            result.append("\\u" + Integer.toHexString(chr1));
        }
  
        return result.toString();
    }
    public static String GBK2UTF8(String str) {  	
    	
    	try {
    		return new String(str.getBytes("UTF-8"), "UTF-8");            
    	} catch(Exception e) {
    		
    	}
    	return str;
    }
    
	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}
	
	 public static String delHTMLTag(String htmlStr){ 
	        String regEx_html="<[^>]+>";      
	        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
	        Matcher m_html=p_html.matcher(htmlStr); 
	        htmlStr=m_html.replaceAll(""); 
	        return htmlStr.trim(); 
	    } 
      public static void main(String args[]) {
    	  System.out.println(delHTMLTag("<hello>abadddd</hello>"));
      }

}
