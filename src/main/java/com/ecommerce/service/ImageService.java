package com.ecommerce.service;

import java.io.File;
import java.io.IOException;

/**
 * 
 * Service Layer interface providing
 * basic save and retrieve operations
 * for images.
 * @throws IOExceptions when missing files
 * or lacking credentials to save in directories 
 */
public interface ImageService {

	/**
	 * Retrieve an image as a base 64 string
	 * @param path of the file to use
	 * @return an image converted to a base 64 string
	 * @throws IOException
	 */
	String getB64(String path) throws IOException;
	
	File loadImage(String path) throws IOException;
	
	/**
	 * Saves an image to a predefined destination
	 * @param file
	 * @param contentType
	 * @param fileName
	 * @throws IOException
	 */
	String saveImage(File file,String contentType,String fileName) throws IOException;
}
