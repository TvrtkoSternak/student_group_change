package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.INeighborhood;
import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution>{

	private double[] deltas;
	private Random rand;
	
	public DoubleArrayNormNeighborhood(double[] deltas) {
		this.deltas = deltas;
		rand = new Random();
	}
	
	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution solution) {
		DoubleArraySolution neighbor = solution.duplicate();
		double[] values = neighbor.getValues();
		
//		int i = rand.nextInt(values.length);
//		values[i] += rand.nextGaussian() * deltas[i];
		for (int i = 0; i < values.length; i++) {
			values[i] += rand.nextGaussian() * deltas[i];
		}
		
		return neighbor;
	}

}
