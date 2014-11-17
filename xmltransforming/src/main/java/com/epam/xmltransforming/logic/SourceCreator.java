package com.epam.xmltransforming.logic;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class SourceCreator {

	public static Source createFileSource(HttpServletRequest request, String path) {
		String sourcePath = getRealPath(request, path);
		File sourceFile = new File(sourcePath);
		Source source = new StreamSource(sourceFile);
		
		return source;
	}
	
	public static String getRealPath(HttpServletRequest request, String path){
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String realPath = context.getRealPath(path);
		
		return realPath;
	}
}
