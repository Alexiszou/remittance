/*
 * 中软万维 数金终端
 */

package com.dfans.dao;

import java.util.List;
import java.util.Map;

import com.dfans.model.Order;

/**
 * @author zhangyn
 * @version 1.0
 */

public interface OrderMapper {

	/**
	 * 列表查询
	 */
	List<Map> selecttb_orderList(Map param);

	/**
	 * 根据id查询详细信息
	 */
	List<Map> selecttb_orderDetail(Map param);

	/**
	 * 添加新数据
	 */
	int inserttb_order(Order param);

	/**
	 * 根据主键更新数据 完成某订单
	 */
	int updatetb_order(Map param);

	List<Map> selectOrderDate(Map param);

	/**
	 * admin
	 * 
	 * @param map
	 * @return
	 */
	List<Map> select_admin_orderList(Map map);
	
	/**
	 * 获取当前条件下订单数量
	 * 
	 * @param map
	 * @return
	 */
	int select_admin_orderCount(Map map);
	

	int selectPayFlg(String id);

	
}
