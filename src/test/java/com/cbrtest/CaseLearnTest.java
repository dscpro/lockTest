package com.cbrtest;

import org.junit.Test;

import com.cbr.CaseRec;
import com.cbr.recommend.CaseLearnRec;

public class CaseLearnTest {
	@Test
	public void testLearn() {
		CaseLearnRec clr = new CaseLearnRec();
		CaseRec c = new CaseRec(2, 1000, 200, 80);
		System.out.println(clr.learncase(c));
	}

}
