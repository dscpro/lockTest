package com.lock.locktype;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ListReentrantLockType extends ListLockType {

	private Lock lock = new ReentrantLock();//非公平

	public ListReentrantLockType() {
	}

	public ListReentrantLockType(List myList) {
		this.list = myList;
	}

	public Object get(int index) {
		lock.lock();
		try {
			return list.get(index);
		} finally {
			lock.unlock();
		}
	}

	public boolean insert(Object newValue) {
		lock.lock();
		try {
			return list.add(newValue);
		} finally {
			lock.unlock();
		}
	}
}
