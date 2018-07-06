package com.dfans.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dfans.common.MyCaptcha;

/**
 * <p>
 * 验证码服务
 * </p>
 * 
 * @author hubin
 * @Date 2016-01-06
 */
@Controller
@RequestMapping("/captcha")
public class CaptchaController {

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	protected HttpServletResponse response;

	/**
	 * 生成图片
	 */
	@ResponseBody
	@RequestMapping("/image")
	public void image(String ctoken) {
		try {
			MyCaptcha.getInstance().generate(request, response.getOutputStream(), ctoken);
			System.out.println("验证码生成！！！" );
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
