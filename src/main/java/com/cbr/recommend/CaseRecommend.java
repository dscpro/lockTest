package com.cbr.recommend;

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
	public int numTopCases = 5;
	public ArrayList<CaseRec> casedatabases = CaseBasicMethod.getCaseDatabases();
	public HashMap<String, Integer> weights = new HashMap<String, Integer>();

	public void analysis() {

	}
	/**
	 * 查找相似案例 默认返回前numTopCases个案例（5）
	 * 
	 * @param searchcase
	 * @return 案例及其相似度
	 */
	public ArrayList<Map.Entry<Double, CaseRec>> retrieval(CaseRec searchcase) {
		TreeMap<Double, CaseRec> simResults = new TreeMap<Double, CaseRec>();
		ArrayList<Map.Entry<Double, CaseRec>> results = new ArrayList<Entry<Double, CaseRec>>();
		//constructWeights();
		double increment = 0.000001;
		for (CaseRec c : casedatabases) {
			double sim = caculateByPearson(c, searchcase);
			//sim += increment;
			//increment += 0.000001;
			simResults.put(sim, c);
		}
		for (int i = 0; i < numTopCases; i++) {
			Map.Entry<Double, CaseRec> item = simResults.pollLastEntry();
			results.add(item);
		}

		return results;

	}

	/**
	 * 案例重用
	 */
	public void reusing() {

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
		//double operate_structure_typeSim = operateStructureTypeSimilarity(c, searchcase);
		int totalWeight = 0;
		int numThreadsWeight = weights.get("NumThreads");
		int readNumWeight = weights.get("ReadNum");
		int numoperateWeight = weights.get("Numoperate");
		int structuretypeWeight = weights.get("Structuretype");
		//int operatetypeWeight = weights.get("Operatetype");
		int operatestructuretypeWeight = weights.get("Operatestructuretype");
		totalWeight = totalWeight + numThreadsWeight + readNumWeight + numoperateWeight + structuretypeWeight
				+ operatestructuretypeWeight;
		totalSim = (numThreadsSim * numThreadsWeight + readNumSim * readNumWeight + num_operateSim * numoperateWeight
				+ structure_typeSim * structuretypeWeight )
				/ totalWeight;
		return totalSim;
	}

	/**
	 *  分段计算相似度 *
	 * 
	 * @return
	 */
	private double caculateByPhase(CaseRec c, CaseRec searchcase) {
		double phase=0.0;
		
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
		//mapX.put(3, c.getOperate_structure_type());
		mapX.put(3, c.getReadNum());
		mapX.put(4, c.getStructure_type());
		// 放置待匹配案例
		mapY.put(0, searchcase.getLock_type());
		mapY.put(1, searchcase.getNum_operate());
		mapY.put(2, searchcase.getNumThreads());
		//mapY.put(3, searchcase.getOperate_structure_type());
		mapY.put(3, searchcase.getReadNum());
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
		if(pearson==1)
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
	 * @param caseV
	 * @param userV
	 * @return
	 */
	private double calDistinct(double caseV, double userV) {
		double value = 0.0;
		double diff = (double) Math.abs(caseV - userV) / Math.max(caseV, userV);
		value = 1 - diff;
		if (value < 0)
			value = 0;
		return value;
	}

	/**
	 * 构造权重
	 */
	private void constructWeights() {

		int numThreadsWeight = 8;
		int readNumWeight = 5;
		int num_operateWeight = 6;
		int structure_typeWeight = 8;
		int operate_typeWeight = 3;
		//int operate_structure_typeWeight = 8;

		weights.put("NumThreads", numThreadsWeight);
		weights.put("ReadNum", readNumWeight);
		weights.put("Numoperate", num_operateWeight);
		weights.put("Structuretype", structure_typeWeight);
		weights.put("Operatetype", operate_typeWeight);
		//weights.put("Operatestructuretype", operate_structure_typeWeight);
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
			
			System.out.printf("Similarity: %.8f \n", simDouble);

		}
	}

	public void setCasedatabases(ArrayList<CaseRec> casedatabases) {
		this.casedatabases = casedatabases;
	}
}
