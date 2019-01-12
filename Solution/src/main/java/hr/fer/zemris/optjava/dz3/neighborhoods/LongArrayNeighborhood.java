package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.concurrent.ThreadLocalRandom;

import hr.fer.zemris.optjava.dz3.INeighborhood;
import hr.fer.zemris.optjava.dz3.solutions.LongArraySolution;

public class LongArrayNeighborhood implements INeighborhood<LongArraySolution> {
	
	@Override
	public LongArraySolution randomNeighbor(LongArraySolution solution) {
		LongArraySolution neighbor = solution.duplicate();
				
		neighbor.swap(ThreadLocalRandom.current().nextInt(neighbor.size()));
		neighbor.swap(ThreadLocalRandom.current().nextInt(neighbor.size()));
		neighbor.swap(ThreadLocalRandom.current().nextInt(neighbor.size()));

		return neighbor;
	}
	
}
