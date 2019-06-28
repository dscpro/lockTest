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
import com.lock.locktype.LockType;

import junit.framework.TestCase;

public class MoreLockTest extends TestCase {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		int numThreads;
		int readNum;
		int exeNum;

		int structure_type = 2;
		int lock_type;
		int[] lock = { 0, 1, 2, 3 };
		int operate_type = 0;
		int operate_structure_type = 6;

		for (int lock_typeIndex : lock) {
			lock_type = lock_typeIndex;
			for (int numThreadsIndex : Constant.NUM_THREADS) {
				numThreads = numThreadsIndex;
				for (int exeTimesIndex : Constant.NUM_EXETIMES) {
					exeNum = exeTimesIndex;
					for (double readNumIndex : Constant.NUM_READ) {
						readNum = (int) (readNumIndex*numThreads);
						if (readNum <= numThreads) {
							TestInfo info = new TestInfo(numThreads, readNum, exeNum, structure_type,
									operate_structure_type, lock_type, operate_type);

							String listReadwritelocktypestr = "com.lock.locktype."
									+ Constant.STRUCTURE_TYPE[structure_type] + Constant.LOCK_TYPE[lock_type];

							LockType locktest = LockTypePre.preLock(listReadwritelocktypestr,
									DataBasic.getDataPre(info));
							ThreadStart.testLock(info, locktest);
						}
					}
				}
			}

		}
	}
}
