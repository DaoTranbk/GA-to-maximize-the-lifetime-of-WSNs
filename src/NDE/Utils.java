package NDE;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class Utils {
//	public static void main(String[] args) throws Exception {
//		String path = "./data/hop/small/ga-dem1_r25_1_0.json";
//		
//		readData(path);
////		String repath = "./result/small/hop/ga-dem1_r25_1_0.txt";
//	}

	static public Graph readData(String filepath, int task) throws Exception {
		Object obj = new JSONParser().parse(new FileReader(filepath));

		// typecasting obj to JSONObject
		JSONObject jo = (JSONObject) obj;

		// getting num of relays, num of sensors
		int numOfRelays = (int) (long) jo.get("num_of_relays");
		int numOfSensors = (int) (long) jo.get("num_of_sensors");
		Parameter.numOfRelays = numOfRelays;
		Parameter.numOfSensors = numOfSensors;
		Parameter.numOfVertex = numOfRelays + numOfSensors + 1;

		// getting radius
		double radius = (double) (long) jo.get("radius");
		Parameter.radius = radius;

		// getting center
		Map center = ((Map) jo.get("center"));
		// iterating address Map
		Iterator<Map.Entry> itr1_center = center.entrySet().iterator();

		ArrayList<Node> vertex = new ArrayList<Node>();
		Node center_node = new Node(0);
		while (itr1_center.hasNext()) {
			Map.Entry pair = itr1_center.next();
			if (((String) pair.getKey()).equals("x")) {
				center_node.x = (double) pair.getValue();
			} else if (((String) pair.getKey()).equals("y")) {
				center_node.y = (double) pair.getValue();
			} else {
				center_node.z = (double) pair.getValue();
			}
			// System.out.println(pair.getKey() + " : " + pair.getValue());
		}
		vertex.add(center_node);
		// center_node.printNode();

		// getting relay nodes
		JSONArray ja_relay = (JSONArray) jo.get("relays");
		Iterator<Map.Entry> itr1_relay;
		// iterating relay nodes

		Iterator itr2_relay = ja_relay.iterator();
		int label = 1;
		while (itr2_relay.hasNext()) {
			Node relay_node = new Node(label);
			itr1_relay = ((Map) itr2_relay.next()).entrySet().iterator();
			while (itr1_relay.hasNext()) {
				Map.Entry positon_node = itr1_relay.next();
				if (((String) positon_node.getKey()).equals("x")) {
					relay_node.x = (double) positon_node.getValue();
				} else if (((String) positon_node.getKey()).equals("y")) {
					relay_node.y = (double) positon_node.getValue();
				} else {
					relay_node.z = (double) positon_node.getValue();
				}
			}
			vertex.add(relay_node);
			label++;
		}
		// getting relay nodes
		JSONArray ja_sensor = (JSONArray) jo.get("sensors");
		Iterator<Map.Entry> itr1_sensor;
		// iterating relay nodes
		Iterator itr2_sensor = ja_sensor.iterator();

		while (itr2_sensor.hasNext()) {
			Node sensor_node = new Node(label);
			itr1_sensor = ((Map) itr2_sensor.next()).entrySet().iterator();
			while (itr1_sensor.hasNext()) {
				Map.Entry positon_node = itr1_sensor.next();
				if (((String) positon_node.getKey()).equals("x")) {
					sensor_node.x = (double) positon_node.getValue();
				} else if (((String) positon_node.getKey()).equals("y")) {
					sensor_node.y = (double) positon_node.getValue();
				} else {
					sensor_node.z = (double) positon_node.getValue();
				}
			}
			vertex.add(sensor_node);
			label++;
		}
		// System.out.println(vertex.size());
		// for(Node v: vertex){
		// v.printNode();
		// }
		
		if(task == 1){
			return covert2Graph(vertex);
		}
		else if(task == 2){
			return covert2cutGraph(vertex);
		}
		else return null;
		
	}

	static public Graph covert2Graph(ArrayList<Node> vertex) {
		Graph G = new Graph(Parameter.numOfVertex);
		// ensure that all relays can connect to base station
		for(int j = 1; j <= Parameter.numOfRelays; j++){
			G.adjList.get(0).add(j);
			G.adjList.get(j).add(0);
			G.distance[0][j] = Node.distance(vertex.get(0), vertex.get(j));
			G.distance[j][0] = G.distance[0][j];
		}
		 
		for (int i = 0; i < vertex.size(); i++) {
			for (int j = i+1; j < vertex.size(); j++) {
				if(i <= Parameter.numOfRelays && i>=0 && j<= Parameter.numOfRelays){
					continue;
				}
				if (Node.distance(vertex.get(i), vertex.get(j)) <= 2*Parameter.radius) {
					G.adjList.get(i).add(j);
					G.adjList.get(j).add(i);
					G.distance[i][j] = Node.distance(vertex.get(i), vertex.get(j));
					G.distance[j][i] = G.distance[i][j];
				}
			}
		}
//		G.printGraph();
		System.out.println("Reading data done!");
		return G;
	}
	static public Graph covert2cutGraph(ArrayList<Node> vertex){
		Graph G = new Graph(Parameter.numOfVertex);
		// ensure that all relays can connect to base station
		for(int j = 1; j <= Parameter.numOfRelays; j++){
			G.adjList.get(0).add(j);
			G.adjList.get(j).add(0);
			G.distance[0][j] = Node.distance(vertex.get(0), vertex.get(j));
			G.distance[j][0] = G.distance[0][j];
		}
		 
		for (int i = 1; i <= Parameter.numOfRelays; i++) {
			for (int j = Parameter.numOfRelays+1; j < vertex.size(); j++) {
				if (Node.distance(vertex.get(i), vertex.get(j)) <= 2*Parameter.radius) {
					G.adjList.get(i).add(j);
					G.adjList.get(j).add(i);
					G.distance[i][j] = Node.distance(vertex.get(i), vertex.get(j));
					G.distance[j][i] = G.distance[i][j];
				}
			}
		}
//		G.printGraph();
		System.out.println("Reading data done!");
		return G;
	}
	static public void logResults(String filename, double[][] result, long[] time) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for(int i = 0; i < Parameter.generation; i++){
			for(int j = 0; j<Parameter.numberOfRuns; j++){
				writer.write(i + " " + result[j][i]);
			}
			writer.write("\n");
		}
		writer.write("Time: ");
		for(int j = 0; j< Parameter.numberOfRuns; j++){
			writer.write(Long.toString(time[j]) + " ");
		}
		double sum = 0;
		for(int i = 0; i< Parameter.numberOfRuns; i++){
			sum += result[i][Parameter.generation-1];
		}
		double mean = sum/Parameter.numberOfRuns;
		writer.write("\nMean objective: " + mean);
		writer.close();
	}
}
