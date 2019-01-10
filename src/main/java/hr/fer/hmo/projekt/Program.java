package hr.fer.hmo.projekt;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.hmo.projekt.utils.StudentGroup;
import hr.fer.zemris.optjava.dz3.IFunction;
import hr.fer.zemris.optjava.dz3.Parser;

public class Program {

	public static void main(String[] args) {
		
		if (args.length != 8) {
			System.out.println("Invalid number of input arguments.");
			System.exit(1);
		}
		
		// Checking if arguments are files and initializing arrays
		Path studentsFile = Paths.get(args[4]);
		if (Files.notExists(studentsFile) || !Files.isRegularFile(studentsFile)) {
			System.out.println("Fifth argument must be a file with students.");
			System.exit(1);
		}
		long[] students = Parser.parseStudentsFile(studentsFile);
		
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
		
		
		IFunction f = new IFunction() {
			private StudentGroup studentGroup = new StudentGroup();
			
			public double valueAt(long[] point) {
				
				for (int i = 0; i < point.length; i += 5) {
					studentGroup.studentId = point[i];
					studentGroup.activityId = point[i+1];
					studentGroup.startingGroupId = point[i+2];
					
					
				}
			}
		};
	}

}
