package com.dfans.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/turn")
public class TurnCenter {

	// 跳转到上传文件的页面
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String goUploadImg() {
		// 跳转到 templates 目录下的 index.html
		return "index";
	}

}
