package com.dfans.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class BootUtil {

	/**
	 * 获取资源文件
	 * 
	 * @param key
	 * @return
	 */
	public static String getPro(String key) {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("application");
		String value = null;
		try {
			value = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 将map型转为请求参数型 对参数进行urlencode
	 * 
	 * @param data
	 * @return
	 */
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		String s = sb.toString();
		s = s.substring(0, s.length() - 1);
		return s;
	}

	/**
	 * 将map型转为请求参数型 不进行urlencode
	 * 
	 * @param data
	 * @return
	 */
	public static String urlencode_cn(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			sb.append(i.getKey()).append("=").append(i.getValue() + "").append("&");
		}
		String s = sb.toString();
		s = s.substring(0, s.length() - 1);
		return s;
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl
	 *            网络连接地址
	 * @return
	 */
	public static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);// 得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 将图片写入到磁盘
	 * 
	 * @param img
	 *            图片数据流
	 * @param fileName
	 *            文件保存时的名称
	 */
	public static void writeImageToDisk(byte[] img, String fileName) {
		try {
			File file = new File("D:\\" + fileName);
			FileOutputStream fops = new FileOutputStream(file);
			fops.write(img);
			fops.flush();
			fops.close();
			System.out.println("图片已经写入到D盘");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		Map map = new LinkedHashMap<String, String>();
		map.put("ctTimeBegin", "你好啊");
		map.put("ctTimeEnd", 222);
		map.put("key", 333);
		map.put("source", 444);
		map.put("state", 555);
		System.out.println(urlencode(map));
		System.out.println(urlencode_cn(map));
		
		
		
		byte b[] =  getImageFromNetByUrl("https://www.erbaopay.com/remittance/files/upload/171108135740Jellyfish.jpg");
		writeImageToDisk(b,"ffff.zip");
		
	}

	/**
	 * 模拟表单上传文件
	 */
	public static Map uploadFileByHTTP(InputStream postFile, String postUrl, Map postParam) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			HttpPost httpPost = new HttpPost(postUrl);
			// 把文件转换成流对象FileBody
			// FileBody fundFileBin = new FileBody(postFile);
			// 设置传输参数
			MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
			multipartEntity.addBinaryBody("file", postFile, ContentType.create("multipart/form-data", Consts.UTF_8),
					"file.zip");// 相当于<input
			// type="file"
			// name="media"/>
			// 设计文件以外的参数
			Set<String> keySet = postParam.keySet();
			for (String key : keySet) {
				// 相当于<input type="text" name="name" value=name>
				multipartEntity.addPart(key,
						new StringBody((String) postParam.get(key), ContentType.create("text/plain", Consts.UTF_8)));
			}

			HttpEntity reqEntity = multipartEntity.build();
			httpPost.setEntity(reqEntity);

			// 发起请求 并返回请求的响应
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				// 打印响应状态
				System.out.println(response.getStatusLine());
				resultMap.put("statusCode", response.getStatusLine().getStatusCode());
				// 获取响应对象
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					// 打印响应长度
					System.out.println("Response content length: " + resEntity.getContentLength());
					// 打印响应内容
					resultMap.put("data", EntityUtils.toString(resEntity, Charset.forName("UTF-8")));
				}
				// 销毁
				EntityUtils.consume(resEntity);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("uploadFileByHTTP result:" + resultMap);
		return resultMap;
	}

}
