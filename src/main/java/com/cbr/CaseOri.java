package com.cbr;

public class CaseOri extends Case {

	int exetimes;

	public CaseOri() {
	}

	public CaseOri(int numThreads, int readNum, int num_operate, int structure_type,  int lock_type,int exetimes) {
		super();
		this.numThreads = numThreads;
		this.readNum = readNum;
		this.num_operate = num_operate;
		this.structure_type = structure_type;
		 
		this.lock_type = lock_type;
		this.exetimes=exetimes;
	}
	public int getExetimes() {
		return exetimes;
	}

	public void setExetimes(int exetimes) {
		this.exetimes = exetimes;
	}
}
