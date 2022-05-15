package NDE;

import java.util.ArrayList;
import java.util.Vector;

public class Graph {
	public int numOfVertex;
	public ArrayList<Integer> V;
	public ArrayList<Vector<Integer>> adjList;
	public double[][] distance;
	
	public Graph(int size){
		this.numOfVertex = size;
		this.V = new ArrayList<Integer>();
		for(int i = 0; i < this.numOfVertex; i++){
			V.add(i);
		}
		this.adjList = new ArrayList<Vector<Integer>>();
		for(int i = 0; i < this. numOfVertex; i++){
			adjList.add(new Vector<Integer>());
		}
		this.distance = new double[this.numOfVertex][this.numOfVertex];
	}
	
	public void addAdjNode(int node, int[] adjNode){
		for(int i: adjNode){
			this.adjList.get(node).add(i);
		}
	}
	public void printGraph(){
		for(int i = 0; i < this.numOfVertex; i++){
			System.out.print(i + ": ");
			for(int j: this.adjList.get(i)){
				System.out.print(j + " ");
			}
			System.out.println();
		}
	}
}
