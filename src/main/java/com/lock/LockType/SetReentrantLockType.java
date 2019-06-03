package com.lock.LockType;

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

	public void iterator() {
		lock.lock();
		try {
			Iterator<Integer> it = myset.iterator();

			while (it.hasNext()) {
				it.next();

			}
		} finally {
			lock.unlock();
		}
	}

	public boolean add(int newValue) {
		lock.lock();

		try {
			return myset.add(newValue);
		} finally {
			lock.unlock();
		}
	}
}
