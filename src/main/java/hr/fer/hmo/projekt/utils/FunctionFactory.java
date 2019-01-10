package hr.fer.hmo.projekt.utils;

import hr.fer.zemris.optjava.dz3.IFunction;

public class FunctionFactory {

	public static IFunction getFunction(long[] students, long[] requests, long[] overlaps, long[] limits, final HardConstraints hardConstraints) {
		
		return new IFunction() {
			
			
			public double valueAt(long[] solution) {
				if (hardConstraints.fulfilled(solution)) {
					return pointsA(solution) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution);
				}
				return -1;
			}
			
			private long pointsA(long[] solution) {
				for (int i = 0; i < solution.length; i += 6) {
					if ()
				}
			}
			
			private long pointsB(long[] solution) {
				
			}
			
			private long pointsC(long[] solution) {
				
			}
			
			private long pointsD(long[] solution) {
				
			}
			
			private long pointsE(long[] solution) {
				
			}
		};
	}
}
