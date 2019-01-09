package hr.fer.zemris.optjava.dz3.decoders;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.IDecoder;
import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {

	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits;

	public BitVectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		this.mins = mins;
		this.maxs = maxs;
		this.bits = bits;
		this.n = n;
		this.totalBits = Arrays.stream(bits).sum();
	}
	
	public BitVectorDecoder(double min, double max, int bit, int n) {
		this.n = n;
		this.mins = new double[n];
		Arrays.fill(mins, min);
		this.maxs = new double[n];
		Arrays.fill(maxs, max);
		this.bits = new int[n];
		Arrays.fill(bits, bit);
		this.totalBits = bit * n;
	}


	int getTotalBits() {
		return totalBits;
	}
	
	int getDimensions() {
		return n;
	}
	
	@Override
	public abstract double[] decode(BitVectorSolution solution);

	@Override
	public void decode(BitVectorSolution solution, double[] decoded) {
		double[] values = decode(solution);
		
		for (int i = 0; i < decoded.length; i++) {
			decoded[i] = values[i];
		}
	}	
	
}
