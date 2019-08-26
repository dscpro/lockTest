package com.proceduretest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.cbr.CaseRec;
import com.cbr.recommend.CaseRecommend;
import com.procedure.analysis.ProAnalysis;;

public class TestAll {
	public static void main(String[] args) {
		// 对LockTest中test推荐加锁
		String name = "com.proceduretest.LockTest";
		// 分析，返回
		HashMap<String, Integer> attributeMap = ProAnalysis.proAnalysis(name);
		//构建案例类
		CaseRec caserec = new CaseRec(attributeMap.get("numthreads"), attributeMap.get("readnum"), attributeMap.get("num_operate"), attributeMap.get("structure_type"), attributeMap.get("operate_structure_type"));
		//推荐案例
		CaseRecommend cbr = new CaseRecommend();
		ArrayList<Map.Entry<Double, CaseRec>> topCases = cbr.retrieval(caserec);
		cbr.printResults(topCases);
	}

}
