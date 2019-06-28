package com.lock.locktype;

import java.util.Map;

public class MapLockType implements LockType {
	protected Map<Integer, Integer> myMap;

	public Object get(int index) {
		return null;
	}

	public boolean insert(Object newValue) {
		return false;
	}

	public String getStruct() {
		return myMap.getClass().getSimpleName().toString();
	}

	public void setOperateType(Object obj) {
		this.myMap = (Map<Integer, Integer>) obj;
	}

}
