package com.lock.LockType;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SetReadWriteLockType extends SetLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public SetReadWriteLockType() {
	}

	public SetReadWriteLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	public void iterator() {
		readLock.lock();
		try {
			Iterator<Integer> it = myset.iterator();

			while (it.hasNext()) {
				it.next();

			}
		} finally {
			readLock.unlock();
		}
	}

	public boolean add(int newValue) {
		writeLock.lock();

		try {
			return myset.add(newValue);
		} finally {
			writeLock.unlock();
		}
	}
}
