package com.dfans.utils;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class test {

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文

			// System.out.println(new String(mdInst.digest()));
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println(test.MD5("123456_shujin").toLowerCase());

		System.out.println(test.MD5("加密"));
		
		String  url = BootUtil.getPro("payurl");
		System.out.println(url);
		
		String amont = "0.01";
		String base64_memo = "备注";
		String busi_code = "PAY";
		String currency_type = "CNY";
		String error_url = BootUtil.getPro("error_url");
		String notify_url = BootUtil.getPro("notify_url");
		String order_no = "20171015183858463";
		String merchant_no = BootUtil.getPro("merchant_no");
		String product_name = BootUtil.getPro("product_name");
		String return_url = BootUtil.getPro("return_url");
		String sett_currency_type = "CNY";
		String sign_type = "SHA256";
		String terminal_no = BootUtil.getPro("terminal_no");
		String key = BootUtil.getPro("paykey");
		
		Map preSignmap = new LinkedHashMap();
		preSignmap.put("amount", amont);
		preSignmap.put("base64_memo", base64_memo);
		preSignmap.put("busi_code", busi_code);
		preSignmap.put("currency_type", currency_type);
		preSignmap.put("error_url", error_url);
		preSignmap.put("merchant_no", merchant_no);
		preSignmap.put("notify_url", notify_url);
		preSignmap.put("order_no", order_no);
		preSignmap.put("product_name", product_name);
		preSignmap.put("return_url", return_url);
		preSignmap.put("sett_currency_type", sett_currency_type);
		preSignmap.put("sign_type", sign_type);
		preSignmap.put("terminal_no", terminal_no);
		
		String preSign = BootUtil.urlencode_cn(preSignmap);
		String shaSign = preSign + "&key=" + BootUtil.getPro("paykey");
		System.out.println(shaSign);
		//String sign = SHA256Utils.getSHA256StrJava(shaSign);
		String sign = SHA256Utils.SHA256Encode(shaSign,"UTF-8").toLowerCase();
		preSignmap.put("sign", sign);
		String urlParam = BootUtil.urlencode(preSignmap);
		
		
		System.out.println( BootUtil.getPro("payurl")+"?"+urlParam);
		
		
	}

}
