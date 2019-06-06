package com.lock.locktype;

import java.util.Set;
import java.util.TreeSet;

public class SetLockType implements LockType {
	protected Set<Integer> myset;

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
		return myset.getClass().getSimpleName().toString();
	}

	@Override
	public void setOperateType(Object obj) {
		this.myset = (TreeSet<Integer>) obj;
	}

}
