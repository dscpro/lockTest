package com.cbrtest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.junit.Test;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;
import com.cbr.recommend.CaseLearnRecML;
import com.cbr.recommend.CaseLearnRecSkML;

import scala.Tuple2;

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
		Collections.shuffle(casedatabases);
		CaseLearnRecML.initiallearncaseML(casedatabases);
		//name2(casedatabases);
	}
	
	public void name2(List<CaseRec> casedata) {
		CaseLearnRecML caseLearnRecML = new CaseLearnRecML();
		double sum = 0;
		for (CaseRec caseRec : casedata) {
			if (caseRec.getLock_type() == caseLearnRecML.learncase(caseRec)) {
				sum++;
			}
			System.out.println(sum + "++++++++");
		}
		double acc = sum / casedata.size();
		System.out.println(acc);
	}

	public void named() {
	}

	public static void main(String[] arg) {
		ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
		List<CaseRec> casedata = casedatabases.subList(8000, 12000);

	}
}
