package com.lock.locktype;

import java.util.Map;

public class MapLockType implements LockType {
	protected Map<Integer, Integer> myMap;

	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public boolean insert(Object newValue) {
		return false;
	}

	@Override
	public String getStruct() {
		return myMap.getClass().getSimpleName().toString();
	}

	@Override
	public void setOperateType(Object obj) {
		this.myMap = (Map<Integer, Integer>) obj;
	}

}
