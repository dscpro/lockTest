package com.lock.thread;

import com.lock.Constant;
import com.lock.locktype.LockType;

public class ReadThread extends Thread {
	int id;
	LockType test;
	int num;

	public ReadThread(int id, LockType test) {
		this.id = id;
		this.test = test;
		num = Constant.NUM_OPERATE;
	}

	@Override
	public void run() {
		int index;
		for (int i = 0; i < Constant.NUM_OPERATE; i++) {
			index = i;
			test.get(index);
		}
	}
}
