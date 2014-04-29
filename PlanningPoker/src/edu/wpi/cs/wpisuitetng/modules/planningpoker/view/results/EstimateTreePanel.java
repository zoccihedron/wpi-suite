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

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
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
import java.util.List;

/**
 * The panel which holds listEstimateRequirementsPanel
 * and the button for sending requirements to requirement
 * manager. 
 * @author Code On Bleu
 * @version 1.00
 */
public class EstimateTreePanel extends JPanel{
	private final ListEstimatedRequirementsPanel listEstimateReqPanel;
	private final JButton sendEstimateToReqButton;
	private final EstimateTreePanel estimateTreePane;
	private final int gameId;
	
	/**
	 * constructor for the panel
	 * @param game the game
	 * @param controller
	 */
	public EstimateTreePanel(Game game, final ViewResultsController controller){
		estimateTreePane = this;
		
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
		sendEstimateToReqButton.setVisible(ConfigManager.getInstance().getConfig().getUserName().
				equals(game.getGameCreator())
				&& !game.getStatus().equals(GameStatus.CLOSED));
		sendEstimateToReqButton.setEnabled(!game.getStatus().equals(GameStatus.CLOSED)&&(!(listEstimateReqPanel.getSelectedEstimates().isEmpty())));
		
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(sendEstimateToReqButton, constraints);
		sendEstimateButtonToolTip();
		
		try {
			final Image img = ImageIO.read(getClass().getResource("sendMail.png"));
			sendEstimateToReqButton.setIcon(new ImageIcon(img));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		
		sendEstimateToReqButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
				final List<Estimate> estimates = listEstimateReqPanel.getSelectedEstimates();
				
				for(Estimate e: estimates){
					e.setGameID(gameId);
					e.estimationSent(true);
					e.setSentBefore(true);
				}
				final RequirementManagerFacade facade = RequirementManagerFacade.getInstance();
				facade.sendEstimates(estimates, estimateTreePane);
				listEstimateReqPanel.refresh();
				sendEstimateToReqButton.setEnabled(false);
				sendEstimateToReqButton.setToolTipText("The selected requirements were sent to the requirement manager.");
				controller.refreshResultsInfo();
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
		try{
			listEstimateReqPanel.refresh();
		}
		finally{
			if(!listEstimateReqPanel.getSelectedEstimates().isEmpty()){
				sendEstimateToReqButton.setEnabled(true);
			}
			else{
				sendEstimateToReqButton.setEnabled(false);
			}
			sendEstimateButtonToolTip();
		}		
	}
	
	/**
	 * 
	 */
	public void sendEstimateButtonToolTip() {
		if(sendEstimateToReqButton.isEnabled()) {
			sendEstimateToReqButton.setToolTipText("Click here to send the selected requirements to the requirement manager.");
		}
		else {
			sendEstimateToReqButton.setToolTipText("Please set an estimate in order to select a requirement to send.");
		}
	}
	
}
