package com.cbrtest;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Test;

import com.cbr.CaseRec;
import com.cbr.create.CaseCreate;
import com.cbr.recommend.CaseRecommend;

import junit.framework.TestCase;

public class CaseTest {
	
	public void testRec() {
		CaseRec case1 = new CaseRec(1000, 450, 200, 1, 1);
		CaseRecommend cbr = new CaseRecommend();
		ArrayList<Map.Entry<Double, CaseRec>> topCases = cbr.retrieval(case1);
		cbr.printResults(topCases);
	}
	@Test
	public void testCre() {
		CaseCreate case2= new CaseCreate();
		case2.constructCase();
		
	}
}
