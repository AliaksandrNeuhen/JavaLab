package com.epam.testappservice.publisher;

import javax.xml.ws.Endpoint;

import com.epam.testappservice.service.impl.NewsService;

public class NewsServicePublisher {
	public static void main(String[] args) {
		Endpoint.publish("http://10.6.103.55:1710/newsService", 
				new NewsService());
		while(true) {}
	}
}