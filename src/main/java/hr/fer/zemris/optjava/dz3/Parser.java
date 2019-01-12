package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Parser {
	
	public static long[] parseFile(Path filePath) throws IOException {
		List<long[]> list = Files.readAllLines(filePath)
			.stream()
			.skip(1)
			.map(line -> toLongArray(line.split(",")))
			.collect(Collectors.toList());
		
		int size = list.get(0).length;
		long[] array = new long[size * list.size()];
		for (int i = 0; i < list.size(); i++) {
			concat(array, list.get(i), i*size);
		}
		
		return array;
	}
	
	private static void concat(long[] array, long[] concatArray, int startingIndex) {
		for (int i = 0; i < concatArray.length; i++) {
			array[startingIndex + i] = concatArray[i];
		}
	}

	public static long[] toLongArray(String[] array) {
		return Arrays.stream(array)
				.mapToLong(Long::parseLong)
				.toArray();
	}
	
}
