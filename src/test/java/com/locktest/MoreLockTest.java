package com.locktest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lock.Constant;
import com.lock.DataBasic;
import com.lock.LockTypePre;
import com.lock.SaveToExcel;
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

		int[] structure_types = { 1, 2, 3, 4 };
		int lock_type;
		int[] lock = { 0, 1, 2, 3 };
		ArrayList<TestInfo> lockinfos = new ArrayList<TestInfo>();
		// int operate_type = 0;
		// int operate_structure_type = 6;
		for (int structure_type : structure_types) {

			for (int lock_typeIndex : lock) {
				lock_type = lock_typeIndex;
				for (int numThreadsIndex : Constant.NUM_THREADS) {
					numThreads = numThreadsIndex;
					for (int exeTimesIndex : Constant.NUM_EXETIMES) {
						exeNum = exeTimesIndex;
						for (double readNumIndex : Constant.NUM_READ) {
							readNum = (int) (readNumIndex * numThreads);
							if (readNum <= numThreads) {
								TestInfo info = new TestInfo(numThreads, readNum, exeNum, structure_type, lock_type);
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
								info = ThreadStart.testLock(info, locktest);
								lockinfos.add(info);
								if(lockinfos.size()>=3000) {
									SaveToExcel.savetoexcel(lockinfos);
									lockinfos.clear();
								}
							}
						}

					}
				}
			}
		}

	}
}
