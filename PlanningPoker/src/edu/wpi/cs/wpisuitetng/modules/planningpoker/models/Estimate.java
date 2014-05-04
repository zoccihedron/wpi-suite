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
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;


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
	private int gameModifiedVersion = 0;
	private int reqID;
	private double mean = 0;
	private HashMap<String,Integer> userWithEstimate;
	private boolean isEstimationSent = false;
	private boolean sentBefore = false;
	private int finalEstimate = 0;
	private String note = "";
	private Map<String, List<Boolean>> userCardSelection;
	private boolean isFinalEstimationSet = false;
	
	/**
	 * Constructor for an estimate object
	 * @param r the requirement id
	 * @param gameID the game to be associate with the estimate
	 */
	public Estimate(int r, int gameID){
		reqID = r;
		this.gameID = gameID;
		userWithEstimate = new HashMap<String,Integer>();
		userCardSelection = new HashMap<String, List<Boolean>>();
	}
	
	
	/**
	 * Get requirement of this estimate
	 * @return requirement of this estimate
	 */
	public int getReqID() {
		return reqID;
	}
	
	/**
	 * Add user to the hashMap of this estimate by creating a new
	 * pair of user and integer; default integer value will be -1
	 * 
	 * @param user UserInfo of the new user given
	 * @return true if the user has been correctly added into
	 */
	public boolean canAddUser(String user){
		return (userWithEstimate.put(user, -1) != null) ? true : false;
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
		if(userWithEstimate.containsKey(user)){
			return (userWithEstimate.get(user) > -1); 
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
			if(e.getValue() <= -1) {
				result &= false;
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
	 * Get estimates of all users
	 * @return map of users and their estimates
	 */
	public Map<String,Integer> getUsersAndEstimates(){
		return userWithEstimate;
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
			if(temp.getValue() > 0) {
				count++;
				sum += temp.getValue();
			}
		}
		if(count != 0){
			mean = (double)sum / (double)count;
			
		} else {
			mean = 0;
		}
		return mean;
		
	}
	
	/**
	 * Generates the max of the estimates for a requirement
	 *
	 * @return the max of the estimate.
	 */
	public int getMax()
	{
		int max = 0;
		final List<Integer> estimates = new ArrayList<Integer>();
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() > 0) {
				estimates.add(temp.getValue());
			}
		}
		Collections.sort(estimates);
		int length = estimates.size();
		if (length == 0)
			return max;
		else
			return max = estimates.get(estimates.size() - 1);
	}
	
	/**
	 * Generates the min of the estimates for a requirement, and
	 * ignores any invalid estimates (0).
	 *
	 * @return the min of the estimate.
	 */
	public int getMin()
	{
		int min = 0;
		final List<Integer> estimates = new ArrayList<Integer>();
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() > 0) {
				estimates.add(temp.getValue());
			}
		}
		Collections.sort(estimates);
		int length = estimates.size();
		if (length == 0)
			return min;
		else
		{
			return min = estimates.get(0);
		}
	}
	
	/**
	 * Generates the number of estimates for a requirement that are 0
	 *
	 * @return the number of (0) estimates.
	 */
	public int getzeroEstimates()
	{
		int count = 0;
		final List<Integer> estimates = new ArrayList<Integer>();
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() == 0) {
				count++;
			}
		}
		return count;
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
			if(temp.getValue() > 0) {
				estimates.add(temp.getValue());
			}
		}
		Collections.sort(estimates);
		final int length = estimates.size();
		final int halfLength = length / 2;
		double median = 0;
		if(length == 0){
			return median;
		}
		if(length % 2 == 0){
			int mid1 = estimates.get(length / 2);
			int mid2 = estimates.get((length / 2) - 1);
			median = (mid1 + mid2) / 2.0;
		}
		else {
			int mid = estimates.get(length / 2);
			median = (double)mid;
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
		if(userWithEstimate.put(user, -1) != null) return true;
		return false;
	}

	/**
	 * Generates a copy of the current estimate
	 * @return copiedEstimate
	 */
	public Estimate getCopy(){
		final Estimate copyEst = new Estimate(reqID, gameID);
		copyEst.gameModifiedVersion = gameModifiedVersion;
		if (userWithEstimate != null)
		{
			copyEst.userWithEstimate = new HashMap<String,Integer>(userWithEstimate);
		}
		else
		{
			copyEst.userWithEstimate = new HashMap<String,Integer>();
		}
		copyEst.isEstimationSent = isEstimationSent;
		copyEst.mean = mean;
		copyEst.finalEstimate = finalEstimate;
		copyEst.isFinalEstimationSet = isFinalEstimationSet;
		copyEst.sentBefore = sentBefore;
		copyEst.userCardSelection = new HashMap<String, List<Boolean>>(userCardSelection);
		copyEst.note = note;
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
	
	/**
	 * set isEstimationSent, call this function 
	 * and pass true as parameter 
	 * before sending this estimation to requirement manager
	 * @param send boolean to indicate whether the estimate has been sent
	 */
	public void estimationSent(boolean send)
	{
		isEstimationSent = send;
	}
	
	/**
	 * @return true the estimation has been sent to the requirement manager
	 */
	public boolean estimationHasBeenSent() {
		
		return isEstimationSent;
	}

	/**
	 * Checks to see if the final estimate has been set
	 * @return true if it's been set
	 */
	public boolean isFinalEstimateSet() {
		return isFinalEstimationSet;
	}

	/**
	 * @return the final estimate
	 */
	public int getFinalEstimate() {
		return finalEstimate;
	}

	/**
	 * @param finalEstimate the final estimate to be set.
	 */
	public void setFinalEstimate(int finalEstimate) {
		this.finalEstimate = finalEstimate;
		isFinalEstimationSet = true;
	}

	/**
	 * Unselect this estimate so that it could show up in
	 * "unselected" category in result panel
	 */
	public void unSelectFinalEstimate(){
		isFinalEstimationSet = false;
	}

	/**
	 * Get state of every card (whether each card is selected or not)
	 * @param user
	 * @return list of boolean corresponding with state of the card
	 */
	public List<Boolean> getUserCardSelection(String user) {
		return userCardSelection.get(user);
	}

	/**
	 * Set state of each card (selected or not)
	 * @param user
	 * @param userCardSelection
	 */
	public void setUserCardSelection(String user, List<Boolean> userCardSelection) {
		this.userCardSelection.put(user, userCardSelection);
	}


	/**
	 * @return the gameModifiedVersion
	 */
	public int getGameModifiedVersion() {
		return gameModifiedVersion;
	}


	/**
	 * @param gameModifiedVersion the gameModifiedVersion to set
	 */
	public void setGameModifiedVersion(int gameModifiedVersion) {
		this.gameModifiedVersion = gameModifiedVersion;
	}
	
	/**
	 * Gets the note for the current estimate
	 *
	 * @return the note for the estimate
	 */
	public String getNote() {
		return note;
	}


	/**
	 * Sets the note for the current estimate
	 *
	 * @param note the note for the estimate
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Returns whether the estimate has ever been sent to the 
	 * requirement manager
	 *
	 * @return status of the estimate ever being sent
	 */
	public boolean isSentBefore() {
		return sentBefore;
	}


	/**
	 * sets whether the estimate has ever been sent to the 
	 * requirement manager previously
	 *
	 * @param hasSentBefore whether the estimate has been sent before or not
	 */
	public void setSentBefore(boolean sentBefore) {
		this.sentBefore = sentBefore;
	}
	
	/**
	 * Returns the number of estimates voted for this requirement
	 * @return number of estimates
	 */
	public int getMaxVoteCount(){
		return userWithEstimate.entrySet().size();
	}
	
	/**
	 * Returns the number of estimates voted for this requirement
	 * @return number of estimates
	 */
	public int getVoteCount(){
		int count = 0;
		for(Entry<String,Integer> temp: userWithEstimate.entrySet()){
			if(temp.getValue() >= 0){
				count++;
			}
		}
		return count;
	}
}
