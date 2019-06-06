package com.lock.locktype;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MapReadWriteLockType extends MapLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public MapReadWriteLockType() {
	}

	public MapReadWriteLockType(Map<Integer, Integer> myMap) {
		this.myMap = myMap;
	}

	public Object get(Integer key) {
		readLock.lock();
		try {
			return myMap.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public void put(Integer key, Integer value) {
		writeLock.lock();
		try {
			myMap.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}
}
