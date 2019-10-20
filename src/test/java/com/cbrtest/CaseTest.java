package com.cbrtest;

import org.junit.Test;
import com.cbr.CaseRec;
import com.cbr.create.CaseCreate;
import com.cbr.recommend.CaseRecommend;

public class CaseTest {
	@Test
	public void testRec() {
		CaseRec case1 = new CaseRec(0,5000, 0.0, 800);
		CaseRecommend cbr = new CaseRecommend();
		int topCases = cbr.caseRecommend(case1);
		System.out.println(topCases);
	}
	
	public void testcheck() {
		CaseCreate case2 = new CaseCreate();
		case2.checkDataPre();;
	}
	public void testCre() {
		CaseCreate case2 = new CaseCreate();
		case2.constructCase();
	}

	public static void main(String[] args) {
		CaseCreate case2 = new CaseCreate();
		//case2.constructCase();
	}
}
