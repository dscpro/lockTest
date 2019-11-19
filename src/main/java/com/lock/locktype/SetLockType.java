package com.lock.locktype;

import java.util.Set;
import java.util.TreeSet;

public class SetLockType implements LockType {
	protected Set myset;

	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public boolean insert(Object newValue) {
		return false;
	}

	public String getStruct() {
		return myset.getClass().getSimpleName().toString();
	}

	public void setOperateType(Object obj) {
		this.myset = (Set) obj;
	}

}
