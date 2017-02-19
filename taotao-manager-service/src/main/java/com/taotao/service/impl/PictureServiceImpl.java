package com.taotao.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.util.FtpUtil;
import com.taotao.common.util.IDUtils;
import com.taotao.service.PictureService;

/**
 * <p>Title: PictureServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.taotao.com</p> 
 * @author	孙建隆
 * @date	2017年2月16日下午4:26:32
 * @version 1.0
 */
@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASEPATH}")
	private String FTP_BASEPATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile){
		Map	resultMap = new HashMap<>();
		//生成一个新的文件名
		//去除文件原始文件名
		String oldName = uploadFile.getOriginalFilename();
		//生成新的文件名
		//UUID.randomUUID();
		String newName = IDUtils.genImageName();
		newName =newName + oldName.substring(oldName.lastIndexOf(".")); 
		
		try {
			String imagePath = new DateTime().toString("/yyyy/MM/dd");
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASEPATH,
					imagePath, newName, uploadFile.getInputStream());
			if(!result)
			{
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败");

				return resultMap ;
			}
			resultMap.put("error",0);
			resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传发生异常");

			return resultMap ;
		}
		
		//反户籍
		
	}

}
