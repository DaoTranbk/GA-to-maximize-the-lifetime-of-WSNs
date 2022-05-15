package NDE;

import java.io.File;
import java.util.ArrayList;




public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("hello");
		// test Graph

		// test for one instance
		String path = "./data/multiple/ga-dem10_r25_1_0.json";
		Graph G1 = new Graph(Parameter.numOfVertex);
		G1 = Utils.readData(path, 1);
//		 int[] v1 = { 0, 6, 29, 66, 76, 15, 48, 11, 78, 30, 49, 19, 47, 37, 69, 44, 35, 10, 79, 46, 17, 73, 12, 7, 13, 74, 31, 14, 50, 64, 36, 27, 59, 65, 2, 54, 40, 33, 62, 8, 9, 5, 20, 4, 68, 80, 22, 61, 77, 25, 75, 18, 70, 57, 34, 72, 3, 16, 45, 67, 23, 21, 52, 71, 28, 42, 63, 24, 26, 53, 55, 32, 43, 41, 39, 38, 60, 51, 1, 56, 58};
//		 int[] d1 = { 0, 1, 1, 2, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1, 1, 1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 2, 1, 2, 2, 1, 2, 1, 2, 2, 1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2, 1, 1, 2, 2, 1, 2, 2};
//		 int[] v2 = {0, 31, 70, 79, 59, 80, 54, 52, 42, 41, 51, 43, 78, 58, 60, 62, 55, 53, 47, 71, 63, 61, 46, 33, 27, 21, 16, 35, 15, 29, 37, 8, 19, 5, 39, 32, 40, 14, 25, 23, 49, 3, 24, 12, 13, 74, 67, 26, 72, 48, 44, 77, 69, 4, 7, 18, 1, 6, 38, 28, 36, 75, 56, 57, 73, 66, 65, 10, 68, 50, 76, 64, 45, 20, 17, 30, 34, 2, 22, 11, 9};
//		 int[] d2 = {0, 1, 2, 3, 4, 5, 3, 4, 5, 6, 7, 7, 6, 7, 5, 6, 7, 7, 8, 2, 3, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 3, 4, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 4, 5, 1, 2, 3, 4, 5, 5, 1, 1, 1, 1, 1, 1, 1, 1};
//		 int[] parent_node = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 57, 68, 54, 54, 7, 57, 68, 51, 65, 7, 16, 24, 7, 55, 51, 65, 65, 54, 16, 35, 7, 57, 71, 55, 69, 55, 71, 57, 52, 35, 68, 68, 51, 65, 57, 77, 7, 55, 51, 51};
//		 Individual a = new Individual(v1, d1, 2);
//		 Task.eval(G2, a);
//		 a.printIndividual();
//		 System.out.println(a.getFitness());
//		 Tree ta = Operator.decoding(a);
//		 ta.printTree();
//		 for(int i = 1; i < ta.numOfVertex; i++){
//			 System.out.println(i + "-" +  ta.parentNode[i]);
//			 if(G2.distance[i][ta.parentNode[i]] > 0){
//				 System.out.println(G2.distance[i][ta.parentNode[i]]);
//			 }
//		 }
		GA solver = new GA();
		double[] results = new double[Parameter.generation];
		long[] time = new long[Parameter.numberOfRuns];
		solver.run(G1,1, 1, results, time);
//		
//		for(int i = 0; i<10; i++){
//			Individual a = new Individual();
//			a.init(G);
//			Operator.decoding(a).printTree();
//		}
		
		
		// test for all instances
		// getting file

//		for(String i: instances){
//			System.out.println(i);
//		}

		
//		for(int taskID = 1; taskID <=2; taskID++){
//			if(taskID == 1){
//				ArrayList<String> instances = new ArrayList<String>();
//				File folder = new File(Parameter.data_path_multihop);
//				File[] listOfFiles = folder.listFiles();
//				String nameIns;
//				for (int i = 0; i < listOfFiles.length; i++) {
//				  if (listOfFiles[i].isFile()) {
//				    nameIns = listOfFiles[i].getName();
//				    instances.add(nameIns.substring(0,nameIns.length()-5));
//				  } else if (listOfFiles[i].isDirectory()) {
//				    System.out.println("Directory " + listOfFiles[i].getName());
//				  }
//				}
//				ArrayList<Double> res = new ArrayList<Double>();
//				double sum;
//				double mean;
//				System.out.println("Running in multi-hop network:");
//				System.out.println();
//				for(String inst: instances){
//					System.out.println("===============================");
//					System.out.println("Instance: " + inst);
//					String path = Parameter.data_path_multihop + inst + ".json";
//					String repath = Parameter.result_path_multihop + inst + ".txt";
//					Graph G = new Graph(Parameter.numOfVertex);
//					G = Utils.readData(path, taskID);
//					double[][] result = new double[Parameter.numberOfRuns][Parameter.generation];
//					long[] time = new long[Parameter.numberOfRuns];
//					for(int seed = 0; seed < Parameter.numberOfRuns; seed++){
//						GA solver = new GA();
//						solver.run(G, taskID, seed, result[seed], time);
//					}
//					Utils.logResults(repath, result, time);
//					sum = 0;
//					for(int i = 0; i< Parameter.numberOfRuns; i++){
//						sum += result[i][Parameter.generation-1];
//					}
//					mean = sum/Parameter.numberOfRuns;
//					res.add(mean);
//				}
//				for(double i: res){
//					System.out.println(i);
//				}
//			}else {
//				ArrayList<String> instances = new ArrayList<String>();
//				File folder = new File(Parameter.data_path_singlehop);
//				File[] listOfFiles = folder.listFiles();
//				String nameIns;
//				for (int i = 0; i < listOfFiles.length; i++) {
//				  if (listOfFiles[i].isFile()) {
//				    nameIns = listOfFiles[i].getName();
//				    instances.add(nameIns.substring(0,nameIns.length()-5));
//				  } else if (listOfFiles[i].isDirectory()) {
//				    System.out.println("Directory " + listOfFiles[i].getName());
//				  }
//				}
//				ArrayList<Double> res = new ArrayList<Double>();
//				double sum;
//				double mean;
//				System.out.println("Running in single-hop network:");
//				System.out.println();
//				for(String inst: instances){
//					System.out.println("===============================");
//					System.out.println("Instance: " + inst);
//					String path = Parameter.data_path_singlehop + inst + ".json";
//					String repath = Parameter.result_path_singlehop + inst + ".txt";
//					Graph G = new Graph(Parameter.numOfVertex);
//					G = Utils.readData(path, taskID);
//					double[][] result = new double[Parameter.numberOfRuns][Parameter.generation];
//					long[] time = new long[Parameter.numberOfRuns];
//					for(int seed = 0; seed < Parameter.numberOfRuns; seed++){
//						GA solver = new GA();
//						solver.run(G, taskID, seed, result[seed], time);
//					}
//					Utils.logResults(repath, result, time);
//					sum = 0;
//					for(int i = 0; i< Parameter.numberOfRuns; i++){
//						sum += result[i][Parameter.generation-1];
//					}
//					mean = sum/Parameter.numberOfRuns;
//					res.add(mean);
//				}
//				for(double i: res){
//					System.out.println(i);
//				}
//			}
//		}

	}
}
