package com.lock.locktype;

public class GroupLockType implements LockType {

	protected int[] intarray;

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
		return intarray.getClass().getSimpleName().toString();
	}

	@Override
	public void setOperateType(Object obj) {

		System.out.println(obj.toString());
	}

}