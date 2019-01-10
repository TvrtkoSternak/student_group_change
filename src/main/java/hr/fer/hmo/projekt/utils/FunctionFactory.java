package hr.fer.hmo.projekt.utils;

import hr.fer.zemris.optjava.dz3.IFunction;

public class FunctionFactory {

	public static IFunction getFunction(long[] students, long[] requests, long[] overlaps, long[] limits, long[] awardActivity, long awardStudents, long minMaxPenalty, final HardConstraints hardConstraints) {
		HashMap<Long, Long> studentActivityCount = new HashMap<>();
		for (int i = 0; i < students.length; i += 5) {
			studentActivityCount.merge(students[i*5], 1, Integer::sum);
		}
		
		return new IFunction() {
			
			private HashMap<Long, Long> studentActivityCountFull = studentActivityCount;
			private HashMap<Long, Long> studentActivityCountSolution = new HashMap<>();
		
			public double valueAt(long[] solution) {
				return pointsA(solution, students) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution);
			}
			
			public double fitness(long[] solution) {
				return pointsA(solution, students) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution) - hardConstraints.penalty(solution);
			}
			
			private long pointsA(long[] solution, long[] students) {
				studentActivityCountSolution.clear();
				long sum = 0;
				int j = 0;
				for (int i = 0; i < solution.length; i += 6) {
					if (hardConstraints.hasRequest() && hardConstraints.noOverlaps() && solution[3] != solution[4]) {
						sum += students[j*5 + 2];
						studentActivityCountSolution.merge(solution[i], 1, Integer::sum);
					}
					j++;
				}
			}
			
			private long pointsB(long[] solution) {
				int size = awardActivity.length;
				studentActivityCountSolution.entrySet()
					.map((k,v) -> studentActivityCountFull()
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
