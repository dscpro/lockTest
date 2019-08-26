package com.cbr.create;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.cbr.CaseOri;
import com.cbr.CaseRec;
import com.lock.TestInfo;

import soot.JastAddJ.Frontend;

public class CaseCreate {
	private static HSSFWorkbook workbook = null;
	private FileOutputStream out = null;
	private ArrayList<CaseOri> casedatabasesori = new ArrayList<CaseOri>();
	private ArrayList<CaseRec> casedatabasesrec = new ArrayList<CaseRec>();

	private void getOriginalData() {
		File file = new File("src/main/resource/lockresults.xls");
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
		for (int index = 1; index <= sheet.getLastRowNum(); index++) {
			CaseOri casetest = new CaseOri();
			Row row = sheet.getRow(index);
			// System.out.println(row.getCell(1).getNumericCellValue());
			casetest.setLock_type((int) row.getCell(0).getNumericCellValue());
			casetest.setStructure_type((int) row.getCell(1).getNumericCellValue());
			casetest.setOperate_structure_type((int) row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(3).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(4).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(5).getNumericCellValue());
			casetest.setOperate_type((int) row.getCell(6).getNumericCellValue());
			casetest.setExetimes((int) row.getCell(7).getNumericCellValue());
			casedatabasesori.add(casetest);
		}

	}

	/**
	 * 根据测试数据生成案例库
	 * 
	 */
	public void constructCase() {
		// 获取原始测试数据
		getOriginalData();
		// 计算推荐案例

		// 保存推荐案例
		for (CaseRec caseRec : casedatabasesrec) {
			savetoexcel(caseRec);
		}

	}

	private void savetoexcel(CaseRec info) {

		File file = new File("src/main/resource/caseresults.xls");
		FileOutputStream out = null;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
		int rowCount = sheet.getLastRowNum() + 1;
		Row row = sheet.createRow(rowCount);
		row.createCell(0).setCellValue(info.getLock_type());
		row.createCell(1).setCellValue(info.getStructure_type());
		row.createCell(2).setCellValue(info.getOperate_structure_type());
		row.createCell(3).setCellValue(info.getNumThreads());
		row.createCell(4).setCellValue(info.getReadNum());
		row.createCell(5).setCellValue(info.getNum_operate());
		row.createCell(6).setCellValue(info.getOperate_type());
		try {
			out = new FileOutputStream("src/main/resource/caseresults.xls");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
