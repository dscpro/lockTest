package com.lock;

import java.io.IOException;
import java.util.logging.Logger;

import com.lock.locktype.LockType;
import com.lock.thread.ReadThread;
import com.lock.thread.WriteThread;

public class ThreadStart {
	static Logger log = Logger.getLogger("");

	public static void testLock(TestInfo testinfo, LockType test) throws IOException {
		int numThreads = testinfo.getNumThreads();
		int readNum = testinfo.getReadNum();
		int exeNum = testinfo.getNum_operate();
		String info = "When Thread_Num is:" + numThreads + ";Read_Num is:" + readNum + ";Op_Num is:" + exeNum + ";";

		
		log.info(test.getClass().getSimpleName() + "++++start test++++");

		Thread[] rd = new ReadThread[readNum];
		int writeNum = numThreads - readNum;
		Thread[] wr = new WriteThread[writeNum];
		long startTime = System.currentTimeMillis();
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
