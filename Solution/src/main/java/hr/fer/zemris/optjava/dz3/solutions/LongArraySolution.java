package hr.fer.zemris.optjava.dz3.solutions;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

import hr.fer.zemris.optjava.dz3.SingleObjectiveSolution;

public class LongArraySolution extends SingleObjectiveSolution {

	private long[] values;
	private int size;
	
	public long[] getValues() {
		return values;
	}

	public void setValues(long[] values) {
		this.values = values;
	}
	
	public int size() {
		return size;
	}

	public LongArraySolution(int size) {
		values = new long[size];
		this.size = size;
	}
	
	public LongArraySolution(long[] values) {
		this.values = values;
		this.size = values.length;
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
	
	public long get(int index) {
		return values[index];
	}
	
	public void set(int index, long value) {
		values[index] = value;
	}
	
	public void swap(int index) {
		long val = values[index];
		values[index] = (val == 1) ? 0 : 1;
	}
	
	@Override
	public String toString() {
		StringJoiner sj = new StringJoiner(" ");
		Arrays.stream(values).forEach(x -> sj.add(String.valueOf(x)));
		return sj.toString();
	}
}
