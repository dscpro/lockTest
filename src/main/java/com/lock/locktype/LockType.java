package com.lock.locktype;

public interface LockType {	
	public String getStruct();
	public void setOperateType(Object obj);
	public Object get(int index);
	public boolean insert(Object newValue);
}
