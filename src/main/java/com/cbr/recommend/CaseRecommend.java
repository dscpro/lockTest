package com.cbr.recommend;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;

public class CaseRecommend {
	private int numTopCases = 10;
	private double simThreshold = 0.82;
	private ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
	private HashMap<String, Integer> weights = new HashMap<String, Integer>();
	private CaseLearnRec clr = null;
	/**
	 * 案例推荐
	 */
	public int caseRecommend(CaseRec searchcase) {
		return retrieval(searchcase);
	}

	/**
	 * 查找相似案例 默认返回前numTopCases个案例（5）
	 * 
	 * @param searchcase
	 * @return 案例及其相似度
	 */
	private int retrieval(CaseRec searchcase) {
		TreeMap<Double, CaseRec> simResults = new TreeMap<Double, CaseRec>();
		ArrayList<Map.Entry<Double, CaseRec>> results = new ArrayList<Entry<Double, CaseRec>>();
		constructWeights();
		double increment = 0.000001;
		for (CaseRec c : casedatabases) {
			double sim = calculateSimilarity(c, searchcase);
			// sim += increment;
			// increment += 0.000001;
			// BigDecimal bg = new BigDecimal(sim).setScale(5, RoundingMode.UP);
			simResults.put(sim, c);
		}
		for (int i = 0; i < numTopCases; i++) {
			Map.Entry<Double, CaseRec> item = simResults.pollLastEntry();
			results.add(item);
		}
		// 判断阈值
		results = deccase(results, searchcase);
		return casemultiPros(results);
	}

	/**
	 * 轮盘赌案例最终推荐
	 */
	private int casemultiPros(ArrayList<Map.Entry<Double, CaseRec>> results) {
		int x0 = 0;
		int x1 = 0;
		int x2 = 0;
		int x3 = 0;
		for (Entry<Double, CaseRec> entry : results) {
			switch (entry.getValue().getLock_type()) {
			case 0:
				x0++;
				continue;
			case 1:
				x1++;
				continue;
			case 2:
				x2++;
				continue;
			case 3:
				x3++;
				continue;
			}
		}
		int a[] = { x0, x2, x3, x1 };
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (a[i] > a[j]) { // 由小到大 第一个最小，最后一个最大
					int b = a[i];
					a[i] = a[j];
					a[j] = b;
				}
			}
		}
		int locktype = 0;
		if (a[0] == x0) {
			locktype = 0;
		}
		if (a[0] == x1) {
			locktype = 1;
		}
		if (a[0] == x2) {
			locktype = 2;
		}
		if (a[0] == x3) {
			locktype = 3;
		}
		return locktype;
	}

	/**
	 * 判断相似度是否达到标准
	 */
	private ArrayList<Map.Entry<Double, CaseRec>> deccase(ArrayList<Map.Entry<Double, CaseRec>> results,
			CaseRec searchcase) {
		ArrayList<Map.Entry<Double, CaseRec>> results1 = new ArrayList<Entry<Double, CaseRec>>();
		TreeMap<Double, CaseRec> change = new TreeMap<Double, CaseRec>();
		double inc = 0.0000001;
		for (Entry<Double, CaseRec> test : results) {
			if (test.getKey() < simThreshold) {
				int locktype = caseLearning(searchcase);
				test.getValue().setLock_type(locktype);
				change.put(1 + inc, test.getValue());
				inc += 0.0000001;
			} else {
				change.put(test.getKey(), test.getValue());
			}
		}
		for (int i = 0; i < numTopCases; i++) {
			Map.Entry<Double, CaseRec> item = change.pollLastEntry();
			results1.add(item);
		}
		return results1;
	}

	/**
	 * 案例学习
	 */
	private int caseLearning(CaseRec c) {
		if (clr == null) {
			clr = new CaseLearnRec();
		}
		return clr.learncase(c);
	}

	/**
	 * 案例重用
	 */
	private void reusing() {

	}

	/**
	 * 计算两个案例的相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return 两个案例的相似度
	 */
	private double calculateSimilarity(CaseRec c, CaseRec searchcase) {

		double totalSim = 0.0;
		double numThreadsSim = numThreadsSimilarity(c, searchcase);
		double readNumSim = readNumSimilarity(c, searchcase);
		double num_operateSim = numOperateSimilarity(c, searchcase);
		double structure_typeSim = structureTypeSimilarity(c, searchcase);
		// double operate_structure_typeSim = operateStructureTypeSimilarity(c,
		// searchcase);
		int totalWeight = 0;
		int numThreadsWeight = weights.get("NumThreads");
		int readNumWeight = weights.get("ReadNum");
		int numoperateWeight = weights.get("Numoperate");
		int structuretypeWeight = weights.get("Structuretype");
		// int operatetypeWeight = weights.get("Operatetype");
		// int operatestructuretypeWeight = weights.get("Operatestructuretype");
		totalWeight = totalWeight + numThreadsWeight + readNumWeight + numoperateWeight + structuretypeWeight;
		totalSim = (numThreadsSim * numThreadsWeight + readNumSim * readNumWeight + num_operateSim * numoperateWeight
				+ structure_typeSim * structuretypeWeight) / totalWeight;

		BigDecimal b = new BigDecimal(totalSim);
		totalSim = b.setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
		return totalSim;
	}

	/**
	 * 分段计算相似度 *
	 * 
	 * @return
	 */
	private double caculateByPhase(CaseRec c, CaseRec searchcase) {
		double phase = 0.0;

		return phase;
	}

	/**
	 * 皮尔逊相关系数计算相似度 *
	 * 
	 * @return
	 */
	private double caculateByPearson(CaseRec c, CaseRec searchcase) {
		Map<Integer, Integer> mapX = new HashMap<Integer, Integer>();
		Map<Integer, Integer> mapY = new HashMap<Integer, Integer>();
		// 放置案例库中案例
		mapX.put(0, c.getLock_type());
		mapX.put(1, c.getNum_operate());
		mapX.put(2, c.getNumThreads());
		// mapX.put(3, c.getOperate_structure_type());
		mapX.put(3, (int) (c.getReadNum() * c.getNumThreads()));
		mapX.put(4, c.getStructure_type());
		// 放置待匹配案例
		mapY.put(0, searchcase.getLock_type());
		mapY.put(1, searchcase.getNum_operate());
		mapY.put(2, searchcase.getNumThreads());
		// mapY.put(3, searchcase.getOperate_structure_type());
		mapY.put(3, (int) (searchcase.getReadNum() * searchcase.getNumThreads()));
		mapY.put(4, searchcase.getStructure_type());

		double sumXY = 0d;
		double sumX = 0d;
		double sumY = 0d;
		double sumPowX = 0d;
		double sumPowY = 0d;
		Set<Integer> setItem = new HashSet<Integer>();
		for (Map.Entry<Integer, Integer> entry : mapX.entrySet()) {
			setItem.add(entry.getKey());
		}
		for (Map.Entry<Integer, Integer> entry : mapY.entrySet()) {
			setItem.add(entry.getKey());
		}
		for (Integer bookId : setItem) {
			Integer x = mapX.get(bookId);
			if (x == null) {
				x = 0;
			}
			Integer y = mapY.get(bookId);
			if (y == null) {
				y = 0;
			}
			sumXY += x * y;
			sumX += x;
			sumY += y;
			sumPowX += Math.pow(x, 2);
			sumPowY += Math.pow(y, 2);
		}
		int n = setItem.size();
		double pearson = (sumXY - sumX * sumY / n)
				/ Math.sqrt((sumPowX - Math.pow(sumX, 2) / n) * (sumPowY - Math.pow(sumY, 2) / n));
		if (pearson == 1)
			return pearson;
		return pearson;
	}

	/**
	 * 计算numThreads相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
	private double numThreadsSimilarity(CaseRec c, CaseRec searchcase) {
		double similarity = 0.0;
		double numThreadscase = c.getNumThreads();
		double numThreadssearchcase = searchcase.getNumThreads();
		similarity = calDistinct(numThreadscase, numThreadssearchcase);
		return similarity;
	}

	/**
	 * 计算readNum相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
	private double readNumSimilarity(CaseRec c, CaseRec searchcase) {
		double similarity = 0.0;
		double readNumscase = c.getReadNum();
		double readNumsearchcase = searchcase.getReadNum() * searchcase.getNumThreads();
		similarity = calDistinct(readNumscase, readNumsearchcase);
		return similarity;
	}

	/**
	 * 计算structureType相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
	private double structureTypeSimilarity(CaseRec c, CaseRec searchcase) {
		double similarity = 0.0;
		double structureTypecase = c.getStructure_type();
		double structureTypesearchcase = searchcase.getStructure_type();
		similarity = calDistinct(structureTypecase, structureTypesearchcase);
		return similarity;
	}

	/**
	 * 计算searchcase相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
	private double numOperateSimilarity(CaseRec c, CaseRec searchcase) {
		double similarity = 0.0;
		double numOperatecase = c.getNum_operate();
		double numOperatesearchcase = searchcase.getNum_operate();
		similarity = calDistinct(numOperatecase, numOperatesearchcase);
		return similarity;
	}

	/**
	 * 计算operateStructureType相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
//	private double operateStructureTypeSimilarity(CaseRec c, CaseRec searchcase) {
//		double similarity = 0.0;
//		double operateStructureTypecase = c.getOperate_structure_type();
//		double operateStructureTypesearchcase = searchcase.getOperate_structure_type();
//		similarity = calDistinct(operateStructureTypecase, operateStructureTypesearchcase);
//		return similarity;
//	}

	/**
	 * 计算两个值的相似度
	 * 
	 * @param caseV
	 * @param userV
	 * @return
	 */
	private double calDistinct(double caseV, double userV) {
		double value = 0.0;
		if (caseV == 0 && userV == 0) {
			value=1;
		} else {
			double diff = (double) Math.abs(caseV - userV) / Math.max(caseV, userV);
			value = 1 - diff;
		}
		if (value < 0)
			value = 0;
		return value;
	}

	/**
	 * 构造权重
	 */
	private void constructWeights() {

		int numThreadsWeight = 12;
		int readNumWeight = 2;
		int num_operateWeight = 8;
		int structure_typeWeight = 10;
		// int operate_typeWeight = 3;
		// int operate_structure_typeWeight = 8;

		weights.put("NumThreads", numThreadsWeight);
		weights.put("ReadNum", readNumWeight);
		weights.put("Numoperate", num_operateWeight);
		weights.put("Structuretype", structure_typeWeight);
		// weights.put("Operatetype", operate_typeWeight);
		// weights.put("Operatestructuretype", operate_structure_typeWeight);
	}

	public ArrayList<CaseRec> getCasedatabases() {
		return casedatabases;
	}

	public void printResults(ArrayList<Map.Entry<Double, CaseRec>> topCases) {

		System.out.println("Here are the top " + numTopCases + " cases: ");
		for (Map.Entry c : topCases) {
			String simString = c.getKey() + "";
			double simDouble = Double.parseDouble(simString);
			CaseRec cc = (CaseRec) (c.getValue());

			if (cc.getLock_type() == 0) {
				System.out.println("Recommended use : ReadWriteLockType");
			}
			if (cc.getLock_type() == 1) {
				System.out.println("Recommended use : ReentrantLockType");
			}
			if (cc.getLock_type() == 2) {
				System.out.println("Recommended use : StampedLockType");
			}
			if (cc.getLock_type() == 3) {
				System.out.println("Recommended use : SynchronizedLockType");
			}

			System.out.printf("Similarity: %.4f \n", simDouble);

		}
	}

	public void setCasedatabases(ArrayList<CaseRec> casedatabases) {
		this.casedatabases = casedatabases;
	}
}
