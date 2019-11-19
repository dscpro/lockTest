package com.lock.locktype;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.StampedLock;

public class SetStampedLockType extends SetLockType {
	StampedLock sl = new StampedLock();

	public SetStampedLockType() {
	}

	public SetStampedLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	@Override
	public Object get(int index) {
		long stamp = sl.readLock();
		try {
			Iterator<Integer> it = myset.iterator();
			while (it.hasNext()) {
				if (index == it.next())
					break;
				else
					index = 0;
				it.next();
			}
		} finally {
			sl.unlockRead(stamp);
		}
		return index;
	}

	@Override
	public boolean insert(Object newValue) {
		long stamp = sl.writeLock();
		try {
			return myset.add(newValue);
		} finally {
			sl.unlockWrite(stamp);
		}
	}
}
