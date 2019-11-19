package com.lock.locktype;

import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class MapStampedLockType extends MapLockType {
	StampedLock sl = new StampedLock();

	public MapStampedLockType() {
	}

	public MapStampedLockType(Map myMap) {
		this.myMap = myMap;
	}

	@Override
	public Object get(int key) {
		long stamp = sl.readLock();
		try {
			return myMap.get(key);
		} finally {
			sl.unlockRead(stamp);
		}
	}

	@Override
	public boolean insert(Object value) {
		boolean flag = false;
		long stamp = sl.writeLock();
		try {
			flag = myMap.put(value+"i", value) != null;
		} finally {
			sl.unlockWrite(stamp);
		}
		return flag;
	}
}
