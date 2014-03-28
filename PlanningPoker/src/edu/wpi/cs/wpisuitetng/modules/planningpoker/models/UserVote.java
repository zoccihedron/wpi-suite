package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

public class UserVote {
	
	private String username;
	private int estimate = -1;
	
	public UserVote(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}	
	
}
