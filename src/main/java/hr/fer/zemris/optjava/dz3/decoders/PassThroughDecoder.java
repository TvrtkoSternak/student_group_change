package hr.fer.zemris.optjava.dz3.decoders;

import hr.fer.zemris.optjava.dz3.IDecoder;
import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;
import hr.fer.zemris.optjava.dz3.solutions.LongArraySolution;

public class PassThroughDecoder implements IDecoder<LongArraySolution>{

	public PassThroughDecoder() {
	}

	@Override
	public long[] decode(LongArraySolution solution) {
		return solution.getValues();
	}

	@Override
	public void decode(LongArraySolution solution, long[] decoded) {
		long[] values = solution.getValues();
		
		for (int i = 0; i < values.length; i++) {
			decoded[i] = values[i];
		}
	}
	
}
