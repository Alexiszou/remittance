package com.dfans.work;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dfans.dao.PriceMapper;
import com.dfans.utils.BootUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 货币汇率调用示例代码 － 聚合数据 在线接口文档：http://www.juhe.cn/docs/23
 **/

@Service
public class UploadFile {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "eb698786206b8699f035944e537761ae";

	/**
	 * 字典表DAO
	 */
	@Autowired
	private PriceMapper priceMapper;

	public final static Map<String, String> RATEMAP = new HashMap<String, String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 6005075348133972031L;

		{
			put("英镑", "GBP");
			put("日元", "JPY");
			put("美元", "USD");
			put("澳大利亚元", "AUD");
			put("欧元", "EUR");
			put("加拿大元", "CAD");
			put("港币", "HKD");
			put("瑞士法郎", "CHF");
		}
	};

	

	public static void main(String[] args) {
		// getRequest1();
		sendUploadPost();
		getRate();
	}
	
	
	
	
	private static void sendUploadPost() {
		
		Map<String, String> map = new HashMap();
		String result = null;
		// String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";//
		// 请求接口地址
		String url = BootUtil.getPro("payurl");
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("busi_code", "UPLOAD_FILE");// APP Key
		params.put("merchant_no", BootUtil.getPro("merchant_no"));// 两种格式(0或者1,默认为0)
		params.put("sign", "441f39fe3efc170febc8cbfe3028a3a456d274060ed570f3ef5351c3d7bb8295");// 两种格式(0或者1,默认为0)
		params.put("sign_type", "SHA256");// 两种格式(0或者1,默认为0)
		params.put("terminal_no", BootUtil.getPro("terminal_no"));// 两种格式(0或者1,默认为0)
		
		
		

		
	}




	// 1.人民币牌价
	public static Map<String, String> getGHTrate() {
		Map<String, String> map = new HashMap();
		String result = null;
		// String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";//
		// 请求接口地址
		String url = BootUtil.getPro("payurl");
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("busi_code", "SEARCH_RATE");// APP Key
		params.put("merchant_no", BootUtil.getPro("merchant_no"));// 两种格式(0或者1,默认为0)
		params.put("sign", "441f39fe3efc170febc8cbfe3028a3a456d274060ed570f3ef5351c3d7bb8295");// 两种格式(0或者1,默认为0)
		params.put("sign_type", "SHA256");// 两种格式(0或者1,默认为0)
		params.put("terminal_no", BootUtil.getPro("terminal_no"));// 两种格式(0或者1,默认为0)

		try {
			Document doc = null;
			result = net(url, params, "GET");
			doc = DocumentHelper.parseText(result); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			Iterator iter = rootElt.elementIterator("currency_rates"); // 获取根节点下的子节点head
			// 遍历head节点
			while (iter.hasNext()) {

				Element recordEle = (Element) iter.next();
				Iterator iters = recordEle.elementIterator("currency_rate"); // 获取子节点head下的子节点script

				// 遍历Header节点下的Response节点
				while (iters.hasNext()) {

					Element itemEle = (Element) iters.next();

					String currency_type = itemEle.elementTextTrim("currency_type"); // 拿到head节点下的子节点title值
					System.out.println("currency_type:" + currency_type);

					String currency_name = itemEle.elementTextTrim("currency_name"); // 拿到head节点下的子节点title值
					System.out.println("currency_name:" + currency_name);

					String rate = itemEle.elementTextTrim("rate"); // 拿到head节点下的子节点title值
					System.out.println("rate:" + rate);

					if (RATEMAP.containsKey(currency_name)) {
						map.put(RATEMAP.get(currency_name), rate);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	private static Map<String, String> getRate() {
		Map resultMap = new HashMap();

		String result = null;
		String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// APP Key
		params.put("type", "");// 两种格式(0或者1,默认为0)
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				JSONArray jsba = (JSONArray) object.get("result");

				JSONObject jsb = (JSONObject) jsba.get(0);
				Iterator<String> iterator = jsb.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					JSONObject value = jsb.getJSONObject(key);
					// 国家名
					String unit = value.getString("name");
					// 银行折算中间价
					String rate = value.getString("bankConversionPri");
					if (RATEMAP.containsKey(unit)) {
						resultMap.put(RATEMAP.get(unit), rate);
					}
				}
			} else {
				System.out.println(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	// 1.人民币牌价
	public static void getRequest1() {
		String result = null;
		String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// APP Key
		params.put("type", "");// 两种格式(0或者1,默认为0)

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {

				JSONArray jsba = (JSONArray) object.get("result");

				JSONObject jsb = (JSONObject) jsba.get(0);
				Iterator<String> iterator = jsb.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					JSONObject value = jsb.getJSONObject(key);

					String unit = value.getString("name");
					String rate = value.getString("bankConversionPri");

					System.out.println(key + "--->" + value);
					System.out.println("---->" + unit + "--->" + rate);
				}

				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 2.外汇汇率
	public static void getRequest2() {
		String result = null;
		// https://epay.gaohuitong.com/entry.do?busi_code=SEARCH_RATE&merchant_no=102100000125&sign=441f39fe3efc170febc8cbfe3028a3a456d274060ed570f3ef5351c3d7bb8295&sign_type=SHA256&terminal_no=20000147
		String url = "http://web.juhe.cn:8080/finance/exchange/frate";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// APP Key
		params.put("type", "");// 两种格式(0或者1,默认为0)

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":" + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 *
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	private static String net(String strUrl, Map<String, Object> params, String method) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try (DataOutputStream out = new DataOutputStream(conn.getOutputStream())) {
					out.writeBytes(urlencode(params));
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	private static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * 读取并解析XML文档 SAXReader就是一个管道，用一个流的方式，把xml文件读出来 SAXReader reader = new
	 * SAXReader(); User.hbm.xml表示你要解析的xml文档 下面的是通过解析xml字符串的
	 * 
	 * @param xml
	 */
	public static Map<String, String> readStringXml(String xml) {
		Map<String, String> map = new HashMap();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			Iterator iter = rootElt.elementIterator("currency_rates"); // 获取根节点下的子节点head
			// 遍历head节点
			while (iter.hasNext()) {

				Element recordEle = (Element) iter.next();
				Iterator iters = recordEle.elementIterator("currency_rate"); // 获取子节点head下的子节点script

				// 遍历Header节点下的Response节点
				while (iters.hasNext()) {

					Element itemEle = (Element) iters.next();

					String currency_type = itemEle.elementTextTrim("currency_type"); // 拿到head节点下的子节点title值
					System.out.println("currency_type:" + currency_type);

					String currency_name = itemEle.elementTextTrim("currency_name"); // 拿到head节点下的子节点title值
					System.out.println("currency_name:" + currency_name);

					String rate = itemEle.elementTextTrim("rate"); // 拿到head节点下的子节点title值
					System.out.println("rate:" + rate);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return map;
	}

}
