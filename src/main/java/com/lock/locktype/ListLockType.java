package com.lock.locktype;

import java.util.List;

public class ListLockType implements LockType {
	protected List list;
	 
	public Object get(int index) {
		return null;
	}

	public boolean insert(Object newValue) {
		return false;
	}

	public void setOperateType(Object myList) {
		this.list = (List) myList;
	}

	public String getStruct() {
		return list.getClass().getSimpleName().toString();
	}

}
