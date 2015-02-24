package com.epam.xmltransforming.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

/**
 * Class for creating StreamResult objects
 * 
 */
public class ResultCreator {
	
	/**
	 * Creates StreamResult object using response output stream for output
	 * @param response - http response
	 * @return StreamResult object for the current response
	 * @throws IOException
	 */
	public static StreamResult createResponseOutputStreamResult(HttpServletResponse response) throws IOException{
		OutputStream responseOutputStream = response.getOutputStream();
		StreamResult result = new StreamResult(responseOutputStream);
		
		return result;
	}
	
	/**
	 * Creates StreamResult using byte array output stream for output
	 * @return StreamResult object with output to byte array output stream
	 */
	public static StreamResult createByteArrayOutputStreamResult(){
		OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(byteArrayOutputStream);
		
		return result;
	}
}
