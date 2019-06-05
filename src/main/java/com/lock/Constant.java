package com.lock;

public final class Constant {
	/**
	 * 线程数
	 */
	public static Integer[] NUM_THREADS = {2,5,10,50,80,100,200,500,1000,10000};
	/**
	 * 读操作线程数
	 */
	public static Integer[] NUM_READ ={0,1,5,50,100,1000};
	/**
	 * 执行次数
	 */
	public static Integer NUM_OPERATE =50;
	//public static Integer[] NUM_WRITE ={1,5,10,50,100};
	public static Integer[] NUM_EXETIMES ={5,50,100,1000,10000,100000};
	/**
	 * 数据结构类型
	 */
	public static String[] STRUCTURE_TYPE={"Group","List","Map","Set"};
	/**
	 * 操作数据类型
	 */
	public static String[] OPERATE_STRUCTURE_TYPE={"Group","Array","Linked","Hash","Tree"};
	
	/**
	 * 锁类型
	 */
	public static String[] LOCK_TYPE={"ReadWriteLockType","ReentrantLockType","StampedLockType","SynchronizedLockType"};
	/**
	 * 操作对象类型
	 */
	public static String[] OPERATE_TYPE={"Int","String","Boolean"};
}
