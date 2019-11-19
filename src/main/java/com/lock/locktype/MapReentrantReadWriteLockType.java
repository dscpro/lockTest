package com.lock.locktype;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapReentrantReadWriteLockType extends MapLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public MapReentrantReadWriteLockType() {
	}

	public MapReentrantReadWriteLockType(Map myMap) {
		this.myMap = myMap;
	}

	@Override
	public Object get(int key) {
		readLock.lock();
		try {
			return myMap.get(key);
		} finally {
			readLock.unlock();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean insert(Object value) {
		boolean flag = false;
		writeLock.lock();

		try {
			flag = myMap.put(value+"i", value) != null;
		} finally {
			writeLock.unlock();
		}
		return flag;
	}
}
