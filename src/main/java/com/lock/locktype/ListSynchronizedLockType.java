package com.lock.locktype;

import java.util.List;

public class ListSynchronizedLockType extends ListLockType {
	public ListSynchronizedLockType() {
	}

	public ListSynchronizedLockType(List myList) {
		this.list = myList;
	}

	public synchronized Object get(int index) {
		return list.get(index);
	}

	public synchronized boolean insert(Object newValue) {
		return list.add(newValue);
	}
}
