package com.locktest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lock.Constant;
import com.lock.DataBasic;
import com.lock.LockTypePre;
import com.lock.ThreadStart;
import com.lock.LockType.LockType;

import junit.framework.TestCase;

public class ArrayListLockTest extends TestCase {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		int numThreads;
		int readNum;
		int exeTimes;
		int structure_type = 1;
		int lock_type = 0;

		for (int numThreadsIndex : Constant.NUM_THREADS) {
			numThreads = numThreadsIndex;
			for (int exeTimesIndex : Constant.NUM_EXETIMES) {
				exeTimes = exeTimesIndex;
				for (int readNumIndex : Constant.NUM_READ) {
					readNum = readNumIndex;
					if (readNum <= numThreads) {

						String listReadwritelocktypestr = "com.lock.LockType." + Constant.STRUCTURE_TYPE[structure_type]
								+ Constant.LOCK_TYPE[lock_type];

						LockType listtest = LockTypePre.preLock(listReadwritelocktypestr,
								DataBasic.getDataPre(Constant.STRUCTURE_TYPE[structure_type],readNum,exeTimes));
						ThreadStart.testLock(numThreads, readNum, exeTimes, listtest);
					}
				}
			}
		}
	}
}
