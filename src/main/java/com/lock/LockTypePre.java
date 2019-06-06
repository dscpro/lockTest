package com.lock;

import com.lock.locktype.LockType;

public class LockTypePre {
	public static LockType preLock(String locktypestr,Object operatetype) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
		LockType locktype = (LockType) SetClass.getclass(locktypestr);
		locktype.setOperateType(operatetype);
		return locktype;
	}
}
