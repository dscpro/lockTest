package com.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataBasic {

	public static Object getDataPre(String structure_type,int readNum,int exeTimes) {

		if (structure_type.equals("List"))
			return DataBasic.setListDataPre(structure_type,readNum,exeTimes);
		if (structure_type.equals("Array"))
			return DataBasic.setArrayDataPre(structure_type,readNum,exeTimes);
		if (structure_type.equals("Map"))
			return DataBasic.setMapDataPre(structure_type,readNum,exeTimes);
		if (structure_type.equals("Set"))
			return DataBasic.setSetDataPre(structure_type,readNum,exeTimes);
		return null;
	}

	public static Object setArrayDataPre(String structure_type,int readNum,int exeTimes) {
		int[] dataArray = null;
		return dataArray;
	}

	public static Object setListDataPre(String structure_type,int readNum,int exeTimes) {

		List<Integer> dataList = new ArrayList<Integer>();
		
		for(int i=0;i<readNum;i++){
			for(int j=0;j<exeTimes;j++){
				dataList.add(i*exeTimes+j);
			}
		}
		
		
		
		return dataList;
	}

	public static Object setMapDataPre(String structure_type,int readNum,int exeTimes) {
		Map<Integer, Integer> dataMap = null;
		return dataMap;
	}

	public static Object setSetDataPre(String structure_type,int readNum,int exeTimes) {
		Set<Integer> dataSet = null;
		return dataSet;
	}
}
