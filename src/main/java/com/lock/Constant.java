package com.lock;

public final class Constant {
	/**
	 * 线程数
	 */
	public static Integer[] NUM_THREADS = {5,50,80,100,200,500,800,1000,1200,1500,1800,2400,2800,3000,3500,3800,4000,4500,5000,6000,7000,8000,10000};
	/**
	 * 读操作线程数
	 */
	public static double[] NUM_READ ={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	/**
	 * 执行次数
	 */
	//public static Integer NUM_OPERATE =50;
	//public static Integer[] NUM_WRITE ={1,5,10,50,100};
	public static Integer[] NUM_EXETIMES ={5,50,100,1000,10000};
	/**
	 * 数据结构类型
	 */
	public static String[] STRUCTURE_TYPE={"Group","List","Map","Set"};
	/**
	 * 操作数据类型
	 */
	public static String[] OPERATE_STRUCTURE_TYPE={"Group","Array","Linked","Hash","Tree","ConcurrentHash","WeakHash"};
	
	/**
	 * 锁类型
	 */
	public static String[] LOCK_TYPE={"ReentrantReadWriteLockType","ReentrantLockType","StampedLockType","SynchronizedLockType"};
	/**
	 * 操作对象类型
	 */
	public static String[] OPERATE_TYPE={"Int","String","Boolean"};
}
