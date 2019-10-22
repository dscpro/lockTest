package com.cbr;

public class CaseRec extends Case {

	public CaseRec() {
	}

	/**
	 * @param structure_type 0:ArrayList,1:LinkedList,2:HashMap,3:TreeMap,4:TreeSet
	 * @param numThreads
	 * @param readNum
	 * @param num_operate
	 */
	public CaseRec(int structure_type,int numThreads, int readNum, int num_operate) {
		super();
		this.numThreads = numThreads;
		this.readNum = readNum;
		this.num_operate = num_operate;
		this.structure_type = structure_type;
	}

	public String toString() {
		String result = "";
		if (this.lock_type >= 0) {

		}

		return result;
	}

}


