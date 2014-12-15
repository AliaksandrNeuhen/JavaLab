package com.epam.xmlwithjdom.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Class for creating Source objects
 * 
 */

public final class SourceCreator {

	/**
	 * Creates Source object from file
	 * @param request - http request. It is used for getting real path from servlet context
	 * @param path - source path
	 * @return Source object
	 */
	public static Source createFileSource(HttpServletRequest request, String path) {
		String sourcePath = getRealPath(request, path);
		File sourceFile = new File(sourcePath);
		Source source = new StreamSource(sourceFile);
		
		return source;
	}
	
	/**
	 * Gets real path of the file
	 * @param request - http request. It is used for getting real path from servlet request
	 * @param path - file path
	 * @return real path of the file
	 */
	public static String getRealPath(HttpServletRequest request, String path){
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String realPath = context.getRealPath(path);
		
		return realPath;
	}
}
