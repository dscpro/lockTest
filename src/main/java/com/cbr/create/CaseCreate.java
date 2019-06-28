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

public class CaseCreate {
	private static HSSFWorkbook workbook = null;
	private FileOutputStream out = null;
	private ArrayList<CaseOri> casedatabasesori = new ArrayList<CaseOri>();

	private void getOriginalData() {
		File file = new File("src\\main\\resource\\lockresults.xls");
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

	public void constructCase() {
		getOriginalData();
		
		
	}
}
