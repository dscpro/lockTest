package com.lock.locktype;

import java.util.Iterator;
import java.util.TreeSet;

public class SetSynchronizedLockType extends SetLockType {
	public SetSynchronizedLockType() {
	}

	public SetSynchronizedLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	public synchronized void iterator() {
		Iterator<Integer> it = myset.iterator();
		while (it.hasNext()) {
			it.next();
		}
	}

	public synchronized boolean add(int newValue) {
		return myset.add(newValue);
	}
}
