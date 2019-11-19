package com.cbrtest;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.junit.Test;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseLearnRecML;
import com.cbr.recommend.CaseRecommend;

public class CaseRecTest {
	
	public void testall() {
		CaseRecommend crec = new CaseRecommend();
		//ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
		
		ArrayList<CaseRec> casedatabasesAll = CaseBasicMethod.getCaseDatabasesByFile("caseresults");
		CaseLearnRecML.initiallearncaseML(CaseBasicMethod.getCaseDatabases());
		List<CaseRec> casedatabases=casedatabasesAll;
		List<CaseRec> casedata = casedatabases;
		double sum = 0;
		for (CaseRec caseRec : casedata) {
			if(crec.caseRecommend(caseRec)==caseRec.getLock_type()) {
				sum++;
			}else {
				System.out.println("Wrong++++++++++++++++++");
			}
			System.out.println(sum);
		}
		 double accuracy = sum/casedata.size();
		 System.out.println("Accuracy is :"+(accuracy));
	}
	
	public void testone() {
		CaseRecommend crec = new CaseRecommend();
		CaseRec cre = new CaseRec(1, 3000,1500, 1000);
		System.out.println(crec.caseRecommend(cre) == 2);
	}
	@Test
	public void eval() {
		ArrayList<Integer> lockrec = new ArrayList<Integer>();
		ArrayList<Integer> lockreal = new ArrayList<Integer>();
		CaseRecommend crec = new CaseRecommend();
		ArrayList<CaseRec> casedatabasesAll = CaseBasicMethod.getCaseDatabasesByFile("caseresults");
		CaseLearnRecML.initiallearncaseML(CaseBasicMethod.getCaseDatabases());
		for (CaseRec caseRec : casedatabasesAll) {
			lockrec.add(crec.caseRecommend(caseRec));
			lockreal.add(caseRec.getLock_type());
			System.out.println(caseRec.getLock_type()+"++++++++++++++++++");
		}
		TreeMap<String, Double> evaluator = CaseBasicMethod.getEvaluator(0, lockrec, lockreal);
		System.out.println(0 + "+acc:" + evaluator.get("p") + ",recall:" + evaluator.get("r") + ",f1:"
				+ evaluator.get("f1") + ",tp:" + evaluator.get("tp") + ",fp:" + evaluator.get("fp"));
		TreeMap<String, Double> evaluator1 = CaseBasicMethod.getEvaluator(1, lockrec, lockreal);
		System.out.println(0 + "+acc:" + evaluator1.get("p") + ",recall:" + evaluator1.get("r") + ",f1:"
				+ evaluator1.get("f1") + ",tp:" + evaluator1.get("tp") + ",fp:" + evaluator1.get("fp"));
		TreeMap<String, Double> evaluator2 = CaseBasicMethod.getEvaluator(2, lockrec, lockreal);
		System.out.println(0 + "+acc:" + evaluator2.get("p") + ",recall:" + evaluator2.get("r") + ",f1:"
				+ evaluator2.get("f1") + ",tp:" + evaluator2.get("tp") + ",fp:" + evaluator2.get("fp"));
		TreeMap<String, Double> evaluator3 = CaseBasicMethod.getEvaluator(3, lockrec, lockreal);
		System.out.println(0 + "+acc:" + evaluator3.get("p") + ",recall:" + evaluator3.get("r") + ",f1:"
				+ evaluator3.get("f1") + ",tp:" + evaluator3.get("tp") + ",fp:" + evaluator3.get("fp"));
	}

}
