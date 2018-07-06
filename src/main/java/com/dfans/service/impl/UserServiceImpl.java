package com.dfans.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dfans.dao.UserMapper;
import com.dfans.model.User;
import com.dfans.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userDao;

	// 2 手机注册 3邮箱注册   0 没有注册
	@Override
	public int registCheck(User obj) {
		Map param = new HashMap();
		param.put("account", obj.getEmail());
		// 根据email 判断用户是否存在
		List<Map> list = userDao.checkUserInfo(param);
		Long count = (Long) list.get(0).get("count");
		// 用户存在情况下 校验失败不能注册
		if (count > 0)
			return 3;
		param = new HashMap();
		param.put("account", obj.getTelphone());
		// 根据email 判断用户是否存在
		list = userDao.checkUserInfo(param);
		count = (Long) list.get(0).get("count");
		// 用户存在情况下 校验失败不能注册
		if (count > 0)
			return 2;
		return 0;
	}

}
