package com.dfans.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.baomidou.kisso.common.encrypt.base64.Base64;

/*public class SHA256Utils {

	public static void main(String[] args) {
		System.out.println(getSHA256StrJava(
				"busi_code=SEARCH_RATE&currency_type=&merchant_no=102100000265&sign_type=SHA256&terminal_no=20000222"));
		System.out.println(getSHA256StrJava(
				"busi_code=SEARCH_RATE&merchant_no=102100000265&sign_type=SHA256&terminal_no=20000222"));
		System.out.println(getSHA256StrJava(
				"busi_code=search_rate&merchant_no=102100000265&sign_type=sha256&terminal_no=20000222"));

		System.out.println(getSHA256StrJava(
				"base64_memo=suLK1LG416LQxc+i&currency_type=RMB&notify_url=http://localhost:8080/paycenter/TestPayCenter/merchant31/notifyUrl.jsp&out_trade_no=1390550837829&partner=130&return_url=http://localhost:8080/paycenter/TestPayCenter/merchant31/returnUrl.jsp&sign_type=SHA256&total_fee=0.01&key=857e6g8y51b5k365f7v954s50u24h14w"));

		System.out.println(getSHA256StrJava(
				"busi_code=SEARCH_RATE&merchant_no=102100000125&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w"));
		System.out.println(getSHA256StrJava(
				"busi_code=SEARCH_RATE&currency_type=USD&merchant_no=102100000125&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w"));
		System.out.println(getSHA256StrJava(
				"busi_code=SEARCH_RATE&currency_type=CAD&merchant_no=102100000125&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w"));

		System.out.println(getSHA256StrJava(
				"amount=0.01&bank_code=ALIPAY&base64_memo=6K6i5Y2V5aSH5rOo&busi_code=PAY&currency_type=CNY&error_url=test.gaohuitong.com/staging/returnUrl.jsp&merchant_no=102100000125&notify_url=test.gaohuitong.com/staging/returnUrl.jsp&order_no=EB20171031001&product_name=%E4%BA%8C%E5%AE%9D%E6%94%AF%E4%BB%98&return_url=test.gaohuitong.com/staging/returnUrl.jsp&sett_currency_type=CNY&sign_type=SHA256&terminal_no=20000147&user_bank_card_no=13426271438&key=857e6g8y51b5k365f7v954s50u24h14w"));
		try { // sign=0b9162d9035694438775e9a5a0bcddbd1c20bbcc223fc63a2bf71963381b422b
			
			//https://epay.gaohuitong.com/entry.do?
			System.out.println(URLEncoder.encode("二宝支付", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(encodeBase64("订单备注"));

	}

	public static String encodeURL(String str) {
		String s = null;
		try {
			s = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}

	public static String encodeBase64(String str) {
		byte[] b = null;

		try {
			b = Base64.encode(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(b);

	}

	*//**
	 * 利用java原生的摘要实现SHA256加密
	 * 
	 * @param str
	 *            加密后的报文
	 * @return
	 *//*
	public static String getSHA256StrJava(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	*//**
	 * 将byte转为16进制
	 * 
	 * @param bytes
	 * @return
	 *//*
	private static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				// 1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

}*/
import java.security.MessageDigest;

public class SHA256Utils {

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String []arg){

		//System.out.println(SHA256Util.SHA256Encode("abc123", "gbk"));
	}

	public static String SHA256Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			if (charsetname == null || "".equals(charsetname))
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charsetname)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
