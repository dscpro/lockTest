package com.cbr.create;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import com.cbr.Case;
import com.cbr.CaseOri;
import com.cbr.CaseRec;
import com.lock.Constant;
import com.lock.TestInfo;

import soot.JastAddJ.Frontend;

public class CaseCreate {
	private static HSSFWorkbook workbook = null;
	private FileOutputStream out = null;
	private ArrayList<CaseOri> casedatabasesori = new ArrayList<CaseOri>();
	private ArrayList<CaseRec> casedatabasesrec = new ArrayList<CaseRec>();
	static Logger log = Logger.getLogger("");

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
			// casetest.setOperate_structure_type((int)
			// row.getCell(2).getNumericCellValue());
			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
			// casetest.setOperate_type((int) row.getCell(6).getNumericCellValue());
			casetest.setExetimes((int) row.getCell(5).getNumericCellValue());
			casedatabasesori.add(casetest);
		}

	}
	/**
	 *  修改不当数据
	 *  对比前后都超过两倍  
	 */
	public void checkDataPre() {
		getOriginalData();
		ArrayList<CaseOri> caseori = casedatabasesori;
		for (int i =0;i<=caseori.size();i++) {
			int exetimenpre = caseori.get(i-1).getExetimes();
			int exetimenow = caseori.get(i).getExetimes();
			if(exetimenow/exetimenpre>=2) {
				int exetimennext = caseori.get(i+1).getExetimes();
				if(exetimenow/exetimennext>=2) {
					
				}
			}
		} 
	}

	/**
	 * 根据测试数据生成案例库
	 * 
	 */
	public void constructCase() {
		// 获取原始测试数据
		getOriginalData();
		// 筛选推荐案例
		ArrayList<CaseOri> casedatabasesrec = new ArrayList<CaseOri>();
		int index = 0;

		for (int i = 0; i <= Constant.STRUCTURE_TYPE.length; i++) {
			ArrayList<CaseOri> casedatabasesxuan = new ArrayList<CaseOri>();
			for (CaseOri caseOri : casedatabasesori) {
				if (caseOri.getStructure_type() == i) {
					casedatabasesxuan.add(caseOri);
				}
			}
			if (casedatabasesxuan.size() == 0) {
				continue;
			}
			// System.out.println("casedatabasesxuan-" + casedatabasesxuan.size());

			ArrayList<CaseOri> casedatabasesxuan1 = casedatabasesxuan;

			// System.out.println("casedatabasesxuan1-" + casedatabasesxuan1.size());
			if (casedatabasesxuan1.size() == 0) {
				continue;
			}
			for (int num_threads : Constant.NUM_THREADS) {
				ArrayList<CaseOri> casedatabasesxuan2 = new ArrayList<CaseOri>();
				for (CaseOri caseXuan : casedatabasesxuan1) {
					if (caseXuan.getNumThreads() == num_threads) {
						casedatabasesxuan2.add(caseXuan);
					}
				}
				// System.out.println("casedatabasesxuan2-" + casedatabasesxuan2.size());
				if (casedatabasesxuan2.size() == 0) {
					continue;
				}
				for (double num_read : Constant.NUM_READ) {
					ArrayList<CaseOri> casedatabasesxuan3 = new ArrayList<CaseOri>();
					for (CaseOri caseOri : casedatabasesxuan2) {
						if (caseOri.getReadNum() == num_threads * num_read) {
							casedatabasesxuan3.add(caseOri);
						}
					}
					if (casedatabasesxuan3.size() == 0) {
						continue;
					}
					// System.out.println("casedatabasesxuan3-" + casedatabasesxuan3.size());
					for (int num_exetimes : Constant.NUM_EXETIMES) {
						ArrayList<CaseOri> casedatabasesxuan4 = new ArrayList<CaseOri>();
						for (CaseOri caseOri : casedatabasesxuan3) {
							if (caseOri.getNum_operate() == num_exetimes) {
								casedatabasesxuan4.add(caseOri);
							}
						}
						// System.out.println("casedatabasesxuan4-" + casedatabasesxuan4.size());
						// 排序
						Collections.sort(casedatabasesxuan4, new Comparator<CaseOri>() {

							public int compare(CaseOri o1, CaseOri o2) {
								// 按照执行时间排列
								if (o1.getExetimes() > o2.getExetimes()) {
									return 1;
								}
								if (o1.getExetimes() == o2.getExetimes()) {
									return 0;
								}
								return -1;
							}
						});
						// System.out.println(casedatabasesxuan4.get(0).getLock_type());
						if (casedatabasesxuan4.size() == 0) {
							continue;
						}
						casedatabasesrec.add(casedatabasesxuan4.get(0));
//							casedatabasesxuan4.clear();
						if (index == 2249)
							System.out.println("Done");
						index++;
						log.info(index + "--Done");
						// log.info("Done" + casedatabasesxuan4.get(0).getLock_type() + "");
					}
				}
			}

		}
		log.info("All Done");
		log.info("Save Cases");
		// 保存推荐案例
		for (CaseOri caseOri : casedatabasesrec) {
			savetoexcel(caseOri);
			log.info("Save Cases "+caseOri.getLock_type());
		}
		log.info("Save Cases Done");
	}

	private void savetoexcel(CaseOri info) {

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
		// row.createCell(2).setCellValue(info.getOperate_structure_type());
		row.createCell(2).setCellValue(info.getNumThreads());
		row.createCell(3).setCellValue(info.getReadNum());
		row.createCell(4).setCellValue(info.getNum_operate());
		// row.createCell(6).setCellValue(info.getOperate_type());
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
		} finally {

		}
	}
}
