package com.cbr.recommend;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.evaluation.MulticlassMetrics;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;

import com.cbr.CaseBasicMethod;
import com.cbr.CaseRec;

import scala.Tuple2;
import scala.Tuple3;

public class CaseLearnRecML {
	private DecisionTreeModel tree_model = null;

	/**
	 * 初始化model
	 * 
	 * @param casedatabasesrec
	 */
	public static void initiallearncaseML(List<CaseRec> casedatabasesrec) {
		if (ifModel() == 1) {
			// 删除
			deleteLocalDir("src/main/resource/mlmodel");
		}
		List<LabeledPoint> caselists = new ArrayList<LabeledPoint>();
		for (CaseRec caserec : casedatabasesrec) {
			LabeledPoint pos = new LabeledPoint(caserec.getLock_type(), Vectors.dense(caserec.getStructure_type(),
					caserec.getNumThreads(), caserec.getReadNum(), caserec.getNum_operate()));
			caselists.add(pos);
		}
		DecisionTreeModel tree_model;
		SparkConf conf;
		// 生成spark对象
		conf = new SparkConf();
		conf.set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext("local[*]", "Spark", conf);
		JavaRDD<LabeledPoint> data = sc.parallelize(caselists); // ＲＤＤ化，泛化类型为LabeledPoint 而不是List
		// 将数据分割为训练集、交叉检验集(CV)和测试集
		JavaRDD<LabeledPoint>[] splitArray = data.randomSplit(new double[] { 0.8, 0.1, 0.1 });
		JavaRDD<LabeledPoint> training = splitArray[0];
		training.cache();
		JavaRDD<LabeledPoint> cvData = splitArray[1];
		cvData.cache();
		JavaRDD<LabeledPoint> testData = splitArray[2];
		testData.cache();
		// 决策树
		Integer numClasses = 4;// 类别数量
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		String impurity = "gini";
		Integer maxDepth = 15;// 最大树深
		Integer maxBins = 30;// 最大划分数
//		List parm = eval(training, cvData);
//		String impurity = parm.get(0).toString();
//		Integer maxDepth = (int) parm.get(1);// 最大树深
//		Integer maxBins = (int) parm.get(2);// 最大划分数
		tree_model = DecisionTree.trainClassifier(training, numClasses, categoricalFeaturesInfo, impurity, maxDepth,
				maxBins);// 构建模型

		tree_model.save(sc.sc(), "src/main/resource/mlmodel");
		MulticlassMetrics metrics = getMetrics(tree_model, cvData);
		// 每个类别相对其他类别的精确度
		System.out.println(impurity + "++" + maxDepth + "++" + maxBins);
		Arrays.asList(new Integer[] { 0, 1, 2, 3 }).stream()
				.forEach(cat -> System.out
						.println(metrics.precision(cat) + "++++" + metrics.recall(cat) + "++++" + metrics.fMeasure(cat)
								+ "++++" + metrics.truePositiveRate(cat) + "++++" + metrics.falsePositiveRate(cat)));
		sc.close();
	}

	/**
	 * 根据搜索案例 推荐锁机制
	 * 
	 * @param c
	 * @return locktype
	 */
	public int learncase(CaseRec c) {
		double[] caselist = { c.getStructure_type(), c.getNumThreads(), c.getReadNum(), c.getNum_operate() };
		Vector v = Vectors.dense(caselist);
//		if (ifModel() == 0) {
//			initiallearncaseML(CaseBasicMethod.getCaseDatabases());
//		}
		if (tree_model == null)
			tree_model = getModel();
		return (int) tree_model.predict(v);
	}

	/**
	 * 获得模型
	 * 
	 * @return
	 */
	private DecisionTreeModel getModel() {
		SparkConf conf;
		String path = "src/main/resource/mlmodel";
		conf = new SparkConf();
		conf.set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext("local[*]", "Spark", conf);
		tree_model = DecisionTreeModel.load(sc.sc(), path);
		sc.close();
		return tree_model;
	}

	/**
	 * 检测当前是否模型存在
	 * 
	 * @return
	 */
	public static int ifModel() {
		SparkConf conf;
		String path = "src/main/resource/mlmodel";
		conf = new SparkConf();
		conf.set("spark.testing.memory", "2147480000");
		JavaSparkContext sc = new JavaSparkContext("local[*]", "Spark", conf);
		int flag = 0;
		try {
			if (DecisionTreeModel.load(sc.sc(), path) != null)
				flag = 1;
		} catch (Exception e) {
			flag = 1;
		}
		sc.close();
		return flag;
	}

	/**
	 * 删除模型
	 * 
	 * @return
	 */
	public static void deleteLocalDir(String dir) {
		File file = new File(dir);
		if (file.exists()) {
			// delete()方法不能删除非空文件夹，所以得用递归方式将file下所有包含内容删除掉，然后再删除file
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					deleteLocalDir(f.getPath());
				}
			}
			file.delete();
		}
	}

	/**
	 * 获得模型矩阵
	 * 
	 * @param model
	 * @param data
	 * @return
	 */
	public static MulticlassMetrics getMetrics(DecisionTreeModel model, JavaRDD<LabeledPoint> data) {

		JavaPairRDD<Object, Object> predictionsAndLabels = data.mapToPair(example -> {

			return new Tuple2<Object, Object>(model.predict(example.features()), example.label());

		});
		return new MulticlassMetrics(JavaPairRDD.toRDD(predictionsAndLabels));
	}

	/**
	 * 调优
	 * 
	 * @param trainData
	 * @param cvData
	 * @return
	 */
	public static List<Object> eval(JavaRDD<LabeledPoint> trainData, JavaRDD<LabeledPoint> cvData) {
		// 决策树调优
		List<Tuple2<Tuple3<String, Integer, Integer>, Double>> evaluations = new ArrayList<>();
		String[] impuritySet = new String[] { "gini", "entropy" };
		Integer[] depthSet = new Integer[] { 5, 10,  15,  20, 25 ,30};
		Integer[] binsSet = new Integer[] { 10, 20, 30, 40, 50, 60, 70, 80 };
		for (String impurity : impuritySet) {
			for (Integer depth : depthSet) {
				for (Integer bins : binsSet) {
					// 构建DecisionTreeModel
					DecisionTreeModel model = DecisionTree.trainClassifier(trainData, 4,
							new HashMap<Integer, Integer>(), impurity, depth, bins);
					// 用CV集来计算结果模型的指标
					MulticlassMetrics metrics = getMetrics(model, cvData);
					evaluations.add(new Tuple2<Tuple3<String, Integer, Integer>, Double>(
							new Tuple3<String, Integer, Integer>(impurity, depth, bins), metrics.precision()));
				}
			}
		}
		Collections.sort(evaluations, (m1, m2) -> (int) ((m2._2 - m1._2) * 1000));
		String impurity = evaluations.get(0)._1._1();
		Integer maxDepth = evaluations.get(0)._1._2();
		Integer maxBins = evaluations.get(0)._1._3();
		List<Object> list = new ArrayList<Object>();
		list.add(impurity);
		list.add(maxDepth);
		list.add(maxBins);
		//evaluations.forEach(x -> System.out.println(x._1._1() + "," + x._1._2() + "," + x._1._3() + "," + x._2));
		return list;
	}
}
