package hr.fer.hmo.projekt.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import hr.fer.zemris.optjava.dz3.IFunction;

public class FunctionFactory {

	public static IFunction getFunction(long[] students, long[] requests, long[] overlaps, long[] limits, long[] awardActivity, long awardStudents, long minMaxPenalty, final HardConstraints hardConstraints) {
		HashMap<Long, Long> studentActivityCount = new HashMap<>();
		HashMap<String, Long> studentActivityCountTemp = new HashMap<>();
		
		for (int i = 0; i < requests.length; i += 3) {
			studentActivityCountTemp.put(requests[i]+","+requests[i+1], (long) 1);
		}
		
		for (Entry<String, Long> entry : studentActivityCountTemp.entrySet()) {
			long key = Long.valueOf(entry.getKey().split(",")[0]);
			studentActivityCount.merge(key, (long) 1, Long::sum);
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
		
			public long valueAt(long[] solution) {
				return pointsA(solution) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution);
			}
			
			public long fitness(long[] solution) {
				if (!hardConstraints.fulfilled(solution)) {
					return Long.MIN_VALUE;
				}
				return pointsA(solution) + pointsB(solution) + pointsC(solution) - pointsD(solution) - pointsE(solution);
			}
			
			private long pointsA(long[] solution) {
				studentActivityCountSolution.clear();
				long points = 0;
				long noOfGroups = solution[0];
				
				int j = 0;
								
				for (int i = (int) (noOfGroups*2 + 1); i < solution.length; i += 4) {
					if (solution[i+2] != solution[i+3]) {
						points += students[j*5 + 2];
						studentActivityCountSolution.merge(solution[i], (long) 1, Long::sum);
					}
					j++;
				}
				
//				Arrays.stream(solution).forEach(x -> System.out.print(x + " "));
//				System.out.println();
//				System.out.println("Points A: " + points);
				
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

//				System.out.println("Points B: " + points);
				
				return points;
			}
			
			private long pointsC(long[] solution) {
//				long points = 0;
				
				long points = awardStudents * studentActivityCountSolution.entrySet()
						.stream()
						.filter(e -> e.getValue() == studentActivityCountFull.get(e.getKey()))
						.count();
				
//				System.out.println("Points C: " + points);

				return points;
//				for (Entry<Long, Long> entry : studentActivityCountSolution.entrySet()) {
//					if (entry.getValue() == studentActivityCountFull.get(entry.getKey())) {
//						
//					}
//				}
			}
			
			private long pointsD(long[] solution) {
				long points = 0;
				long noOfGroups = solution[0];
				
				for (int i = 1; i < noOfGroups*2+1; i += 2) {
					long minPref = groupsMinPref.get(solution[i]);
					long studentCount = solution[i+1];
					if (studentCount < minPref) {
						points += (minPref - studentCount) * minMaxPenalty;
					}
				}
				
//				System.out.println("Points D: " + points);

				
				return points;
			}
			
			private long pointsE(long[] solution) {
				long points = 0;
				long noOfGroups = solution[0];
				
				for (int i = 1; i < noOfGroups*2+1; i += 2) {
					long maxPref = groupsMaxPref.get(solution[i]);
					long studentCount = solution[i+1];
					if (studentCount > maxPref) {
						points += (studentCount - maxPref) * minMaxPenalty;
					}
				}
				
//				System.out.println("Points E: " + points);

				return points;
			}
		};
	}
}
