/*
 * 中软万维 数金终端
 */

package com.dfans.dao;

import java.util.List;
import java.util.Map;

import com.dfans.model.User;

/**
 * @author zhangyn
 * @version 1.0
 */
public interface UserMapper {

	/**
	 * 查询用户信息
	 * 
	 * <p>
	 * 根据用户名密码登录
	 * 
	 * @param param
	 * @return
	 */
	List<Map> selectUserInfo(Map param);
	
	List<Map> selectUserInfoAdm(Map map);

	/**
	 * 根据用户邮箱或者手机号查找是否已经注册过
	 * 
	 * @param param
	 * @return
	 */
	List<Map> checkUserInfo(Map param);

	/**
	 * 插入用户信息
	 * 
	 * @param user
	 * @return
	 */
	int insertUser(User user);

	/**
	 * 根据主键更新数据
	 * 
	 * @param param
	 *            用户信息
	 * @return 更新条数
	 */
	int updateuser(com.dfans.model.User param);

	/**
	 * 根据用户密码校验存在
	 * 
	 * @param param
	 * @return
	 */
	List<Map> checkUserBypassword(Map param);

	/**
	 * 重设密码
	 * 
	 * @param param
	 * @return
	 */
	int resetPasswrod(Map param);

	/**
	 * ADMIN--获取用户列表
	 * 
	 * @param map
	 * @return
	 */
	List<Map> selectUserList(Map map);

	/**
	 * ADMIN--删除用户信息
	 * 
	 * @param id 用户id
	 * @return
	 */
	int delUser(Long id);

	int selectUserCount(Map map);



	/**
	 * ADMIN--插入新用户信息
	 * 
	 * @param obj
	 * @return
	 *//*
	int inserttb_user(User obj);*/

}
