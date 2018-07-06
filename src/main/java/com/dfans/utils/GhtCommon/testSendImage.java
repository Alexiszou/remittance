package com.dfans.utils.GhtCommon;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class testSendImage {

	/**
	 * 第三方接口测试类
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		// 压缩文件存放地址（需要更改）
		String filePath1 = "D:\\javaIo\\hello.zip";

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busi_code", "UPLOAD_FILE");
		map.put("merchant_no", "102100000125");
		map.put("terminal_no", "20000147");
		map.put("order_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("key", "857e6g8y51b5k365f7v954s50u24h14w");
		map.put("req_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("pic_type", "01");
		map.put("sign_type", "SHA256");
		File flie1 = new File(filePath1);
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("fileName1", flie1);
		map.put(SendImageUtils.MAP_KEY_REQ_FILES, fileMap);
		// SendImageUtils.sendUpPost(map);
		String respXml = SendImageUtils.sendUpPost(map);
		System.out.println("【发送请求响应的参数】:" + respXml);

		// 响应参数格式化，方便查看调试。
		try {
			String formatXml = SendImageUtils.formatXml(respXml);
			Document doc = null;
			doc = DocumentHelper.parseText(formatXml);

			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			String stat = rootElt.elementTextTrim("resp_code"); // 获取根节点下的子节点head
			System.out.println(stat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
