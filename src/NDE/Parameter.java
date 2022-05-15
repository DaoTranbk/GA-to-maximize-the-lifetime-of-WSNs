package NDE;

import java.util.Random;

public class Parameter {
	// parameter of RSM problem
	public static int numOfVertex = 10;
	public static int numOfRelays = 4;
	public static int numOfSensors = 5;
	public static int numOfHop = 10;
	public static double radius;
	public static double e_lec = 50 * 1e-9;
	public static double e_fs = 10 * 1e-12;
	public static double e_mp = 0.0013 * 1e-12;
	public static double e_da = 5 * 1e-12;
	public static double k_bit = 4000;
	public static double E_da = k_bit * e_da ;
	public static double E_r = k_bit * e_lec;
	public static double d_0 = Math.sqrt(e_fs/e_mp);
	
	// parameter of the implementation
	public static Random rand = new Random(1);
	public static int generation = 300;
	public static double p_c = 0.9;
	public static double p_m = 0.1;
	public static int population_size = 150;
	public static int numberOfRuns = 20;
	public static String data_path_multihop = "./data/multiple/";
	public static String result_path_multihop = "./result/multiple/";
	public static String data_path_singlehop = "./data/single/";
	public static String result_path_singlehop = "./result/single/";
}
