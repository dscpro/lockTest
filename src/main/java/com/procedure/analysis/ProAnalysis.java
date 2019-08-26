package com.procedure.analysis;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Unit;
import soot.options.Options;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class ProAnalysis {
	static String className;
	static HashMap<String, Integer> attributeMap = new HashMap<String, Integer>();
	/**
	 * @param className
	 * @return  各个属性及其对应的值
	 */
	public static HashMap<String, Integer> proAnalysis(String className){
		ProAnalysis.className = className;
		setSootClassPath();// 设置classpath
		setOptions();// 设置soot的选项
		getClassUnderDir();
		// this.getMethods();
		getAttribute();
		return attributeMap;
	}

	protected static void getClassUnderDir() {
		Scene.v().loadClass(className, SootClass.BODIES).setApplicationClass();
	}

	protected static void getAttribute() {
		for (SootClass clz : Scene.v().getApplicationClasses()) {
			System.out.println(clz.getName());
			Object object = null;
			try {
				object = Class.forName(clz.getName()).newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			if (clz.getFieldCount() == 0) {
				System.out.println("do not have Attribute");
			} else {
				Class<?> aClass = object.getClass();
				Field[] declaredFields = aClass.getDeclaredFields();
				for (Field field : declaredFields) {
					try {
						field.setAccessible(true);
						String fn = field.getName();
						// System.out.println("Attribute：" + fn + ";Value:" + field.get(object));
						//属性及其值存入map中
						attributeMap.put(fn.toString(), Integer.parseInt(field.get(object).toString()));
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

				}

			}
		}

	}

	protected static void getMethods() {
		for (SootClass clz : Scene.v().getApplicationClasses()) {
			System.out.println(clz.getName());
			if (clz.getMethods().size() == 0) {
				System.out.println("do not have methods");
			} else {
				System.out.println("method num:" + clz.getMethods().size());
				for (SootMethod me : clz.getMethods()) {
					System.out.println(me.toString());
					// 生成图
					Body body = me.retrieveActiveBody();
					UnitGraph g = new BriefUnitGraph(body);
					Iterator<Unit> it = g.iterator();
					while (it.hasNext()) {
						Unit tmpUnit = it.next();
						if (tmpUnit.branches()) {
							System.out.println("the branch is :" + tmpUnit.toString());
							List<Unit> us = g.getSuccsOf(tmpUnit);
							Iterator<Unit> i = us.iterator();
							while (i.hasNext()) {
								System.out.println("the secc is : " + i.next());
							}
						}
					}
					if (me.hasActiveBody()) {
						// System.out.println(me.getActiveBody().toString());
					}
				}
			}

		}

	}

	protected static void setOptions() {
		soot.options.Options.v().set_keep_line_number(true);
		soot.options.Options.v().set_whole_program(true);
		// LWG
		soot.options.Options.v().setPhaseOption("jb", "use-original-names:true");
		soot.options.Options.v().setPhaseOption("cg", "verbose:false");
		soot.options.Options.v().setPhaseOption("cg", "trim-clinit:true");
		soot.options.Options.v().set_src_prec(Options.src_prec_java);
		soot.options.Options.v().set_prepend_classpath(true);
		// don't optimize the program
		soot.options.Options.v().setPhaseOption("wjop", "enabled:false");
		// allow for the absence of some classes
		// soot.options.Options.v().set_allow_phantom_refs(true);
	}

	protected static void setSootClassPath() {
		StringBuffer cp = new StringBuffer();
		// cp.append(".");
		// 这里加载的时候 路径名应该缺少包名
		cp.append(File.pathSeparator + "/home/dsc/git/lockTest/src/test/java/");

		cp.append(File.pathSeparator + "/home/dsc/java/jdk1.8.0_211/jre/lib/rt.jar" + File.pathSeparator
				+ "/home/dsc/java/jdk1.8.0_211/jre/lib/jce.jar");
		System.setProperty("soot.class.path", cp.toString());

	}

}
