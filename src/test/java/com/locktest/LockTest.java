package com.locktest;

import org.junit.Test;

import com.lock.Constant;
import com.lock.DataBasic;
import com.lock.LockTypePre;
import com.lock.ThreadStart;
import com.lock.LockType.LockType;

public class LockTest {
	@Test
	public void testlock() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		int numThreads = Constant.NUM_THREADS[9];
		int readNum = Constant.NUM_READ[4];
		int exeTimes = Constant.NUM_EXETIMES[3];
		int structure_type = 1;
		int lock_type = 0;

		String listReadwritelocktypestr = "com.lock.LockType." + Constant.STRUCTURE_TYPE[structure_type]
				+ Constant.LOCK_TYPE[lock_type];

		LockType locktest = LockTypePre.preLock(listReadwritelocktypestr,DataBasic.getDataPre(Constant.STRUCTURE_TYPE[structure_type], readNum, exeTimes));

		ThreadStart.testLock(numThreads, readNum, exeTimes, locktest);

	}
}
