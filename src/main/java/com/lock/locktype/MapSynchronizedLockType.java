package com.lock.locktype;

import java.util.Map;

public class MapSynchronizedLockType extends MapLockType {
	public MapSynchronizedLockType() {
	}

	public MapSynchronizedLockType(Map myMap) {
		this.myMap = myMap;
	}
	@Override
	public synchronized Object get(int key) {
		return myMap.get(key);
	}
	@Override
	public synchronized boolean insert(Object value) {
		return myMap.put(value+"i", value) != null;
	}
}
