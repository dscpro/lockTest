package com.cbr.recommend;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.Computable;
import org.jpmml.evaluator.Evaluator;
import org.jpmml.evaluator.FieldValue;
import org.jpmml.evaluator.InputField;
import org.jpmml.evaluator.ModelEvaluatorFactory;
import org.jpmml.evaluator.TargetField;
import org.xml.sax.SAXException;

import com.cbr.CaseOri;
import com.cbr.CaseRec;

public class CaseLearnRecSkML {

	/**
	 * 案例学习
	 * 
	 * @param c
	 * @return
	 */
	public static int learncase(CaseRec c) {
		Evaluator model = loadPmml();
		int locktype;
		locktype = predict(model, c.getStructure_type(), c.getNumThreads(), (int) (c.getReadNum() * c.getNumThreads()),
				c.getNum_operate());
		return locktype;
	}

	private static Evaluator loadPmml() {
		PMML pmml = new PMML();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("src/main/resource/demo.pmml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (inputStream == null) {
			return null;
		}
		InputStream is = inputStream;
		try {
			pmml = org.jpmml.model.PMMLUtil.unmarshal(is);
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (JAXBException e1) {
			e1.printStackTrace();
		} finally {
			// 关闭输入流
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
		Evaluator evaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
		pmml = null;
		return evaluator;
	}

	private static int predict(Evaluator evaluator, int a, int b, int c, int d) {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("x1", a);
		data.put("x2", b);
		data.put("x3", c);
		data.put("x4", d);
		List<InputField> inputFields = evaluator.getInputFields();
		// 过模型的原始特征，从画像中获取数据，作为模型输入
		Map<FieldName, FieldValue> arguments = new LinkedHashMap<FieldName, FieldValue>();
		for (InputField inputField : inputFields) {
			FieldName inputFieldName = inputField.getName();
			Object rawValue = data.get(inputFieldName.getValue());
			FieldValue inputFieldValue = inputField.prepare(rawValue);
			arguments.put(inputFieldName, inputFieldValue);
		}
		Map<FieldName, ?> results = evaluator.evaluate(arguments);
		List<TargetField> targetFields = evaluator.getTargetFields();
		TargetField targetField = targetFields.get(0);
		FieldName targetFieldName = targetField.getName();
		Object targetFieldValue = results.get(targetFieldName);
		// System.out.println("target: " + targetFieldName.getValue() + " value: " +
		// targetFieldValue);
		int primitiveValue = -1;
		if (targetFieldValue instanceof Computable) {
			Computable computable = (Computable) targetFieldValue;
			primitiveValue = (Integer) computable.getResult();
		}
		// System.out.println(a + " 数据结构，" + b + " 线程数，" + c + "读线程数，" + d +
		// "执行次数,+++预测锁类型" + primitiveValue);
		return primitiveValue;
	}

//	public void getOriginalData() {
//		HSSFWorkbook workbook = null;
//		File file = new File("src/main/resource/caseresults.xls");
//		try {
//			workbook = new HSSFWorkbook(new FileInputStream(file));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		HSSFSheet sheet = (HSSFSheet) workbook.getSheet("Sheet1");
//		for (int index = 1; index <= sheet.getLastRowNum(); index++) {
//			CaseOri casetest = new CaseOri();
//			Row row = sheet.getRow(index);
//			casetest.setLock_type((int) row.getCell(0).getNumericCellValue());
//			casetest.setStructure_type((int) row.getCell(1).getNumericCellValue());
//			casetest.setNumThreads((int) row.getCell(2).getNumericCellValue());
//			casetest.setReadNum((int) row.getCell(3).getNumericCellValue());
//			casetest.setNum_operate((int) row.getCell(4).getNumericCellValue());
//			casedatabasesori.add(casetest);
//		}
//		try {
//			workbook.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

}
