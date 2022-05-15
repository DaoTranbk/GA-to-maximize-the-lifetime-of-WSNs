package NDE;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Random;

public class GA {
	public void run(Graph G, int task, int seed, double[] res, long[] time){
		long start =  System.currentTimeMillis();
		Parameter.rand = new Random(seed);
		
		int gen = 0;
		Population p = new Population();
		p.init(G, task);
		while(gen < Parameter.generation){
			ArrayList<Individual> offSpring = new ArrayList<Individual>();
			while(offSpring.size() < Parameter.population_size){
				int index_p1, index_p2;
				index_p1 = Parameter.rand.nextInt(p.size);
				do{
					index_p2 = Parameter.rand.nextInt(p.size);
				}while(index_p2 == index_p1);
				Individual p1 = p.pop.get(index_p1);
				Individual p2 = p.pop.get(index_p2);
				double r = Parameter.rand.nextDouble();
				
				if(r < Parameter.p_c){
					Individual fa = Operator.crossoverECO(p1, p2, G);
					Individual fb = Operator.crossoverECO(p2, p1, G);
					if(fa.isValid()){
						offSpring.add(fa);
					}
					if(fb.isValid()){
						offSpring.add(fb);
					}
					if(r < Parameter.p_m){
						Individual faa = Operator.mutateEPO(fa, -1, -1, G);
						Individual fbb = Operator.mutateEPO(fb, -1, -1, G);
						if(faa.isValid()){
							offSpring.add(faa);
						}
						if(fbb.isValid()){
							offSpring.add(fbb);
						}
					}
					
				}
			}
			
			for(Individual child: offSpring){
				Task.eval(G, child);
			}
			p.pop.addAll(offSpring);
			Operator.selectionElistism(p);
			System.out.println(gen + ": " + p.pop.get(0).getFitness());
			res[gen] = p.pop.get(0).getFitness();
			gen++;
		}
		p.pop.get(0).printIndividual();
		long end =  System.currentTimeMillis();
		time[seed] = end - start;
		System.out.println("Seed " + seed + ": " + p.pop.get(0).getFitness() + " " + Long.toString(end-start));
	}
}
