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

import java.util.ArrayList;
import java.util.Collections;
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
	
	private int reqID;
	private HashMap<String,Integer> userWithEstimate;
	
	public Estimate(int r){
		reqID = r;
		userWithEstimate = new HashMap<String,Integer>();	
	}
	
	/**
	 * Get requirement of this estimate
	 * @return requirement of this estimate
	 */
	public int getReqID() {
		return reqID;
	}

	/**
	 * update new requirement
	 * @param requirement the new requirement given
	 */
	public void setRequirement(String requirement) {
		this.reqID= reqID;
	}
	
	/**
	 * Add user to the hashMap of this estimate by creating a new
	 * pair of user and integer; default integer value will be -1
	 * 
	 * @param user UserInfo of the new user given
	 * @return true if the user has been correctly added into
	 */
	public boolean addUser(String user){
		if(userWithEstimate.put(user,0)!= null) return true;
		return false;
	}
	
	/**
	 * Check if this user is included in this estimate. This method must be called before
	 * the method hasMadeAnEstimation is called
	 * @param user given for checking
	 * @return true if the user is in
	 */
	public boolean hasUser(String user){
		return userWithEstimate.containsKey(user);
	}
	
	/**
	 * Check if an user has made an estimation for this requirement. This method should be
	 * called after hasUser() is called
	 * @param user the user given for check
	 * @return true if the user has made a valid estimation, false if not
	 */
	public boolean hasMadeAnEstimation(UserInfo user){
		if(userWithEstimate.get(user)==0)	return false; 
		return true;	
	}
		
	/**
	 * Check if all users have made estimations for this requirement
	 * @return true if every user has made a valid estimation
	 */
	public boolean allEstimationsMade(){
		for(Entry<String,Integer> e: userWithEstimate.entrySet()){
			if(e.getValue()==0)	return false;
		}
		return true;
	}
	
	/**
	 * Let an user to make an estimation
	 * @param user the user
	 * @param est the value of estimation set by user in Integer
	 * @return true if the data is successfully updated, false if user does not exist in this game
	 */
	public boolean makeEstimate(String user, int est){
		if(hasUser(user)==false)	return false;
		userWithEstimate.put(user,est);
		return true;
	}
	
	/**
	 * Get estimate value of a user. This method should be called after hasUser() is called
	 * @param user the given user
	 * @return the estimate of the given user
	 */
	public int getEstimate(UserInfo user){
		return userWithEstimate.get(user);
	}
	
	/**
	 * Generates the mean of the estimates for a requirement, and
	 * ignores any invalid estimates (0).
	 *
	 * @return the mean of the estimate.
	 */
	public double getMean() {
		int sum = 0;
		int count = 0;
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() != 0) {
				count++;
				sum += temp.getValue();
			}
		}
		double mean = (double)sum/(double)count;
		return mean;
	}
	
	public double getMedian() {
		ArrayList<Integer> estimates = new ArrayList<Integer>();
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() != 0) {
				estimates.add(temp.getValue());
			}
		}
		Collections.sort(estimates);
		int length = estimates.size();
		double median = 0;
		if(length % 2 == 0){
			median = ((double) estimates.get(length / 2) + (double)estimates.get((length/2) - 1)) / 2.0;
		}
		else {
			median = (double)estimates.get(length / 2);
		}
		return median;
	}
}
