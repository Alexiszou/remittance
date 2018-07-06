/*
 * 中软万维 数金终端
 */

package com.dfans.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dfans.enums.Merchant;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.common.Consoles;
import com.dfans.dao.OrderMapper;
import com.dfans.model.Order;
import com.dfans.service.OrderService;
import com.dfans.utils.BootUtil;
import com.dfans.utils.SHA256Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private OrderService orderService;

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 添加新订单 并返回主键
	 * 
	 * <hr>
	 * 订单包括：
	 * <LI>用户信息</LI>
	 * <LI>学校信息</LI>
	 * <LI>学生信息</LI>
	 * <LI>支付信息</LI>
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 !0成功 返回主键
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public Object doAdd(@RequestBody Order obj) {
		SimpleDateFormat df = new SimpleDateFormat("YYYYMMddHHmmss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		Integer db = new Integer((int) ((Math.random() * 9 + 1) * 100));
		System.out.println(db);
		String orderNo = date.concat(db.toString());
		obj.setOrderNo(orderNo);
		int s = orderMapper.inserttb_order(obj);
		Map mapr = new HashMap();
		// 创建订单成功 发送邮件
		if (s > 0) {
			List<Map> researchList = getOrderInfo(orderNo);
			Map map = researchList.get(0);
			int sendResult = orderService.sendMsg(map, orderNo,"0");
			mapr.put("msgResult", sendResult);
			mapr.put("result", orderNo);
			return JSONArray.fromObject(mapr);
		}
		mapr.put("result", -2);
		return JSONArray.fromObject(mapr);
	}

	/**
	 * 根据登录用户，展示历史订单
	 * 
	 * @param userId
	 *            登录用户ID
	 * @param flg
	 *            完成状态 0 未完成 1 已完成
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getList/{userId}/{flg}")
	@ResponseBody
	public Object getListByPager(@PathVariable int userId, @PathVariable int flg) {

		Map map = new LinkedHashMap<String, String>();
		map.put("userId", userId); // 用户
		// flg 4-已完成 4以外 0-待付款1 付款成功 2-已失效 3-到账失败
		map.put("flg", flg); // 完成状况

		List resultList = orderMapper.selecttb_orderList(map);
		System.out.println(JSONArray.fromObject(resultList));
		return JSONArray.fromObject(resultList);
	}

	/**
	 * 根据订单号查询订单信息
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getDetail/{id}")
	@ResponseBody
	public Object getInfoById(@PathVariable String id) {
		List<Map> researchList = getOrderInfo(id);
		return JSONArray.fromObject(researchList);
	}

	/**
	 * 根据主键更新数据
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update/{id}/{flg}")
	@ResponseBody
	public Object doUpdate(@PathVariable("id") String id, @PathVariable("flg") int flg) {
		Map map = new HashMap();
		map.put("orderNo", id);
		// 修改状态 已完成
		map.put("finishFlg", flg);

		if (1 == flg || 5 == flg) {
			// 不可直接操作付款状态
			return 0;
		}

		int s = orderMapper.updatetb_order(map);
		return s;
	}

	/**
	 * 根据主键更新数据
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/confirmRemittance")
	@ResponseBody
	public Object confirmRemittance(String orderNo, String result) {
		Map map = new HashMap();
		map.put("orderNo", orderNo);
		// 修改状态 已完成
		map.put("finishFlg", result);

		if ("1".equals(result) || "5".equals(result)) {
			// 不可直接操作付款状态
			return 0;
		}

		int s = orderMapper.updatetb_order(map);
		return s;
	}

	/**
	 * 付款完后更新数据订单
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/updatePayResult/{id}")
	@ResponseBody
	public synchronized Object updatePayResult(@PathVariable("id") String id) {

		int payFlg = orderMapper.selectPayFlg(id);

		int s = 0;


		// 未修改过付款状态的可以继续操作
		if (0 == payFlg) {
			// 1 支付成功 5 支付失败
			int flg;
			// 付款成功查看该订单实际付款状态 1.获取第三方查询地址
			Map orderMap = getOrderInfo(id).get(0);
			//Merchant merchant = null;
			int payStyle = (Integer) orderMap.get("PayStyle");
			/*if(payStyle == 0){
				merchant = Merchant.T1;
			}else if(payStyle == 1){
				merchant = Merchant.T0;
			}*/
			String searchUrl = orderService.getSearchUrl(id,payStyle);
			// 2.获取查询状态
			String result = orderService.getPayStat(searchUrl);

			Document doc = null;
			try {
				doc = DocumentHelper.parseText(result);
			} catch (DocumentException e) {
				flg = 5;
				e.printStackTrace();
			} // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点
			System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

			String stat = rootElt.elementTextTrim("resp_desc"); // 获取根节点下的子节点head

			// 第三方订单校验，付款校验
			if (!"Success".equals(stat)) {
				// 支付失败
				flg = 5;
			} else {
				flg = 1;
				
				List<Map> researchList = getOrderInfo(id);
				Map map = researchList.get(0);
				orderService.sendMsg(map, id,"1");

			}
			Map map = new HashMap();
			map.put("orderNo", id);
			map.put("finishFlg", flg);
			map.put("payFlg", payFlg);
			map.put("payResult", result);
			s = orderMapper.updatetb_order(map);
		}
		return s;
	}

	/**
	 * 付款完后更新数据订单
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/goOrderList")
	/* @ResponseBody */
	public Object goOrderList(HttpServletRequest request) {

		return "personal/wddd";
	}
	
	/**
	 * 付款完后更新数据订单
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/goOrderListWechat")
	/* @ResponseBody */
	public Object goOrderListWx(HttpServletRequest request) {

		return "h5/personal/wddd";
	}

	/**
	 * 压缩并上传资料
	 * 
	 * @param id
	 *            订单号
	 * @return s 0 压缩失败 1 上传成 2 上传失败
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(method = RequestMethod.POST, value = "/zipAndUpFile/{id}")
	@ResponseBody
	public Object zipAndUpFile(@PathVariable("id") String id) {

		int s = 0;

		// *******************1. 压缩文件夹**********************
		List<Map> researchList = getOrderInfo(id);
		InputStream zInputStream = null;
		String zipFiles = null;
		if (researchList.size() > 0) {
			Map orderMap = researchList.get(0);

			String rootDir = BootUtil.getPro("rootDir");
			String OrderSeq = (String) orderMap.get("OrderSeq");

			// 文件存放位置 待压缩文件夹
			String folder = rootDir + OrderSeq;
			// /opt/www/remittance/WEB-INF/classes/static/files/upload/13131313131/20170101111111001.zip
			zipFiles = folder + ".zip";

			File fileFolder = new File(folder);
			File zipFile = new File(zipFiles);

			try {
				InputStream input = null;
				ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
				// zip的名称为
				zipOut.setComment(fileFolder.getName());
				if (fileFolder.isDirectory()) {
					File[] files = fileFolder.listFiles();
					for (int i = 0; i < files.length; ++i) {
						input = new FileInputStream(files[i]);

						zipOut.putNextEntry(new ZipEntry(fileFolder.getName() + "/" + files[i].getName()));
						int temp = 0;
						byte[] bytes = new byte[1024];

						while ((temp = input.read()) != -1) {
							zipOut.write(bytes, 0, temp);
						}
						input.close();
					}
				}
				zipOut.close();
				zInputStream = new FileInputStream(zipFile);
			} catch (Exception e) {
				System.out.println("创建zip失败");
				e.printStackTrace();
				return 0;
			}
		} else {
			return 0;
		}

		// *******************2.向第三方发送post**********************
		// **********************uploadPost(id,zInputStream)**********************

		// ********************2.向第三方发送POST***********************
		Map orderMap = getOrderInfo(id).get(0);
		//Merchant merchant = null;
		int payStyle = (Integer) orderMap.get("PayStyle");
		s = orderService.postZipFile(zipFiles, id,payStyle);

		Map param = new HashMap<String, Object>();
		param.put("orderNo", id);
		// 上传失败 db状态改为2,上传成功 仍然为1
		if (s == 0) {
			s = 2;
		}
		param.put("uploadFlg", s);
		orderMapper.updatetb_order(param);
		return s;
	}

	/**
	 * 点击继续付款时，查询该订单是否过期，过期则修改订单为失效状态 2
	 * 
	 * @param id
	 *            数据主键
	 * @return 0 未失效 2已失效 1已付款 -1 修改订单失败
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getDetailInfo/{id}")
	@ResponseBody
	public Object getDetailInfo(@PathVariable String id) {
		Map param = new LinkedHashMap<String, String>();
		param.put("id", Long.parseLong(id));
		List<Map> researchList = orderMapper.selectOrderDate(param);
		// 有效期内标志
		boolean flg = false;

		if (researchList.size() > 0) {
			Calendar cal = Calendar.getInstance();
			Date time = (Date) researchList.get(0).get("CreatedTime");
			Date now = new Date();

			cal.setTime(time);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			cal.setTime(now);
			int nowHour = cal.get(Calendar.HOUR_OF_DAY);

			int dif = differentDaysByMillisecond(time, now);
			// 当天早上八点到晚上21点之间
			if (dif == 0) {
				// 有效期到当天22:00
				if (hour >= 8 && hour < 21 && nowHour <= 22) {
					flg = true;
					// 八点之前注册 有效期到八点
				} else if (hour < 8 && nowHour < 8) {
					flg = true;
				}
			} else if (dif == 1 && hour > 21) {
				// 第二天有效期到八点
				if (nowHour <= 8) {
					flg = true;
				}
			}
		}
		// 有效期内的 可以继续操作，无效的则更改状态->无效
		if (!flg) {
			param.put("finishFlg", 2);
			param.put("orderNo", id);
			int s = orderMapper.updatetb_order(param);
			if (s < 1) {
				return -1;
			} else {
				return 2;
			}
		}
		return 0;
	}

	public int differentDaysByMillisecond(Date date1, Date date2) {
		int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
		return days;
	}

	/**
	 * 进入付款页面
	 * 
	 * @param id
	 *            订单号
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/payment/{id}/{wxflg}")
	@ResponseBody
	public Object toPayPage(@PathVariable("id") String id,@PathVariable("wxflg") String wxflg) {
		// 返回map
		Map resultMap = new HashMap();
		Integer flg = Consoles.FAILURE;
		String returnUrl = null;

		// 返回页面地址
		StringBuilder payPage = new StringBuilder();

		List<Map> researchList = getOrderInfo(id);
		if (researchList.size() > 0) {
			Map orderMap = researchList.get(0);
			BigDecimal amount = (BigDecimal) orderMap.get("PriceCN");
			BigDecimal amountEN = (BigDecimal) orderMap.get("PriceEN");
			// BigDecimal amount = new BigDecimal("0.01");
			String base64_memo = (String) orderMap.get("Remarks");
			String busi_code = "PAY";
			String currency_type = "CNY";
			String error_url = "";
			String notify_url = "";
			String return_url = "";
			if("0".equals(wxflg)){
				error_url = BootUtil.getPro("error_url");
				 notify_url = BootUtil.getPro("notify_url");
				 return_url = BootUtil.getPro("return_url");
			} else if ("1".equals(wxflg)){
				 error_url = BootUtil.getPro("error_url_wechat");
				 notify_url = BootUtil.getPro("notify_url_wechat");
				 return_url = BootUtil.getPro("return_url_wechat");
			}
			
			String order_no = id;

			String country = (String)orderMap.get("Country");
			Merchant merchant = null;
			int payStyle = (Integer) orderMap.get("PayStyle");
			if(payStyle == 0){
				merchant = Merchant.T1;
			}else if(payStyle == 1){
				merchant = Merchant.T0;
			}
			//商户号
			//String merchant_no = BootUtil.getPro("merchant_no");
			String merchant_no = merchant.getMerchant();
			//终端号
			//String terminal_no = BootUtil.getPro("terminal_no");
			String terminal_no = merchant.getTerminal();
			//交易秘钥
			//String key = BootUtil.getPro("paykey");
			String key = merchant.getKey();

			String product_name = BootUtil.getPro("product_name");
			String product_desc = (String)orderMap.get("EnName")+BootUtil.getPro("product_desc");
			String sett_currency_type = "CNY";
			String sign_type = "SHA256";



			// *****************************预留域
			JSONObject object = new JSONObject();
			object.put("accountName", orderMap.get("AccountName"));
			object.put("accountNo", orderMap.get("AccountNo"));
			//object.put("schoolAddr", orderMap.get("SchoolAddress"));// =====>
			object.put("schoolAddr", orderMap.get("Location"));// =====>
			object.put("bankName", orderMap.get("BankName"));// ===>
			object.put("bankAddr", orderMap.get("BankAddress"));
			object.put("bankCountry", orderMap.get("Country"));
			object.put("swiftCode", orderMap.get("BIC"));
			object.put("outDate", orderMap.get("EndDate"));
			//object.put("exchangeOrderAmount",amount);
			object.put("exchangeOrderAmount",String.valueOf(amountEN));
			object.put("exchangeOrderCurr",orderMap.get("currencyType"));
			object.put("exchangeRate",orderMap.get("currencyMark"));

			Map preSignmap = new LinkedHashMap();
			//preSignmap.put("amount", amount);
			preSignmap.put("amount", String.valueOf(amount));
			preSignmap.put("base64_memo", base64_memo);
			preSignmap.put("busi_code", busi_code);
			preSignmap.put("currency_type", currency_type);
			preSignmap.put("error_url", error_url);
			preSignmap.put("merchant_no", merchant_no);
			preSignmap.put("notify_url", notify_url);
			preSignmap.put("order_no", order_no);
			preSignmap.put("product_desc", product_desc);
			preSignmap.put("product_name", product_name);


			// 1.1.13 添加 预留域 json格式
			preSignmap.put("reserved1", object);
			preSignmap.put("return_url", return_url);
			preSignmap.put("sett_currency_type", sett_currency_type);
			preSignmap.put("sign_type", sign_type);
			preSignmap.put("terminal_no", terminal_no);
			// 新加学生信息
			preSignmap.put("user_cert_no", (String) orderMap.get("CardNo"));
			preSignmap.put("user_cert_type", "01");
			preSignmap.put("user_name", (String) orderMap.get("ChName"));

			String preSign = BootUtil.urlencode_cn(preSignmap);
			String shaSign = preSign + "&key=" + key;
			logger.error("shaSign:"+shaSign);
			// 获取签名
			//String sign = SHA256Utils.getSHA256StrJava(shaSign);
			String sign = SHA256Utils.SHA256Encode(shaSign,"UTF-8").toLowerCase();
			logger.error("sign:"+sign);
			preSignmap.put("sign", sign);
			// 拼接带签名的参数
			String urlParam = BootUtil.urlencode(preSignmap);

			flg = 1;

			returnUrl = BootUtil.getPro("payurl") + "?" + urlParam;
		}
		resultMap.put("FLG", flg);
		resultMap.put("URL", returnUrl);

		return JSONArray.fromObject(resultMap);
	}

	// ==================ADMIN操作 START
	/**
	 * 列表查询
	 * 
	 * @param count
	 *            每页数量
	 * @param page
	 *            第几页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/getList")
	@ResponseBody
	public Object getListByPager(PageVO pageVO, @RequestParam(value = "state", required = false) String state) {

		Map map = new LinkedHashMap<String, String>();

		Integer count = pageVO.getRows();
		Integer page = pageVO.getPage();
		map.put("count", count);// 每页显示数量
		map.put("page", (page - 1) * count);// 第几页
		if (StringUtils.isNotBlank(pageVO.getKey())) {
			map.put("key", pageVO.getKey());
		}
		map.put("timeBegin", pageVO.getTimeBegin());
		map.put("timeEnd", pageVO.getTimeEnd());
		map.put("state", state);
		/*
		 * List resultList = userMapper.selectUserList(map);
		 * 
		 * int totalCount = userMapper.selectUserCount(map);
		 */

		// MessageResult result = new MessageResult();

		Map result = new HashMap();
		int totalCount = orderMapper.select_admin_orderCount(map);
		result.put("total", totalCount);
		List resultList = orderMapper.select_admin_orderList(map);

		result.put("rows", resultList);

		JSONObject jsonObject = JSONObject.fromObject(result);

		return jsonObject;
	}

	// ___________________________测试使用—————————-----------__————---——————--————____---_-____
	/**
	 * 跳转支付收银台测试
	 * 
	 * @param id
	 *            订单号
	 * @return s 0 失败 1 成功
	 */
	public static Object toPayPageTest(String id) {
		// 返回map
		Map resultMap = new HashMap();
		Integer flg = Consoles.FAILURE;
		String returnUrl = null;

		// 返回页面地址
		StringBuilder payPage = new StringBuilder();

		// notify_url=test.gaohuitong.com/staging/returnUrl.jsp&order_no=EB20171031133
		// &product_name=%E4%BA%8C%E5%AE%9D%E6%94%AF%E4%BB%98&return_url=test.gaohuitong.com/staging/returnUrl.jsp
		// &sett_currency_type=CNY&sign_type=SHA256&terminal_no=20000147&key=857e6g8y51b5k365f7v954s50u24h14w
		BigDecimal amount = new BigDecimal("0.01");
		String base64_memo = "bkbkbkbkbkbk";
		String busi_code = "PAY";
		String currency_type = "CNY";
		String error_url = BootUtil.getPro("error_url");
		String notify_url = BootUtil.getPro("notify_url");
		String order_no = id;
		String merchant_no = BootUtil.getPro("merchant_no");
		String product_name = BootUtil.getPro("product_name");
		String return_url = BootUtil.getPro("return_url");
		String sett_currency_type = "CNY";
		String sign_type = "SHA256";
		String terminal_no = BootUtil.getPro("terminal_no");
		String key = BootUtil.getPro("paykey");
		JSONObject object = new JSONObject();
		object.put("accountName", "accountName");
		object.put("accountNo", "accountNo");
		object.put("schoolAddr", "schoolAddr");
		object.put("bankName", "bankName");
		object.put("bankAddr", "bankAddr");
		object.put("swiftCode", "swiftCode");

		Map preSignmap = new LinkedHashMap();
		preSignmap.put("amount", amount);
		preSignmap.put("base64_memo", base64_memo);
		preSignmap.put("busi_code", busi_code);
		preSignmap.put("currency_type", currency_type);
		preSignmap.put("error_url", error_url);
		preSignmap.put("merchant_no", merchant_no);
		preSignmap.put("notify_url", notify_url);
		preSignmap.put("order_no", order_no);
		preSignmap.put("product_name", product_name);

		preSignmap.put("reserved1", object);
		preSignmap.put("return_url", return_url);
		preSignmap.put("sett_currency_type", sett_currency_type);
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

		flg = 1;

		returnUrl = BootUtil.getPro("payurl") + "?" + urlParam;
		resultMap.put("FLG", flg);
		resultMap.put("URL", returnUrl);

		return JSONArray.fromObject(resultMap);
	}

	/**
	 * 上传资料
	 * 
	 * @param id
	 *            订单号
	 * @return s 0 失败 1 成功
	 * @throws IOException
	 */
	public Object uploadPost(String id, InputStream inputStream) throws IOException {
		// 返回map
		Map resultMap = new HashMap();
		Integer flg = Consoles.FAILURE;
		String postUrl = null;

		List<Map> researchList = getOrderInfo(id);
		if (researchList.size() > 0) {

			Map orderMap = researchList.get(0);

			// 设置流水号，年月日时分秒+随机四位数
			SimpleDateFormat df = new SimpleDateFormat("YYYYMMddHHmmss");// 设置日期格式
			String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
			Integer db = new Integer((int) ((Math.random() * 9 + 1) * 1000));
			String req_no = date.concat(db.toString());

			// 返回页面地址
			String busi_code = "UPLOAD_FILE";
			String merchant_no = BootUtil.getPro("merchant_no");
			String terminal_no = BootUtil.getPro("terminal_no");
			String order_no = id;
			String sign_type = "SHA256";
			String key = BootUtil.getPro("paykey");
			// 01：入学通知书
			// 02：缴费通知书
			// 03：I20 首页表格
			String pic_type = "02";
			String country = (String) orderMap.get("Country");
			if (country.equals("USA")) {
				pic_type = "03";
			}

			Map preSignmap = new LinkedHashMap();
			preSignmap.put("busi_code", busi_code);
			preSignmap.put("merchant_no", merchant_no);
			preSignmap.put("order_no", order_no);
			preSignmap.put("pic_type", pic_type);
			preSignmap.put("req_no", req_no);
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

			flg = 1;

			// 向第三方发送流传送请求
			resultMap = BootUtil.uploadFileByHTTP(inputStream, BootUtil.getPro("payurl"), preSignmap);
			System.out.println(resultMap);
		}

		return JSONArray.fromObject(resultMap);
	}

	/**
	 * 上传测试
	 * 
	 * @param id
	 *            订单号
	 * @return s 0 失败 1 成功
	 * @throws IOException
	 */
	public static Object uploadPostTest(String id) throws IOException {
		// 返回map
		Map resultMap = new HashMap();
		Integer flg = Consoles.FAILURE;
		String postUrl = null;

		// 设置流水号，年月日时分秒+随机四位数
		SimpleDateFormat df = new SimpleDateFormat("YYYYMMddHHmmss");// 设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		Integer db = new Integer((int) ((Math.random() * 9 + 1) * 1000));
		String req_no = date.concat(db.toString());

		// 返回页面地址
		String busi_code = "UPLOAD_FILE";
		String merchant_no = BootUtil.getPro("merchant_no");
		String terminal_no = BootUtil.getPro("terminal_no");
		String order_no = id;
		String sign_type = "SHA256";
		String key = BootUtil.getPro("paykey");
		// 01：入学通知书
		// 02：缴费通知书
		// 03：I20 首页表格
		String pic_type = "02";
		String country = "Country";
		if (country == "USA") {
			pic_type = "03";
		}

		Map preSignmap = new LinkedHashMap();
		preSignmap.put("busi_code", busi_code);
		preSignmap.put("merchant_no", merchant_no);
		preSignmap.put("order_no", order_no);
		preSignmap.put("pic_type", pic_type);
		preSignmap.put("req_no", req_no);
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

		flg = 1;

		postUrl = BootUtil.getPro("payurl") + "?" + urlParam;
		resultMap.put("FLG", flg);
		resultMap.put("URL", postUrl);

		String rootUrl = BootUtil.getPro("rootUrl");
		String uploadDir = BootUtil.getPro("uploadDir");
		String fileName = "/171108135740Jellyfish.jpg";
		String surl = rootUrl + uploadDir + fileName;
		URL url = new URL(surl);

		HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
		conn2.setRequestMethod("GET");
		conn2.setConnectTimeout(5 * 1000);
		InputStream fis = conn2.getInputStream();// 通过输入流获取图片数据
		String urlllll = "http://localhost:8080/orders/uppost";// BootUtil.getPro("payurl")

		File fileNew = new File("D:\\javaIo\\hello.zip");

		FileInputStream fiss = new FileInputStream(fileNew);

		resultMap = BootUtil.uploadFileByHTTP(fiss, urlllll, preSignmap);
		System.out.println(resultMap);

		return JSONArray.fromObject(resultMap);
	}

	/**
	 * 根据订单号获取该订单信息
	 * 
	 * @param orderNo
	 * @return
	 */
	private List<Map> getOrderInfo(String orderNo) {
		Map param = new LinkedHashMap<String, String>();
		param.put("id", orderNo);
		List<Map> researchList = orderMapper.selecttb_orderDetail(param);
		return researchList;
	}

	/**
	 * TEST
	 * 
	 * @param id
	 *            数据主键
	 * @param flg
	 *            0-代付款 1 付款成功 2-已失效 3-到账失败 4-已完成
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/uppost")
	@ResponseBody
	public Object uppost(HttpServletRequest request, HttpServletResponse response) {
		Map map = new HashMap();

		try {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// Set factory constraints
			factory.setSizeThreshold(1024 * 10); // 设置缓冲区大小，这里是4kb
			factory.setRepository(new File("D:\\javaIo\\"));// 设置缓冲区目录

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			upload.setProgressListener(new ProgressListener() {

				@Override
				public void update(long readedBytes, long totalBytes, int currentItem) {
					// TODO Auto-generated method stub
					System.out.println("当前已处理：" + readedBytes + "-----------文件大小为：" + totalBytes + "--" + currentItem);
				}
			});
			// Set overall request size constraint
			upload.setSizeMax(1024 * 1024 * 10);// 10M // 设置最大文件尺寸，这里是4MB

			// 解决上传文件名的中文乱码问题
			upload.setHeaderEncoding("UTF-8");
			// 3.判断提交上来的数据是否是上传表单的数据
			if (!ServletFileUpload.isMultipartContent(request)) {
				// 按照传统方式获取数据
				return 0;
			}

			List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
			Iterator<FileItem> i = items.iterator();
			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				String fileName = fi.getName();
				if (fileName != null) {
					File fullFile = new File(new String(fi.getName().getBytes(), "utf-8")); // 解决文件名乱码问题
					File savedFile = new File("D:\\javaIo\\", fullFile.getName());
					fi.write(savedFile);
				}
			}
			System.out.print("upload succeed");
		} catch (Exception e) {

		}
		return map;
	}

	public static void main(String[] args) {

		// toPayPageTest("20171015183858799");
		// String searchUrl = getSearchUrl("20171015183858799");
		// String stat = getPayStat(searchUrl);
		//

		// getUrlFile();

		try {
			uploadPostTest("20171015183858799");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void getUrlFile() {

		String rootUrl = BootUtil.getPro("rootUrl");
		String uploadDir = BootUtil.getPro("uploadDir");
		String fileName = "/171108135740Jellyfish.jpg";
		String url = rootUrl + uploadDir + fileName;
		byte[] btImg = BootUtil.getImageFromNetByUrl(url);
		BootUtil.writeImageToDisk(btImg, fileName);
		/*
		 * if (null != btImg && btImg.length > 0) { System.out.println("读取到：" +
		 * btImg.length + " 字节"); String fileName = "百度.gif";
		 * BootUtil.writeImageToDisk(btImg, fileName); } else {
		 * System.out.println("没有从该连接获得内容"); }
		 */
	}

}
