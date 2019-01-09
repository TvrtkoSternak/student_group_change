package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.INeighborhood;
import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public class BitVectorNeighborhood implements INeighborhood<BitVectorSolution> {

	private Random rand;
	private int[] bits;
	int n;
	
	public BitVectorNeighborhood(int[] bits, int n) {
		this.bits = bits;
		this.n = n;
		rand = new Random();
	}
	
	public BitVectorNeighborhood(int bit, int n) {
		this.bits = new int[n];
		this.n = n;
		Arrays.fill(bits, bit);
		rand = new Random();
	}
	
	@Override
	public BitVectorSolution randomNeighbor(BitVectorSolution solution) {
		BitVectorSolution neighbor = solution.duplicate();
				
		neighbor.flip(rand.nextInt(n * bits[0]));
		
		return neighbor;
	}

}
