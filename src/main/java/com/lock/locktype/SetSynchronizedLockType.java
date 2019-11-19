package com.lock.locktype;

import java.util.Iterator;
import java.util.TreeSet;

public class SetSynchronizedLockType extends SetLockType {
	public SetSynchronizedLockType() {
	}

	public SetSynchronizedLockType(TreeSet<Integer> myset) {
		this.myset = myset;
	}

	@Override
	public synchronized Object get(int index) {
		Iterator<Integer> it = myset.iterator();
		while (it.hasNext()) {
			if (index == it.next())
				break;
			else
				index = 0;
			it.next();
		}
		return index;
	}

	@Override
	public synchronized boolean insert(Object newValue) {
		return myset.add(newValue);
	}
}
