package com.epam.testappview.service;

import java.net.MalformedURLException;
import java.util.Iterator;

import javax.xml.namespace.QName;

import org.junit.Test;

import com.epam.testappservice.service.impl.INewsService;
import com.epam.testappservice.service.impl.ServiceException;

public class TestRemoteNewsService {

	@Test
	public void remoteServiceProviderTest() {
		for (int i = 0; i < 100; i++) {
			Thread oneMoreThread = new Thread(new Runnable() {

				@Override
				public void run() {
					INewsService newsService = RemoteNewsService.getNewsService();
					try {
						System.out.println(newsService.getList());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			});
			System.out.println(oneMoreThread.getId());
			oneMoreThread.run();
		}
	}
}
