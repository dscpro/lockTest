package com.proceduretest;

import java.util.HashMap;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseRecommend;
import com.procedure.analysis.ProAnalysis;;

public class TestAll {
	public static void main(String[] args) {
		// for class LockTest recommend
		String classname = "com.proceduretest.LockTest";
		// Procedure analysis
		HashMap<String, Integer> attributeMap = ProAnalysis.proAnalysis(classname);
		// Create Case
		CaseRec caserec = new CaseRec(attributeMap.get("structure"), attributeMap.get("threadsnum"),
				attributeMap.get("readnum"), attributeMap.get("operatenum"));
		// Recommend
		CaseRecommend cbr = new CaseRecommend();
		int lock = cbr.caseRecommend(caserec);
		cbr.printLock(lock);
	}

}
