package com.lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SaveToExcel {
	static Logger log = Logger.getLogger("");

	public static void savetoexcel(ArrayList<TestInfo> infos) throws IOException {
		log.info("save lockinfo");
		File file = new File("src/main/resource/lockresults.xlsx");
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(fs);
			XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");
			for (TestInfo info : infos) {
				int rowCount = sheet.getLastRowNum() + 1;
				Row row = sheet.createRow(rowCount);
				row.createCell(0).setCellValue(info.getLock_type());
				row.createCell(1).setCellValue(info.getStructure_type());
				// row.createCell(2).setCellValue(info.getOperate_structure_type());
				row.createCell(2).setCellValue(info.getNumThreads());
				row.createCell(3).setCellValue(info.getReadNum());
				row.createCell(4).setCellValue(info.getNum_operate());
				// row.createCell(5).setCellValue(info.getOperate_type());
				row.createCell(5).setCellValue(info.getWasteTime());
			}
			FileOutputStream out = new FileOutputStream(file);
			workbook.write(out);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			workbook.close();
		}

		
	}
}
