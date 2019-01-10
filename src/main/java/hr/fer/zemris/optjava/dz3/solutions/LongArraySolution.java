package hr.fer.zemris.optjava.dz3.solutions;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.SingleObjectiveSolution;

public class LongArraySolution extends SingleObjectiveSolution {

	private long[] values;
	
	public long[] getValues() {
		return values;
	}

	public void setValues(long[] values) {
		this.values = values;
	}

	public LongArraySolution(int size) {
		values = new long[size];
	}
	
	public LongArraySolution(long[] values) {
		this.values = values;
	}
	
	public LongArraySolution newLikeThis() {
		return new LongArraySolution(values.length);
	}
	
	public LongArraySolution duplicate() {
		return new LongArraySolution((long[])values.clone());
	}
	
	public void randomize(Random rand, long[] min, long[] max) {
		for (int i = 0; i < values.length; i++) {
			values[i] = (long) (min[i] + (max[i] - min[i]) * rand.nextDouble());
		}
	}
	
	public void randomize(Random rand, long min, long max) {
		for (int i = 0; i < values.length; i++) {
			values[i] = (long) (min + (max - min) * rand.nextDouble());
		}
	}
	
	public double get(int index) {
		return values[index];
	}
	
	public void set(int index, long value) {
		values[index] = value;
	}
}
