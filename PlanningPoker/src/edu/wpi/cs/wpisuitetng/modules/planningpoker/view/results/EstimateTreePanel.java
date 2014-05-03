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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

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
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import java.awt.Insets;
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
	private final JButton unselectEstimateBtn;
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
		constraints.weighty = .8;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(listEstimateReqPanel, constraints);
		
		
		//buttons
		
		
		
		unselectEstimateBtn = new JButton("Unselect Requirement");
		unselectEstimateBtn.setVisible(ConfigManager.getInstance().getConfig().getUserName().
				equals(game.getGameCreator())
				&& !game.getStatus().equals(GameStatus.CLOSED));
		unselectEstimateBtn.setEnabled(false);
		unselectEstimateBtn.setToolTipText("Unselect this requirement so that it will not be sent to the requirement manager.");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = .1;
		constraints.insets = new Insets(2, 2, 1, 2);
		add(unselectEstimateBtn, constraints);
		unselectEstimateBtn.addActionListener(controller);
		

		
		sendEstimateToReqButton = new JButton();
		sendEstimateToReqButton.setText("Update Requirement Manager");
		sendEstimateToReqButton.setVisible(ConfigManager.getInstance().getConfig().getUserName().
				equals(game.getGameCreator())
				&& !game.getStatus().equals(GameStatus.CLOSED));
		sendEstimateToReqButton.setEnabled(!game.getStatus().equals(GameStatus.CLOSED)&&(!(listEstimateReqPanel.getSelectedEstimates().isEmpty())));

		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.weighty = .1;
		constraints.weightx = 1.0;
		constraints.insets = new Insets(1, 2, 2, 2);
		add(sendEstimateToReqButton, constraints);
		sendEstimateButtonToolTip();

		
		//add pictures
		try {
			final Image img = ImageIO.read(getClass().getResource("sendMail.png"));
			sendEstimateToReqButton.setIcon(new ImageIcon(img));
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		unselectEstimateBtn.setSize(sendEstimateToReqButton.getWidth(), 
				sendEstimateToReqButton.getHeight());
	
		
		sendEstimateToReqButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
				final List<Estimate> estimates = listEstimateReqPanel.getSelectedEstimates();
				
				for(Estimate e: estimates){
					e.setGameID(gameId);
					e.estimationSent(true);
					e.setSentBefore(true);
					
					//in addition to sending this estimate to requirement manager,
					//update information related to this estimate
					final Request request = Network.getInstance().makeRequest(
							"Advanced/planningpoker/game/sendToReq", 
							HttpMethod.POST); // POST is update
					request.setBody(e.toJSON()); 
					request.addObserver(new RequestObserver(){
						@Override
						public void responseSuccess(IRequest iReq) {
						}

						@Override
						public void responseError(IRequest iReq) {
						}

						@Override
						public void fail(IRequest iReq, Exception exception) {
						}
						
					}); 
					request.send();

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

	
	/**
	 * Reset the unselect estimate button to be enabled or not
	 * based on the given boolean
	 * @param valid if the button is valid
	 */
	public void setUnselectButtonEnabled(boolean valid) {
		unselectEstimateBtn.setEnabled(valid);

	}
	
}
