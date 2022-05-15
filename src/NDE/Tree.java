package NDE;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;


public class Tree {
	public int numOfVertex;
	public int[] parentNode;
	public ArrayList<Vector<Integer>> childNode;
	public int[] depthNode;
	
	public Tree(int size) {
		this.numOfVertex = size;
		this.parentNode = new int[numOfVertex];
		this.depthNode = new int[numOfVertex];
		this.setParentnode(parentNode);
		for(int i = 0; i < this.numOfVertex; i++){
			this.parentNode[i] = -1;
		}
	}
	public Tree(int size, int[] parentNode) {
		this.numOfVertex = size;
		this.parentNode = new int[numOfVertex];
		this.depthNode = new int[numOfVertex];
		this.setParentnode(parentNode);
	}
	public Tree(Tree a) {
		this.numOfVertex = a.numOfVertex;
		this.parentNode = new int[numOfVertex];
		this.depthNode = new int[numOfVertex];
		for (int i = 0; i < numOfVertex; i++) {
			this.parentNode[i] = a.parentNode[i];
			this.depthNode[i] = a.depthNode[i];
		}
		this.findChildNode();
	}
	public void setParentnode(int[] pnode){
		for(int i = 0; i < this.numOfVertex; i++){
			this.parentNode[i] = pnode[i];
		}
	}
	public void updateTree(int parent, int child) {
		this.parentNode[child] = parent;
//		this.depthNode[parent] = depth_parent;
//		this.depthNode[child] = depth_child;
	}

	public boolean checkAncestor(int preNode, int afterNode) {
		// preNode differ from afterNode
		if (afterNode == 0) {
			return false;
		}
		boolean check = false;
		int v;
		v = this.parentNode[afterNode];
		int i = 1;
		while (v != 0 && check != true) {
			if (v == preNode) {
				check = true;
			}
			// System.out.println(v);
			v = this.parentNode[v];
		}
		return check;
	}

	public void findChildNode() {
		this.childNode = new ArrayList<Vector<Integer>>();
		for (int i = 0; i < numOfVertex; i++) {
			this.childNode.add(new Vector<Integer>());
		}
		for (int i = numOfVertex - 1; i > 0; i--) {
			this.childNode.get(this.parentNode[i]).add(i);
		}
		// System.out.println("-Children: ");
		// for(int i = 0; i<numOfVertex; i++){
		// System.out.print(i + ": ");
		// for(int j: this.childNode.get(i)){
		// System.out.print(j + " ");
		// }
		// System.out.println();
		// }
	}

	// return order traversed node in gen
	public int[] dfsTree() {
		int[] order_traverse = new int[numOfVertex];
		Stack<Integer> nodes_to_visit = new Stack<Integer>();
		nodes_to_visit.push(0);
		int currentNode;
		int j = 0;
		while (!nodes_to_visit.empty()) {
			currentNode = nodes_to_visit.pop();
			for (int i : this.childNode.get(currentNode)) {
				nodes_to_visit.push(i);
			}
			order_traverse[j] = currentNode;
			j++;
		}
		return order_traverse;
	}

	// find depth node
	public void findNodeDepth() {
		this.depthNode = new int[numOfVertex];
		this.depthNode[0] = 0;
		int depth;
		int target;
		for (int i = 1; i < numOfVertex; i++) {
			depth = 0;
			target = i;
			while (target != 0) {
				target = this.parentNode[target];
				depth += 1;
			}
			this.depthNode[i] = depth;
		}
		// check print
		// System.out.println("Node depth");
		// for(int i: this.depthNode){
		// System.out.print(i + " ");
		// }
	}

	public static Tree primRST(Graph G) {
		Tree t = new Tree(G.numOfVertex);
		ArrayList<Integer> U = new ArrayList<Integer>();
		U.add(0);
		t.depthNode[0] = 0;
		int v_node;
		int u_node;
		while (U.size() < G.numOfVertex) {
			 v_node = U.get(Parameter.rand.nextInt(U.size()));
			// adding check h-constraints
//			do {
//				v_node = U.get(Parameter.rand.nextInt(U.size()));
//			} while (t.depthNode[v_node] >= Parameter.numOfHop);
			u_node = G.adjList.get(v_node).get(
					Parameter.rand.nextInt(G.adjList.get(v_node).size()));
			if (!checkOnSet(u_node, U)) {
				U.add(u_node);
				t.parentNode[u_node] = v_node;
				t.depthNode[u_node] = t.depthNode[v_node] + 1;
			}
			// System.out.println("lol");
		}
		return t;
	}
	public static Tree primRST4RL(Graph G) {
		Tree t = new Tree(G.numOfVertex);
		ArrayList<Integer> U = new ArrayList<Integer>();
		U.add(0);
		t.depthNode[0] = 0;
		int v_node;
		int u_node;
		while (U.size() < G.numOfVertex) {
			 v_node = U.get(Parameter.rand.nextInt(U.size()));
			// adding check h-constraints
//			do {
//				v_node = U.get(Parameter.rand.nextInt(U.size()));
//			} while (t.depthNode[v_node] >= Parameter.numOfHop);
			if(v_node != 0){
				ArrayList<Integer> sensors_conect_v = new ArrayList<Integer>();
				for(int i: G.adjList.get(v_node)){
					if(i>Parameter.numOfRelays){
						sensors_conect_v.add(i);
					}
				}
				if(sensors_conect_v.size()==0){
					continue;
				}
				u_node = sensors_conect_v.get(
							Parameter.rand.nextInt(sensors_conect_v.size()));
			}
			else{
				u_node = G.adjList.get(v_node).get(
						Parameter.rand.nextInt(G.adjList.get(v_node).size()));
			}
			if (!checkOnSet(u_node, U)) {
				U.add(u_node);
				t.parentNode[u_node] = v_node;
				t.depthNode[u_node] = t.depthNode[v_node] + 1;
			}
			// System.out.println("lol");
		}
		return t;
	}

	public static boolean checkOnSet(int u, ArrayList<Integer> S) {
		for (int i : S) {
			if (u == i)
				return true;
		}
		return false;
	}
	public boolean checkLeaveNode(int node){
		boolean check = false;
		this.findChildNode();
		if(node != 0 &&  this.childNode.get(node).size() == 0){
			check = true;
		}
		return check;
	}

	public void printTree() {
		System.out.println("Tree: ");
		System.out.print("-Node:    ");
		for (int i = 0; i < numOfVertex; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.print("-Parent: ");
		for (int i = 0; i < numOfVertex; i++) {
			System.out.print(parentNode[i] + " ");
		}
		System.out.print("\n-Depth:   ");
		for (int i = 0; i < numOfVertex; i++) {
			System.out.print(depthNode[i] + " ");
		}
		System.out.print("\nIs leave: ");
		int count_relay_is_leave = 0;
		for(int i = 0; i < numOfVertex; i++){
			if(this.checkLeaveNode(i) && i < Parameter.numOfRelays){
				System.out.print(1 + " ");
				count_relay_is_leave++;
			}
			else {
				System.out.print(0 + " ");
			}
		}
		System.out.println("\nNumber of relays node is not leave node: " + Integer.toString(Parameter.numOfRelays-count_relay_is_leave));
		
	}
	public int maxDepth(){
		int max_depth = this.depthNode[0];
		for(int i: this.depthNode){
			if(max_depth < i){
				max_depth = i;
			}
		}
		return max_depth;
	}
}
