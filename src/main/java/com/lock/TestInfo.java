package com.lock;

public class TestInfo {
	int numThreads;
	int readNum;
	int exeTimes;
	int structure_type;
	int lock_type;
	int operate_type;
	int operate_structure_type;
	long wasteTime;

	public TestInfo() {
	}

	public TestInfo(int numThreads, int readNum, int exeTimes, int structure_type,int operate_structure_type, int lock_type,int operate_type) {
		super();
		this.numThreads = numThreads;
		this.readNum = readNum;
		this.exeTimes = exeTimes;
		this.structure_type = structure_type;
		this.operate_structure_type=operate_structure_type;
		this.lock_type = lock_type;
		this.operate_type=operate_type;
	}

	public int getNumThreads() {
		return numThreads;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public int getReadNum() {
		return readNum;
	}

	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}

	public int getExeTimes() {
		return exeTimes;
	}

	public void setExeTimes(int exeTimes) {
		this.exeTimes = exeTimes;
	}

	public int getStructure_type() {
		return structure_type;
	}

	public void setStructure_type(int structure_type) {
		this.structure_type = structure_type;
	}

	public int getOperate_structure_type() {
		return operate_structure_type;
	}

	public void setOperate_structure_type(int operate_structure_type) {
		this.operate_structure_type = operate_structure_type;
	}

	public int getLock_type() {
		return lock_type;
	}

	public void setLock_type(int lock_type) {
		this.lock_type = lock_type;
	}

	public int getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(int operate_type) {
		this.operate_type = operate_type;
	}

	public long getWasteTime() {
		return wasteTime;
	}

	public void setWasteTime(long wasteTime) {
		this.wasteTime = wasteTime;
	}

}
