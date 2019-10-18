package com.lock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class DataBasic {

	public static Object getDataPre(TestInfo info) {

		String structure_type =  Constant.STRUCTURE_TYPE[info.getStructure_type()];

		int opnum = info.getNum_operate();
		if (structure_type.equals("ArrayList"))
			return DataBasic.setArrayListDataPre(structure_type, opnum);
		if (structure_type.equals("LinkedList"))
			return DataBasic.setLinkedListDataPre(structure_type, opnum);
		if (structure_type.equals("GroupGroup"))
			return DataBasic.setArrayDataPre(structure_type, opnum);
		if (structure_type.equals("HashMap"))
			return DataBasic.setHashMapDataPre(structure_type, opnum);
		if (structure_type.equals("TreeSet"))
			return DataBasic.setTreeSetDataPre(structure_type, opnum);
		if (structure_type.equals("TreeMap"))
			return DataBasic.setTreeMapDataPre(structure_type, opnum);		
		if (structure_type.equals("HashSet"))
			return DataBasic.setHashSetDataPre(structure_type, opnum);
//		if (structure_type.equals("ConcurrentHashMap"))
//			return DataBasic.setConcurrentHashMapDataPre(structure_type, opnum);
//		if (structure_type.equals("WeakHashMap"))
//			return DataBasic.setWeakHashMapDataPre(structure_type, opnum);
		return null;
	}

//	private static Object setWeakHashMapDataPre(String structure_type, int opnum) {
//		Map<String, Integer> weakMap = new WeakHashMap<String, Integer>();
//		for (int j = 0; j < opnum; j++) {
//			weakMap.put(j + "i", j);
//		}
//		return weakMap;
//
//	}
	
	
	

	private static Object setHashSetDataPre(String structure_type, int opnum) {
		Set<Integer> set = new HashSet<Integer>();
		for (int j = 0; j < opnum; j++) {
			set.add(j);
		}
		return set;
	}

//	private static Object setConcurrentHashMapDataPre(String structure_type, int opnum) {
//		Map<String, Integer> conMap = new ConcurrentHashMap<String, Integer>();
//		for (int j = 0; j < opnum; j++) {
//			conMap.put(j + "i", j);
//		}
//		return conMap;
//	}

	private static Object setTreeMapDataPre(String structure_type, int opnum) {
		Map<String, Integer> map = new TreeMap<String, Integer>();
		for (int j = 0; j < opnum; j++) {
			map.put(j + "i", j);
		}
		return map;
	}

	private static Object setLinkedListDataPre(String structure_type, int opnum) {
		List<Integer> myList = new LinkedList<Integer>();

		for (int j = 0; j < opnum; j++) {
			myList.add(j);
		}
		return myList;
	}

	public static Object setArrayDataPre(String structure_type, int opnum) {
		int[] dataArray = null;
		return dataArray;
	}

	public static Object setArrayListDataPre(String structure_type, int opnum) {

		List<Integer> dataList = new ArrayList<Integer>();
		for (int j = 0; j < opnum; j++) {
			dataList.add(j);
		}
		return dataList;
	}

	public static Object setHashMapDataPre(String structure_type, int opnum) {
		Map<Integer, Integer> dataMap = new HashMap<Integer, Integer>();

		for (int i = 0; i < opnum; i++) {
			dataMap.put(i * opnum, i);
		}

		return dataMap;
	}

	public static Object setTreeSetDataPre(String structure_type, int opnum) {
		Set<Integer> dataSet = new TreeSet<Integer>();
		for (int i = 0; i < opnum; i++) {
			dataSet.add(i);
		}
		return dataSet;
	}
}
