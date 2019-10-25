package com.cbrtest;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.junit.Test;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseLearnRecML;
import com.cbr.recommend.CaseLearnRecSkML;

public class CaseLearnTest {

	public void testLearn() {
		CaseLearnRecSkML clr = new CaseLearnRecSkML();
		CaseRec c = new CaseRec(2, 1000, 200, 80);
		System.out.println(clr.learncase(c));

	}
	@Test
	public void name() {
		// 训练集生成
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
		//List<CaseRec> casedata = casedatabases.subList(0, (int) (casedatabases.size() * 0.05));
		CaseLearnRecML.initiallearncaseML(casedatabases);
	}

	
	public void named() {
		double[] d = { 1, 1, 2 };
		//System.out.println(CaseLearnRecML.learncaseML(d));
	}

	public static void main(String[] arg) {
		System.out.println(CaseLearnRecML.ifModel());
	}
}
