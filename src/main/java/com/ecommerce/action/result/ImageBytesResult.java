package com.ecommerce.action.result;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.ecommerce.action.util.LoadImageAction;

/**
 *	Custom result that flushes
 *	an image in a byte[] as response.<br/>
 *  Used for sending dynamic images with 
 *  LoadImageACtion
 */
public class ImageBytesResult implements Result {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6956487193438831420L;

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		LoadImageAction action = (LoadImageAction) invocation.getAction();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType(action.getImageContentType());
		
		File image = action.getImage();
		response.setContentLength((int)image.length());
		
		FileInputStream in = new FileInputStream(image);
	    OutputStream out = response.getOutputStream();
	    try{
	        byte[] buf = new byte[1024];
	        int count = 0;
	        while ((count = in.read(buf)) >= 0) {
	           out.write(buf, 0, count);
	        }
	    }finally{
	       out.close();
	       in.close();
	    }
	}

}