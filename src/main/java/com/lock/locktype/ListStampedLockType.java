package com.lock.locktype;

import java.util.concurrent.locks.StampedLock;
import java.util.List;

public class ListStampedLockType extends ListLockType {

	StampedLock sl = new StampedLock();

	public ListStampedLockType() {
	}

	public ListStampedLockType(List myList) {
		this.list = myList;
	}
	 @Override
	public Object get(int index) {
		long stamp = sl.readLock();
		try {
			return list.get(index);
		} finally {
			sl.unlockRead(stamp);
		}
	}
	 @Override
	public boolean insert(Object newValue) {

		long stamp = sl.writeLock();
		try {
			return list.add(newValue);
		} finally {
			sl.unlockWrite(stamp);
		}
	}
}
