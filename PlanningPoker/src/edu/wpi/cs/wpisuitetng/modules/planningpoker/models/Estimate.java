package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.HashMap;
import java.util.Map.Entry;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This class acts as a record of each requirement. 
 * It contains the content of the requirement, and the the map between 
 * each person and his/her estimate. If the person has not made an estimate,
 * the estimate stored will be set to default as zero. 
 * 
 */
public class Estimate extends AbstractModel{
	
	private String requirement;
	private HashMap<User,Integer> userWithEstimate;
	
	
	public Estimate(){
		requirement = null;
		userWithEstimate = new HashMap<User,Integer>();
	}
	
	public Estimate(String r){
		requirement = r;
		userWithEstimate = new HashMap<User,Integer>();
		
	}
	
	
	
	/**
	 * Get requirement of this estimate
	 * @return requirement
	 */
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
	
	
	/**
	 * Check if an user has made an estimation for this requirement
	 * @param user the user given for check
	 * @return true if the user has made a valid estimation, false if not
	 * @throws Exception if this user does not exist in the list of users for this estimate
	 */
	public boolean hasMadeAnEstimation(User user) throws Exception{
		if(!userWithEstimate.containsKey(user)) throw new Exception("This user is not in this game");
		if(userWithEstimate.get(user)==0)	return false;
		return true;
		
		
	}
	
		
	/**
	 * Check if all users have made estimations for this requirement
	 * @return true if every user has made a valid estimation
	 */
	public boolean allEstimationsMade(){
		for(Entry<User,Integer> e: userWithEstimate.entrySet()){
			if(e.getValue()==0)	return false;
		}
		return true;
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
