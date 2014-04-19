/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.VoteActionController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This class create a GUI pane that a user can use to submit their estimation to
 * a game.
 * @author Code On Bleu
 * @version 0.1
 */
public class EstimationPane extends JPanel {
	private final JLabel titleLabel;
	private final JLabel nameLabel;
	private final JLabel descriptionLabel;
	private final JScrollPane descriptionScroll;
	private final JTextArea requirementName;
	private final JTextArea descriptionText;
	private DeckPanel deckPanel;
	private JLabel message;
	private JButton voteButton;
	private int reqid;
	private ListRequirementsPanel listReqPanel;
	private Game game;
	
	private Requirement req;
	
	/**
	 * Constructor for panel
	 * @param game 
	 * @param reqid The req to vote on
	 * @param draftGame The game the vote is going towards
	 */
	public EstimationPane(ListRequirementsPanel listReqPanel, Game game) {
		this.game = game;
		this.listReqPanel = listReqPanel;
		
		setBounds(5, 5, 307, 393);
		this.setLayout(new GridBagLayout());

		final Border jtextFieldBorder = new JTextField().getBorder();
		
		// Adds the fields and button to the main panel.
		requirementName = new JTextArea();
		requirementName.setEditable(false);
		requirementName.setEnabled(true);
		requirementName.setBorder(jtextFieldBorder);
		
		descriptionText = new JTextArea();
		descriptionText.setBorder(jtextFieldBorder);
		descriptionText.setEditable(false);
		descriptionText.setAutoscrolls(true);
		
		descriptionScroll = new JScrollPane();
		descriptionScroll.add(descriptionText);

		titleLabel = new JLabel("Game Information");

		nameLabel = new JLabel("Name:");

		descriptionLabel = new JLabel("Description:");
		infoPanelSetup();
	}

	
	/**
	 * Sets all the grid components for either constructor
	 */
	public void infoPanelSetup() {
		// DEFINE CONSTAINTS
		final GridBagConstraints constraints = new GridBagConstraints();


		// GAME INFORMATION LABEL
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(titleLabel, constraints);

		// NAME LABEL
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(nameLabel, constraints);
		
		// RequirementName FIELD
		requirementName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 1;
		constraints.ipadx = 40;
		constraints.insets = new Insets(0, 20, 0, 20);
		requirementName.setColumns(3);
		add(requirementName, constraints);

		// DESCRIPTION LABEL
		descriptionLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(descriptionLabel, constraints);

		
		JScrollPane scrollPane = new JScrollPane(descriptionText); 
		descriptionText.setEditable(false);
		
		// DESCRIPTION
		descriptionText.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;
		
		// DESCRIPTION SCROLL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.insets = new Insets(0, 20, 10, 20);
		add(scrollPane, constraints);
		
		// DECK PANEL
		deckPanel = new DeckPanel(game.getDeck());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 12;
		constraints.weightx = 1.0;
		constraints.weighty = 1.5;
		constraints.insets = new Insets(10, 20, 0, 20);
		add(deckPanel, constraints);
		
		//error message
		message = new JLabel();
		message.setText("");
		message.setFont(new Font("Dialog", Font.ITALIC, 15));
		message.setVisible(true);
		message.setMinimumSize(new Dimension(80, 15));
		message.setMaximumSize(new Dimension(80, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.gridy = 13;
		constraints.weightx = 0.0;
		constraints.gridwidth = 1;
		constraints.ipadx = 200;
		constraints.anchor = GridBagConstraints.NORTHWEST;
		add(message, constraints);

		// VOTE BUTTON
		voteButton = new JButton("");
		voteButton.setPreferredSize(new Dimension(140, 40));
		constraints.fill = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 13;
		constraints.weightx = 1.0;
		constraints.gridwidth = 4;
		constraints.ipadx = 80;
		constraints.anchor = GridBagConstraints.NORTHEAST;
		constraints.insets = new Insets(2, 0, 0, 21);
		add(voteButton, constraints);
		voteButton.setEnabled(false);
		
		// adds listener for live validation of the Estimate Field
		deckPanel.getEstimateFieldComponent().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				checkField();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				checkField();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				checkField(); 
			}
			
		});

		

		try {
		    Image img = ImageIO.read(getClass().getResource("vote.png"));
		    voteButton.setIcon(new ImageIcon(img));		    
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
	}

	/**
	 * Sets the game and requirement for displaying in the deck panel and 
	 * in the requirement information fields, and displays the old
	 * estimate for the user if there is one
	 *
	 * @param reqid the requirement ID
	 * @param game the game
	 */
	public void setGameAndRequirement(int reqid, Game game){
		this.game = game;
		
		voteButton.addActionListener(new VoteActionController(this, game));
		
		voteButton.setEnabled(true);

		this.reqid = reqid;
		try{
			req = getRequirementFromId();
			requirementName.setText(req.getName());
			
			descriptionText.setText(req.getDescription());
			
			deckPanel.clearCardsSelected();
			deckPanel.displayOldEstimate(game, reqid);
			
			message.setText(" ");
		}
		catch(NotFoundException exception){
			message.setText("Exception: Requirement Not Found");
		}
		
		deckPanel.setEstimateFieldEditable(true);
		
		checkField();
		message.setText("<html></html>");


	}

	private Requirement getRequirementFromId() throws NotFoundException{
		List<Requirement> reqs = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement req: reqs){
			if(req.getId() == reqid){
				return req;
			}
		}
		throw new NotFoundException("Requirement was not found");
	}

	/**
	 * This method checks if the estimation is of the correct value (An interger) And updates the
	 * display to show an error if the value is wrong. It also checks that a deck is selected in the deck version
	 * @return True if everything is all set, return false if the value is incorrect
	 */
	public boolean checkField() {
		
		if(!deckPanel.hasDeckSelected()) {
			reportError("<html>Error: Select a card.</html>");
			voteButton.setEnabled(false);
			return false;
		}
		
		final int estimate;
		try{
			reportError("<html></html>");
			estimate = Integer.parseInt(deckPanel.getEstimateField());

		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");
			voteButton.setEnabled(false);
			return false;
		}

		if(estimate < 0) {
			reportError("<html>Error: Estimate must be an integer greater than 0.</html>");
			voteButton.setEnabled(false);
			return false;
		}
		
		if(estimate == 0){
			reportInfo("<html>0 indicates that you are unable to estimate this requirement. </html>");
			voteButton.setEnabled(true);
			return true;
		}
		
		if(game.getStatus() == GameStatus.ENDED){
			reportError("<html>Error: Game has ended</html>");
			voteButton.setEnabled(false);
			return false;
		}
		voteButton.setEnabled(true);
		return true;
	}



	public void reportError(String string) {
		message.setText(string);
		message.setForeground(Color.RED);
	}

	/**
	 * This method returns the estimate of the requirement. If the value 
	 * is wrong then it updates the display
	 * to show there is an error
	 * @return The value of the estimate, or zero if there is an error
	 */
	public int getEstimate(){
		final int estimate;
		try{
			estimate = Integer.parseInt(deckPanel.getEstimateField());
			return estimate;

		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");

		}
		return 0;

	}

	public int getReqID(){
		return reqid;
	}
	
	/**
	 * This function updates the display to report an information message.
	 * @param value is the string
	 */
	public void reportInfo(String string){
		message.setText(string);
		message.setForeground(Color.BLUE);
	}

	/**
	 * This function updates the display to report a success message.
	 * @param value is the numerical value of the vote
	 */
	public void reportSuccess(int value) {
		message.setText("<html>Success: Vote Updated! You voted " + value + "</html>");
		message.setForeground(Color.BLUE);

	}

	/**
	 * Refreshes the list requirements panel
	 */
	public void refresh(){
		listReqPanel.refresh();
	}
	
	public DeckPanel getDeckPanel(){
		return deckPanel;
	}


	public ArrayList<Boolean> getCardSelection() {
		if(!deckPanel.isDeckView()){
			return null;
		} else{
			return deckPanel.getCardSelection();
		}
	}

}
