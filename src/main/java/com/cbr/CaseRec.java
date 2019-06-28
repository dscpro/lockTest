package com.cbr;

public class CaseRec extends Case {

	public CaseRec() {
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
	public CaseRec(int numThreads, int readNum, int num_operate, int structure_type, int operate_structure_type) {
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

}
