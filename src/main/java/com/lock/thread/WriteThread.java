package com.lock.thread;

import com.lock.Constant;
import com.lock.locktype.LockType;

public class WriteThread extends Thread {
	int id;
	LockType test;
	int num;

	public WriteThread(int id, LockType test,int opnum) {
		this.id = id;
		this.test = test;
		this.num =  opnum;
	}
	@Override
	public void run() {
		for (int i = 0; i < num; i++) {
			test.insert(id * num + i);
		}
	}
}
