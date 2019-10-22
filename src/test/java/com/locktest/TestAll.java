package com.locktest;

import java.util.ArrayList;
import java.util.List;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.create.CaseCreate;
import com.cbr.recommend.CaseRecommend;
import com.lock.DataBasic;

public class TestAll {
	public static void main(String[] args) {
		DataBasic.moreLockCreate();
		CaseCreate.constructCase();
		
		CaseRecommend crec= new CaseRecommend();
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
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
