package com.dfans.utils.GhtCommon;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.dfans.utils.BootUtil;

public class SendImageUtils {
	// //localhost
	// public final static String URL =
	// "http://localhost:8080/payment-gateway/fileEntry.do";
	// test service
	public final static String URL = BootUtil.getPro("uppayurl");

	public final static String BOUNDARY = "--------------Upload"; // 数据分隔线
	public static final String CHARSET = "utf-8";
	public final static String MULTIPART_FORM_DATA = "Multipart/form-data";
	public static final int SUCCESS = 200;

	public final static String MAP_KEY_REQ_URL = "reqUrl";
	public final static String MAP_KEY_REQ_FILES = "files";
	public final static String MAP_KEY_KEY = "key";

	public static String sendUpPost(Map<String, Object> map) {
		Map<String, File> fileMap = (Map<String, File>) map.get(MAP_KEY_REQ_FILES);
		String key = (String) map.get(MAP_KEY_KEY);
		// 1、Map除了"files"外，其他的均参与签名。
		// 1.1、移除files
		map.remove(MAP_KEY_REQ_FILES);
		// 1.2、将签名参数排序转成key=value用&的拼接形式
		String parmasString = SendImageUtils.paramsToStringSort(map);
		// System.out.println("将签名参数排序转成key=value用&的拼接形式:"+parmasString);
		// 1.3、计算签名和拼接请求参数字符串
		String requestParam = SendImageUtils.signatureParams(parmasString, key);
		System.out.println("【拼接请求参数字符串】:" + requestParam);
		// 1.4、拼接请求字符串（包含ip地址）
		String reqUrl = SendImageUtils.getReqUrl(URL, requestParam);
		System.out.println("【拼接请求地址字符串】:" + reqUrl);
		// 1.5、推送参数
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put(MAP_KEY_REQ_URL, reqUrl);
		reqMap.put(MAP_KEY_REQ_FILES, fileMap);
		return SendImageUtils.excuteHttpRequest(reqMap);
	}

	/**
	 * --------------------------------排序
	 * @author: kiwiw
	 * @date: 2017年11月9日 上午11:38:05
	 * @Description: TODO(map转stirng按key排序)
	 * @param params
	 * @return
	 */
	public static String paramsToStringSort(Map<String, Object> params) {
		if (params == null)
			return "";

		StringBuilder nStringBuilder = new StringBuilder();
		Set<String> nSet = params.keySet();

		/* 将Set集合转为List,这样获得的list并不能有序排列 */
		List<String> employeeList = new ArrayList<String>(nSet);
		/* 将list有序排列 */
		Collections.sort(employeeList, new Comparator<String>() {
			public int compare(String arg0, String arg1) {
				return arg0.compareTo(arg1);
			}
		});

		for (String string : employeeList) {
			String nValue = null;
			nValue = (String) params.get(string);
			if (StringUtils.isEmpty((string)) || StringUtils.isEmpty(nValue))
				continue;
			if ("key".equals(string)) {
				// MerchantParams.setMerchantKey(null, nValue);
				continue;
			}
			nStringBuilder.append(string).append("=").append(nValue).append("&");
		}
		if (nStringBuilder.length() > 0)
			nStringBuilder.delete(nStringBuilder.length() - 1, nStringBuilder.length());
		String nString = nStringBuilder.toString();
		nStringBuilder.delete(0, nStringBuilder.length());
		return nString;
	}

	/**
	 * ]
	 * 
	 * @author: kiwiw
	 * @date: 2017年11月9日 上午11:48:53
	 * @Description: TODO(对拼接参数进行签名)
	 * @param souceString
	 * @param key
	 * @return
	 */

	public static String signatureParams(String souceString, String key) {

		if (StringUtils.isEmpty(souceString) || StringUtils.isEmpty(key))
			return "";
		String destString = String.format("%s&key=%s", souceString, key);
		System.out.println("【请求参数签名字符串】:"+destString);

		try {
			destString = Digest.encode(destString, Digest.SHA256);
			destString = String.format("%s&sign=%s", souceString, destString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return destString;
		}
	}

	public static String getReqUrl(String url, String paramString) {
		String reqUrl = String.format("%s?%s", url, paramString);
		return reqUrl;
	}

	/**
	 * 
	 * @author: kiwiw
	 * @date: 2017年11月9日 下午2:31:23
	 * @Description: TODO(发送参数)
	 * @param reqMap
	 */
	public final static String excuteHttpRequest(Map<String, Object> reqMap) {

		String respXml = "";
		int returnFlg = 0;

		DataOutputStream os = null;
		InputStream is = null;
		HttpURLConnection conn = null;
		try {

			URL url = new URL((String) reqMap.get(MAP_KEY_REQ_URL));
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);// 不使用Cache
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000);// 连接超时 10s
			conn.setReadTimeout(60000);// 读超时 60s
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", CHARSET);
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY);
			os = new DataOutputStream(conn.getOutputStream());
			Map<String, File> files = (Map<String, File>) reqMap.get(MAP_KEY_REQ_FILES);

			if (files != null && files.size() > 0) {
				File file;
				for (Entry<String, File> fileEntry : files.entrySet()) {
					file = fileEntry.getValue();
					StringBuilder split = new StringBuilder();
					split.append("--");
					split.append(BOUNDARY);
					split.append("\r\n");
					split.append("Content-Disposition: form-data;name=\""
                            + fileEntry.getKey() + "\";filename=\""
                            + file.getName() + "\"\r\n");
					// split.append("enctype=\"multipart/form-data\"\r\n");
					split.append("Content-Type: application/octet-stream; charset=UTF-8\r\n\r\n");
					// NotesUtil.log(Constant.TAG, "upload:" +
					// split.toString());
					os.write(split.toString().getBytes());
					BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
					byte[] bytes = new byte[1024];
					int numReadByte = 0;
					while ((numReadByte = bufferedInputStream.read(bytes, 0, 1024)) > 0) {
						os.write(bytes, 0, numReadByte);
					}
					bufferedInputStream.close();
					os.write("\r\n".getBytes());
					break;
				}
			}

			byte[] end_data = ("--" + BOUNDARY + "--\r\n").getBytes();// 数据结束标志
			os.write(end_data);
			os.flush();
			int nResponseCode = conn.getResponseCode();
			/*is = conn.getInputStream();
			respXml = getStreamString(is);*/
			System.out.println(respXml);
			if (nResponseCode == SUCCESS)// 如果发布成功则提示成功HttpStatus.SC_OK
			{
				is = conn.getInputStream();
				respXml = getStreamString(is);
				System.out.println(respXml);
				returnFlg = 1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
				if (conn != null)
					conn.disconnect();
			} catch (Exception _ex) {
				returnFlg = 0;
			}
		}
		// httpResponseBean.init(Constant.JSON_ERROR_NETWORK);
		return respXml;
	}

	/**
	 * 将输入流转换为String
	 *
	 * @param tInputStream
	 * @return
	 */
	public static String getStreamString(InputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();

				String sTempOneLine;
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine);
				}

				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return "";
	}

	public static void main(String args[]) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busi_code", "UPLOAD_FILE");
		map.put("merchant_no", "102100000125");
		map.put("terminal_no", "20000147");
		map.put("req_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("order_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("pic_type", "01");
		map.put("sign_type", "SHA256");
		map.put("key", "857e6g8y51b5k365f7v954s50u24h14w");
		File flie1 = new File("C:\\Users\\kiwiw\\Desktop\\work\\temp\\test\\qrcode\\test.gif");
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("file1", flie1);
		map.put("files", fileMap);
		SendImageUtils.sendUpPost(map);

		getPayResult();
	}
	
	private static void getPayResult() {
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busi_code", "UPLOAD_FILE");
		map.put("merchant_no", "102100000125");
		map.put("terminal_no", "20000147");
		map.put("req_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("order_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("pic_type", "01");
		map.put("sign_type", "SHA256");
		map.put("key", "857e6g8y51b5k365f7v954s50u24h14w");
		SendImageUtils.sendUpPost(map);
		
		String key = "857e6g8y51b5k365f7v954s50u24h14w";
		
		String parmasString = SendImageUtils.paramsToStringSort(map);
		String requestParam = SendImageUtils.signatureParams(parmasString, key);
		String reqUrl = SendImageUtils.getReqUrl(URL, requestParam);
		
	}

	
	
	public static String formatXml(String str) throws Exception {
        SAXReader reader = new SAXReader();
        // System.out.println(reader);
        // 注释：创建一个串的字符输入流
        StringReader in = new StringReader(str);
        Document doc = reader.read(in);
        // System.out.println(doc.getRootElement());
        // 注释：创建输出格式
        OutputFormat formater = OutputFormat.createPrettyPrint();
        //formater=OutputFormat.createCompactFormat();
        // 注释：设置xml的输出编码
        formater.setEncoding("utf-8");
        // 注释：创建输出(目标)
        StringWriter out = new StringWriter();
        // 注释：创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        writer.write(doc);

        writer.close();
        System.out.println("【请求响应参数格式化】:");
        System.out.println(out.toString());
        // 注释：返回我们格式化后的结果
        return out.toString();
        }
}
