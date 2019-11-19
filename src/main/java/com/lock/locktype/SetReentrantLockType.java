package com.lock.locktype;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SetReentrantLockType extends SetLockType {
	private Lock lock = new ReentrantLock();

	public SetReentrantLockType() {
	}

	public SetReentrantLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	@Override
	public Object get(int index) {
		lock.lock();
		try {
			Iterator<Integer> it = myset.iterator();

			while (it.hasNext()) {
				if (index == it.next())
					break;
				else
					index=0;
				it.next();
			}
		} finally {
			lock.unlock();
		}
		return index;
	}

	@Override
	public boolean insert(Object newValue) {
		lock.lock();

		try {
			return myset.add(newValue);
		} finally {
			lock.unlock();
		}
	}
}
