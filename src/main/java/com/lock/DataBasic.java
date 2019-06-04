package com.lock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DataBasic {

	public static Object getDataPre(String structure_type) {

		if (structure_type.equals("Array"))
			return DataBasic.setArrayListDataPre(structure_type);
		if (structure_type.equals("Group"))
			return DataBasic.setArrayDataPre(structure_type);
		if (structure_type.equals("Hash"))
			return DataBasic.setHashMapDataPre(structure_type);
		if (structure_type.equals("Tree"))
			return DataBasic.setTreeSetDataPre(structure_type);
		return null;
	}

	public static Object setArrayDataPre(String structure_type) {
		int[] dataArray = null;
		return dataArray;
	}

	public static Object setArrayListDataPre(String structure_type) {

		List<Integer> dataList = new ArrayList<Integer>();
		for (int j = 0; j < Constant.NUM_OPERATE; j++) {
			dataList.add(j);
		}
		return dataList;
	}

	public static Object setHashMapDataPre(String structure_type) {
		Map<Integer, Integer> dataMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < Constant.NUM_OPERATE; i++) {
			dataMap.put(i * Constant.NUM_OPERATE, i);
		}

		return dataMap;
	}

	public static Object setTreeSetDataPre(String structure_type) {
		Set<Integer> dataSet = new TreeSet<Integer>();
		for (int i = 0; i < Constant.NUM_OPERATE; i++) {
			dataSet.add(i);
		}
		return dataSet;
	}
}
