package com.lock.LockType;

import java.util.Map;

public class MapSynchroizedLockType extends MapLockType {
	public MapSynchroizedLockType() {
	}

	public MapSynchroizedLockType(Map<Integer, Integer> myMap) {
		this.myMap = myMap;
	}

	public synchronized Object get(Integer key) {
		return myMap.get(key);
	}

	public synchronized void put(Integer key, Integer value) {
		myMap.put(key, value);
	}
}
