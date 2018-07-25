package com.dfans.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.dfans.enums.Merchant;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dfans.model.User;
import com.dfans.service.OrderService;
import com.dfans.utils.BootUtil;
import com.dfans.utils.SHA256Utils;
import com.dfans.utils.SendEmail;
import com.dfans.utils.GhtCommon.SendImageUtils;
import com.dfans.utils.shortmsgutils.SingletonClient;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	private Logger logger = LoggerFactory.getLogger("OrderServiceImpl");
	@SuppressWarnings("rawtypes")
	@Override
	public int sendMsg(Map msgMap, String orderNo,String sendFlg) {
		// 发送邮件
		int mailFlg = sendMail(msgMap, orderNo, sendFlg);
		if (mailFlg == -1) {
			return -1;
		} else {
			// 发送短信
			int smsFlg = sendSms(msgMap, orderNo, sendFlg);
			if (smsFlg == -1) {
				return 0;
			}
		}
		return 1;
	}

	// ------------------------TTTTTT
	public static void main(String[] args) {
		/*
		 String  sb = "【二宝支付】恭喜，xxxxx学校已成功收到您的汇款xxx.xx(币种缩写)，感谢您使用二宝支付，期待下一次我们能更好的为您服务。";
		try {
			SingletonClient.getClient().sendSMS(new String[] { "13426271438" }, sb, "", 5);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		StringBuilder sbContent = new StringBuilder();
		sbContent.append("<div style='text-align:center'>恭喜您，您的订单已成功到账！</div><br>");
		sbContent.append("尊敬的用户【" + "赵云" + "】：<br>");
		sbContent.append("您编号为：<span style ='color: #3e7fe0;'>" + "0101000101192912" + "</span>的订单已经汇款成功。");
		sbContent.append("<span style ='color: #3e7fe0;'>" + "牛津大学"
				+ "</span>学校成功收到<span style ='color: #3e7fe0;'>" + "54000" + " " + "UD" + "。</span>");
		sbContent.append("<br>您可以登录【二宝支付】官网，在“我的订单”中查看“汇款凭证”与院校进行汇款确认。");
		sbContent.append("<br><br>感谢您使用【二宝支付】全球在线跨境留学平台，期待下一次我们能更好的为您服务.");
		
		try {
			SendEmail.sendEmail("zhangyn@cssweb.com.cn", sbContent.toString(), "恭喜您，您订单已成功到账");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	//	postZipFile("D:\\javaIo\\hello.zip", "111222", 0);

	}

	public static int postZipFile(String zipFilePath, String orderNo, int flg) {
		if (StringUtils.isBlank(zipFilePath)) {
			return 0;
		}

		// 返回页面地址
		String busi_code = "UPLOAD_FILE";
		String merchant_no = BootUtil.getPro("merchant_no");
		String terminal_no = BootUtil.getPro("terminal_no");
		String sign_type = "SHA256";
		String key = BootUtil.getPro("paykey");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busi_code", busi_code);
		map.put("merchant_no", merchant_no);
		map.put("terminal_no", terminal_no);
		map.put("req_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("order_no", orderNo);
		map.put("pic_type", "01");
		map.put("sign_type", sign_type);
		map.put("key", key);
		File flie1 = new File(zipFilePath);
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("fileName1", flie1);
		map.put(SendImageUtils.MAP_KEY_REQ_FILES, fileMap);
		String xml = SendImageUtils.sendUpPost(map);

		// 响应参数格式化，方便查看调试。
		try {
			String formatXml = SendImageUtils.formatXml(xml);
			Document doc = null;
			doc = DocumentHelper.parseText(formatXml);

			Element rootElt = doc.getRootElement(); // 获取根节点

			String stat = rootElt.elementTextTrim("resp_code"); // 获取根节点下的子节点head

			String reason = rootElt.elementText("resp_desc"); // 成功或者失败原因
			System.out.println("成功或失败原因====" + reason);
			// 00 上传成功 其他表示上传失败
			if ("00".equals(stat)) {
				return 1;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	// ----------------TTTTT

	/**
	 * 
	 * @param zipFilePath
	 * @param orderNo
	 * @return
	 */
	public int postZipFile(String zipFilePath, String orderNo,Integer accountType) {
		if (StringUtils.isBlank(zipFilePath)) {
			return 0;
		}
		Merchant merchant = null;
		if(accountType == 0){
			merchant = Merchant.T1;
		}else if(accountType == 1){
			merchant = Merchant.T0;
		}
		// 返回页面地址
		String busi_code = "UPLOAD_FILE";
		//String merchant_no = BootUtil.getPro("merchant_no");
		String merchant_no = merchant.getMerchant();
		String terminal_no = merchant.getTerminal();
		//String terminal_no = BootUtil.getPro("terminal_no");
		String sign_type = "SHA256";
		//String key = BootUtil.getPro("paykey");
		String key = merchant.getKey();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("busi_code", busi_code);
		map.put("merchant_no", merchant_no);
		map.put("terminal_no", terminal_no);
		map.put("req_no", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		map.put("order_no", orderNo);
		map.put("pic_type", "01");
		map.put("sign_type", sign_type);
		map.put("key", key);
		File flie1 = new File(zipFilePath);
		Map<String, File> fileMap = new HashMap<String, File>();
		fileMap.put("fileName1", flie1);
		map.put(SendImageUtils.MAP_KEY_REQ_FILES, fileMap);
		String xml = SendImageUtils.sendUpPost(map);
		System.out.println("【发送请求响应的参数】:" + xml);

		// 响应参数格式化，方便查看调试。
		try {
			String formatXml = SendImageUtils.formatXml(xml);
			Document doc = null;
			doc = DocumentHelper.parseText(formatXml);

			Element rootElt = doc.getRootElement(); // 获取根节点

			String stat = rootElt.elementTextTrim("resp_code"); // 获取根节点下的子节点head

			String reason = rootElt.elementText("resp_desc"); // 成功或者失败原因
			System.out.println("成功或失败原因====" + reason);
			// 00 上传成功 其他表示上传失败
			if ("00".equals(stat)) {
				return 1;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public String getPayStat(String searchUrl) {
		logger.error("searchUrl:"+searchUrl);
		String result = null;
		// String url = "http://web.juhe.cn:8080/finance/exchange/rmbquot";//
		// 请求接口地址
		String resp_desc = null;
		try {
			result = net(searchUrl);
			/*
			 * Document doc = null; doc = DocumentHelper.parseText(result); //
			 * 将字符串转为XML
			 * 
			 * Element rootElt = doc.getRootElement(); // 获取根节点
			 * System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
			 * 
			 * resp_desc = rootElt.elementTextTrim("resp_desc"); //
			 * 获取根节点下的子节点head
			 */} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public String getSearchUrl(String orderNo,Integer accountType) {
		String order_no = orderNo;
		Merchant merchant = null;
			if(accountType == 0){
				merchant = Merchant.T1;
			}else if(accountType == 1){
				merchant = Merchant.T0;
			}
		//String merchant_no = BootUtil.getPro("merchant_no");
		String merchant_no = merchant.getMerchant();
		String sign_type = "SHA256";
		//String terminal_no = BootUtil.getPro("terminal_no");
		String terminal_no = merchant.getTerminal();
		//String key = BootUtil.getPro("paykey");
		String key = merchant.getKey();
		Map preSignmap = new LinkedHashMap();
		preSignmap.put("busi_code", "SEARCH");
		preSignmap.put("merchant_no", merchant_no);
		preSignmap.put("order_no", order_no);
		preSignmap.put("sign_type", sign_type);
		preSignmap.put("terminal_no", terminal_no);

		String preSign = BootUtil.urlencode_cn(preSignmap);
		String shaSign = preSign + "&key=" + key;
		// 获取签名
		//String sign = SHA256Utils.getSHA256StrJava(shaSign);
		String sign = SHA256Utils.SHA256Encode(shaSign,"UTF-8").toLowerCase();
		preSignmap.put("sign", sign);
		// 拼接带签名的参数
		String urlParam = BootUtil.urlencode(preSignmap);
		String returnUrl = BootUtil.getPro("payurl") + "?" + urlParam;
		return returnUrl;
	}

	/************************* PRIVATE FUNCTION *************************/
	/**
	 * 发送短信
	 * 
	 * @param msgMap
	 *            参数map
	 * @param orderNo
	 *            订单号
	 * @param sendFlg
	 *            <br>
	 *            0--创建订单发送<br>
	 *            1--付款完发送<br>
	 *            2--交易完成发送<br>
	 * 
	 * @return
	 */
	private int sendSms(Map msgMap, String orderNo, String sendFlg) {
		String tel = (String) msgMap.get("Telphone");
		StringBuilder sbContent = new StringBuilder();
		String schoolEnName = (String) msgMap.get("EnName");
		BigDecimal payPriceEN = (BigDecimal) msgMap.get("PriceEN");

		String schoolUnit = (String) msgMap.get("UnitText");
		if ("0".equals(sendFlg)) {
			sbContent.append("【二宝支付】您已成功创建汇往 " + schoolEnName + " 学校 ");
			sbContent.append(payPriceEN + " " + schoolUnit);
			sbContent.append(" 的订单(订单编号：" + orderNo + "),");
			sbContent.append("请您在订单有效期内及时完成");
		} else if ("1".equals(sendFlg)){
			sbContent.append("【二宝支付】您汇往"+schoolEnName+"学校"+payPriceEN + "（" + schoolUnit+"）的订单已经支付完成，预计2个工作日内到账，请留意短信或邮件进度通知。");
		} else if ("2".equals(sendFlg)){
			sbContent.append("【二宝支付】恭喜，"+schoolEnName+"学校已成功收到您的汇款"+payPriceEN + "（" + schoolUnit+"），感谢您使用二宝支付，期待下一次我们能更好的为您服务。");
		}

		try {
			return SingletonClient.getClient().sendSMS(new String[] { tel }, sbContent.toString(), "", 5);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 发送邮件
	 * 
	 * @param msgMap
	 *            参数map
	 * @param orderNo
	 *            订单号
	 * @param sendFlg
	 *            <br>
	 *            0--创建订单发送<br>
	 *            1--付款完发送<br>
	 *            2--交易完成发送<br>
	 * @return
	 */
	private int sendMail(Map msgMap, String orderNo, String sendFlg) {

		String title = "邮件标题";

		String studentNm = (String) msgMap.get("ChName");
		String studentNmPy = (String) msgMap.get("PyName");
		String studentRemarks = (String) msgMap.get("Remarks");
		BigDecimal payPriceCN = (BigDecimal) msgMap.get("PriceCN");
		BigDecimal payPriceEN = (BigDecimal) msgMap.get("PriceEN");
		String schoolAccountName = (String) msgMap.get("AccountName");
		// String schoolChName = (String) msgMap.get("ChName");
		String schoolEnName = (String) msgMap.get("EnName");
		String schoolAccountNo = (String) msgMap.get("AccountNo");
		String userEmail = (String) msgMap.get("Email");
		String userName = (String) msgMap.get("UserName");
		String schoolUnit = (String) msgMap.get("UnitText");

		int payStyle = (Integer) msgMap.get("PayStyle");

		// 发送邮件内容
		StringBuilder sbContent = new StringBuilder();

		Date orderDate = (Date) msgMap.get("CreatedTime");
		SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");// 设置日期格式
		SimpleDateFormat df2 = new SimpleDateFormat("YYYY-MM-dd");// 设置日期格式
		String orderDateTimeStr = df.format(orderDate);// new
		// Date()为获取当前系统时间，也可使用当前时间戳
		String orderDateStr = df2.format(orderDate);// new
		// Date()为获取当前系统时间，也可使用当前时间戳
		String ddlTime = "";
		Calendar cal = Calendar.getInstance();
		cal.setTime(orderDate);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		// 1 八点之前下单 有效期到八点钟
		// 2 八点到21点钟下单，有效期到当天22点
		// 3 21点之后下单，有效期到第二天八点
		if (hour < 8) {
			ddlTime = orderDateStr + " 08:00:00";
		} else if (hour >= 8 && hour <= 21) {
			ddlTime = orderDateStr + " 22:00:00";
		} else if (hour > 21) {
			cal.add(Calendar.DAY_OF_MONTH, +1);
			Date ddlDate = cal.getTime();
			String ddlDateStr = df2.format(ddlDate);
			ddlTime = ddlDateStr + " 08:00:00";
		}
		// 创建订单发送
		if ("0".equals(sendFlg)) {
			title = "确认订单信息";



			/*sbContent.append("尊敬的用户【" + userName + "】：<br><br>");
			sbContent.append("&nbsp;&nbsp;&nbsp;&nbsp;感谢您使用二宝支付汇款到:");
			sbContent.append("<span style ='color: #3e7fe0;'>" + schoolEnName + "</span>");
			sbContent.append("学校<br><br>");
			sbContent.append("&nbsp;&nbsp;&nbsp;&nbsp;以下是您的汇款信息，请您仔细核对后，在 ");
			sbContent.append("<span style ='color: #ea392d;'>" + ddlTime + "</span>");
			sbContent.append(" 之前完成付款，否则订单价格无效<br>");
			sbContent.append("<br>订单编号：<span style ='color: #3e7fe0;'>" + orderNo + "</span>");
			sbContent.append("<br>订单日期：<span style ='color: #3e7fe0;'>" + orderDateTimeStr + "</span>");
			sbContent.append("<br>学生姓名：<span style ='color: #3e7fe0;'>" + studentNm + " " + studentNmPy + " </span>");
			sbContent.append("<br>汇款金额：<span style ='color: #3e7fe0;'>" + payPriceCN + " RMB</span>");
			sbContent.append("<br>收款金额：<span style ='color: #3e7fe0;'>" + payPriceEN + " " + schoolUnit + "</span>");
			sbContent.append("<br>收款账户：<span style ='color: #3e7fe0;'>" + schoolAccountName + "</span>");
			sbContent.append("<br>收款账号：<span style ='color: #3e7fe0;'>" + schoolAccountNo + "</span>");
			sbContent.append("<br>汇款附言：<span style ='color: #3e7fe0;'>" + studentRemarks + "</span>");
			sbContent.append("<br>在您付款成功后，您将收到一份来自<a href='http://www.qhins.com'>前海财险</a>的保单邮件");*/
			sbContent.append("<div><p>尊敬的用户<strong>【"+userName+"】</strong>：</p>");
			sbContent.append("<p>感谢选择二宝平台跨境缴费服务。请在"+ddlTime+"之前完成付款，\n" +
					"            以确保款项可以按照您预期的时间到账，如果您已经付款完毕请忽略本邮件。\n" +
					"        </p>");
			sbContent.append("<a href='http://www.erbaopay.com/personal/wddd.html'>付款请点击这里</a>");
			sbContent.append("<h3>订单信息</h3>");

			sbContent.append("<table border='1'>");

			sbContent.append("<tr>");
			sbContent.append("<th>订单号</th>");
			sbContent.append("<td>"+orderNo+"</td>");
			sbContent.append("<td>付款金额<br>"+payPriceCN+"<br>人民币</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>订单日期</th>");
			sbContent.append("<td>"+orderDateTimeStr+"</td>");
			sbContent.append("<td rowspan='4'>外币金额<br>"+payPriceEN+"<br>"+schoolUnit+"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>学生姓名</th>");
			sbContent.append("<td>"+studentNm + " " + studentNmPy +"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>收款方</th>");
			sbContent.append("<td>"+schoolAccountName+"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>到账服务</th>");
			if(payStyle == 0) {
				sbContent.append("<td>T+2</td>");
			}else if(payStyle == 1){
				sbContent.append("<td>T+0</td>");
			}
			sbContent.append("</tr>");

			sbContent.append("</table>");

			sbContent.append("<h3>联系信息</h3>");

			sbContent.append("<table  border='1'>");

			sbContent.append("<tr>");
			sbContent.append("<th>微信公众号</th>");
			sbContent.append("<td>二宝平台</td>");
			sbContent.append("<td rowspan='4'><img style='width: 140px;' src='http://www.erbaopay.com/images/twoCode.png'></td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>客服QQ号</th>");
			sbContent.append("<td>3264465415</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>邮箱</th>");
			sbContent.append("<td>erbao@leadingmax.com</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>联系电话</th>");
			sbContent.append("<td>0755-86525927</td>");
			sbContent.append("</tr>");

			sbContent.append("</table>");

			sbContent.append("<div>");
			sbContent.append("<img  style='width: 140px;' src='http://www.erbaopay.com/images/second/hksy11.png'/>");
			sbContent.append("<img style='width: 140px;' src='http://www.erbaopay.com/images/second/qianhairenshou.png'/>");
			sbContent.append("</div>");

			sbContent.append("<p>在您付款成功后，您将收到一份来自<a href='http://www.qhins.com'>前海财险</a>的保单邮件</p>");
			sbContent.append("<p>感谢您对二宝平台的支持，如有问题请<a href='http://www.erbaopay.com'>与我们联系</a>。</p>");
			sbContent.append("</div>");

			/*sbContent.append("<tr>");
			sbContent.append("<tr>");
			sbContent.append("<tr>");
			sbContent.append("<tr>");*/


		} else if ("1".equals(sendFlg)) {
			title = "支付订单完成，可跟踪您的汇款进度";
			// 付款完发送
			/*sbContent.append("<div style='text-align:center'>恭喜您，您的订单已支付成功！</div><br>");
			sbContent.append("尊敬的用户【" + userName + "】：<br>");
			sbContent.append("&nbsp;&nbsp;&nbsp;&nbsp;您的订单已支付成功，预计两个工作日内到达对方账户，请您留意短信或邮件的汇款进度通知，");
			sbContent.append("您可以登录【二宝支付】官网，在“我的订单”中查看“汇款凭证”，两个工作日后与院校进行汇款确认。");*/

			sbContent.append("<div><p>尊敬的用户<strong>【"+userName+"】</strong>：</p>");
			sbContent.append("<p>非常感谢您使用二宝平台跨境缴费服务，汇款将在您选定的到账服务时间内足额到账，届时您可以在二宝平台上查询进程，再次感谢您对我们的信任。</p>");
			sbContent.append("<a href='http://www.erbaopay.com/personal/wddd.html'>查看订单请点击这里</a>");
			sbContent.append("<h3>订单信息</h3>");

			sbContent.append("<table border='1'>");

			sbContent.append("<tr>");
			sbContent.append("<th>订单号</th>");
			sbContent.append("<td>"+orderNo+"</td>");
			sbContent.append("<td>付款金额<br>"+payPriceCN+"<br>人民币</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>订单日期</th>");
			sbContent.append("<td>"+orderDateTimeStr+"</td>");
			sbContent.append("<td rowspan='4'>外币金额<br>"+payPriceEN+"<br>"+schoolUnit+"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>学生姓名</th>");
			sbContent.append("<td>"+studentNm + " " + studentNmPy +"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>收款方</th>");
			sbContent.append("<td>"+schoolAccountName+"</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>到账服务</th>");
			if(payStyle == 0) {
				sbContent.append("<td>T+2</td>");
			}else if(payStyle == 1){
				sbContent.append("<td>T+0</td>");
			}
			sbContent.append("</tr>");

			sbContent.append("</table>");

			sbContent.append("<h3>联系信息</h3>");

			sbContent.append("<table  border='1'>");

			sbContent.append("<tr>");
			sbContent.append("<th>微信公众号</th>");
			sbContent.append("<td>二宝平台</td>");
			sbContent.append("<td rowspan='4'><img style='width: 140px;' src='http://www.erbaopay.com/images/twoCode.png'></td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>客服QQ号</th>");
			sbContent.append("<td>3264465415</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>邮箱</th>");
			sbContent.append("<td>erbao@leadingmax.com</td>");
			sbContent.append("</tr>");

			sbContent.append("<tr>");
			sbContent.append("<th>联系电话</th>");
			sbContent.append("<td>0755-86525927</td>");
			sbContent.append("</tr>");

			sbContent.append("</table>");

			sbContent.append("<div>");
			sbContent.append("<img  style='width: 140px;' src='http://www.erbaopay.com/images/second/hksy11.png'/>");
			sbContent.append("<img style='width: 140px;' src='http://www.erbaopay.com/images/second/qianhairenshou.png'/>");
			sbContent.append("</div>");

			sbContent.append("<p>在您付款成功后，您将收到一份来自<a href='http://www.qhins.com'>前海财险</a>的保单邮件</p>");
			sbContent.append("<p>感谢您对二宝平台的支持，如有问题请<a href='http://www.erbaopay.com'>与我们联系</a>。</p>");
			sbContent.append("</div>");
			//sbContent.append("<br><br>");
			//sbContent.append("&nbsp;&nbsp;&nbsp;&nbsp;感谢您使用【二宝支付】全球在线跨境留学平台，如果您有任何问题，请及时联系我们，我们将竭诚为您服务。");
		} else if ("2".equals(sendFlg)) {
			title = "您的汇款已成功到达对方账户";
			sbContent.append("<div style='text-align:center'>恭喜您，您的订单已成功到账！</div><br>");
			sbContent.append("尊敬的用户【" + userName + "】：<br>");
			sbContent.append("您编号为：<span style ='color: #3e7fe0;'>" + orderNo + "</span>的订单已经汇款成功。");
			sbContent.append("<span style ='color: #3e7fe0;'>" + schoolEnName
					+ "</span>学校成功收到<span style ='color: #3e7fe0;'>" + payPriceEN + " " + schoolUnit + "</span>。");
			sbContent.append("<br>您可以登录【二宝支付】官网，在“我的订单”中查看“汇款凭证”与院校进行汇款确认。");
			sbContent.append("<br><br>感谢您使用【二宝支付】全球在线跨境留学平台，期待下一次我们能更好的为您服务。");
		}

		/*sbContent.append("<br><br><br>------------------------------------------<br>");
		sbContent.append("如果您有任何疑问，请联系我们：<br>");
		sbContent.append("客服热线（TEL）：0755-86525927<br>");
		sbContent.append("电子邮件（E-mail）：erbao@leadingmax.com<br>");
		sbContent.append("官方网址（Website）：<a href='http://www.erbaopay.com'>http://www.erbaopay.com<a><br>");*/
		try {
			return SendEmail.sendEmail(userEmail, sbContent.toString(), title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
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
	private static String net(String strUrl) throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
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

	@Override
	public String sendMsg(User obj) {
		String  tel = obj.getTelphone();
		String  userName = obj.getName();
		
		String shortMsg = "【二宝支付】"+userName+"，您好！恭喜您已成功注册二宝支付，请您妥善保管账号与密码。";
		// 发送短信
		try {
			SingletonClient.getClient().sendSMS(new String[] { tel }, shortMsg, "", 5);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		
		String  mail = obj.getEmail();
		StringBuilder sbContent = new  StringBuilder();
		sbContent.append("<div><p>尊敬的用户<strong>【"+userName+"】</strong>：</p>");
		sbContent.append("<p>恭喜您已经成功注册二宝平台账号，您的账号为"+mail+",请您妥善保管账号与密码，此邮箱将作为您二宝平台的用户标识，同时可用于找回密码。</p>");


		sbContent.append("<h3>联系信息</h3>");

		sbContent.append("<table  border='1'>");

		sbContent.append("<tr>");
		sbContent.append("<th>微信公众号</th>");
		sbContent.append("<td>二宝平台</td>");
		sbContent.append("<td rowspan='4'><img style='width: 140px;' src='http://www.erbaopay.com/images/twoCode.png'></td>");
		sbContent.append("</tr>");

		sbContent.append("<tr>");
		sbContent.append("<th>客服QQ号</th>");
		sbContent.append("<td>3264465415</td>");
		sbContent.append("</tr>");

		sbContent.append("<tr>");
		sbContent.append("<th>邮箱</th>");
		sbContent.append("<td>erbao@leadingmax.com</td>");
		sbContent.append("</tr>");

		sbContent.append("<tr>");
		sbContent.append("<th>联系电话</th>");
		sbContent.append("<td>0755-86525927</td>");
		sbContent.append("</tr>");

		sbContent.append("</table>");

		sbContent.append("<div>");
		sbContent.append("<img  style='width: 140px;' src='http://www.erbaopay.com/images/second/hksy11.png'/>");
		sbContent.append("<img style='width: 140px;' src='http://www.erbaopay.com/images/second/qianhairenshou.png'/>");
		sbContent.append("</div>");

		sbContent.append("<p>感谢您对二宝平台的支持，如有问题请<a href='http://www.erbaopay.com'>与我们联系</a>。</p>");
		sbContent.append("</div>");
		/*sbContent.append("尊敬的用户【" + userName + "】：<br><br>");
		sbContent.append("&nbsp;&nbsp;&nbsp;&nbsp;恭喜您已经成功注册二宝支付，您的账号为"+mail+"，请您妥善保管账号和密码，<br>");
		sbContent.append("此邮箱将作为您在二宝支付网站的用户标识，同时可用于找回密码。");

		sbContent.append("<br><br><br>------------------------------------------<br>");
		sbContent.append("如果您有任何疑问，请联系我们：<br>");
		sbContent.append("客服热线（TEL）：0755-86525927<br>");
		sbContent.append("电子邮件（E-mail）：erbao@leadingmax.com<br>");
		sbContent.append("官方网址（Website）：<a href='http://www.erbaopay.com'>http://www.erbaopay.com<a><br>");*/

		// 发送邮件
		try {
			 SendEmail.sendEmail(mail, sbContent.toString(), "会员注册成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//发送邮件到二宝邮箱
		StringBuilder content = new  StringBuilder();
		content.append("新用户注册<br>");
		content.append("用户名：<span style ='color: #3e7fe0;'>" + obj.getName() + "</span><br>");
		content.append("联系电话：<span style ='color: #3e7fe0;'>" + obj.getTelphone() + "</span><br>");
		content.append("邮箱：<span style ='color: #3e7fe0;'>" + obj.getEmail() + "</span><br>");
		try {
			SendEmail.sendEmail(SendEmail.getPro("from"), content.toString(), "新用户注册");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String sendFeedbackEmail(Map map) {
		//发送邮件到二宝邮箱
		StringBuilder content = new  StringBuilder();
		//content.append("用户反馈内容test");
		content.append("用户反馈<br>");
		content.append("用户姓名：<span style ='color: #3e7fe0;'>" + map.get("name") + "</span><br>");
		content.append("联系电话：<span style ='color: #3e7fe0;'>" + map.get("tel") + "</span><br>");
		content.append("邮箱：<span style ='color: #3e7fe0;'>" + map.get("email") + "</span><br>");
		content.append("反馈内容：<span style ='color: #3e7fe0;'>" + map.get("content") + "</span><br>");
		try {
			SendEmail.sendEmail(SendEmail.getPro("from"), content.toString(), "用户反馈");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
