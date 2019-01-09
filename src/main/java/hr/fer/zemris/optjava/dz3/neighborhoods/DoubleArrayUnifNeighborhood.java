package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.INeighborhood;
import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {

	private double[] deltas;
	private Random rand;
	
	public DoubleArrayUnifNeighborhood(double[] deltas) {
		this.deltas = deltas;
		rand = new Random();
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
		DoubleArraySolution neighbor = solution.duplicate();
		double[] values = neighbor.getValues();
		
		for (int i = 0; i < values.length; i++) {
			values[i] += rand.nextDouble() * 2 * deltas[i] - deltas[i];
		}
		
		return neighbor;
	}
	
}
