package com.dfans.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class SendEmail {

	private static Logger logger = LoggerFactory.getLogger("SendEmail");
	public static String getPro(String key) throws UnsupportedEncodingException {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("application");
		String value = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		return value;
	}

	public static int sendEmail(String email, String emailContent, String emailSubject) throws Exception {

		int value = -1;
		// 收件人电子邮箱
		String to = email;

		// 发件人电子邮箱
		final String from = getPro("from");
		// 发件人电子邮箱密码
		final String password = getPro("password");

		logger.error("from:"+from);
		logger.error("password:"+password);

		// 指定发送邮件的主机为 smtp.cssweb.com.cn
		final String host = getPro("host");

		// 发送名
		final String fromName = getPro("fromName");
		// 收件名
		final String toName = getPro("toName");

		// 获取系统属性
		Properties properties = System.getProperties();

		// 设置邮件服务器
		properties.setProperty("mail.smtp.host", host);

		properties.put("mail.smtp.auth", "true");
		// 获取默认session对象 getDefaultInstance
		Session session = Session.getInstance(properties, new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password); // 发件人邮件用户名、密码
			}
		});

		logger.error("session:"+session);

		try {
			// 创建默认的 MimeMessage 对象
			MimeMessage message = new MimeMessage(session);

			// Set From: 头部头字段
			message.setFrom(new InternetAddress(from, fromName, "UTF-8"));

			// Set To: 头部头字段
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to, toName, "UTF-8"));

			// Set Subject: 头部头字段
			message.setSubject(emailSubject);

			// 设置消息体
			//message.setText(emailContent);
			//--------------
			MimeBodyPart mp = new MimeBodyPart();
			mp.setContent(emailContent,"text/html;charset=gb2312");
			/*// 写第一个图片
			MimeBodyPart mp2 = new MimeBodyPart();
			mp2.setDataHandler(new DataHandler(new FileDataSource("classpath:009.jpg")));
			mp2.setContentID("logo.jpg");
			// 写第二个图片
			MimeBodyPart mp3 = new MimeBodyPart();
			mp3.setDataHandler(new DataHandler(new FileDataSource("E:/sdSpace/remittance_2bzf/src/main/java/com/dfans/009.jpg")));
			mp3.setContentID("2.jpg");
			// 4、创建关系，把图片的文字联系起来，用到的类是MimeMultiPart
			MimeMultipart mmp = new MimeMultipart();
			mmp.addBodyPart(mp);
			mmp.addBodyPart(mp2);
			//mmp.addBodyPart(mp3);
			mmp.setSubType("related");//related是表示关联关系
			// 5、再把上面的关系放到MimeBodyPart中
			MimeBodyPart mp4 = new MimeBodyPart();
			mp4.setContent(mmp);
			mmp.setSubType("mixed"); */
			MimeMultipart mmp = new MimeMultipart();
			mmp.addBodyPart(mp);
			message.setContent(mmp);
			
			//--------------
			// 发送消息
			Transport.send(message);
			value = 0;
			System.out.println("Sent message successfully");
			logger.error("Sent message successfully");
		} catch (MessagingException mex) {
			//mex.printStackTrace();
			logger.error("Sent message failed:"+mex.toString());
		}

		return value;
	}

	public static void main(String[] args) {
		try {
			sendEmail("zhangyn@cssweb.com.cn", "尊敬的XXX您好:\n\n    感谢您的使用，您已注册成功", "会员注册成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
