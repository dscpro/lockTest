package com.lock.locktype;

import java.util.Map;

public class MapLockType implements LockType {
	protected Map  myMap;

	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public boolean insert(Object newValue) {
		return false;
	}

	public String getStruct() {
		return myMap.getClass().getSimpleName().toString();
	}

	public void setOperateType(Object obj) {
		this.myMap = (Map) obj;
	}

}
