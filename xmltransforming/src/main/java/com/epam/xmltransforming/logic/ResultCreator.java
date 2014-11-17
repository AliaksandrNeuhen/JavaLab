package com.epam.xmltransforming.logic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.stream.StreamResult;

public class ResultCreator {
	
	public static StreamResult createResponseOutputStreamResult(HttpServletResponse response) throws IOException{
		OutputStream responseOutputStream = response.getOutputStream();
		StreamResult result = new StreamResult(responseOutputStream);
		
		return result;
	}
	
	public static StreamResult createByteArrayOutputStreamResult(){
		OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		StreamResult result = new StreamResult(byteArrayOutputStream);
		
		return result;
	}
}
