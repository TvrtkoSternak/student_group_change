package hr.fer.zemris.optjava.dz3.decoders;

import java.util.HashMap;
import java.util.Map.Entry;

import hr.fer.zemris.optjava.dz3.IDecoder;
import hr.fer.zemris.optjava.dz3.solutions.LongArraySolution;

public class LongArrayDecoder implements IDecoder<LongArraySolution> {
	private long[] students;
	private long[] requests;
	private HashMap<Long, Long> groupCount;
	private HashMap<String, Long> studentGroups;
	private int size;
	
	public LongArrayDecoder(long[] students, long[] requests, long[] limits) {
		this.students = students;
		this.requests = requests;
		groupCount = new HashMap<>();
		for (int i = 0; i < limits.length; i += 6) {
			groupCount.put(limits[i], limits[i+1]);
		}
		studentGroups = new HashMap<>();
		for (int i = 0; i < students.length; i += 5) {
			studentGroups.put(students[i]+","+students[i+1], students[i+3]);
		}
		
		size = students.length - students.length/5 + groupCount.size()*2 + 1;
	}

	@Override
	public long[] decode(LongArraySolution solution) {
		HashMap<Long, Long> groupCountCopy = (HashMap<Long, Long>) groupCount.clone();
		HashMap<String, Long> changes = new HashMap<>();
		
		long[] decoded = new long[size];
		
		long[] values = solution.getValues();
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] == 1) {
				long studentId = requests[i*3];
				long activityId = requests[i*3+1];
				long requestedGroupId = requests[i*3+2];
				
				long startingGroupId = studentGroups.get(studentId+","+activityId);
				
				changes.put(studentId+","+activityId, (changes.get(studentId+","+activityId) != null) ? -1 : requestedGroupId);
				
				groupCountCopy.replace(requestedGroupId, groupCountCopy.get(requestedGroupId)+1);
				groupCountCopy.replace(startingGroupId, groupCountCopy.get(startingGroupId)-1);
			}
		}
		
		decoded[0] = groupCountCopy.size();
		int i = 1;
		for (Entry<Long, Long> groupCount : groupCountCopy.entrySet()) {
			decoded[i++] = groupCount.getKey();
			decoded[i++] = groupCount.getValue();
		}
		
		for (int j = 0; j < students.length; j += 5) {
			Long newGroupId = changes.get(students[j]+","+students[j+1]);
			decoded[i++] = students[j];
			decoded[i++] = students[j+1];
			decoded[i++] = students[j+3];
			
			decoded[i++] = (newGroupId==null) ? students[j+3] : newGroupId;
		}
		
		return decoded;
	}

	@Override
	public void decode(LongArraySolution solution, long[] decoded) {
		throw new RuntimeException("Not implemented");		
	}
	
}
