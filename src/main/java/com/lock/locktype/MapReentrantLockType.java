package com.lock.locktype;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapReentrantLockType extends MapLockType {
	private Lock lock = new ReentrantLock();

	public MapReentrantLockType() {
	}

	public MapReentrantLockType(Map<Integer, Integer> myMap) {
		this.myMap = myMap;
	}

	public Object get(Integer key) {
		lock.lock();
		try {
			return myMap.get(key);
		} finally {
			lock.unlock();
		}
	}

	public void put(Integer key, Integer value) {

		lock.lock();
		try {
			myMap.put(key, value);
		} finally {
			lock.unlock();
		}
	}
}
