package com.cbrtest;

import java.util.ArrayList;
import java.util.Map;

import com.cbr.Case;
import com.cbr.recommend.CaseRecommend;

import junit.framework.TestCase;

public class CaseTest extends TestCase {

	public static void main(String[] args) {
		Case case1 = new Case(1000, 450, 200, 1, 1);
		CaseRecommend cbr = new CaseRecommend();
		ArrayList<Map.Entry<Double, Case>> topCases = cbr.retrieval(case1);
		cbr.printResults(topCases);
	}
}
