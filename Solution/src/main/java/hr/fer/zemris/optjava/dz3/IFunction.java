package hr.fer.zemris.optjava.dz3;

public interface IFunction {

	long fitness(long[] point);
	
	long valueAt(long[] point);
}
