package com.dfans.service;

import java.util.Map;

import com.dfans.model.Insurance;
import com.dfans.model.User;

public interface OrderService {

	/**
	 * 下单成功后发送信息
	 * 
	 * @param msgMap
	 *            订单信息
	 * @param sendFlg
	 *            <br>
	 *            0--创建订单发送<br>
	 *            1--付款完发送<br>
	 *            2--交易完成发送<br>
	 * 
	 * @return int -1 发送邮件失败， 0 发送短信失败， 1 发送成功
	 */
	int sendMsg(Map msgMap, String orderNo, String sendFlg);

	/**
	 * 发送post请求
	 * 
	 * @param zipFiles
	 *            压缩文件路径
	 * @param id
	 *            订单号
	 * @return
	 */
	int postZipFile(String zipFiles, String id,Integer accountType);

	/**
	 * 获取收银台地址
	 * 
	 * @param id
	 * @return
	 */
	String getSearchUrl(String id,Integer accountType);

	/**
	 * 
	 * @param searchUrl
	 * @return
	 */
	String getPayStat(String searchUrl);

	/**
	 * 注册成功发送短信和邮箱
	 * 
	 * @param obj
	 * @return
	 */
	String sendMsg(User obj);

	String sendFeedbackEmail(Map map);

	String sendInsuranceMail(Insurance insurance);

}
