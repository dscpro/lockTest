package com.lock.LockType;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.StampedLock;

public class SetStampedLockType extends SetLockType {
	StampedLock sl;

	public SetStampedLockType() {
	}

	public SetStampedLockType(TreeSet<Integer> myset) {
		this.myset = myset;
		sl = new StampedLock();
	}

	public void iterator() {
		long stamp = sl.readLock();
		try {
			Iterator<Integer> it = myset.iterator();
			while (it.hasNext()) {
				it.next();

			}
		} finally {
			sl.unlockRead(stamp);
		}
	}

	public boolean add(int newValue) {
		long stamp = sl.writeLock();
		try {
			return myset.add(newValue);
		} finally {
			sl.unlockWrite(stamp);
		}
	}
}
