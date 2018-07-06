/*package com.dfans.common.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.dfans.common.StringUtil;

@Controller
@Scope("prototype")
@RequestMapping(value = "/common/file")
public class FileUploadController {

	private static String getPro(String key) throws UnsupportedEncodingException {
		ResourceBundle bundle = PropertyResourceBundle.getBundle("application");
		String value = new String(bundle.getString(key).getBytes("ISO-8859-1"), "UTF-8");
		return value;
	}

	private static long FileIndex = 0;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ExceptionHandler(Exception.class)
	// @ResponseBody
	public String upload(Exception ex, HttpServletRequest request, HttpServletResponse rep)
			throws UnsupportedEncodingException {
		String rootDir = getPro("rootDir");
		String uploadDir = getPro("uploadDir");
		String fullPath = null;
		Map<String, Object> msg = new HashMap<String, Object>();
		if (ex instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
			msg.put("error", "上传文件大小不能超过"
					+ (((org.springframework.web.multipart.MaxUploadSizeExceededException) ex).getMaxUploadSize() / 1024
							/ 1024)
					+ "M");
		} else {
			String title = StringUtils.trimToNull(request.getParameter("title"));

			try {
				// 转型为ΪMultipartHttpRequest
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				// 得到所有的文件
				List<MultipartFile> files = new ArrayList<MultipartFile>();
				// 根据文件参数名得到文件并且他添加到集合中
				for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
					String key = (String) it.next();
					MultipartFile file = multipartRequest.getFile(key);
					if (file.getOriginalFilename().length() > 0) {
						files.add(file);
					}
				}
				// 获得文件
				// MultipartFile file = multipartRequest.getFile("file");
				MultipartFile file = files.get(0);
				// 获得文件名
				String filename = file.getOriginalFilename();
				InputStream input = file.getInputStream();
				String genFileName = StringUtil.genStrByCurTime();
				genFileName += filename.indexOf(".") != -1
						? filename.substring(filename.indexOf("."), filename.length()) : "";
				String userStr = "";
				String saveDir = rootDir + "/" + uploadDir + "/" + userStr;// 保存上传文件绝对目录
				// String relatPath = relateDir +"/" + userId;//相对访问目录
				File f = new File(saveDir);
				if (!f.exists())
					f.mkdirs();
				// int res = 0;
				byte[] bytes = file.getBytes();
				FileOutputStream fos = new FileOutputStream(saveDir + "/" + genFileName);
				fos.write(bytes); // 写入文件
				fos.close();

				// 完全路径
				fullPath = uploadDir + "/" + userStr + "/" + genFileName;

				String attachName = title != null ? title : filename;
				FileAttach a = new FileAttach(attachName, uploadDir + "/" + userStr + "/" + genFileName, 0);
				a.setId(FileIndex++);
				msg.put("attach", a);
				msg.put("msg", "文件上传成功！");
				
				 * int id = attachDAO.add(a); if(id > 0) { a.setId(id);
				 * msg.put("attach",a); msg.put("msg","文件上传成功！"); } else
				 * msg.put("error","文件上传失败");
				 
			} catch (Exception e) {
				e.printStackTrace();
				msg.put("error", "文件上传失败");
			}
		}

		 为了兼容ie6,7,8浏览器，json响应头为text/html 
		rep.setContentType("text/html;charset=UTF-8");
		Writer wr;
		try {

			ObjectMapper mapper = new ObjectMapper();
			StringWriter writer = new StringWriter();
			JsonGenerator gen = new JsonFactory().createJsonGenerator(writer);
			mapper.writeValue(gen, msg);
			gen.close();
			String json = writer.toString();
			writer.close();

			wr = rep.getWriter();
			wr.write(json);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fullPath;
	}
	
	 * @RequestMapping(method = RequestMethod.DELETE,value="")
	 * 
	 * @ResponseBody public Object delete(@RequestBody String filePath){ int
	 * code=RespCodeConstants.SUCCESS; String message=""; String description =
	 * "";
	 * 
	 * boolean flag = false; filePath = rootDir + filePath; File file = new
	 * File(filePath); // 路径为文件且不为空则进行删除 if (file.isFile() && file.exists()) {
	 * file.delete(); flag = true; }
	 * 
	 * if(flag) { code = RespCodeConstants.SUCCESS; message = "OK"; description
	 * = "删除成功"; } else { code = RespCodeConstants.FAILURE; message = "ERROR";
	 * description = "删除失败"; }
	 * 
	 * return MessageBuilder.buildMessage(code,message,description); }
	 * 
	 * @RequestMapping(method=RequestMethod.GET, value="/get")
	 * 
	 * @ResponseBody public Object getListByPager(AttachVO attachVO) { List list
	 * = attachDAO.getData(attachVO); return
	 * MessageBuilder.buildMessage(RespCodeConstants.SUCCESS, "OK",
	 * "Get Data Success", list); }
	 
}
*/