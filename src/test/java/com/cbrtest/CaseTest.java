package com.cbrtest;

import org.junit.Test;
import com.cbr.CaseRec;
import com.cbr.create.CaseCreate;
import com.cbr.recommend.CaseRecommend;

public class CaseTest {
	@Test
	public void testRec() {
		CaseRec case1 = new CaseRec(4,3800, 0.5,800);
		case1.setLock_type(3);
		CaseRecommend cbr = new CaseRecommend();
		int topCases = cbr.caseRecommend(case1);
		System.out.println("rec:"+topCases+" real:"+case1.getLock_type());
	}
	
	public void testcheck() {
		//CaseCreate case2 = new CaseCreate();
		//case2.checkDataPre();;
	}
	public void testCre() {
	//	CaseCreate case2 = new CaseCreate();
		//case2.constructCase();
	}

	public static void main(String[] args) {
		//CaseCreate case2 = new CaseCreate();
		CaseCreate.constructCase();
	}
}
