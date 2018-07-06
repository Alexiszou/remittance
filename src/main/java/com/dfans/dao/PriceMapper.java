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
public interface PriceMapper {

	/**
	 * 更新汇率
	 * 
	 * @param param
	 *            用户信息
	 * @return 更新条数
	 */
	int updatePrice(Map param);


}
