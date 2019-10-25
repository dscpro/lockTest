package com.cbr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CaseBasicMethod {
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
			//casetest.setOperate_structure_type((int) row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
			//casetest.setOperate_type((int) row.getCell(5).getNumericCellValue());
			casedatabases.add(casetest);
		}

		return casedatabases;
	}

	public static ArrayList<CaseRec> getCaseDatabasesByFile(String filename) {
		ArrayList<CaseRec> casedatabases = new ArrayList<CaseRec>();
		XSSFWorkbook workbook = null;
		File file = new File("src/main/resource/"+filename+".xlsx");
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
			//casetest.setOperate_structure_type((int) row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
			//casetest.setOperate_type((int) row.getCell(5).getNumericCellValue());
			casedatabases.add(casetest);
		}

		return casedatabases;
	}
	public static double CaseAttributeSimilarity(CaseRec c, CaseRec userCase, String attribute) {
		double similarity = 0.0;
		int caseType = 0;
		int userType = 0;
		if (attribute == "numThreads") {
			caseType = c.getNumThreads();
			userType = userCase.getNumThreads();
		}

		HashMap<String, Integer> typeKeys = constructTypeHashMap();
		double[][] matrix = constructTypeMatrix();

		int caseNumericTransportation = typeKeys.get(caseType);
		int userNumericTransportation = typeKeys.get(userType);

		similarity = matrix[userNumericTransportation][caseNumericTransportation];

		return similarity;
	}

	public static HashMap<String, Integer> constructTypeHashMap() {
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		result.put("city", 0);
		result.put("education", 1);
		result.put("language", 2);
		result.put("recreation", 3);
		result.put("bathing", 4);
		result.put("wandering", 5);
		result.put("active", 6);
		result.put("skiing", 7);

		return result;
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

//	public static int getSentenceDistance(String s1, String s2) {
//		int distance = 0;
//
//		String[] l1 = s1.split(" ");
//		String[] l2 = s2.split(" ");
//
//		int[][] mat = new int[l1.length + 1][l2.length + 1];
//
//		for (int i = 1; i < l1.length + 1; i++)
//			mat[i][0] = i;
//
//		for (int j = 1; j < l2.length + 1; j++)
//			mat[0][j] = j;
//
//		for (int j = 1; j < l2.length + 1; j++) {
//			for (int i = 1; i < l1.length + 1; i++) {
//				int cost = 0;
//				if (!l1[i - 1].equals(l2[j - 1]))
//					cost = 1;
//				mat[i][j] = Math.min(mat[i - 1][j] + 1, mat[i][j - 1] + 1);
//				mat[i][j] = Math.min(mat[i][j], mat[i - 1][j - 1] + cost);
//
//			}
//		}
//		distance = mat[l1.length][l2.length];
//
//		return distance;
//	}
//
//	public static double getSentenceSimilarity(String s1, String s2) {
//		int distance = getSentenceDistance(s1, s2);
//		int longerWordLength = Math.max(s1.split(" ").length, s2.split(" ").length);
//		int sim = longerWordLength - distance;
//
//		double similarity = (double) sim / longerWordLength;
//		if (similarity < 0)
//			similarity = 0;
//		return similarity;
//	}

//	public static String findMostSimilarInput(String s, HashSet<String> set) {
//		String result = "";
//		Iterator<String> i = set.iterator();
//		double maxSim = 0.0;
//
//		while (i.hasNext()) {
//			String cur = i.next();
//			double similarity = getWordSimilarity(cur, s);
//			if (similarity > maxSim) {
//				result = cur;
//				maxSim = similarity;
//			}
//
//		}
//
//		return result;
//	}

}
