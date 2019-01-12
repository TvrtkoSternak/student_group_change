package hr.fer.hmo.projekt.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HardConstraints {
	private HashMap<Long, Long> minCountConstraints;
	private HashMap<Long, Long> maxCountConstraints;
	private List<Pair> forbiddenOverlaps;
	private List<Request> requests;
	
	private HashMap<Long, List<Long>> studentsGroups;
	
	private Pair groupPair;
	private Request request;
	
	public HardConstraints() {
		minCountConstraints = new HashMap<>();
		maxCountConstraints = new HashMap<>();
		forbiddenOverlaps = new LinkedList<>();
		requests = new LinkedList<>();
		
		groupPair = new Pair();
		request = new Request();
		
		studentsGroups = new HashMap<>();
	}
	
	public void addCountConstraints(long groupId, long min, long max) {
		minCountConstraints.put(groupId, min);
		maxCountConstraints.put(groupId, max);
	}
	
	public void addForbiddenOverlapsConstraints(long group1, long group2) {
		forbiddenOverlaps.add(new Pair(group1, group2));
	}
	
	public void addRequest(long studentId, long activityId, long requestedGroupId) {
		requests.add(new Request(studentId, activityId, requestedGroupId));
	}
	
	public boolean fulfilled(long[] solution) {
		studentsGroups.clear();
		
		long noOfGroups = solution[0];
		
		for (int i = 1; i < noOfGroups*2 + 1; i += 2) {
			if (minCountConstraints.get(solution[i]) > solution[i+1] || maxCountConstraints.get(solution[i]) < solution[i+1]) {
				return false;
			}
		}
		
//		System.out.println("Prošao prvi");
		
		for (int i = (int) (noOfGroups*2 + 1); i < solution.length; i += 4) {
			if (!goodRequest(solution[i], solution[i+1], solution[i+3])) {
				return false;
			}
						
			List<Long> groups = studentsGroups.get(solution[i]);
			if (groups == null) {
				groups = new ArrayList<>();
				studentsGroups.put(solution[i], groups);
			}
			groups.add(solution[i+3]);
		}
		
//		System.out.println("Prošao drugi");
		
		// dodaj dvije grupe 
		for (List<Long> groups : studentsGroups.values()) {
			for (int i = 0; i < groups.size(); i++) {
				for (int j = i+1; j < groups.size(); j++) {
					if (!noOverlaps(groups.get(i), groups.get(j))) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
//	
//	private boolean changeFulfillsConstraints(long[] solution, int i) {
//		
//	}
//	
//	private boolean fulfilCountConstraints(long groupId, long studentsCount) {
//		return minCountConstraints.get(groupId) <= studentsCount 
//				&& maxCountConstraints.get(groupId) >= studentsCount;
//	}
	
	public boolean noOverlaps(long startingGroupId, long newGroupId) {
		groupPair.setVar1(startingGroupId);
		groupPair.setVar2(newGroupId);
		
		return !forbiddenOverlaps.contains(groupPair);
	}
	
	public boolean goodRequest(long studentId, long activityId, long requestedGroupId) {
		request.studentId = studentId;
		request.activityId = activityId;
		request.requestedGroupId = requestedGroupId;
		
		return requestedGroupId == -1 ? false : requests.contains(request);		
	}
	
	public long penalty(long[] solution) {
		return 0;
	}
}
