package com.locktest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.lock.SaveToExcel;
import com.lock.TestInfo;

public abstract class TestSave {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		TestInfo info = new TestInfo();
		info.setNumThreads(5);
		info.setNum_operate(5);
		info.setOperate_type(2);
		info.setWasteTime(56);
		info.setLock_type(0);
		info.setStructure_type(2);
		SaveToExcel.savetoexcel(info);
	}

}
