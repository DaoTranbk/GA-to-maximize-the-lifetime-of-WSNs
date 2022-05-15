package NDE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Operator{
	
	// decoding according algorithm 1 in pp node depth encoding
	static public Individual enconding(Tree tree){
		tree.findChildNode();
		tree.findNodeDepth();
		int[] u, v;
		u = tree.dfsTree();
		v = new int[Parameter.numOfVertex];
		for(int i = 0; i < Parameter.numOfVertex; i++){
			v[i] = tree.depthNode[u[i]];
		}
		Individual ind = new Individual(u,v,1);
		return ind;
		
	}
	static public Tree decoding(Individual ind){
		Tree tree = new Tree(ind.size);
		int r[] = new int[Parameter.numOfVertex];
		r[0] = ind.chromosome.get(0).getVertex();
		int i = 1;
		while(i < Parameter.numOfVertex){
			r[ind.chromosome.get(i).getDepth()] = ind.chromosome.get(i).getVertex();
			tree.updateTree(r[ind.chromosome.get(i).getDepth()-1], ind.chromosome.get(i).getVertex());
			i += 1;
		}
		tree.findNodeDepth();
		return tree;
	}
	static public Individual decoding_from_uss(Graph G, Individual ind, int taskID){
		Individual newInd = new Individual();
		if(taskID == 1){ // if task is RSM
			newInd = ind;
		}
		else if (taskID == 2){ // if task is RSS
			// find a set of sensors with depth > 2 
//			int[] afterNode = new int[ind.size];
//			int[] beforeNode = new int[ind.size];
//			beforeNode[0] = -1;
			ArrayList<Integer> sensorsSet = new ArrayList<Integer>();
			ArrayList<Integer> indexS = new ArrayList<Integer>();
			int nameNode[] = new int[ind.size];
			for(int i = 0; i < ind.size; i++){
				if(ind.chromosome.get(i).getDepth() <= 2){
					newInd.chromosome.add(new Gen(ind.chromosome.get(i).getVertex(),ind.chromosome.get(i).getDepth()));
				}
				if(ind.chromosome.get(i).getDepth() > 2){
					sensorsSet.add(ind.chromosome.get(i).getVertex());
					indexS.add(i);
					newInd.chromosome.add(new Gen(ind.chromosome.get(i).getVertex(),2));
				}
//				if(i < ind.size - 1){
//					afterNode[ind.chromosome.get(i).getVertex()] = ind.chromosome.get(i+1).getVertex();
//					beforeNode[ind.chromosome.get(i+1).getVertex()] = ind.chromosome.get(i).getVertex();
//				}
//				else {
//					afterNode[ind.chromosome.get(i).getVertex()] = -1;
//				}
			}
			// find relay set is parent set of sensors
			ArrayList<Integer> parentSet = new ArrayList<Integer>();
			ArrayList<Integer> indexR = new ArrayList<Integer>();
			ArrayList<Boolean> needMove = new ArrayList<Boolean>();
			for(int i = 0; i < indexS.size(); i++){
				boolean checkParentNode = false;
				int demNodeDepth_1 = 0;
				if(checkParentNode == false){
					for(int j = indexS.get(i); j>=0; j--){
						if(ind.chromosome.get(j).getDepth() == 1){
							demNodeDepth_1 ++;
							if (G.distance[ind.chromosome.get(j).getVertex()][sensorsSet.get(i)]>0){
								parentSet.add(ind.chromosome.get(j).getVertex());
								indexR.add(j);
								checkParentNode = true;
								if(demNodeDepth_1 == 1){
									needMove.add(false);
								}
								else {
									needMove.add(true);
								}
							}
						}
					}
				}
				if(checkParentNode == false){
					demNodeDepth_1 = 0;
					for(int j = indexS.get(i); j< ind.size; j++){
						if(ind.chromosome.get(j).getDepth() == 1){
							demNodeDepth_1 ++;
							if (G.distance[ind.chromosome.get(j).getVertex()][sensorsSet.get(i)]>0){
								parentSet.add(ind.chromosome.get(j).getVertex());
								indexR.add(j);
								checkParentNode = true;
								if(demNodeDepth_1 == 1){
									needMove.add(false);
								}
								else {
									needMove.add(true);
								}
							}
						}
					}
				}
				
			}
			for(int i = 0; i < sensorsSet.size(); i++){
				if(needMove.get(i) == true){
					insertAfterVertex(newInd, new Gen(sensorsSet.get(i),2),new Gen(parentSet.get(i),2));
				}
			}
		}
//		ind.printIndividual();
//		newInd.printIndividual();
		return newInd;
	}
	static public void insertAfterVertex(Individual ind, Gen a, Gen b){// insert gen a after gen b
		// O(n)
		ind.chromosome.removeIf(gen-> gen.getVertex() == a.getVertex());
		int index_b = -1;
		for(int i = 0; i < ind.size; i++){
			if (ind.chromosome.get(i).getVertex() == b.getVertex()){
				index_b = i;
				break;
			}
		}
		ind.chromosome.add(index_b+1, a);
	}
	static public Individual mutateEPO(Individual ind, int v1, int v2, Graph G){
//		System.out.print(v1 + "- ");
//		System.out.println(v2);
//		ind.printIndividual();
		
//		System.out.println("OOOOOOOO");
		
		if(v1 == -1 && v2 ==-1){
			v1 = Parameter.rand.nextInt(Parameter.numOfSensors) + Parameter.numOfRelays + 1;
			v2 = G.adjList.get(v1).get(Parameter.rand.nextInt(G.adjList.get(v1).size()));
		}
		Individual offIndividual = new Individual(ind);
		// check (v1,v2) exists on the input graph
		int v1_a = v1;
		int v2_a = v2;
		if(G.adjList.get(v1).size() > G.adjList.get(v2).size()){
			v1_a = v2;
			v2_a = v1;
		}
		boolean check = false;
		for(int i : G.adjList.get(v1_a)){
			if(i == v2_a){
				check = true;
				break;
			}
		}
		if(!check){
			System.out.println(v1 + "-" + v2 +" does not exists in the input graph");
			return ind;
		}
		// continue decoding
		// check v1: always after v2
		if(ind.checkAncestor(v1, v2)){
			int tmp = v1;
			v1 = v2;
			v2 = tmp;
		}
		// find index of v1, v2
		int index_v1 = 0;
		int index_v2 = 0;
		for(int i = 0; i < ind.size; i++){
			if(ind.chromosome.get(i).getVertex() == v1){
				index_v1 = i;
			}
			if(ind.chromosome.get(i).getVertex() == v2){
				index_v2 = i;
			}
		}
		// find set children node of v1: because v1 is always afternode
		ArrayList<Integer> setchild_v1 = ind.findChildSet(v1);
		if(index_v1 > index_v2){
			for(int i = 0; i < index_v1-index_v2-1; i++){
//				System.out.println(index_v2+setchild_v1.size()+i+2);
				offIndividual.chromosome.get(index_v2+setchild_v1.size()+i+2).setVertex(ind.chromosome.get(index_v2+i+1).getVertex());
				offIndividual.chromosome.get(index_v2+setchild_v1.size()+i+2).setDepth(ind.chromosome.get(index_v2+i+1).getDepth());
			}
		}else {
			
			for(int i = 0; i <	 index_v2-index_v1 - setchild_v1.size(); i++){
//				System.out.println(index_v2 + "- " + index_v1 + " " + setchild_v1.size() + " " + i);
				offIndividual.chromosome.get(index_v1+i).setVertex(ind.chromosome.get(index_v1+setchild_v1.size()+1+i).getVertex());
				offIndividual.chromosome.get(index_v1+i).setDepth(ind.chromosome.get(index_v1+setchild_v1.size()+1+i).getDepth());
			}
//			Operator.decoding(ind).printTree();
//			System.out.println(ind.findChildSet(v1));
//			ind.printIndividual();
//			System.out.println(v1 + " " + v2);
//			System.err.println(index_v2 + " " + setchild_v1.size());
			index_v2 = index_v2-setchild_v1.size()-1;
			
		}
//		System.err.println(index_v2);
		
		int depth_v2 = offIndividual.chromosome.get(index_v2).getDepth();
		offIndividual.chromosome.get(index_v2+1).setVertex(v1);
		offIndividual.chromosome.get(index_v2+1).setDepth(depth_v2+1);
		for(int i = 0; i < setchild_v1.size() ; i++){
//			System.out.println(index_v2+2+i);
			offIndividual.chromosome.get(index_v2+2+i).setVertex(setchild_v1.get(i));
			offIndividual.chromosome.get(index_v2+2+i).setDepth(ind.chromosome.get(index_v1+1+i).getDepth()+depth_v2+1-ind.chromosome.get(index_v1).getDepth());
		}
		// 
		offIndividual.skill_factor = ind.skill_factor;
//		offIndividual.check();
		return offIndividual;
	}
	static public Individual mutationEPO(Individual ind, int v1, int v2, Graph G){
		if(v1 == -1 && v2 ==-1){
			v1 = Parameter.rand.nextInt(Parameter.numOfSensors) + Parameter.numOfRelays + 1;
			v2 = G.adjList.get(v1).get(Parameter.rand.nextInt(G.adjList.get(v1).size()));
		}
		Tree t_ind = Operator.decoding(ind);
		// check (v1,v2) exists on the input graph
		int v1_a = v1;
		int v2_a = v2;
		if(G.adjList.get(v1).size() > G.adjList.get(v2).size()){
			v1_a = v2;
			v2_a = v1;
		}
		boolean check = false;
		for(int i : G.adjList.get(v1_a)){
			if(i == v2_a){
				check = true;
				break;
			}
		}
		if(!check){
			System.out.println(v1 + "-" + v2 + " does not exists in the input graph");
			return ind;
		}
		// continue decoding
		if(t_ind.checkAncestor(v1, v2)){
			int tmp = v1;
			v1 = v2;
			v2 = tmp;
//			System.out.println("y");
		}
		t_ind.updateTree(v2, v1);
//		t_ind.findChildNode();
		t_ind.findNodeDepth();
		Individual child = new Individual(Operator.enconding(t_ind));
		child.skill_factor = ind.skill_factor;
		return child;
	}
	
	static public Individual crossoverECO(Individual A, Individual B, Graph G){
		Individual offsIndividual = new Individual(A);
		ArrayList<Integer> vr = new ArrayList<Integer>();
		int r = Parameter.rand.nextInt(2*Parameter.numOfVertex + 1) + Parameter.numOfVertex;
				int vr_size = (int)(r/4);
		Vector<Integer> v_set = new Vector<Integer>();
		for(int j = 1; j < Parameter.numOfVertex; j++){
			v_set.add(j);
		}
		int tmp;
		
		for(int i = 1; i < vr_size; i++){
			tmp = v_set.get(Parameter.rand.nextInt(v_set.size()));
			vr.add(tmp);
			v_set.remove((Integer) tmp);
		}
//		vr.add(5);
//		vr.add(6);
//		vr.add(8);
//		vr.add(3);
		int vp_B;
		for(int vr_i: vr){
			vp_B = B.findParent(vr_i);
//			offsIndividual = Operator.mutationEPO(offsIndividual, vr_i, vp_B, G);
			offsIndividual = Operator.mutateEPO(offsIndividual, vr_i, vp_B, G);
		}
		return offsIndividual;
	}
	
	static public void selectionElistism(Population p){
		Collections.sort(p.pop, new Comparator<Individual>() {
			@Override
			 public int compare(Individual a, Individual b){
				return Double.compare(a.getFitness(), b.getFitness());
			}
		});
		if(p.pop.size() > p.size){
			p.pop.subList(0, p.size);
		}
	}
}
