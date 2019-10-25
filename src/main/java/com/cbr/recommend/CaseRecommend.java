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
import com.cbr.CaseOri;
import com.cbr.CaseRec;
import com.cbr.create.CaseCreate;

public class CaseRecommend {
	private int numTopCases = 10;
	private double simThreshold = 0.8;
	private HashMap<String, Integer> weights = new HashMap<String, Integer>();
	private ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
	private CaseLearnRecML caseLearnRecML= null;
	/**
	 * 案例推荐
	 */
	public int caseRecommend(CaseRec searchcase) {
		return retrieval(searchcase);
	}

	/**
	 * 查找相似案例 默认返回前numTopCases个案例
	 * 
	 * @param searchcase
	 * @return 案例及其相似度
	 */
	private int retrieval(CaseRec searchcase) {
		
		TreeMap<Double, CaseRec> simResults = new TreeMap<Double, CaseRec>();
		ArrayList<Map.Entry<Double, CaseRec>> results = new ArrayList<Entry<Double, CaseRec>>();
		constructWeightsByNumThread(searchcase);
		double increment = 1 / (casedatabases.size());
		boolean newCase = true;
		int allmatchlock = 0;
		for (CaseRec c : casedatabases) {
			//数据结构完全匹配
			if (c.getStructure_type() == searchcase.getStructure_type()) {
				double sim = calculateSimilarity(c, searchcase);
				// key值相同
				if (simResults.get(sim) != null) {
					if (c.getLock_type() != simResults.get(sim).getLock_type()) {
						sim += increment;
						increment += increment;
					}
				}
				simResults.put(sim, c);
			}
		}
		for (int i = 0; i < numTopCases; i++) {
			Map.Entry<Double, CaseRec> item = simResults.pollLastEntry();
			results.add(item);
		}
		// 判断是否新案例 完全匹配案例
		for (Entry<Double, CaseRec> entry : results) {
			if (Math.floor(entry.getKey()) == 1) {
				newCase = false;
				allmatchlock = entry.getValue().getLock_type();
				break;
			}
		}

		// 判断阈值
		results = deccase(results, searchcase);

		int lockfinal = casemultiPros(results, newCase, allmatchlock);
		if (newCase)
			saveNewCase(lockfinal, searchcase);
		return lockfinal;
	}

	/**
	 * 占比案例最终推荐
	 */
	private int casemultiPros(ArrayList<Map.Entry<Double, CaseRec>> results, boolean newcase, int allmatchlock) {

		TreeMap<Integer, Integer> recResults = new TreeMap<Integer, Integer>();
		recResults.put(0, 0);
		recResults.put(1, 0);
		recResults.put(2, 0);
		recResults.put(3, 0);
		for (Entry<Double, CaseRec> entry : results) {
			switch (entry.getValue().getLock_type()) {
			case 0:
				recResults.put(0, recResults.get(0) + 1);
				continue;
			case 1:
				recResults.put(1, recResults.get(1) + 1);
				continue;
			case 2:
				recResults.put(2, recResults.get(2) + 1);
				continue;
			case 3:
				recResults.put(3, recResults.get(3) + 1);
				continue;
			}
		}

		// 如果完全匹配
		if (!newcase) {
			recResults.put(allmatchlock, recResults.get(allmatchlock) + 2);
		}
		int locktype = 0;
		int a[] = { recResults.get(0), recResults.get(1), recResults.get(2), recResults.get(3) };
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				if (a[i] > a[j]) { // 由小到大 第一个最小，最后一个最大
					int b = a[i];
					a[i] = a[j];
					a[j] = b;
				}
			}
		}
		if (a[0] == recResults.get(0)) {
			locktype = 0;
		}
		if (a[0] == recResults.get(1)) {
			locktype = 1;
		}
		if (a[0] == recResults.get(2)) {
			locktype = 2;
		}
		if (a[0] == recResults.get(3)) {
			locktype = 3;
		}
		// 判断是否是最终推荐案例为完全匹配案例
		if (!newcase) {
			if (locktype != allmatchlock) {
				// 如果票数未超过3
				if (recResults.get(locktype) - recResults.get(allmatchlock) < 3) {
					locktype = allmatchlock;
				}
			}
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
				int x = test.getValue().getLock_type();
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
		if(caseLearnRecML==null) {
			caseLearnRecML =new CaseLearnRecML();
		}
		return caseLearnRecML.learncase(c);
	}

	/**
	 * 新案例保存
	 */
	private void saveNewCase(int lock, CaseRec searchcase) {
		ArrayList<CaseOri> casedatabasesrec = new ArrayList<CaseOri>();
		CaseOri caseori = new CaseOri(lock, searchcase.getStructure_type(), searchcase.getNumThreads(),
				searchcase.getReadNum(), searchcase.getNum_operate());
		casedatabasesrec.add(caseori);
		CaseCreate.savetoexcel(casedatabasesrec);
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
				+ structure_typeSim * structuretypeWeight);
		totalSim = totalSim / totalWeight;

		// BigDecimal b = new BigDecimal(totalSim);
		// totalSim = b.setScale(1, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
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
		double readNumsearchcase = searchcase.getReadNum();
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
		if (structureTypecase == structureTypesearchcase)
			similarity = 1;
		return similarity;
	}

	/**
	 * 计算numOperatecase相似度
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
		if (caseV == 0 || userV == 0) {
			caseV += 1;
			userV += 1;
		}
		if (caseV == 0 && userV == 0) {
			value = 1;
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
	private void constructWeightsByNumThread(CaseRec searchcase) {
		int numThreadsWeight = 1;
		int readNumWeight = 1;
		int num_operateWeight = 1;
		int structure_typeWeight = 1;
		if (searchcase.getNumThreads() > 2800 && searchcase.getNumThreads() < 4000) {
			numThreadsWeight = 5;
			readNumWeight = 2;
			num_operateWeight = 5;
			structure_typeWeight = 4;
		}
		if (searchcase.getNumThreads() > 4000 && searchcase.getNumThreads() < 5000) {
			numThreadsWeight = 8;
			readNumWeight = 2;
			num_operateWeight = 8;
			structure_typeWeight = 7;
		}
		if (searchcase.getNumThreads() > 5000) {
			numThreadsWeight = 12;
			readNumWeight = 2;
			num_operateWeight = 8;
			structure_typeWeight = 10;
		}

		// int operate_typeWeight = 3;
		// int operate_structure_typeWeight = 8;

		weights.put("NumThreads", numThreadsWeight);
		weights.put("ReadNum", readNumWeight);
		weights.put("Numoperate", num_operateWeight);
		weights.put("Structuretype", structure_typeWeight);
		// weights.put("Operatetype", operate_typeWeight);
		// weights.put("Operatestructuretype", operate_structure_typeWeight);
	}

	private void constructWeights(CaseRec searchcase) {
		int numThreadsWeight = 1;
		int readNumWeight = 1;
		int num_operateWeight = 1;
		int structure_typeWeight = 1;
		weights.put("NumThreads", numThreadsWeight);
		weights.put("ReadNum", readNumWeight);
		weights.put("Numoperate", num_operateWeight);
		weights.put("Structuretype", structure_typeWeight);
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

}
