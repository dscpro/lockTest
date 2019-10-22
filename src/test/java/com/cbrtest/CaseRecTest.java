package com.cbrtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseRecommend;

public class CaseRecTest {
	@Test
	public void testall() {
		CaseRecommend crec = new CaseRecommend();
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
		List<CaseRec> casedata = casedatabases.subList(105, 110);
		double sum = 0;
		for (CaseRec caseRec : casedatabases) {
			if(crec.caseRecommend(caseRec)==caseRec.getLock_type()) {
				sum++;
			}
		}
		 double accuracy = sum/casedatabases.size();
		 System.out.println("Accuracy is :"+(accuracy));
	}
	
	public void testonel() {
		CaseRecommend crec = new CaseRecommend();
		CaseRec cre = new CaseRec(0, 5, 2, 5);
		System.out.println(crec.caseRecommend(cre) == 2);
	}

}
