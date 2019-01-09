package hr.fer.zemris.optjava.dz3.decoders;

import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public class NaturalBinaryDecoder extends BitVectorDecoder {

	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
	}
	
	public NaturalBinaryDecoder(double min, double max, int bit, int n) {
		super(min, max, bit, n);
	}

	@Override
	public double[] decode(BitVectorSolution solution) {
		double[] values = new double[n];
		
		int sum = 0;
		int size = bits[0];
		int count = 0;
		int j = 0;
		
		for (int i = 0; i < totalBits; i++) {
			sum *= 2;
			byte bit = solution.get(i);
			if (bit!=0) {
				sum++;
			}
			
			count++;
			
			if (count == size) {
				values[j] = mins[j] + sum * (maxs[j] - mins[j]) / ((double)(Math.pow(2, bits[j])) - 1);
				
				if (++j >= bits.length) {
					break;
				}
				
				sum = 0;
				count = 0;
				size = bits[j];
				
			}
			
		}
		
		return values;

	}

}
