package com.locktest;

import java.io.IOException;

import org.junit.Test;

import com.lock.Constant;
import com.lock.DataBasic;
import com.lock.LockTypePre;
import com.lock.TestInfo;
import com.lock.ThreadStart;
import com.lock.locktype.LockType;

public class LockTest {
	@Test
	public void testlock() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		
		int numThreads = Constant.NUM_THREADS[0];		
		double readNum1 = Constant.NUM_READ[1];		
		int num_operate = Constant.NUM_EXETIMES[2];		
		int structure_type = 4;
		int lock_type = 0;
	 
		int readNum=(int) (readNum1*numThreads);
		TestInfo info = new TestInfo(numThreads, readNum, num_operate, structure_type, lock_type);
		String listlocktypestr = "";
		String s = Constant.STRUCTURE_TYPE[structure_type];
		if (structure_type == 0 || structure_type == 1) {
			listlocktypestr = "com.lock.locktype." + s.substring(s.length() - 4, s.length())
					+ Constant.LOCK_TYPE[lock_type];
		} else {
			listlocktypestr = "com.lock.locktype." + s.substring(s.length() - 3, s.length())
					+ Constant.LOCK_TYPE[lock_type];
		}
		LockType locktest = LockTypePre.preLock(listlocktypestr, DataBasic.getDataPre(info));
		
		ThreadStart.testLock(info, locktest);

	}
}
