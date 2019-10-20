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
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
		List<CaseRec> casedata = casedatabases.subList(100, 800);
		double sum=0;
		for (CaseRec caseRec : casedata) {
			if(crec.caseRecommend(caseRec)==caseRec.getLock_type()) {
				sum++;
			}
		}
		double accuracy = sum/casedata.size();
		System.out.println("Accuracy is :"+(accuracy));
	}
	
}
