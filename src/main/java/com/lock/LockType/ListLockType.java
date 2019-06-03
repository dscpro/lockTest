package com.lock.LockType;

import java.util.List;

public class ListLockType implements LockType {
	protected List list;
	 
	@Override
	public Object get(int index) {

		return null;
	}

	@Override
	public boolean insert(Object newValue) {

		return false;
	}

	@Override
	public void setOperateType(Object myList) {
		this.list = (List) myList;
	}

	@Override
	public String getStruct() {
		return list.getClass().getSimpleName().toString();
	}

}
