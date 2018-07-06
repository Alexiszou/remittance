/*
 * 中软万维 数金终端
 */

package com.dfans.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.dao.PayMapper;
import com.dfans.dao.SchoolMapper;
import com.dfans.model.Pay;

import net.sf.json.JSONArray;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/pays")
public class PayController {

	@Autowired
	private PayMapper payMapper;

	@Autowired
	private SchoolMapper schoolMapper;

	/**
	 * 添加新支付信息
	 * <p>
	 * 金额-其他币种 金额-人民币 付款方式
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 !0 成功 返回id
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add/{schoolId}")
	@ResponseBody
	public Object doAdd(@RequestBody Pay obj, @PathVariable String schoolId) {
		Integer style = obj.getPayStyle();
		// 汇款货币金额
		Float priceEn = obj.getPriceEN();

		// 汇率
		Float rate = null;
		Map param = new HashMap();
		param.put("id", schoolId);

		// 根据学校获取该学校汇率
		List<Map> schoollist = schoolMapper.select_schoolDetail(param);
		if (schoollist.size() > 0) {
			String rates = (String) schoollist.get(0).get("Rate");

			rate = Float.parseFloat(rates);
		} else {
			return 0;
		}

		// 汇款人民币
		Float priceCn = priceEn * rate / 100;
		// 手续费
		Float sjPrice = new Float(300);


		
		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		
		String today = df.format(d);
		String daysLater = df.format(new Date(d.getTime() + 2 * 24 * 60 * 60 * 1000));
		String  endDate = daysLater;
		
		// 普通汇款 6.5
		if (0 == style && (priceCn * 0.0065 > 300)) {
			sjPrice = (float) (priceCn * 0.0065);
		} else if (1 == style ) { // 加急汇款 7.5
			if(priceCn * 0.0075 > 300) {
				sjPrice = (float) (priceCn * 0.0075);
			}
			endDate = today;

		}
		// 汇款人民币 = 换算汇率后的人民币+ 手续费
		obj.setPriceCN(priceCn + sjPrice);
		obj.setEndDate(endDate);
		int s = payMapper.insertpay(obj);
		if (s > 0) {
			return obj.getID();
		}
		return s;
	}

	/**
	 * 根据主键更新数据
	 * 
	 * @param id
	 *            数据主键
	 * @param obj
	 *            bean
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/update/{id}")
	@ResponseBody
	public Object doUpdate(@PathVariable int id, @RequestBody Pay obj) {
		obj.setID(id);
		int s = payMapper.update_pay(obj);
		return s;
	}

	/**
	 * 根据id查询详细信息
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public Object getInfoById(@PathVariable int id) {
		Map param = new LinkedHashMap<String, String>();
		param.put("id", id);
		List researchList = payMapper.select_payDetail(param);
		return JSONArray.fromObject(researchList);
	}

	/**
	 * 根据主键删除数据
	 * 
	 * @param id
	 *            数据主键
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public Object doDelete(@PathVariable String id) {
		Map map = new LinkedHashMap<String, String>();
		map.put("id", id); // 删除数据主键
		int s = payMapper.deletetb_payById(map);
		return s;
	}
}
