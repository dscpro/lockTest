package com.lock.locktype;

public class GroupLockType implements LockType {

	protected int[] intarray;

	public Object get(int index) {
		return null;
	}

	public boolean insert(Object newValue) {
		return false;
	}

	public String getStruct() {

		return intarray.getClass().getSimpleName().toString();

	}

	public void setOperateType(Object obj) {

		System.out.println(obj.toString());
	}

}
