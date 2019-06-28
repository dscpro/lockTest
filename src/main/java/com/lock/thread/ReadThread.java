package com.lock.thread;

import com.lock.Constant;
import com.lock.locktype.LockType;

public class ReadThread extends Thread {
	int id;
	LockType test;
	int num;

	public ReadThread(int id, LockType test,int opnum) {
		this.id = id;
		this.test = test;
		this.num = opnum;
	}

	@Override
	public void run() {
		int index;
		for (int i = 0; i < num; i++) {
			index = i;
			test.get(index);
		}
	}
}
