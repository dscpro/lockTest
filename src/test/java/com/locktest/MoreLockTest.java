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

public class MoreLockTest extends TestCase {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {

		int numThreads;
		int readNum;
		int exeNum;

		int structure_type = 3;
		int lock_type;
		int[] lock = { 0, 1, 2, 3 };
		int operate_type = 0;
		int operate_structure_type = 4;

		for (int lock_typeIndex : lock) {
			lock_type = lock_typeIndex;
			for (int numThreadsIndex : Constant.NUM_THREADS) {
				numThreads = numThreadsIndex;
				for (int exeTimesIndex : Constant.NUM_EXETIMES) {
					exeNum = exeTimesIndex;
					for (int readNumIndex : Constant.NUM_READ) {
						readNum = readNumIndex;
						if (readNum <= numThreads) {
							TestInfo info = new TestInfo(numThreads, readNum, exeNum, structure_type,
									operate_structure_type, lock_type, operate_type);

							String listReadwritelocktypestr = "com.lock.LockType."
									+ Constant.STRUCTURE_TYPE[structure_type] + Constant.LOCK_TYPE[lock_type];

							LockType locktest = LockTypePre.preLock(listReadwritelocktypestr,
									DataBasic.getDataPre(Constant.OPERATE_STRUCTURE_TYPE[operate_structure_type]));
							ThreadStart.testLock(info, locktest);
						}
					}
				}
			}

		}
	}
}
