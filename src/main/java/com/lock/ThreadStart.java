package com.lock;

import java.io.IOException;
import java.util.logging.Logger;

import com.lock.LockType.LockType;
import com.lock.Thread.ReadThread;
import com.lock.Thread.WriteThread;

public class ThreadStart {
	static Logger log = Logger.getLogger("");

	public static void testLock(TestInfo testinfo, LockType test) throws IOException {
		int numThreads = testinfo.getNumThreads();
		int readNum = testinfo.getReadNum();
		int exeTimes = testinfo.getExeTimes();
		String info = "When Thread_Num is:" + numThreads + ";Read_Num is:" + readNum + ";Exe_Num is:" + exeTimes + ";";

		long startTime = System.currentTimeMillis();
		log.info(test.getClass().getSimpleName() + "++++start test++++");

		Thread[] rd = new ReadThread[readNum];
		int writeNum = numThreads - readNum;
		Thread[] wr = new WriteThread[writeNum];
		for (int i = 0; i < readNum; i++) {
			rd[i] = new ReadThread(i, test);
			rd[i].start();
		}
		for (int i = 0; i < writeNum; i++) {
			wr[i] = new WriteThread(readNum + i, test);
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
		log.info(info+test.getStruct() + " use " + test.getClass().getSimpleName()+" waste time is:" + (endTime - startTime) + "ms");
		testinfo.setWasteTime(endTime - startTime);
		//SaveToExcel.savetoexcel(testinfo);
	}
}
