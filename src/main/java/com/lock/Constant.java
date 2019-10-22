package com.lock;

public final class Constant {
	/**
	 * 线程数
	 */
	public static Integer[] NUM_THREADS = {5,50,80,100,200,500,800,1000,1200,1400,1600,1800,2000,2200,2400,2600,2800,3000,3200,3500,3800,4000,4200,4500,4800,5000,5200,5500,5800,6000};
	/**
	 * 读操作线程数
	 */
	public static double[] NUM_READ ={0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0};
	
	/**
	 * 执行次数
	 */
	//public static Integer NUM_OPERATE =50;
	//public static Integer[] NUM_WRITE ={1,5,10,50,100};
	public static Integer[] NUM_EXETIMES ={5,50,100,150,180,200,240,280,300,320,360,380,420,460,480,550,600,640,680,700,720,760,780,800,820,860,880,900,940,960,980,1000,1100,1200,1250,1300,1350,1400,1450,1500,1550,1600,1650,1700,1750,1800,1850,1900,1950,2000,2500,3000,3500,4000,4500,5000};
	/**
	 * 数据结构类型
	 */
	public static String[] STRUCTURE_TYPE={"ArrayList","LinkedList","HashMap","TreeMap","TreeSet"};
	/**
	 * 操作数据类型
	 */
	//public static String[] OPERATE_STRUCTURE_TYPE={"Group","Array","Linked","Hash","Tree","ConcurrentHash","WeakHash"};
	
	/**
	 * 锁类型
	 */
	public static String[] LOCK_TYPE={"ReentrantReadWriteLockType","ReentrantLockType","StampedLockType","SynchronizedLockType"};
	/**
	 * 操作对象类型
	 */
	public static String[] OPERATE_TYPE={"Int","String","Boolean"};
}
