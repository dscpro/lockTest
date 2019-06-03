package com.lock;

import com.lock.LockType.LockType;
import com.lock.Thread.ReadThread;
import com.lock.Thread.WriteThread;

public class ThreadStart {
	public static void testLock(int numThreads, int readNum, int exeTimes, LockType test) {
		String info = "When Thread_Num is:" + numThreads + ";Read_Num is:" + readNum + ";Exe_Num is:" + exeTimes + ",";

		long startTime = System.currentTimeMillis();
		System.out.println(test.getClass().getSimpleName() + "++++start test++++");

		Thread[] rd = new ReadThread[readNum];
		int writeNum = numThreads - readNum;
		Thread[] wr = new WriteThread[writeNum];
		for (int i = 0; i < readNum; i++) {
			rd[i] = new ReadThread(i, test, exeTimes);
			rd[i].start();
		}
		for (int i = 0; i < writeNum; i++) {
			wr[i] = new WriteThread(readNum + i, test, exeTimes);
			wr[i].start();
		}
		try {
			for (int i = 0; i < readNum; i++)
				rd[i].join();
			for (int j = 0; j < writeNum; j++)
				wr[j].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();

		System.out.println(info + test.getStruct() + " use " + test.getClass().getSimpleName());
		System.out.println("waste time is:" + (endTime - startTime) + "ms");
		System.out.println();
	}
}
