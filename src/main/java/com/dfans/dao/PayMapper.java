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



public interface PayMapper{
    

	/**
	 * 添加付款信息
	 */
    int insertpay(com.dfans.model.Pay param);

	/**
	 * 根据主键更新付款信息
	 */
    int update_pay(com.dfans.model.Pay param);
    
    /**
	 * 根据id查询详细信息
	 */
    List<Map> select_payDetail(Map param);
    
    /**
	 * 根据主键删除数据
	 */
    int deletetb_payById(Map param);
}
