package hr.fer.zemris.optjava.dz3;

import java.util.Random;

public class SimulatedAnnealing<T extends SingleObjectiveSolution> implements IOptAlgorithm<T> {

	private IFunction function;
	private T startingSolution;
	private ITempSchedule schedule;
	private INeighborhood<T> neighborhoodGenerator;
	private IDecoder<T> decoder;
	private boolean minimize;
	
	public SimulatedAnnealing(IFunction function, boolean minimize, T startingSolution, 
			ITempSchedule schedule, INeighborhood<T> neighborhoodGenerator, IDecoder<T> decoder) {
		this.function = function;
		this.minimize = minimize;
		this.startingSolution = startingSolution;
		this.schedule = schedule;
		this.neighborhoodGenerator = neighborhoodGenerator;
		this.decoder = decoder;
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		
		T solution = startingSolution;
		solution.setFitness(function.valueAt(decoder.decode(solution)));

		for (int i = 0; i < schedule.getOuterLoopCounter(); i++) {
			double temp = schedule.getNextTemperature();
			for (int j = 0; j < schedule.getInnerLoopCounter(); j++) {
				T neighbor = neighborhoodGenerator.randomNeighbor(solution);
				neighbor.setFitness(function.valueAt(decoder.decode(neighbor)));
				
				solution = accept(solution, neighbor, temp, rand) ? neighbor : solution;
			}
			StringBuilder sb = new StringBuilder();
			for (double d : decoder.decode(solution)) {
				sb.append(d + ", ");
			}
			System.out.println(i + ". " + sb.toString() + " = " + function.valueAt(decoder.decode(solution)) + "; T=" + temp);
		}
	}
	
	private boolean accept(T current, T neighbor, double temp, Random rand) {
		double delta = neighbor.getFitness() - current.getFitness();
		
		if (!minimize) {
			delta *= -1;
		}
		
		if (delta <= 0) {
			return true;
		} else {
			return rand.nextDouble() < Math.exp(-delta / temp);
		}
		
	}

}
