/*
 * 中软万维 数金终端
 */

package com.dfans.dao;

import java.util.List;
import java.util.Map;

import java.sql.PreparedStatement;

import com.dfans.model.*;
import com.dfans.vo.*;

/**
 * @author zhangyn
 * @version 1.0
 */

public interface StudentMapper {

	/**
	 * 根据id查询详细信息
	 */
	List<Map> selecttb_studentDetail(Map param);

	/**
	 * 添加新数据
	 */
	int inserttb_student(com.dfans.model.Student param);

	/**
	 * 根据主键更新数据
	 */
	int updatetb_student(com.dfans.model.Student param);

	/**
	 * 根据主键删除数据
	 */
	int deletetb_studentById(Map param);
}
