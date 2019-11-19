package com.lock.locktype;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SetReentrantReadWriteLockType extends SetLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public SetReentrantReadWriteLockType() {
	}

	public SetReentrantReadWriteLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	@Override
	public Object get(int index) {
		readLock.lock();
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
			readLock.unlock();
		}
		return index;
	}

	@Override
	public boolean insert(Object newValue) {
		writeLock.lock();

		try {
			return myset.add(newValue);
		} finally {
			writeLock.unlock();
		}
	}
}
