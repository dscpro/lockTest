package com.locktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lock.Constant;
import com.lock.DataBasic;
import com.lock.LockTypePre;
import com.lock.TestInfo;
import com.lock.ThreadStart;
import com.lock.LockType.LockType;

import junit.framework.TestCase;

public class ArrayListLockTest extends TestCase {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		int numThreads;
		int readNum;
		int exeTimes;

		int structure_type = 1;
		int lock_type = 0;
		int operate_type = 0;
		int operate_structure_type=0;

		for (int numThreadsIndex : Constant.NUM_THREADS) {
			numThreads = numThreadsIndex;
			for (int exeTimesIndex : Constant.NUM_EXETIMES) {
				exeTimes = exeTimesIndex;
				for (int readNumIndex : Constant.NUM_READ) {
					readNum = readNumIndex;
					if (readNum <= numThreads) {
						TestInfo info = new TestInfo(numThreads, readNum, exeTimes, structure_type,operate_structure_type, lock_type,
								operate_type);

						String listReadwritelocktypestr = "com.lock.LockType." + Constant.STRUCTURE_TYPE[structure_type]
								+ Constant.LOCK_TYPE[lock_type];

						LockType locktest = LockTypePre.preLock(listReadwritelocktypestr,
								DataBasic.getDataPre(Constant.OPERATE_STRUCTURE_TYPE[operate_structure_type]));
						ThreadStart.testLock(info, locktest);
					}
				}
			}
		}
	}
}
