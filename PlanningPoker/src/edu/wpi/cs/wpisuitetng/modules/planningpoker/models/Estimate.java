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
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;


/**
 * This class acts as a record of each requirement. 
 * It contains the content of the requirement, and the the map between 
 * each person and his/her estimate. If the person has not made an estimate,
 * the estimate stored will be set to default as zero. 
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
public class Estimate {
	
	private int gameID;
	private int reqID;
	private HashMap<String,Integer> userWithEstimate;
	
	/**
	 * Constructor for an estimate object
	 * @param r the requirement id
	 * @param gameID the game to be associate with the estimate
	 */
	public Estimate(int r, int gameID){
		reqID = r;
		this.gameID = gameID;
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
	 * @param requirement the given requirement
	 */
	public void setRequirement(Requirement requirement) {
		reqID = requirement.getId();
	}
	
	/**
	 * Add user to the hashMap of this estimate by creating a new
	 * pair of user and integer; default integer value will be -1
	 * 
	 * @param user UserInfo of the new user given
	 * @return true if the user has been correctly added into
	 */
	public boolean canAddUser(String user){
		return (userWithEstimate.put(user, 0) != null) ? true : false;
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
	public boolean hasMadeAnEstimation(String user){
		if(userWithEstimate.containsValue(user)){
			return (userWithEstimate.get(user) == 0) ? false : true; 
		}
		else{
			return false;
		}
	}
		
	/**
	 * Check if all users have made estimations for this requirement
	 * @return true if every user has made a valid estimation
	 */
	public boolean areAllEstimationsMade()
	{
		boolean result = true;
		for(Entry<String,Integer> e: userWithEstimate.entrySet())
		{
			if(e.getValue() <= 0) {
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * Converts the estimate to JSON-encoded string
	 * 
	 * @return JSON-encoded string
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	public String toJSON() {
		return new Gson().toJson(this, Estimate.class);
	}
	
	/**
	 * Returns an estimate from JSON encoded string
	 *
	 * @param json JSON-encoded string
	 * @return Estimate
	 */
	public static Estimate fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Estimate.class);
	}
	
	/**
	 * Let an user to make an estimation
	 * @param user the user
	 * @param est the value of estimation set by user in Integer
	 * @return true if the data is successfully updated, false if user does not exist in this game
	 */
	public boolean canMakeEstimate(String user, int est)
	{
		boolean result = true;
		if(!hasUser(user)) 
		{
			result = false;
		}
		else
		{
			userWithEstimate.put(user, est);
			result = true;
		}
		return result;
	}
	
	/**
	 * Get estimate value of a user. This method should be called after hasUser() is called
	 * @param user the given user
	 * @return the estimate of the given user
	 */
	public int getEstimate(String user)
	{
		return userWithEstimate.get(user);
	}
	
	/**
	 * Generates the mean of the estimates for a requirement, and
	 * ignores any invalid estimates (0).
	 *
	 * @return the mean of the estimate.
	 */
	public double getMean()
	{
		int sum = 0;
		int count = 0;
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() != 0) {
				count++;
				sum += temp.getValue();
			}
		}
		final double mean = (double)sum / (double)count;
		return mean;
	}
	
	/**
	 * Generates the median of the estimates for a requirement, and
	 * ignores any invalid estimates (0).
	 *
	 * @return the median of the estimate.
	 */
	public double getMedian() {
		final List<Integer> estimates = new ArrayList<Integer>();
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() != 0) {
				estimates.add(temp.getValue());
			}
		}
		Collections.sort(estimates);
		final int length = estimates.size();
		double median = 0;
		if(length % 2 == 0){
			median = ((double) estimates.get(length / 2) + 
					(double)estimates.get((length / 2) - 1)) / 2.0;
		}
		else {
			median = (double)estimates.get(length / 2);
		}
		return median;
	}
	
	/**
	 * Makes an estimate for a user, if he is included in the game.
	 *
	 * @param user the username
	 * @param est the ID of the requirement being scored
	 * @return true if user is in the game, false if not
	 */
	public boolean makeEstimate(String user, int est){
		if(!hasUser(user)) return false;
		userWithEstimate.put(user, est);
		return true;
	}
	
	/**
	 * Adds a user to an estimate with an initial score of 0.
	 *
	 * @param user the username
	 * @return true if the user was added, false if not
	 */
	public boolean addUser(String user){
		System.out.println("Adding User to estimate!");
		if(userWithEstimate.put(user, -1) != null) return true;
		return false;
	}

	/**
	 * Generates a copy of the current estimate
	 * @return copiedEstimate
	 */
	public Estimate getCopy(){
		final Estimate copyEst = new Estimate(reqID, gameID);
		copyEst.userWithEstimate = new HashMap<String,Integer>(userWithEstimate);
		return copyEst;
	}
	
	/**
	 * Gets the current game id associated with the estimate
	 * @return gameID
	 */
	public int getGameID() {
		return gameID;
	}

	/**
	 * sets the game id associated with the current estimate
	 * @param gameID
	 */
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	/**
	 * Set the requirement id for the estimate
	 * @param reqID
	 */
	public void setReqID(int reqID) {
		this.reqID = reqID;
	}
}
