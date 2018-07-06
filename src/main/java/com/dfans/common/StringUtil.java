package com.dfans.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;


public class StringUtil {

	//首字母转小写
    public static String toLowerCaseFirstOne(String s)
    {
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    //首字母转大写
    public static String toUpperCaseFirstOne(String s)
    {
        if(Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }
    
    /*
	 * 获取数字字符串增1，不足位数前补0
	 * @param assignLength : 指定返回字数字字符串长度
	 */
	public static String getIncremIntValString(String src,int assignLength)
	{
		String strVal = "";
		try
		{
			int intVal = Integer.valueOf(src).intValue()+1;
			strVal = String.valueOf(intVal);
			for(int i=src.length()-1-strVal.length();i>=0;i--)
			{
				strVal = "0" + strVal;
			}
		}
		catch(Exception e)
		{
			for(int i=0;i<src.length();i++)
			{
				strVal = "0" + strVal;
			}
			e.printStackTrace();
		}
		return strVal;
	}
	
	private static Random random = new Random(255);
	//图片的上传随机事件名称
	public static String genStrByCurTime()
	{
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmsss");
		return df.format(c.getTime()) + getIntRandom(100000);
	}
	
	public static int getIntRandom(int maxIntVal)
	{
		return random.nextInt(maxIntVal);
	}
	public static String getCurDateStr()
	{
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = df.format(c.getTime());
		return str;
	}
	
	/** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
          
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    } 
    
    public static int getDaysByYear(int year)
    {
    	int days = new GregorianCalendar().isLeapYear(year) ? 366 : 365;
    	return days;
    }
}
