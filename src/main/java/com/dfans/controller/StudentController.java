/*
 * 中软万维 数金终端
 */

package com.dfans.controller;

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

import com.dfans.dao.StudentMapper;
import com.dfans.model.Student;

import net.sf.json.JSONArray;

/**
 * @author zhangyn
 * @version 1.0
 */

@Controller
@RequestMapping("/students")
public class StudentController {

	@Autowired
	private StudentMapper studentMapper;

	/**
	 * 添加学生信息 返回插入主键
	 * 
	 * @param obj
	 *            bean
	 * @return s 0 失败 !0 成功
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/add")
	@ResponseBody
	public Object doAdd(@RequestBody Student obj) {
		int s = studentMapper.inserttb_student(obj);
		if (s > 0) {
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
	@RequestMapping(method = RequestMethod.GET, value = "/getInfo/{id}")
	@ResponseBody
	public Object getInfoById(@PathVariable int id) {

		Map param = new LinkedHashMap<String, String>();
		param.put("id", id);
		List researchList = studentMapper.selecttb_studentDetail(param);
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
	public Object doUpdate(@PathVariable int id, @RequestBody Student obj) {
		obj.setID(id);
		int s = studentMapper.updatetb_student(obj);
		return s;
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
		int s = studentMapper.deletetb_studentById(map);
		return s;
	}
}
