package com.lock.LockType;

import java.util.List;

public class ListSynchronizedType extends ListLockType {
	public ListSynchronizedType() {
	}

	public ListSynchronizedType(List myList) {
		this.list = myList;
	}

	public synchronized Object get(int index) {
		return list.get(index);
	}

	public synchronized boolean insert(Object newValue) {
		return list.add(newValue);
	}
}
