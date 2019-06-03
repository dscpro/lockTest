package com.lock;

public final class Constant {
	public static Integer[] NUM_THREADS = {2,5,10,50,80,100,200,500,1000,10000};
	public static Integer[] NUM_READ ={0,1,5,50,100,1000};
	public static Integer NUM_OPERATE =50;
	//public static Integer[] NUM_WRITE ={1,5,10,50,100};
	public static Integer[] NUM_EXETIMES ={5,50,100,1000,10000,100000};
	public static String[] STRUCTURE_TYPE={"Array","List","Map","Set"};
	public static String[] LOCK_TYPE={"ReadWriteLockType","ReentrantLockType","StampedLockType","SynchronizedLockType"};
	public static String[] OPERATE_TYPE={"int","string","boolean"};
}
