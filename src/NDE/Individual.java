package NDE;

import java.util.ArrayList;



public class Individual {
	public int size;
	public ArrayList<Gen> chromosome;
	private double fitness;
	public int skill_factor;
	
	public Individual(){
		this.size = Parameter.numOfVertex;
		this.chromosome = new ArrayList<Gen>();
	}
	public Individual(Individual ind){
		this.size = ind.size;
		this.chromosome = new ArrayList<Gen>();
		for(Gen i: ind.chromosome){
			Gen j = new Gen();
			j.setVertex(i.getVertex());
			j.setDepth(i.getDepth());
			this.chromosome.add(j);
		}
		this.skill_factor = ind.skill_factor;
	}
	public void init(Graph G, int task){
		Individual ind = new Individual(Operator.enconding(Tree.primRST4RL(G)));
		for(Gen i: ind.chromosome){
			this.chromosome.add(i);
		}
		this.skill_factor = task;
	}
	
	public Individual(int vertex[], int depth[], int sf){
		this.size = Parameter.numOfVertex;
		this.chromosome = new ArrayList<Gen>();
		for(int i = 0; i < vertex.length; i++){
			this.chromosome.add(new Gen(vertex[i], depth[i]));
		}
		this.skill_factor = sf;
	}
	public int findParent(int node){
		int parentNode = 0;
		int index_node = 0;
		for(int i = this.size-1; i >= 0; i--){
			if(this.chromosome.get(i).getVertex() == node){
				index_node = i;
				break;
			}
		}
		for(int i = index_node; i >= 0; i--){
			if(this.chromosome.get(i).getDepth() < this.chromosome.get(index_node).getDepth()){
				parentNode = this.chromosome.get(i).getVertex();
				break;
			}
		}
		return parentNode;
	}
	public boolean checkAncestor(int preNode, int afterNode){
		if (afterNode == 0) {
			return false;
		}
		boolean check = false;
		int v;
		int af_node = afterNode;
		do {
			v = findParent(af_node);
			if(v == preNode){
				check = true;
			}
			af_node = v;
		}
		while(v != 0);
		return check;
	}
	public double getFitness() {
		return fitness;
	}
	public ArrayList<Integer> findChildSet(int v) {
		ArrayList<Integer> setchild_v = new ArrayList<Integer>();
		// if(v == 0){
		// return setchild_v;
		// }
		int index_v = -1;
		int depth_v = -1;
		int index_stop = this.size;
		for (int i = 0; i < this.size; i++) {
			if (this.chromosome.get(i).getVertex() == v) {
				index_v = i;
				depth_v = this.chromosome.get(i).getDepth();
				break;
			}
		}
		for (int i = index_v + 1; i < this.size; i++) {
			if (this.chromosome.get(i).getDepth() <= depth_v) {
				index_stop = i;
				// System.err.println(index_stop);
				break;
			}
		}
		// System.out.print(index_v + " - ");
		// System.out.println(index_stop);
		for (int i = index_v + 1; i < index_stop; i++) {
			setchild_v.add(this.chromosome.get(i).getVertex());
		}
		return setchild_v;
	}
	public ArrayList<Integer> findDirectChildSet(int v) {
		ArrayList<Integer> setchild_v = new ArrayList<Integer>();
		// if(v == 0){
		// return setchild_v;
		// }
		int index_v = -1;
		int depth_v = -1;
		int index_stop = this.size;
		for (int i = 0; i < this.size; i++) {
			if (this.chromosome.get(i).getVertex() == v) {
				index_v = i;
				depth_v = this.chromosome.get(i).getDepth();
				break;
			}
		}
		for (int i = index_v + 1; i < this.size; i++) {
			if (this.chromosome.get(i).getDepth() <= depth_v) {
				index_stop = i;
				// System.err.println(index_stop);
				break;
			}
		}
		// System.out.print(index_v + " - ");
		// System.out.println(index_stop);
		for (int i = index_v + 1; i < index_stop; i++) {
			if(this.chromosome.get(i).getDepth() == depth_v + 1){
				setchild_v.add(this.chromosome.get(i).getVertex());
			}
		}
		return setchild_v;
	}
	
	public int findHeightSubtree(int node_asroot){
		int height = 0;
		int index_node_asroot = 0;
		int depth_node_asroot = 0;
		int last_child = 0;
		int index_last_child = 0;
		for(int i = 0; i < this.size; i++){
			if(this.chromosome.get(i).getVertex() == node_asroot){
				index_node_asroot = i;
				depth_node_asroot = this.chromosome.get(i).getDepth();
			}
			if(i > index_node_asroot && index_node_asroot != 0){
				if(this.chromosome.get(i).getDepth() > depth_node_asroot){
					last_child = this.chromosome.get(i).getVertex();
					index_last_child = i;
				}
				else {
					break;
				}
			}
		}
		height = this.chromosome.get(index_last_child).getDepth() - this.chromosome.get(index_node_asroot).getDepth();
		return height;
	}
	public boolean isValid(){
		boolean check = true;
		// check constraint Hop
		if (this.maxDepth() > Parameter.numOfHop){
			check = false;
		}
		Tree tmp = new Tree(Operator.decoding(this));
		// check relays node depth : 1
		for(int i = 0; i < this.size; i++){
			if(this.chromosome.get(i).getVertex() <= Parameter.numOfRelays && this.chromosome.get(i).getVertex() != 0 && this.chromosome.get(i).getDepth()!=1){
				if(tmp.checkLeaveNode(i) == false){
					check = false;
					break;
				}
			}
		}
		return check;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public void printIndividual(){
		System.out.println("Genotype: ");
		System.out.print("-Vertex:  ");
		for(int i = 0; i < Parameter.numOfVertex; i++){
			System.out.print(this.chromosome.get(i).getVertex()+", ");
		}
		System.out.println();
		System.out.print("-Depth:   ");
		for(int i = 0; i < Parameter.numOfVertex; i++){
			System.out.print(this.chromosome.get(i).getDepth()+", ");
		}
		System.out.println();
	}
	public int maxDepth(){
		int max_depth = this.chromosome.get(0).getDepth();
		for(Gen i: this.chromosome){
			if(max_depth < i.getDepth()){
				max_depth = i.getDepth();
			}
		}
		return max_depth;
	}
}
