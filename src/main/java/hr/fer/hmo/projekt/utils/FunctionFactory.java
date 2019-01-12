package hr.fer.hmo.projekt.utils;

import java.util.HashMap;

import hr.fer.zemris.optjava.dz3.IFunction;

public class FunctionFactory {

	public static IFunction getFunction(long[] students, long[] requests, long[] overlaps, long[] limits, long[] awardActivity, long awardStudents, long minMaxPenalty, final HardConstraints hardConstraints) {
		HashMap<Long, Long> studentActivityCount = new HashMap<>();
		for (int i = 0; i < students.length; i += 5) {
			studentActivityCount.merge(students[i*5], (long) 1, Long::sum);
		}
		
		HashMap<Long, Long> groupsMinPref = new HashMap<>();
		for (int i = 0; i < limits.length; i += 6) {
			groupsMinPref.put(limits[i], limits[i+3]);
		}
		
		HashMap<Long, Long> groupsMaxPref = new HashMap<>();
		for (int i = 0; i < limits.length; i += 6) {
			groupsMaxPref.put(limits[i], limits[i+5]);
		}
		
		return new IFunction() {
			
			/**
			 * Has a number of requested changes for every student.
			 * Key is student id, value is number of changes
			 */
			private HashMap<Long, Long> studentActivityCountFull = studentActivityCount;
			
			/**
			 * Has a number of successful changes for every student.
			 * Key is student id, value is number of changes.
			 * It is filled in pointsA method.
			 */
			private HashMap<Long, Long> studentActivityCountSolution = new HashMap<>();
		
			public double valueAt(long[] solution) {
				return pointsA(solution) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution);
			}
			
			public double fitness(long[] solution) {
				return pointsA(solution) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution) - hardConstraints.penalty(solution);
			}
			
			private long pointsA(long[] solution) {
				studentActivityCountSolution.clear();
				long points = 0;
				long noOfGroups = solution[0];
				
				int j = 0;
				for (int i = (int) (noOfGroups*2 + 1); i < solution.length; i += 4) {
					if (hardConstraints.goodRequest(solution[i], solution[i+1], solution[i+3]) && hardConstraints.noOverlaps(solution[i+2], solution[i+3]) && solution[3] != solution[4]) {
						points += students[j*5 + 2];
						studentActivityCountSolution.merge(solution[i], (long) 1, Long::sum);
					}
					j++;
				}
				
				return points;
			}
			
			private long pointsB(long[] solution) {
				long points = 0;

				for (Long count : studentActivityCountSolution.values()) {
					if (count > awardActivity.length) {
						points += awardActivity[awardActivity.length-1];
					} else if (count > 0) {
						points += awardActivity[(int) (count-1)];
					}
				}

				return points;
			}
			
			private long pointsC(long[] solution) {
//				long points = 0;
				
				return awardStudents * studentActivityCountSolution.entrySet()
						.stream()
						.filter(e -> e.getValue() == studentActivityCountFull.get(e.getKey()))
						.count();
				
//				for (Entry<Long, Long> entry : studentActivityCountSolution.entrySet()) {
//					if (entry.getValue() == studentActivityCountFull.get(entry.getKey())) {
//						
//					}
//				}
			}
			
			private long pointsD(long[] solution) {
				long points = 0;
				long noOfGroups = solution[0];
				
				for (int i = 1; i < noOfGroups*2+1; i++) {
					long minPref = groupsMinPref.get(solution[i]);
					long studentCount = solution[i+1];
					if (studentCount < minPref) {
						points += (minPref - studentCount) * minMaxPenalty;
					}
				}
				
				return points;
			}
			
			private long pointsE(long[] solution) {
				long points = 0;
				long noOfGroups = solution[0];
				
				for (int i = 1; i < noOfGroups*2+1; i++) {
					long maxPref = groupsMaxPref.get(solution[i]);
					long studentCount = solution[i+1];
					if (studentCount > maxPref) {
						points += (studentCount - maxPref) * minMaxPenalty;
					}
				}
				
				return points;
			}
		};
	}
}
