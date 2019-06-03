package com.lock;

public class SetClass {

	public static Object getclass(String className)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class obj = Class.forName(className);
		return obj.newInstance();
	}

}
