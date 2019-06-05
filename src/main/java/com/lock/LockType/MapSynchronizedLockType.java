package com.lock.LockType;

import java.util.Map;

public class MapSynchronizedLockType extends MapLockType {
	public MapSynchronizedLockType() {
	}

	public MapSynchronizedLockType(Map<Integer, Integer> myMap) {
		this.myMap = myMap;
	}

	public synchronized Object get(Integer key) {
		return myMap.get(key);
	}

	public synchronized void put(Integer key, Integer value) {
		myMap.put(key, value);
	}
}
