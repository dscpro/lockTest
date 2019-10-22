package com.cbr;

public class CaseOri extends Case {

	int exetimes;

	public CaseOri() {
	}
	/**
	 * lock_type  structure_type  numThreads  readNum  num_operate
	 */
	public CaseOri(int lock_type, int structure_type, int numThreads, int readNum, int num_operate) {
		super();
		this.numThreads = numThreads;
		this.readNum = readNum;
		this.num_operate = num_operate;
		this.structure_type = structure_type;
		this.lock_type = lock_type;
		// this.exetimes=exetimes;
	}

	public int getExetimes() {
		return exetimes;
	}

	public void setExetimes(int exetimes) {
		this.exetimes = exetimes;
	}
}
