package com.lock.locktype;

import java.util.Set;
import java.util.TreeSet;

public class SetLockType implements LockType {
	protected Set<Integer> myset;

	public Object get(int index) {
		return null;
	}
	public boolean insert(Object newValue) {
		return false;
	}

	public String getStruct() {
		return myset.getClass().getSimpleName().toString();
	}

	public void setOperateType(Object obj) {
		this.myset = (Set<Integer>) obj;
	}

}
