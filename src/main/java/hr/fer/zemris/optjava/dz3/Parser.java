package hr.fer.zemris.optjava.dz3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {

	private static final String COMMENT_SIGN = "#";
	private static final String NUMBER_DELIMITER = ",\\s+";
	
	public static IFunction parse(List<String> lines) {
		
		return new IFunction() {
			
			@Override
			public double valueAt(double[] point) {
				return lines.stream()
						.filter(l -> !l.startsWith(COMMENT_SIGN))
						.map(l -> l.substring(1, l.length()-1))
						.map(l -> toDoubleArray(l.split(NUMBER_DELIMITER)))
						.mapToDouble(data -> lineToDoubleValue(data, point))
						.sum();
			}
			
		};
		
	}
	
	public static List<Long[]> parseFile(Path filePath) {
		Files.readAllLines(filePath)
			.stream()
			.skip(1)
			.map(toLongArray(line -> line.split(",")));
	}
	
	public static long[] toLongArray(String[] array) {
		return Arrays.stream(array)
				.mapToLong(Long::parseLong)
				.toArray();
	}
	
	private static double[] toDoubleArray(String[] array) {
		return Arrays.stream(array)
				.mapToDouble(Double::parseDouble)
				.toArray();
	}
	
	private static double lineToDoubleValue(double[] data, double[] point) {
		double value = -data[5]
			+ point[0] * data[0]
			+ point[1] * data[0]*data[0]*data[0] * data[1]
			+ point[2] * Math.pow(Math.E, point[3] * data[2]) * (1 + Math.cos(point[4]*data[3]))
			+ point[5] * data[3] * data[4]*data[4];
		
		return value * value;
	}
}
