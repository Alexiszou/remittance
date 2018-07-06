package com.dfans.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.common.Consoles;
import com.dfans.dao.AdminMapper;
import com.dfans.utils.MD5Utils;

import net.sf.json.JSONArray;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminMapper adminMapper;

	/**
	 * 用户登陆
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	@ResponseBody
	public Object login(@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "password", required = true) String password) {

		Map param = new HashMap();
		param.put("userName", userName);
		// 密码MD5加盐“ebpay” 生成参考PayTest.java
		param.put("password", MD5Utils.MD5(MD5Utils.MD5(password + "ebpay")));
		List<Map> researchList = adminMapper.selectUserInfo(param);
		if (researchList.size() > 0) {
			return Consoles.SUCCESS;
		}
		return Consoles.FAILURE;
	}

	/**
	 * 用户信息查询
	 * 
	 * @param userid
	 *            用户ID
	 * @return 用户信息
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAdminInfo")
	@ResponseBody
	public Object getUserInfo(@RequestParam(value = "userId", required = true) String userId) {
		Map map = new LinkedHashMap<String, String>();
		map.put("id", userId);// 第几页
		List resultList = adminMapper.selectUserInfo(map);
		return JSONArray.fromObject(resultList);
	}
	/*
		*//**
			 * 更新用户信息（密码）
			 * 
			 * @param id
			 *            数据主键
			 * @param obj
			 *            bean
			 * @return s 0 失败 1 成功
			 *//*
			 * @RequestMapping(method = RequestMethod.POST, value =
			 * "/update/{userId}")
			 * 
			 * @ResponseBody public Object doUpdate(@PathVariable int
			 * userId, @RequestBody User obj) { obj.setID(userId); Map param =
			 * new HashMap(); param.put("id", userId); param.put("oldPassword",
			 * MD5Utils.MD5(obj.getOldPassword())); // 根据旧密码校验是否正确 List s =
			 * adminMapper.checkUserBypassword(param); if (s.size() > 0) {
			 * obj.setPassword(MD5Utils.MD5(obj.getNewPassword())); int n =
			 * adminMapper.updateuser(obj); if (n > 0) { return
			 * Consoles.SUCCESS; } } return Consoles.FAILURE; }
			 */

}
