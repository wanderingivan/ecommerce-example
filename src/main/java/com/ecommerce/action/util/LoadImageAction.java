package com.ecommerce.action.util;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.service.ImageService;

/**
 * 
 * Action that uses ImageService
 * to load images from outside the webapp's
 * static resource folders.
 * Usable with jpg format.<br/>
 * Returns ImageResult custom result type
 * @see ImageResult 
 *
 */
public class LoadImageAction implements ServletRequestAware{

	private ImageService service;
	
	private String path;
	
	private HttpServletRequest servletRequest;
	
	public String execute(){
		return "ImageResult";
	}

	public byte[] getImageInBytes() throws IOException{
		return service.loadImage(path);
	}
	
	public String getImageContentType(){
		return "image/jpg";
	}
	
	public String getImageContentDisposition() {
		return "anyname.jpg";
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.servletRequest = arg0;
	}
	
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String imagePath) {
		this.path = imagePath;
	}

	@Autowired
	public void setService(ImageService service) {
		this.service = service;
	}
}