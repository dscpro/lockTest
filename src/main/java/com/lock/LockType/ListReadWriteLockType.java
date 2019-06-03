package com.lock.LockType;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListReadWriteLockType extends ListLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public ListReadWriteLockType() {
	}

	public ListReadWriteLockType(List myList) {
		this.list = myList;
	}

	public Object get(int index) {
		readLock.lock();
		try {
			return list.get(index);
		} finally {
			readLock.unlock();
		}
	}

	public boolean insert(Object newValue) {
		writeLock.lock();
		try {
			return list.add(newValue);
		} finally {
			writeLock.unlock();
		}
	}

}
