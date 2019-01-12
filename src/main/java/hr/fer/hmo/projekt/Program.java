package hr.fer.hmo.projekt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.hmo.projekt.utils.FunctionFactory;
import hr.fer.hmo.projekt.utils.HardConstraints;
import hr.fer.hmo.projekt.utils.Pair;
import hr.fer.hmo.projekt.utils.StudentGroup;
import hr.fer.zemris.optjava.dz3.IDecoder;
import hr.fer.zemris.optjava.dz3.IFunction;
import hr.fer.zemris.optjava.dz3.INeighborhood;
import hr.fer.zemris.optjava.dz3.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.ITempSchedule;
import hr.fer.zemris.optjava.dz3.Parser;
import hr.fer.zemris.optjava.dz3.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.SingleObjectiveSolution;
import hr.fer.zemris.optjava.dz3.decoders.LongArrayDecoder;
import hr.fer.zemris.optjava.dz3.neighborhoods.LongArrayNeighborhood;
import hr.fer.zemris.optjava.dz3.schedules.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.solutions.LongArraySolution;

public class Program {
	private static final double ALPHA = 0.99;
	private static final double INITIAL_TEMP = 1000;
	private static final int OUTER_LOOP = 1000;
	private static final int INNER_LOOP = 5000;

	public static void main(String[] args) {
		
		if (args.length != 8) {
			System.out.println("Invalid number of input arguments.");
			System.exit(1);
		}
		
		// input arguments
		long time = Long.parseLong(args[0]);
		
		long[] awardActivity = Arrays.stream(args[1].split(",")).mapToLong(Long::valueOf).toArray();
		
		long awardStudent = Long.parseLong(args[2]);
		
		long minMaxPenalty = Long.parseLong(args[3]);
		
		// Checking if arguments are files and initializing arrays
		Path studentsFile = Paths.get(args[4]);
		if (Files.notExists(studentsFile) || !Files.isRegularFile(studentsFile)) {
			System.out.println("Fifth argument must be a file with students.");
			System.exit(1);
		}
		long[] students = Parser.parseFile(studentsFile);
		
		Path requestsFile = Paths.get(args[5]);
		if (Files.notExists(requestsFile) || !Files.isRegularFile(requestsFile)) {
			System.out.println("Sixth argument must be a file with requests.");
			System.exit(1);
		}
		long[] requests = Parser.parseFile(requestsFile);
		
		Path overlapsFile = Paths.get(args[6]);
		if (Files.notExists(overlapsFile) || !Files.isRegularFile(overlapsFile)) {
			System.out.println("Seventh argument must be a file with overlaps.");
			System.exit(1);
		}
		long[] overlaps = Parser.parseFile(overlapsFile);
	
		Path limitsFile = Paths.get(args[7]);
		if (Files.notExists(limitsFile) || !Files.isRegularFile(limitsFile)) {
			System.out.println("Eighth argument must be a file with limits.");
			System.exit(1);
		}
		long[] limits = Parser.parseFile(limitsFile);
		
		HardConstraints hardConstraints = new HardConstraints();
		// fill hard constraints
		List<Pair> groupOverlaps = new ArrayList<>();
		for (int i = 0; i < overlaps.length; i += 2) {
			groupOverlaps.add(new Pair(overlaps[i], overlaps[i+1]));
		}
		
		for (int i = 0; i < students.length; i += 5) {
			for (int j = i+5; j < students.length; j += 5) {
				if (students[i] == students[j]) {
					groupOverlaps.remove(new Pair(students[i+3], students[j+3]));
				}
			}
		}
		
		for (int i = 0; i < requests.length; i += 3) {
			hardConstraints.addRequest(requests[i], requests[i+1], requests[i+2]);
		}
		
		for (int i = 0; i < limits.length; i += 6) {
			hardConstraints.addCountConstraints(limits[i], limits[i+2], limits[i+4]);
		}
		
		IFunction function = FunctionFactory.getFunction(students, requests, overlaps, limits, awardActivity, awardStudent, minMaxPenalty, hardConstraints);
		ITempSchedule tempSchedule = new GeometricTempSchedule(ALPHA, INITIAL_TEMP, INNER_LOOP, OUTER_LOOP);
		LongArraySolution initial = new LongArraySolution();
		INeighborhood<LongArraySolution> neighborhood = new LongArrayNeighborhood();
		IDecoder<LongArraySolution> decoder = new LongArrayDecoder();
		
		IOptAlgorithm<LongArraySolution> alg = new SimulatedAnnealing<LongArraySolution>(
				function,
				false, 
				initial, 
				tempSchedule, 
				neighborhood,
				decoder
		);
		
		alg.run();
	}

}
