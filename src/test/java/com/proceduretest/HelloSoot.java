package com.proceduretest;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootField;
import soot.SootMethod;
import soot.SourceLocator;
import soot.Unit;
import soot.options.Options;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.UnitGraph;

public class HelloSoot {
	/// private String apiPath =
	/// "/home/dsc/code/locktest/src/test/java/com/proceduretest";
	//String className = "com.proceduretest.Employee";
	String className = "com.proceduretest.LockTest";
    //被检测类  要有空构造函数
	public void getClassUnderDir() {
		// System.out.println(Scene.v().getApplicationClasses().size());
		Scene.v().loadClass(className, SootClass.BODIES).setApplicationClass();
		// for (String clzName : SourceLocator.v().getClassesUnder(apiPath)) {
		// System.out.printf("api class: %s\n", clzName);
		// // 加载要处理的类设置为应用类，并加载到soot环境Scene中
		// SootClass sc = Scene.v().loadClass(clzName, SootClass.BODIES);
		// System.out.println("加载完成");
		// sc.setApplicationClass();
		// System.out.println("配置完成");
		// }
	}

	public void getAttribute() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		
		// System.out.println(Scene.v().getApplicationClasses().size());
		for (SootClass clz : Scene.v().getApplicationClasses()) {
			System.out.println(clz.getName());
			Object object = Class.forName(clz.getName()).newInstance();
			if (clz.getFieldCount() == 0) {
				System.out.println("do not have Attribute");
			} else {
				// System.out.println("Attribute num:" + clz.getFieldCount());
				//for (SootField sf : clz.getFields()) {
					//System.out.println(sf.getName());
					Class<?> aClass = object.getClass();
					Field[] declaredFields = aClass.getDeclaredFields();
					for (Field field : declaredFields) {
						try {
							field.setAccessible(true);
							String fn = field.getName();
							System.out.println("Attribute：" + fn + "Value:" + field.get(object));
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}

					//}
				}

			}
		}

	}

	public void getMethods() {
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

	private static void setOptions() {
		soot.options.Options.v().set_keep_line_number(true);
		soot.options.Options.v().set_whole_program(true);
		// LWG
		soot.options.Options.v().setPhaseOption("jb", "use-original-names:true");
		soot.options.Options.v().setPhaseOption("cg", "verbose:false");
		soot.options.Options.v().setPhaseOption("cg", "trim-clinit:true");
		// soot.options.Options.v().setPhaseOption("jb.tr",
		// "ignore-wrong-staticness:true");

		soot.options.Options.v().set_src_prec(Options.src_prec_java);
		soot.options.Options.v().set_prepend_classpath(true);

		// don't optimize the program
		soot.options.Options.v().setPhaseOption("wjop", "enabled:false");
		// allow for the absence of some classes
		// soot.options.Options.v().set_allow_phantom_refs(true);

	}

	private static void setSootClassPath() {
		StringBuffer cp = new StringBuffer();
		// cp.append(".");
		// 这里加载的时候 路径名应该缺少包名
		cp.append(File.pathSeparator + "/home/dsc/git/lockTest/src/test/java/");

		cp.append(File.pathSeparator + "/home/dsc/java/jdk1.8.0_211/jre/lib/rt.jar" + File.pathSeparator
				+ "/home/dsc/java/jdk1.8.0_211/jre/lib/jce.jar");
		System.setProperty("soot.class.path", cp.toString());

	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		setSootClassPath();// 设置classpath
		setOptions();// 设置soot的选项
		HelloSoot s = new HelloSoot();
		s.getClassUnderDir();
		// s.getMethods();
		s.getAttribute();
	}
}
