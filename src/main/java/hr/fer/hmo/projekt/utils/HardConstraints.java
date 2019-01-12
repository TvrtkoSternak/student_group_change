package hr.fer.hmo.projekt.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class HardConstraints {
	private HashMap<Long, Long> minCountConstraints;
	private HashMap<Long, Long> maxCountConstraints;
	private List<Pair> forbiddenOverlaps;
	private List<Request> requests;
	
	private Pair groupPair;
	private Request request;
	
	public HardConstraints() {
		minCountConstraints = new HashMap<>();
		maxCountConstraints = new HashMap<>();
		forbiddenOverlaps = new LinkedList<>();
		requests = new LinkedList<>();
		
		groupPair = new Pair();
		request = new Request();
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
	
//	public boolean fulfilled(long[] solution) {
//		for (int i = 0; i < solution.length; i++) {
//			if (!changeFulfillsConstraints(solution, i)) {
//				return false;
//			}
//		}
//		return true;
//	}
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
		
		return requests.contains(request);
	}
	
	public long penalty(long[] solution) {
		return 0;
	}
}
