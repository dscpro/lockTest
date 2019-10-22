package com.cbrtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseRecommend;

public class CaseRecTest {
	@Test
	public void name() {
		CaseRecommend crec= new CaseRecommend();
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabasesByFile("caseresults.xlsx");
		List<CaseRec> casedata = casedatabases.subList(2000,5000);
		double sum=0;
		for (CaseRec caseRec : casedatabases) {
			if(crec.caseRecommend(caseRec)==caseRec.getLock_type()) {
				sum++;
			}
		}
		double accuracy = sum/casedatabases.size();
		System.out.println("Accuracy is :"+(accuracy));
	}
	
}
