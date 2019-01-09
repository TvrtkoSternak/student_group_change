package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.decoders.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoders.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.neighborhoods.BitVectorNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhoods.DoubleArrayNormNeighborhood;
import hr.fer.zemris.optjava.dz3.schedules.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class RegresijaSustava {

	private static final double ALPHA = 0.99;
	private static final double INITIAL_TEMP = 1000;
	private static final int OUTER_LOOP = 1000;
	private static final int INNER_LOOP = 5000;
	
	private static final double[] DELTAS = new double[] { 0.02, 0.02, 0.02, 0.02, 0.02, 0.02 };
	private static final int MIN = -10;
	private static final int MAX = 10;
	
	private static final int MIN_BIT_COUNT = 5;
	private static final int MAX_BIT_COUNT = 30;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println(
					"Invalid number of arguments. "
					+ "You must provide path to the file with data and the type of algorithm."
			);
			return;
		}
		
		Path inputFile = Paths.get(args[0]);
		
		IFunction f = null;
		try {
			f = Parser.parse(Files.readAllLines(inputFile));
		} catch (IOException e) {
			System.out.println("Error while reading data.");
			return;
		}
		
		IOptAlgorithm<SingleObjectiveSolution> algorithm = null;
		
		ITempSchedule schedule = new GeometricTempSchedule(ALPHA, INITIAL_TEMP, INNER_LOOP, OUTER_LOOP);

		if (args[1].equals("decimal")) {			
			DoubleArraySolution solution = new DoubleArraySolution(6);
			solution.randomize(new Random(), MIN, MAX);
			algorithm = new SimulatedAnnealing(
					f, 
					true, 
					solution,
					schedule,
					new DoubleArrayNormNeighborhood(DELTAS), 
					new PassThroughDecoder()
			);
		} else if (args[1].matches("binary:\\d{1,2}")) {
			int nOfBits = Integer.parseInt(args[1].substring(7));
			if (nOfBits < MIN_BIT_COUNT || nOfBits > MAX_BIT_COUNT) {
				System.out.println("Invalid bit count.");
				return;
			}
			
			BitVectorSolution solution = new BitVectorSolution(6 * nOfBits);
			solution.randomize(new Random());
			
			algorithm = new SimulatedAnnealing(
					f, 
					true, 
					solution, 
					schedule,
					new BitVectorNeighborhood(nOfBits, 6),
					new NaturalBinaryDecoder(MIN, MAX, nOfBits, 6)
			);
		} else {
			System.out.println("Invalid method of solving.");
			return;
		}
		
		algorithm.run();
		
	}
}
