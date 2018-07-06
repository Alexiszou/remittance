package com.dfans.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

	String restore(MultipartFile multipartFile);

}
