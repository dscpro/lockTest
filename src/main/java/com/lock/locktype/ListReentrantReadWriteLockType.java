package com.lock.locktype;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ListReentrantReadWriteLockType extends ListLockType {

	private ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();
	private Lock readLock = rwlock.readLock();
	private Lock writeLock = rwlock.writeLock();

	public ListReentrantReadWriteLockType() {
	}

	public ListReentrantReadWriteLockType(List myList) {
		this.list = myList;
	}
	 @Override
	public Object get(int index) {
		readLock.lock();
		try {
			return list.get(index);
		} finally {
			readLock.unlock();
		}
	}
	 @Override
	public boolean insert(Object newValue) {
		writeLock.lock();
		try {
			return list.add(newValue);
		} finally {
			writeLock.unlock();
		}
	}

}
