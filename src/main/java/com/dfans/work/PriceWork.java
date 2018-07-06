package com.dfans.work;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dfans.utils.BootUtil;

/**
 * @author zhangyn
 *
 *         CRON表达式 含义 "0 0 12 * * ?" 每天中午十二点触发 "0 15 10 ? * *" 每天早上10：15触发
 *         "0 15 10 * * ?" 每天早上10：15触发 "0 15 10 * * ? *" 每天早上10：15触发
 *         "0 15 10 * * ? 2005" 2005年的每天早上10：15触发 "0 * 14 * * ?"
 *         每天从下午2点开始到2点59分每分钟一次触发 "0 0/5 14 * * ?" 每天从下午2点开始到2：55分结束每5分钟一次触发
 *         "0 0/5 14,18 * * ?" 每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发
 *         "0 0-5 14 * * ?" 每天14:00至14:05每分钟一次触发 "0 10,44 14 ? 3 WED"
 *         三月的每周三的14：10和14：44触发 "0 15 10 ? * MON-FRI" 每个周一、周二、周三、周四、周五的10：15触发
 */
@Component
public class PriceWork {

	private static final Logger logger = LoggerFactory.getLogger(PriceWork.class);
	
	@Autowired
	PriceService ps;

	/**
	 * 每天早上九点更新
	 * 0 0 9  * * ?
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
	public void executeFileDownLoadTask() {
		Thread current = Thread.currentThread();
		logger.info("汇率更新任务开始"+ current.getId() + ",name:" + current.getName());
		ps.exchange();
		logger.info("汇率更新任务结束" + current.getId() + ",name:" + current.getName());
	}

	/**
	 * 五分钟压缩一次
	 * 0 0 9  * * ?
	 */
	//@Scheduled(cron = "0 0/5 * * * ?")
	public void tozip(){
System.out.println("--------------压缩开始");
		// 图片写到D盘
		String rootUrl = BootUtil.getPro("rootDir");
		String uploadDir = BootUtil.getPro("uploadDir");
		/*String fileName = "/171108135740Jellyfish.jpg";
		String surl = rootUrl + uploadDir + fileName;*/

		try {
		/*	URL url = new URL(surl);

			HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
			conn2.setRequestMethod("GET");
			conn2.setConnectTimeout(5 * 1000);
			InputStream fis = conn2.getInputStream();// 通过输入流获取图片数据
*/
			String fileName1 = rootUrl + uploadDir + "/13711";

			 // fileName1 = "D:" + File.separator + "javaIo" + File.separator + "hello";

			File file = new File(fileName1);
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}

			String zipFileName = rootUrl + uploadDir + "/" + file.getName() + ".zip";
			//zipFileName = "d:" + File.separator + "javaIo" + File.separator + file.getName() + ".zip";
			File zipFile = new File(zipFileName);
			InputStream input = null;

			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);

			ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
			zipOut.setComment(file.getName());

			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; ++i) {
					input = new FileInputStream(files[i]);
					zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
					int temp = 0;
					while ((temp = input.read()) != -1) {
						zipOut.write(temp);
					}
					input.close();
				}
			}
			zipOut.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("--------------压缩异常");
		}
		System.out.println("--------------压缩结束");
	}
	

	
}
