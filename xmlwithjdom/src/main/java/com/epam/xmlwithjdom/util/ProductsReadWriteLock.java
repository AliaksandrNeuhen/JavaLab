package com.epam.xmlwithjdom.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * Class represents single instance of ReentrantReadWriteLock for locking
 * product source.
 * 
 */
public final class ProductsReadWriteLock {
	private ReentrantReadWriteLock readWriteLock = null;
	private static ProductsReadWriteLock instance = null;
	
	private ProductsReadWriteLock() {
		readWriteLock = new ReentrantReadWriteLock();
	}
	
	public static synchronized ProductsReadWriteLock getInstance() {
		if (instance == null){
			instance = new ProductsReadWriteLock();
		}
		return instance;
	}
	
	public ReadLock readLock() {
		return readWriteLock.readLock();
	}
	
	public WriteLock writeLock() {
		return readWriteLock.writeLock();
	}
	
}
