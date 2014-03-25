/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.HashMap;
import java.util.Map.Entry;


/**
 * This class acts as a record of each requirement. 
 * It contains the content of the requirement, and the the map between 
 * each person and his/her estimate. If the person has not made an estimate,
 * the estimate stored will be set to default as zero. 
 * 
 * @author yyan
 * @version Mar 25, 2014
 */


public class Estimate {
	
	private String requirement;
	private HashMap<UserInfo,Integer> userWithEstimate;
	
	
	public Estimate(){
		requirement = null;
		userWithEstimate = new HashMap<UserInfo,Integer>();
	}
	
	public Estimate(String r){
		requirement = r;
		userWithEstimate = new HashMap<UserInfo,Integer>();
		
	}
	
	
	
	/**
	 * Get requirement of this estimate
	 * @return requirement of this estimate
	 */
	public String getRequirement() {
		return requirement;
	}

	/**
	 * update new requirement
	 * @param requirement the new requirement given
	 */
	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	
	/**
	 * 
	 * Add user to the hashMap of this estimate by creating a new
	 * pair of user and integer; default integer value will be -1
	 * 
	 * @param user UserInfo of the new user given
	 * @return true if the user has been correctly added into
	 */
	public boolean addUser(UserInfo user){
		if(userWithEstimate.put(user,0)!= null) return true;
		return false;
	}
	
	/**
	 * Check if an user has made an estimation for this requirement
	 * @param user the user given for check
	 * @return true if the user has made a valid estimation, false if not
	 * @throws Exception if this user does not exist in the list of users for this estimate
	 */
	public boolean hasMadeAnEstimation(UserInfo user) throws Exception{
		if(!userWithEstimate.containsKey(user)) throw new Exception("This user is not in this game");
		if(userWithEstimate.get(user)==0)	return false; 
		return true;
		
		
	}
	
	
	
	/**
	 * Check if all users have made estimations for this requirement
	 * @return true if every user has made a valid estimation
	 */
	public boolean allEstimationsMade(){
		for(Entry<UserInfo,Integer> e: userWithEstimate.entrySet()){
			if(e.getValue()==0)	return false;
		}
		return true;
	}
	
	

}
