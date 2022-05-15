package NDE;

import java.util.ArrayList;

public class Population {
	public ArrayList<Individual> pop;
	public int size = Parameter.population_size;
	
	public Population(){
		this.pop = new ArrayList<Individual>();
	}
	public void init(Graph G, int task){
		int i = 0;
		while(i < this.size){
			Individual a = new Individual();
			a.init(G, task);
			if(a.isValid()){
				i++;
				Task.eval(G, a);
				this.pop.add(a);
			}
		}
	}
}
