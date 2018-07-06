package com.dfans.dao;

import java.util.List;
import java.util.Map;

import com.dfans.model.User;

/**
 * @author zhangyn
 * @version 1.0
 */
public interface AdminMapper {

	/**
	 * 查询用户信息
	 * 
	 * <p>
	 * 根据用户名密码登录
	 * 
	 * @param param
	 * @return
	 */
	List selectUserInfo(Map param);

	int resetPasswrod(Map param);

	int insertLog(Map map);
	List<Map> getAdmTel();
	

}
