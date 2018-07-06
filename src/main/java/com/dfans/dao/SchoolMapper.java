/*
 * 中软万维 数金终端
 */

package com.dfans.dao;

import java.util.List;
import java.util.Map;

/**
 * @author zhangyn
 * @version 1.0
 */
public interface SchoolMapper {

	/**
	 * 添加新数据
	 */
	int insert_school(com.dfans.model.School param);

	/**
	 * 列表查询
	 */
	List<Map> select_schoolList(Map param);

	/**
	 * 根据id查询详细信息
	 */
	List<Map> select_schoolDetail(Map param);

	/**
	 * 根据国家得到汇率
	 * 
	 * @param param
	 * @return
	 */
	List<Map> selectRate();

	// ------------------管理员操作 ------START
	/**
	 * ADMIN 获取学校列表
	 */
	List<Map> selecttb_schoolList(Map map);

	/**
	 * ADMIN--添加新数据
	 */
	int admininsert_school(com.dfans.model.School param);

	/**
	 * 根据主键删除数据
	 */
	int delete_schoolById(Long id);

	/**
	 * 根据主键更新数据
	 */
	int updatetb_school(com.dfans.model.School param);

	int select_schoolCount(Map map);

	// ------------------管理员操作 ------END

}
