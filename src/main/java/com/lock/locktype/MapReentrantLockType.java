package com.lock.locktype;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapReentrantLockType extends MapLockType {
	private Lock lock = new ReentrantLock();

	public MapReentrantLockType() {
	}

	public MapReentrantLockType(Map myMap) {
		this.myMap = myMap;
	}

	@Override
	public Object get(int key) {
		lock.lock();
		try {
			return myMap.get(key);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public boolean insert(Object value) {
		boolean flag = false;
		lock.lock();
		try {
			flag=myMap.put(value+"i", value) != null;
		} finally {
			lock.unlock();
		}
		return flag;
	}
}
