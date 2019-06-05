package com.lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class SaveToExcel {
	private static HSSFWorkbook workbook = null;
	public static void savetoexcel(TestInfo info) throws IOException {
		
		File file = new File("src\\main\\resource\\lockresults.xls");
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
		row.createCell(7).setCellValue(info.getWasteTime());
		out = new FileOutputStream("src\\main\\resource\\lockresults.xls");
		workbook.write(out);
	}
}
