package com.cbr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import scala.util.control.Exception;

public class CaseBasicMethod {
	/**
	 * 获得案例文件
	 * 
	 * @return
	 */
	public static ArrayList<CaseRec> getCaseDatabases() {
		ArrayList<CaseRec> casedatabases = new ArrayList<CaseRec>();
		XSSFWorkbook workbook = null;
		File file = new File("src/main/resource/caseresults.xlsx");
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");

		for (int index = 1; index <= sheet.getLastRowNum(); index++) {
			CaseRec casetest = new CaseRec();
			Row row = sheet.getRow(index);
			// System.out.println(row.getCell(1).getNumericCellValue());
			casetest.setLock_type((int) row.getCell(0).getNumericCellValue());
			casetest.setStructure_type((int) row.getCell(1).getNumericCellValue());
			// casetest.setOperate_structure_type((int)
			// row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
			// casetest.setOperate_type((int) row.getCell(5).getNumericCellValue());
			casedatabases.add(casetest);
		}

		return casedatabases;
	}

	/**
	 * 获得案例文件Byname
	 * 
	 * @return
	 */
	public static ArrayList<CaseRec> getCaseDatabasesByFile(String filename) {
		ArrayList<CaseRec> casedatabases = new ArrayList<CaseRec>();
		XSSFWorkbook workbook = null;
		File file = new File("src/main/resource/" + filename + ".xlsx");
		try {
			workbook = new XSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");

		for (int index = 1; index <= sheet.getLastRowNum(); index++) {
			CaseRec casetest = new CaseRec();
			Row row = sheet.getRow(index);
			// System.out.println(row.getCell(1).getNumericCellValue());
			casetest.setLock_type((int) row.getCell(0).getNumericCellValue());
			casetest.setStructure_type((int) row.getCell(1).getNumericCellValue());
			// casetest.setOperate_structure_type((int)
			// row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
			// casetest.setOperate_type((int) row.getCell(5).getNumericCellValue());
			casedatabases.add(casetest);
		}

		return casedatabases;
	}

	/**
	 * 返回评价指标
	 * 
	 * @param locktype
	 * @param lockrec
	 * @param lockreal
	 * @return
	 */
	public static TreeMap<String, Double> getEvaluator(int locktype,ArrayList<Integer> lockrec,ArrayList<Integer> lockreal) {
		TreeMap<String, Double> evaluator = new TreeMap<String, Double>();
		double tp=0,tn=0,fp=0,fn=0;
		for (int lock =0;lock<lockreal.size();lock++) {
			if(locktype==lockreal.get(lock)&&locktype==lockrec.get(lock)){
				tp++;
			}
			if(locktype==lockreal.get(lock)&&locktype!=lockrec.get(lock)){
				fn++;
			}
			if(locktype!=lockreal.get(lock)&&locktype==lockrec.get(lock)){
				fp++;
			}
			if(locktype!=lockreal.get(lock)&&locktype!=lockrec.get(lock)){
				tn++;
			}
			
		}
		//计算精确率
		double precision= tp/(tp+fp);
		//计算召回率
		double recall= tp/(tp+fn);
		//计算F1
		double fscore=2*(precision*recall)/(precision+recall);
		//计算truePositiveRate
		double truePositiveRate= tp/(tp+fn);
		//计算falsePositiveRate
		double falsePositiveRate=fp/(fp+tn); 
		evaluator.put("p", precision*100);
		evaluator.put("r", recall*100);
		evaluator.put("f1", fscore*100);
		evaluator.put("tp", truePositiveRate*100);
		evaluator.put("fp", falsePositiveRate*100);
		
		return evaluator;
	}

	public static double[][] constructTypeMatrix() {
		double[][] matrix = new double[8][8];
		matrix[0] = new double[] { 1, 0.7, 0.7, 0.2, 0.2, 0.2, 0.2, 0.2 };
		matrix[1] = new double[] { 0.7, 1, 0.7, 0.2, 0.2, 0.2, 0.2, 0.2 };
		matrix[2] = new double[] { 0.7, 0.7, 1, 0.2, 0.2, 0.2, 0.2, 0.2 };
		matrix[3] = new double[] { 0.2, 0.2, 0.2, 1, 0.6, 0.6, 0.6, 0.6 };
		matrix[4] = new double[] { 0.2, 0.2, 0.2, 0.6, 1, 0.6, 0.6, 0.6 };
		matrix[5] = new double[] { 0.2, 0.2, 0.2, 0.6, 0.6, 1, 0.6, 0.6 };
		matrix[6] = new double[] { 0.2, 0.2, 0.2, 0.6, 0.6, 0.6, 1, 0.7 };
		matrix[7] = new double[] { 0.2, 0.2, 0.2, 0.6, 0.6, 0.6, 0.7, 1 };

		return matrix;
	}

	public static int getWordDistance(String s1, String s2) {
		int distance = 0;

		char[] chars1 = s1.toCharArray();
		char[] chars2 = s2.toCharArray();

		int[][] mat = new int[chars1.length + 1][chars2.length + 1];

		for (int i = 1; i < chars1.length + 1; i++)
			mat[i][0] = i;

		for (int j = 1; j < chars2.length + 1; j++)
			mat[0][j] = j;

		for (int j = 1; j < chars2.length + 1; j++) {
			for (int i = 1; i < chars1.length + 1; i++) {
				int cost = 0;
				if (chars1[i - 1] != chars2[j - 1])
					cost = 1;
				mat[i][j] = Math.min(mat[i - 1][j] + 1, mat[i][j - 1] + 1);
				mat[i][j] = Math.min(mat[i][j], mat[i - 1][j - 1] + cost);

			}
		}
		distance = mat[chars1.length][chars2.length];

		return distance;
	}

	public static double getWordSimilarity(String s1, String s2) {
		int distance = getWordDistance(s1, s2);
		int longerWordLength = Math.max(s1.length(), s2.length());
		int sim = longerWordLength - distance;

		double similarity = (double) sim / longerWordLength;
		if (similarity < 0)
			similarity = 0;

		return similarity;
	}
}
