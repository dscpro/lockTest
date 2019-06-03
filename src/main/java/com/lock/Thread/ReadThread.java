package com.lock.Thread;

import com.lock.LockType.LockType;

public class ReadThread extends Thread {
	int id;
	LockType test;
	int num;

	public ReadThread(int id, LockType test, int executeTimes) {
		this.id = id;
		this.test = test;
		num = executeTimes;
	}

	public void run() {
		int index;
		for (int i = 0; i < num; i++) {
			index = id * num + i;
			test.get(index);
		}
	}
}
