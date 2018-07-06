/*
 * 中软万维 数金终端
 */

package com.dfans.controller;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.dao.AdminMapper;
import com.dfans.dao.SchoolMapper;
import com.dfans.model.School;
import com.dfans.utils.shortmsgutils.SingletonClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/schools")
public class SchoolController {
	@Autowired
	private SchoolMapper schoolMapper;

	@Autowired
	private AdminMapper adminMapper;
	
	/**
	 * 根据key查找学校信息
	 * 
	 * @param count
	 *            每页数量
	 * @param page
	 *            第几页
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getInfo")
	@ResponseBody
	public Object getSchoolInfo(@RequestParam(value = "key", required = true) String key) {

		Map map = new LinkedHashMap<String, String>();
		map.put("key", key); // 每页数据量

		List resultList = schoolMapper.select_schoolList(map);
		return JSONArray.fromObject(resultList);
	}


	/**
	 * 添加新学校
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 !0成功 返回id
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public Object doAdd(@RequestBody School obj) {
		int s = schoolMapper.insert_school(obj);
		if (s > 0) {
			List<Map> list = adminMapper.getAdmTel();
			if (list.size() > 0){
				String  tel = (String) list.get(0).get("telphone");
				// 给管理员发送短信 有用户添加新学校
				try {
					SingletonClient.getClient().sendSMS(new String[] { tel }, "【二宝支付】有用户添加新学校,请到管理端查看", "", 5);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
			return obj.getID();
		}
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
		List researchList = schoolMapper.select_schoolDetail(param);
		return JSONArray.fromObject(researchList);
	}

	/**
	 * 根据国家查找汇率
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getRate")
	@ResponseBody
	public Object getRate() {
		List researchList = schoolMapper.selectRate();
		return JSONArray.fromObject(researchList);
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
	public Object doUpdate(@PathVariable int id, @RequestBody School obj) {
		obj.setID(id);
		int s = schoolMapper.updatetb_school(obj);
		return s;
	}

	// **********************************************ADMIN操作START***********************
	/**
	 * ADMIN -- 列表查询
	 * 
	 * @param count
	 *            每页数量
	 * @param page
	 *            第几页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/admin/getSchoolList")
	@ResponseBody
	public Object getListByPager(PageVO pageVO,
			@RequestParam(value = "country", required = false) String country,
			@RequestParam(value = "ctFlag", required = false) String ctFlag) {

		Map map = new LinkedHashMap<String, String>();

		Integer count = pageVO.getRows();
		Integer page = pageVO.getPage();
		map.put("count", count);// 每页显示数量
		map.put("page", (page - 1) * count);// 第几页
		if (StringUtils.isNotBlank(pageVO.getKey())) {
			map.put("key", pageVO.getKey());
		}
		map.put("timeBegin", pageVO.getTimeBegin());
		map.put("timeEnd",  pageVO.getTimeEnd());
		map.put("country", country);
		map.put("ctFlag", ctFlag);
		
		List resultList = schoolMapper.selecttb_schoolList(map);

		int totalCount = schoolMapper.select_schoolCount(map);

		// MessageResult result = new MessageResult();
		Map result = new HashMap();
		result.put("total", totalCount);
		result.put("rows", resultList);

		JSONObject jsonObject = JSONObject.fromObject(result);
		
		return jsonObject;
	}

	/**
	 * ADMIN --- 添加新学校
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 !0成功 返回id
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/add")
	@ResponseBody
	public Object doAddAdmin(@RequestBody School obj) {
		int s = schoolMapper.admininsert_school(obj);
		if (s > 0) {
			return obj.getID();
		}
		return s;
	}

	/**
	 * ADMIN -- 根据主键删除数据
	 * 
	 * @param id
	 *            数据主键
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/admin/{id}")
	@ResponseBody
	public Object doDelete(@PathVariable Long id) {
		int s = schoolMapper.delete_schoolById(id);
		return s;
	}

	/**
	 * ADMIN -- 根据主键更新数据
	 * 
	 * @param id
	 *            数据主键
	 * @param obj
	 *            bean
	 * @return s 0 失败 1 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/admin/update")
	@ResponseBody
	public Object adminUpdate( @RequestBody School obj) {
		//obj.setID(id);
		obj.setCtFlag(0);
		int s = schoolMapper.updatetb_school(obj);
		return s;
	}

	/**
	 * 根据id查询详细信息
	 * 
	 * @param id
	 *            数据主键
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/admin/{id}")
	@ResponseBody
	public Object adminGetInfoById(@PathVariable int id) {
		Map param = new LinkedHashMap<String, String>();
		param.put("id", id);
		List researchList = schoolMapper.select_schoolDetail(param);
		return JSONArray.fromObject(researchList);
	}
	// **********************************************ADMIN操作END***********************

	public static void main(String[] args) {
		long  l1 = 21;
		long  l21 = 21L;
		long  l31 = 21l;
		if (l1 == l21 && l21 == l31){
			System.out.println("equals");
		}
		
	}


}
