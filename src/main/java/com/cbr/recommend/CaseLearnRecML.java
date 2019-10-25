package com.cbr.recommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import org.apache.spark.mllib.tree.DecisionTree;
import org.apache.spark.mllib.tree.model.DecisionTreeModel;
import com.cbr.CaseRec;

public class CaseLearnRecML {
	private DecisionTreeModel tree_model = null;

	public static void initiallearncaseML(List<CaseRec> casedatabasesrec) {

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
		JavaRDD<LabeledPoint> training = sc.parallelize(caselists); // ＲＤＤ化，泛化类型为LabeledPoint 而不是List
		// 决策树
		Integer numClasses = 4;// 类别数量
		Map<Integer, Integer> categoricalFeaturesInfo = new HashMap<Integer, Integer>();
		String impurity = "gini";
		Integer maxDepth = 15;// 最大树深
		Integer maxBins = 32;// 最大划分数
		tree_model = DecisionTree.trainClassifier(training, numClasses, categoricalFeaturesInfo, impurity, maxDepth,
				maxBins);// 构建模型
		tree_model.save(sc.sc(), "src/main/resource/mlmodel");
		sc.close();
	}

	public int learncase(CaseRec c) {
		double[] caselist = { c.getStructure_type(), c.getNumThreads(), c.getReadNum(), c.getNum_operate() };
		Vector v = Vectors.dense(caselist);
		if (tree_model == null)
			tree_model = getModel();
		return (int) tree_model.predict(v);
	}

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
}
