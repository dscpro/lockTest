package com.cbr.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.cbr.Case;
import com.cbr.CaseBasicMethod;

public class CaseRecommend {
	public int numTopCases = 3;
	public ArrayList<Case> casedatabases = CaseBasicMethod.getCaseDatabases();
	public HashMap<String, Integer> weights = new HashMap<>();

	public void analysis() {

	}

	/**
	 * 查找相似案例 默认返回前numTopCases个案例（3）
	 * 
	 * @param searchcase
	 * @return 案例及其相似度
	 */
	public ArrayList<Map.Entry<Double, Case>> retrieval(Case searchcase) {
		TreeMap<Double, Case> simResults = new TreeMap<>();
		ArrayList<Map.Entry<Double, Case>> results = new ArrayList<>();
		constructWeights();
		double increment = 0.000001;
		for (Case c : casedatabases) {
			double sim = calculateSimilarity(c, searchcase);
			sim += increment;
			increment += 0.000001;
			simResults.put(sim, c);
		}
		for (int i = 0; i < numTopCases; i++) {
			Map.Entry<Double, Case> item = simResults.pollLastEntry();
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
	public double calculateSimilarity(Case c, Case searchcase) {

		double totalSim = 0.0;

		double numThreadsSim = numThreadsSimilarity(c, searchcase);
		double readNumSim = readNumSimilarity(c, searchcase);
		double num_operateSim = numOperateSimilarity(c, searchcase);
		double structure_typeSim = structureTypeSimilarity(c, searchcase);
		double operate_structure_typeSim = operateStructureTypeSimilarity(c, searchcase);
		int totalWeight = 0;
		int numThreadsWeight = weights.get("NumThreads");
		int readNumWeight = weights.get("ReadNum");
		int numoperateWeight = weights.get("Numoperate");
		int structuretypeWeight = weights.get("Structuretype");
		int operatetypeWeight = weights.get("Operatetype");
		int operatestructuretypeWeight = weights.get("Operatestructuretype");
		totalWeight = totalWeight + numThreadsWeight + readNumWeight + numoperateWeight + structuretypeWeight
				+ operatestructuretypeWeight;
		totalSim = (numThreadsSim * numThreadsWeight + readNumSim * readNumWeight + num_operateSim * numoperateWeight
				+ structure_typeSim * structuretypeWeight + operate_structure_typeSim * operatestructuretypeWeight)
				/ totalWeight;
		return totalSim;
	}

	/**
	 * 计算numThreads相似度
	 * 
	 * @param c
	 * @param searchcase
	 * @return
	 */
	private double numThreadsSimilarity(Case c, Case searchcase) {
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
	private double readNumSimilarity(Case c, Case searchcase) {
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
	private double structureTypeSimilarity(Case c, Case searchcase) {
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
	private double numOperateSimilarity(Case c, Case searchcase) {
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
	private double operateStructureTypeSimilarity(Case c, Case searchcase) {
		double similarity = 0.0;
		double operateStructureTypecase = c.getOperate_structure_type();
		double operateStructureTypesearchcase = searchcase.getOperate_structure_type();
		similarity = calDistinct(operateStructureTypecase, operateStructureTypesearchcase);
		return similarity;
	}

	/**
	 * 计算两个值的相似度
	 * 
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
		int operate_structure_typeWeight = 8;

		weights.put("NumThreads", numThreadsWeight);
		weights.put("ReadNum", readNumWeight);
		weights.put("Numoperate", num_operateWeight);
		weights.put("Structuretype", structure_typeWeight);
		weights.put("Operatetype", operate_typeWeight);
		weights.put("Operatestructuretype", operate_structure_typeWeight);
	}

	public ArrayList<Case> getCasedatabases() {
		return casedatabases;
	}

	public void printResults(ArrayList<Map.Entry<Double, Case>> topCases) {

		System.out.println("Here are the top " + numTopCases + " cases: ");
		for (Map.Entry c : topCases) {
			String simString = c.getKey() + "";
			double simDouble = Double.parseDouble(simString);
			Case cc = (Case) (c.getValue());

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
			System.out.printf("Similarity: %.2f \n", simDouble);

		}
	}

	public void setCasedatabases(ArrayList<Case> casedatabases) {
		this.casedatabases = casedatabases;
	}
}
