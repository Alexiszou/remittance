package com.dfans.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/upload")
public class UpController {

	// 跳转到上传文件的页面
	@RequestMapping(value = "/gouploadimg", method = RequestMethod.GET)
	public String goUploadImg() {
		// 跳转到 templates 目录下的 uploadimg.html
		return "uploadimg";
	}

	// 处理文件上传
	@RequestMapping(value = "/testuploadimg", method = RequestMethod.POST)
	public @ResponseBody String uploadImg(@RequestParam("file") MultipartFile file,
			@RequestParam("telphone") String telphone, HttpServletRequest request) throws Exception {

		String rootDir = getPro("rootDir");
		String uploadDir = getPro("uploadDir");
		String saveDir = rootDir + uploadDir  + telphone;// 保存上传文件绝对目录

		File targetFile = new File(saveDir);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		String contentType = file.getContentType();
		String fileName = file.getOriginalFilename();
		/*
		 * System.out.println("fileName-->" + fileName);
		 * System.out.println("getContentType-->" + contentType);
		 */
		String filePath = request.getSession().getServletContext().getRealPath("imgupload/");
		try {
			uploadFile(file.getBytes(), saveDir, fileName);
		} catch (Exception e) {
			return "uploadimgFailure";
			// TODO: handle exception
		}
		// 返回json
		return "uploadimg success";
	}

	// ******************************************************************
	/**
	 * 
	 * 多文件具体上传时间，主要是使用了MultipartHttpServletRequest和MultipartFile
	 * 
	 * @param request
	 * 
	 * @return
	 * @throws IOException
	 * 
	 */

	@RequestMapping(value = "/uploadFile/{telphone}/{folder}", method = RequestMethod.POST)
	public @ResponseBody Object handleFileUpload(HttpServletRequest request, @PathVariable String telphone,@PathVariable String folder)
			throws IOException {
		String rootDir = getPro("rootDir");
		String uploadDir = getPro("uploadDir");
		
		String saveDir = rootDir + uploadDir + "/"+telphone + "/"+ folder;// 保存上传文件绝对目录
		String retDir = uploadDir + "/"+telphone+ "/" + folder;

		File targetFile = new File(saveDir);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}

		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("files[]");
		MultipartFile file = null;
		BufferedOutputStream stream = null;

		// 返回上传文件的路径，以存在DB
		String  sb = null;
		for (int i = 0; i < files.size(); ++i) {
			/*
			 * if (!sb.equals(new StringBuilder())){ sb.append(";"); }
			 */
			file = files.get(i);
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					String fileName = file.getOriginalFilename();
					String newFileName = saveDir + "/" +  fileName;

					// 创建的文件名
					stream = new BufferedOutputStream(new FileOutputStream(new File(newFileName)));
					sb = retDir+ "/" + fileName;
					stream.write(bytes);
					stream.close();
				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " =>" + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " becausethe file was empty.";
			}
		}
		Map resultMap = new HashMap();
		
		// 存在db的保存地址，用于存在订单中，每个订单对应一个文件夹
		// 一一对应，订单<->压缩文件
		resultMap.put("orderSeq", retDir);
		// 存在db的地址 ，调用时  ip+sb
		resultMap.put("fileUrl", sb);
		
		return JSONArray.fromObject(resultMap);
	}
	// ******************************************************************

	public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
		File targetFile = new File(filePath);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		FileOutputStream out = new FileOutputStream(filePath + fileName);
		out.write(file);
		out.flush();
		out.close();
	}

	private static String getPro(String key) throws UnsupportedEncodingException {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("application");
		String value = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		return value;
	}

}
