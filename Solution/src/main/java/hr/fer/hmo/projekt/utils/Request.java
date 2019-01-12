package hr.fer.hmo.projekt.utils;

public class Request {
	public long studentId;
	public long activityId;
	public long requestedGroupId;
	
	public Request() {
	}

	public Request(long studentId, long activityId, long requestedGroupId) {
		this.studentId = studentId;
		this.activityId = activityId;
		this.requestedGroupId = requestedGroupId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (activityId ^ (activityId >>> 32));
		result = prime * result + (int) (requestedGroupId ^ (requestedGroupId >>> 32));
		result = prime * result + (int) (studentId ^ (studentId >>> 32));
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Request))
			return false;
		Request other = (Request) obj;
		if (activityId != other.activityId)
			return false;
		if (requestedGroupId != other.requestedGroupId)
			return false;
		if (studentId != other.studentId)
			return false;
		return true;
	}
}
