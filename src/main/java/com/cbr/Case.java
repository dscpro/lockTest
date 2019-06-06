package com.cbr;

public class Case {
	int numThreads;
	int readNum;
	int num_operate;
	int structure_type;
	int operate_type;
	int operate_structure_type;
	int lock_type;

	public Case() {
	}

	/**
	 * 
	 * @param numThreads
	 * @param readNum
	 * @param num_operate
	 * @param structure_type
	 * @param operate_type
	 * @param operate_structure_type
	 */
	public Case(int numThreads, int readNum, int num_operate, int structure_type, int operate_structure_type) {
		super();
		this.numThreads = numThreads;
		this.readNum = readNum;
		this.num_operate = num_operate;
		this.structure_type = structure_type;
		this.operate_structure_type = operate_structure_type;

	}

	public String toString() {
		String result = "";
		if (this.lock_type >= 0) {

		}

		return result;
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

	public int getNum_operate() {
		return num_operate;
	}

	public void setNum_operate(int num_operate) {
		this.num_operate = num_operate;
	}

	public int getStructure_type() {
		return structure_type;
	}

	public void setStructure_type(int structure_type) {
		this.structure_type = structure_type;
	}

	public int getOperate_type() {
		return operate_type;
	}

	public void setOperate_type(int operate_type) {
		this.operate_type = operate_type;
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
}
