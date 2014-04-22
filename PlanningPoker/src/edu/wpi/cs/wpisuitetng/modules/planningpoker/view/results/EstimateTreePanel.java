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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ViewResultsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The panel which holds listEstimateRequirementsPanel
 * and the button for sending requirements to requirement
 * manager. 
 * @author Code On Bleu
 *
 */
public class EstimateTreePanel extends JPanel{
	private ListEstimatedRequirementsPanel listEstimateReqPanel;
	private JButton sendEstimateToReqButton;
	private EstimateTreePanel estimateTreePanel;
	private final int gameId;
	
	/**
	 * constructor for the panel
	 * @param game the game
	 * @param controller
	 */
	public EstimateTreePanel(Game game, ViewResultsController controller){
		estimateTreePanel = this;
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		gameId = game.getId();
		
		//tree
		listEstimateReqPanel = new ListEstimatedRequirementsPanel(game, controller);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(listEstimateReqPanel, constraints);
		
		
		//button
		sendEstimateToReqButton = new JButton();
		sendEstimateToReqButton.setText("Send Selected Estimates");
		
		sendEstimateToReqButton.setEnabled(!game.getStatus().equals(GameStatus.CLOSED));
	
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(sendEstimateToReqButton, constraints);
		
		try {
			Image img = ImageIO.read(getClass().getResource("sendMail.png"));
			sendEstimateToReqButton.setIcon(new ImageIcon(img));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		sendEstimateToReqButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
				ArrayList<Estimate> estimates = listEstimateReqPanel.getSelectedEstimates();
				
				for(Estimate e: estimates){
					e.setGameID(gameId);
					e.estimationSent(true);
				}
				RequirementManagerFacade facade = RequirementManagerFacade.getInstance();
				facade.sendEstimates(estimates, estimateTreePanel);
				listEstimateReqPanel.refresh();
			}
			
		});
		
	}
	
	/**
	 * getter for the tree panel
	 * @return listEstimateReqPanel
	 */
	public ListEstimatedRequirementsPanel getTreePanel() {
		return listEstimateReqPanel;
	}
	
	



	/**
	 * refresh panel
	 */
	public void refresh() {
		listEstimateReqPanel.refresh();
		
	}
	
}
