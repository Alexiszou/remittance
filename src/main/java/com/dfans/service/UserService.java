package com.dfans.service;

import com.dfans.model.User;

public interface UserService {

	/**
	 * 注册校验
	 * 
	 * @param obj
	 *            用户信息
	 * @return true 不存在用户，可以继续进行 ;false 用户已存在，校验失败
	 */
	int registCheck(User obj);

}
