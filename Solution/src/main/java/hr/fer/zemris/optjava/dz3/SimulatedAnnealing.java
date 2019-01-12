package hr.fer.zemris.optjava.dz3;

import java.util.Random;
import java.util.StringJoiner;

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
		solution.setFitness(function.fitness(decoder.decode(solution)));

		for (int i = 0; i < schedule.getOuterLoopCounter(); i++) {
			double temp = schedule.getNextTemperature();
			for (int j = 0; j < schedule.getInnerLoopCounter(); j++) {
				T neighbor = neighborhoodGenerator.randomNeighbor(solution);
				
				long fitness = function.fitness(decoder.decode(neighbor));
				
				neighbor.setFitness(fitness);
				
				solution = accept(solution, neighbor, temp, rand) ? neighbor : solution;
				
				System.out.println(j + ". = " + fitness + "; T=" + temp);

//				System.out.println(j + " = " + fitness + "; T=" + temp);
			}
//			StringJoiner sb = new StringJoiner(",");
//			for (double d : decoder.decode(solution)) {
//				sb.add(String.valueOf(d));
//			}
//			System.out.println(solution);
//			if (solution.toString().split(" ")[0].equals(solution.toString().split(" ")[1]) && solution.toString().split(" ")[0].equals("1")) {
//				System.out.println("Double jump");
//				System.exit(1);
//			}
			System.out.println(i + ". = " + function.fitness(decoder.decode(solution)) + "; T=" + temp);
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
