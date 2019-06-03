package com.lock.Thread;

import com.lock.LockType.LockType;

public class WriteThread extends Thread {
	int id;
	LockType test;
	int num;

	public WriteThread(int id, LockType test, int executeTimes) {
		this.id = id;
		this.test = test;
		num = executeTimes;
	}

	public void run() {
		for (int i = 0; i < num; i++) {
			test.insert(id * num + i);
		}
	}
}
