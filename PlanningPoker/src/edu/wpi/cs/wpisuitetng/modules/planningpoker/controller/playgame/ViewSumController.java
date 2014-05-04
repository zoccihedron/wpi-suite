/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;

/**
 * This class allow deck panel to update the label in estimation pane 
 * which shows sum of the voting
 * 
 * @author Codon Bleu
 * @version Apr 23, 2014
 */
public class ViewSumController {
	
	private EstimationPane estimationPane;
	
	/**
	 * constructor for this controller 
	 * @param pane estimation pane which contains the deck panel
	 */
	public ViewSumController(EstimationPane pane){
		estimationPane = pane;
	}
	
	/**
	 * Let estimation pane to update its label showing sum
	 * @param sum value passed by deckpanel
	 */
	public void updateSum(int sum){
		estimationPane.updateSum(sum);
		
	}
	
	public void passTextToVoteButton(){
		estimationPane.pressVoteButton();
	}

}
