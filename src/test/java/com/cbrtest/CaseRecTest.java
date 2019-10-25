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
		ArrayList<CaseRec> casedatabasesOther = CaseBasicMethod.getCaseDatabasesByFile("caseresults");
		List<CaseRec> casedata = casedatabasesOther;
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

}
