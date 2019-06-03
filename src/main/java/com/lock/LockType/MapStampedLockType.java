package com.lock.LockType;

import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class MapStampedLockType extends MapLockType {
	StampedLock sl;

	public MapStampedLockType() {
	}

	public MapStampedLockType(Map<Integer, Integer> myMap) {
		this.myMap = myMap;
		sl = new StampedLock();
	}

	public Object get(Integer key) {
		long stamp = sl.readLock();
		try {
			return myMap.get(key);
		} finally {
			sl.unlockRead(stamp);
		}
	}

	public void put(Integer key, Integer value) {

		long stamp = sl.writeLock();
		try {
			myMap.put(key, value);
		} finally {
			sl.unlockWrite(stamp);
		}
	}
}
