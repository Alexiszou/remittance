/*
 * 中软万维 数金终端
 */
package com.dfans.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.common.Consoles;
import com.dfans.common.EhcacheHelper;
import com.dfans.common.MyCaptcha;
import com.dfans.dao.UserMapper;
import com.dfans.model.User;
import com.dfans.service.OrderService;
import com.dfans.service.UserService;
import com.dfans.utils.MD5Utils;
import com.dfans.utils.SendEmail;
import com.dfans.utils.shortmsgutils.SingletonClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.GET, value = "/ckCode/{ctoken}/{captcha}")
	@ResponseBody
	public Object testCode(HttpServletRequest request, @PathVariable String ctoken, @PathVariable String captcha) {
		if (MyCaptcha.getInstance().verification(request, ctoken, captcha)) {
			return true;
		}

		return false;
	}

	/**
	 * 用户注册
	 * 
	 * @param obj
	 *            用户信息
	 * 
	 * @return s 0 失败 !0 成功 并返回注册id
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = "application/json;charset=utf-8", value = "/register")
	@ResponseBody
	public Object regist(@RequestBody User obj, HttpServletRequest request) {
		int checkFlg = userService.registCheck(obj);
		String result = "F";
		String data = null;

		// 判断是否已经注册
		if (checkFlg == 0) {
			// 验证码校验
			if (MyCaptcha.getInstance().verification(request, obj.getCtoken(), obj.getCaptcha())) {
				obj.setPassword(MD5Utils.MD5(obj.getPassword()));
				// 通过校验，添加用户信息
				int s = userMapper.insertUser(obj);
				if (s > 0) {
					result = "T";
					data = obj.getID().toString();
					// 校验成功发送短信和邮箱
					orderService.sendMsg(obj);
				} else {
					data = "添加用户失败";
				}
			} else {
				data = "校验码错误";
			}
		} else if (checkFlg == 2){
			data = "手机号已经注册过";
		}else if (checkFlg == 3){
			data = "邮箱已经注册过";
		}
		Map map = new HashMap();
		map.put("result", result);
		map.put("data", data);

		return JSONArray.fromObject(map);
	}

	/**
	 * 用户存在性校验
	 * 
	 * @param obj
	 *            用户信息
	 * 
	 * @return s 1已经存在，0用户不存在
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/ckUser")
	@ResponseBody
	public Object ckUser(@RequestParam(value = "userName", required = true) String userName) {
		User user = new User();
		user.setEmail(userName);
		user.setTelphone(userName);
		int checkFlg = userService.registCheck(user);
		// 不存在
		if (checkFlg == 0) {
			return Consoles.FAILURE;
		}
		// 存在
		return Consoles.SUCCESS;
	}

	/**
	 * 用户登陆
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	@ResponseBody
	public Object login(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "password", required = true) String password) {

		Map param = new HashMap();
		param.put("userName", name);
		param.put("password", MD5Utils.MD5(password));
		List<Map> researchList = userMapper.selectUserInfo(param);
		if (researchList.size() > 0) {
			return researchList.get(0).get("ID");
		}

		return Consoles.FAILURE;
	}

	/**
	 * 更新用户信息（密码）
	 * 
	 * @param id
	 *            数据主键
	 * @param obj
	 *            bean
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update/{userId}")
	@ResponseBody
	public Object doUpdate(@PathVariable Long userId, @RequestBody User obj) {
		obj.setID(userId);
		Map param = new HashMap();
		param.put("id", userId);
		param.put("oldPassword", MD5Utils.MD5(obj.getOldPassword()));
		// 根据旧密码校验是否正确
		List s = userMapper.checkUserBypassword(param);
		if (s.size() > 0) {
			obj.setPassword(MD5Utils.MD5(obj.getNewPassword()));
			int n = userMapper.updateuser(obj);
			if (n > 0) {
				return Consoles.SUCCESS;
			}
		}
		return Consoles.FAILURE;
	}

	/**
	 * 找回密码-发送验证码
	 * 
	 * @param userName
	 *            找回密码时输入的用户名
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/sendCode")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object sendCode(@RequestParam(value = "userName", required = true) String userName) {
		Map param = new HashMap();
		param.put("account", userName);
		/*param.put("telphone", userName);*/
		// 根据email 判断用户是否存在
		List<Map> list = userMapper.checkUserInfo(param);
		Long count = (Long) list.get(0).get("count");
		// 确保用户存在
		if (count == 1) {
			// 随机验证码
			Integer db = new Integer((int) ((Math.random() * 9 + 1) * 100000));
			String checkCode = db.toString();
			// 用户输入的是邮箱 发送邮件
			if (userName.contains("@")) {
				try {
					SendEmail.sendEmail(userName, "您正在通过邮箱重置密码，验证码是" + checkCode, "重置密码");
				} catch (Exception e) {
					e.printStackTrace();
					return Consoles.SENDMAILFAIL;
				}
			} else {
				// 用户输入手机号，发送短信
				try {
					SingletonClient.getClient().sendSMS(new String[] { userName },
							"【二宝支付】您的验证码" + checkCode + ",10分钟内有效，请妥善保管", "", 5);
				} catch (RemoteException e) {
					e.printStackTrace();
					return Consoles.SENDSMSFAIL;
				}
			}
			EhcacheHelper.put("msgCache", userName, checkCode);
			return checkCode;
		} else {
			return Consoles.FAILURE;
		}


	}

	/**
	 * 找回密码-发送验证码
	 * 
	 * @param userName
	 *            找回密码时输入的用户名
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/resetPassword")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object resetPassword(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "checkCode", required = true) String checkCode) {

		// 获取userName缓存中的值（发送信息的值） 并校验是否与输入值相同
		String sendCode = (String) EhcacheHelper.get("msgCache", userName);
		//System.out.println("cacheCode:"+sendCode);
		//System.out.println("checkCode:"+checkCode);
		//System.out.println("checkCode == cachecode"+sendCode.equals(checkCode));
		if (checkCode.equals(sendCode)) { // 校验成功，修改该用户的密码
			Map param = new HashMap();
			param.put("userName", userName);
			param.put("password", MD5Utils.MD5(password));
			int t = userMapper.resetPasswrod(param);
			if (t > 0)
				return Consoles.SUCCESS;
			else
				return Consoles.FAILURE;

		} else {
			// 校验失败
			return Consoles.CHCECKFAILURE;
		}
	}

	/**
	 * 用户信息查询
	 * 
	 * @param userid
	 *            用户ID
	 * @return 用户信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getUserInfo")
	@ResponseBody
	public Object getUserInfo(@RequestParam(value = "userId", required = true) String userId) {
		Map map = new LinkedHashMap<String, String>();
		map.put("id", userId);// 第几页
		List resultList = userMapper.selectUserInfo(map);
		return JSONArray.fromObject(resultList);
	}

	// ***************************************ADMIN**************
	/**
	 * ADMIN--用户列表 信息查询
	 * 
	 * @param count
	 *            每页显示数量
	 * @param page
	 *            第几页
	 * 
	 * @return 用户信息列表
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/getUserList")
	@ResponseBody
	public Object getUserList(PageVO pageVO, @RequestParam(value = "source", required = false) String source

	) {
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
		map.put("source", source);
		List resultList = userMapper.selectUserList(map);

		int totalCount = userMapper.selectUserCount(map);

		// MessageResult result = new MessageResult();
		Map result = new HashMap();
		result.put("total", totalCount);
		result.put("rows", resultList);

		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}

	/**
	 * ADMIN--用户详细信息查询
	 * 
	 * @return 用户信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/getUserInfo/{id}")
	@ResponseBody
	public Object getAdmUserInfo(@PathVariable("id") String id) {
		Map map = new LinkedHashMap<String, String>();
		map.put("id", id);// 每页显示数量
		List resultList = userMapper.selectUserInfoAdm(map);
		return JSONArray.fromObject(resultList);
	}

	/**
	 * ADMIN--用户信息更改
	 * 
	 * @param user
	 *            用户修改信息
	 * @return 修改结果 0 修改失败 1 修改成功 2手机重复 3邮箱重复
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/updateUser")
	@ResponseBody
	public Object updateUser(@RequestBody User user) {
		int flg = 0;
		if (user.getID() != 0) {
			int checkFlg = userService.registCheck(user);
			if (checkFlg > 0) {
				flg = checkFlg;
			} else {
				int ups = userMapper.updateuser(user);
				if (ups > 0) {
					flg = 1;
				}
			}
		}
		return flg;

	}

	/**
	 * ADMIN--删除用户信息
	 * 
	 * @param id
	 *            待删除用户的id
	 * @return 修改结果 0 删除失败 1删除
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/delUser/{id}")
	@ResponseBody
	public Object delUser(@PathVariable("id") Long id) {

		int ups = userMapper.delUser(id);
		return ups;
	}

	/**
	 * ADMIN-重置密码
	 * 
	 * @param userName
	 *            找回密码时输入的用户名
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/resetPassword")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object adminResetPassword(@RequestBody User user) {

		int flg = 0;

		Map param = new HashMap();
		String resetPassword = "000000";
		/*
		 * if (telphone.length() > 6) { resetPassword =
		 * telphone.substring(telphone.length() - 6, telphone.length()); }
		 * param.put("userName", telphone);
		 */
		param.put("id", user.getID());
		param.put("password", MD5Utils.MD5(resetPassword));
		param.put("tel", user.getTelphone());
		param.put("email", user.getEmail());

		int t = userMapper.resetPasswrod(param);
		if (t > 0) {
			flg = 1;
		}
		return flg;

	}

	// ***************************************ADMIN**************

	// 系统管理员暂时无法添加新数据
	/**
	 * ADMIN0--添加新数据
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 1 成功
	 */
	/*
	 * @RequestMapping(method = RequestMethod.POST, value = "/admin/addUser")
	 * 
	 * @ResponseBody public Object doAdd(@RequestBody User obj) { int s = 0;
	 * boolean checkFlg = userService.registCheck(obj); // 用户校验通过 可以继续操作 if
	 * (checkFlg) { s = userMapper.insertUser(obj); } return s; }
	 */


	@RequestMapping(value = "/feedback",method = RequestMethod.POST)
	@ResponseBody
	public Object feedback(HttpServletRequest request ) {

		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String tel = request.getParameter("tel");
		String content = request.getParameter("content");

		Map map = new HashMap();
		map.put("name",name);
		map.put("email",email);
		map.put("tel",tel);
		map.put("content",content);
		orderService.sendFeedbackEmail(map);

		return Consoles.SUCCESS;
	}
}
