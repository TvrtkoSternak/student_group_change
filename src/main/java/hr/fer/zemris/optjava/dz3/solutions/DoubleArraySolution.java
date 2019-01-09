package hr.fer.zemris.optjava.dz3.solutions;

import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

import hr.fer.zemris.optjava.dz3.SingleObjectiveSolution;

public class DoubleArraySolution extends SingleObjectiveSolution {

	private double[] values;
	
	public double[] getValues() {
		return values;
	}

	public void setValues(double[] values) {
		this.values = values;
	}

	public DoubleArraySolution(int size) {
		values = new double[size];
	}
	
	public DoubleArraySolution(double[] values) {
		this.values = values;
	}
	
	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}
	
	public DoubleArraySolution duplicate() {
		return new DoubleArraySolution(Arrays.copyOf(values, values.length));
	}
	
	public void randomize(Random rand, double[] min, double[] max) {
		for (int i = 0; i < values.length; i++) {
			values[i] = min[i] + (max[i] - min[i]) * rand.nextDouble();
		}
	}
	
	public void randomize(Random rand, double min, double max) {
		for (int i = 0; i < values.length; i++) {
			values[i] = min + (max - min) * rand.nextDouble();
		}
	}
	
	public double get(int index) {
		return values[index];
	}
	
	public void set(int index, double value) {
		values[index] = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		
		StringJoiner sj = new StringJoiner(",");
		Arrays.stream(values)
			.forEach(v -> sj.add(String.valueOf(v)));
		sb.append(sj.toString());
		
		sb.append("]");
		
		return sb.toString();
	}
}
