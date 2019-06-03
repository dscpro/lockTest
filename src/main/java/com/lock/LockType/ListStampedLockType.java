package com.lock.LockType;

import java.util.concurrent.locks.StampedLock;
import java.util.List;

public class ListStampedLockType extends ListLockType {

	StampedLock sl;

	public ListStampedLockType() {
	}

	public ListStampedLockType(List myList) {
		this.list = myList;
		sl = new StampedLock();
	}

	public Object get(int index) {
		long stamp = sl.readLock();
		try {
			return list.get(index);
		} finally {
			sl.unlockRead(stamp);
		}
	}

	public boolean insert(Object newValue) {
		long stamp = sl.writeLock();
		try {
			return list.add(newValue);
		} finally {
			sl.unlockWrite(stamp);
		}
	}
}
