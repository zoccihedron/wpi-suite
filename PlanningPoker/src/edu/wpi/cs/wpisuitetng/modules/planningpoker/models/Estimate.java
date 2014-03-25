package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.HashMap;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;


public class Estimate extends AbstractModel{
	
	private String requirement;
	HashMap<User,Integer> userWithEstimate;
	
	public Estimate(){
		requirement = null;
		userWithEstimate = new HashMap<User,Integer>();
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	
	/**
	 * 
	 * Add user to the hashMap of this estimate by creating a new
	 * pair of user and integer; default integer value will be -1
	 * 
	 * @param user the new user given
	 * @return true if the user has been correctly added into
	 */
	public boolean addUser(User user){
		if(userWithEstimate.put(user,0)!= null) return true;
		return false;
	}
	
	
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
