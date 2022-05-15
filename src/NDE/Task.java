package NDE;

public class Task {
	static public void eval(Graph G, Individual ind){
		if(!ind.isValid()){
			ind.setFitness(Double.POSITIVE_INFINITY);
//			System.out.println("ok");
		}
		else {
			double maxEnergyConsumption = 0;
			double energy_consumption_of_anode = 0;
			double E_t = 0;
			int parent_node;
			double d;
			if(ind.skill_factor == 1){
				for(int i = 1; i < G.numOfVertex; i++){
					parent_node = ind.findParent(i);
					d = G.distance[i][parent_node];
					E_t = Parameter.k_bit * (Parameter.e_lec + Parameter.e_fs * d * d);
//					if (Parameter.d_0 <= d && d <= Parameter.radius) {
//						E_t = Parameter.k_bit * (Parameter.e_lec + Parameter.e_mp * d * d * d * d);
//					}
//						if(2*Parameter.radius < d){
//							System.out.println(d);
//							E_t = Double.POSITIVE_INFINITY;
//						}
					energy_consumption_of_anode = ind.findDirectChildSet(i).size()*(Parameter.E_r + Parameter.E_da) + E_t;
					if(i <= Parameter.numOfRelays){
						if(ind.findChildSet(i).size() == 0){
							energy_consumption_of_anode = -Double.POSITIVE_INFINITY;
						}
					}
					else{
						energy_consumption_of_anode += Parameter.E_da;
					}
					maxEnergyConsumption = Math.max(maxEnergyConsumption, energy_consumption_of_anode);
				}
				ind.setFitness(maxEnergyConsumption);
			}
			if(ind.skill_factor == 2){
				double E_relay = 0;
				double E_sensor = 0;
				for(int i = 1; i < G.numOfVertex; i++){
					if(i <= Parameter.numOfRelays){
						if(ind.findChildSet(i).size() == 0){
							E_relay = -Double.POSITIVE_INFINITY;
						}
						else{
							parent_node = ind.findParent(i);
							d = G.distance[i][parent_node];
							E_relay = Parameter.k_bit*(ind.findDirectChildSet(i).size()*(Parameter.e_lec+Parameter.e_da)+Parameter.e_mp*d*d*d*d);
						}
						maxEnergyConsumption = Math.max(maxEnergyConsumption, E_relay);
					}
					else{
						parent_node = ind.findParent(i);
						d = G.distance[i][parent_node];
						E_sensor = Parameter.k_bit*(Parameter.e_lec+Parameter.e_fs*d*d);
						maxEnergyConsumption = Math.max(maxEnergyConsumption, E_sensor);
					}
				}
//				System.out.println(maxEnergyConsumption);
				ind.setFitness(maxEnergyConsumption);
			}
		}
	}
}
