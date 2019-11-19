package com.proceduretest;

import java.util.ArrayList;

public class LockTest {
	int structure = 1;
	int threadsnum = 10000;
	int readnum = 3000;
	int operatenum = 50000;

	public LockTest() {
	}

	public void testAdd() {
		ArrayList<Integer>  a =new ArrayList<Integer>();
		
		synchronized (a) {
			a.add(1);
		}
	}
	public void testGet() {
		ArrayList<Integer>  a =new ArrayList<Integer>();
		
		synchronized (a) {
			a.get(1);
		}
	}
}
