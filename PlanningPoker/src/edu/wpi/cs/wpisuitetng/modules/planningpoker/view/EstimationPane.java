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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.ViewSumController;
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
	
	private JLabel lblTitle;
	private JLabel lblReqName;
	private JTextField fldReqName;
	private JLabel lblReqDescription;
	private JLabel lblDeckSelection;
	private JTextArea fldReqDescription;
	private JScrollPane scrollDescription;
	private DeckPanel deckPanel;
	private JLabel message;
	private JButton voteButton;
	private int reqid;
	private final ListRequirementsPanel listReqPanel;
	private Game game;
	private Requirement req;
	private JLabel helpTitle;
	private JLabel helpText;
	//this variable allows the panel to close before an estimation is selected
	private boolean nothingHappened;
	private JLabel currentVote;
	
/**
 * Constructor for panel
 * @param listReqPanel
 * @param game
 */
	public EstimationPane(ListRequirementsPanel listReqPanel, Game game) {
		initPlayGameHelpPanel();
		this.listReqPanel = listReqPanel;
		this.game = game;
	}
	
	/**
	 * Constructs the estimation pane with voting capabilities.
	 */
	public void initEstimationPane() {
		nothingHappened = false;
		
		this.remove(helpText);
		this.remove(helpTitle);
		
		this.setLayout(new GridBagLayout());

		lblTitle = new JLabel();
		lblReqName = new JLabel();
		fldReqName = new JTextField();
		lblReqDescription = new JLabel();
		lblDeckSelection = new JLabel();
		fldReqDescription = new JTextArea();
		scrollDescription = new JScrollPane(fldReqDescription);
		message = new JLabel();
		voteButton = new JButton();
		
		lblReqName.setText("Name:");
		lblReqDescription.setText("Description:");
		lblTitle.setText("Requirement Information");
		
		fldReqName.setEditable(false);
		fldReqName.setBackground(Color.WHITE);
		fldReqName.setMargin(new Insets(3, 3, 3, 3));
		
		fldReqDescription.setBorder(new JTextField().getBorder());
		fldReqDescription.setEditable(false);
		fldReqDescription.setLineWrap(true);
		
		deckPanel = new DeckPanel(game.getDeck(), new ViewSumController(this));
		
		infoPanelSetup();
	}
	
	 /**
	  * sets up help panel to show before a requirement has been selected
	  */
	public void initPlayGameHelpPanel(){
		nothingHappened = true;
		
		// Set up layout constraints
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
				
		helpTitle = new JLabel();
		helpText = new JLabel();
		
		helpTitle.setText("Play Game");
		helpTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		
		helpText.setText("To begin, please select a requirement from the panel on the left.");
		helpText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(helpTitle, constraints);
		
		constraints.gridy = 1;
		constraints.gridx = 0;
		this.add(helpText, constraints);
	}
	
	/**
	 * Sets all the grid components for either constructor
	 */
	public void infoPanelSetup() {
		// DEFINE CONSTAINTS
		final GridBagConstraints constraints = new GridBagConstraints();

		// GAME INFORMATION LABEL
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipady = 20;
		add(lblTitle, constraints);
		constraints.ipady = 0;

		// NAME LABEL
		lblReqName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblReqName.setBorder(new EmptyBorder(5, 0, 5, 5));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(lblReqName, constraints);

		// NAME FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(fldReqName, constraints);

		// DESCRIPTION LABEL
		lblReqDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		lblReqDescription.setBorder(new EmptyBorder(5, 0, 5, 0));
		add(lblReqDescription, constraints);

								
		// DESCRIPTION
		constraints.fill = GridBagConstraints.BOTH;
	
		
		// DESCRIPTION SCROLL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.4;
		constraints.gridx = 0;
		constraints.gridy = 3;
		scrollDescription.setBorder(new EmptyBorder(0, 0, 10, 0));
		add(scrollDescription, constraints);
		
		// CURRENT VOTING
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 4;
		currentVote = new JLabel();
		currentVote.setText("Current Vote: " + 0);
		add(currentVote, constraints);
		if(game.getDeck() == -1){
			currentVote.setVisible(false);
		}
		
		// DECK PANEL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.6;
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(deckPanel, constraints);
		
		// DECK SELECTION LABEL
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.weightx = 0;
		constraints.weighty = 0;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.insets = new Insets(5, 0, 5, 0);
		add(lblDeckSelection, constraints);
		
		if(deckPanel.isDeckView()){
			if(deckPanel.isMultipleSelection()){
				lblDeckSelection.setText("Deck Settings: Multiple Selection");
			}
			else{
				lblDeckSelection.setText("Deck Settings: Single Selection");
			}
		}
		else {
			lblDeckSelection.setVisible(false);
			
			deckPanel.getEstimateFieldComponent()
			.getDocument().addDocumentListener(new DocumentListener(){

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
			
		}
		
		// VOTE BUTTON
		constraints.fill = GridBagConstraints.NONE;
		voteButton.setAlignmentX(LEFT_ALIGNMENT);
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 7;
		constraints.ipadx = 40;
		add(voteButton, constraints);
		
		//error message
		message = new JLabel();
		message.setText("");
		message.setFont(new Font("Dialog", Font.ITALIC, 15));
		message.setVisible(true);
		message.setMinimumSize(new Dimension(200, 25));
		message.setMaximumSize(new Dimension(200, 25));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 2;
		constraints.gridy = 7;
		constraints.weightx = 0.0;
		constraints.gridwidth = 3;
		add(message, constraints);
		

		
		
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		for(JToggleButton j : deckPanel.getListOfButtons()){
			j.addMouseListener(new MouseAdapter(){

				public void mouseClicked(MouseEvent arg0) {
					checkField();
				}
				
			});
		}

		try {
		    final Image img = ImageIO.read(getClass().getResource("vote.png"));
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
		voteButton.setToolTipText("Click here to vote!");

		this.reqid = reqid;
		try{
			req = getRequirementFromId();
			fldReqName.setText(req.getName());
			
			fldReqDescription.setText(req.getDescription());
			fldReqDescription.setCaretPosition(0);
			
			deckPanel.clearCardsSelected();
			deckPanel.displayOldEstimate(game, reqid);
			
			message.setText(" ");
		}
		catch(NotFoundException exception){
			message.setText("Exception: Requirement Not Found");
			System.err.println(exception.getMessage());
		}
		
		deckPanel.setEstimateFieldEditable(true);
		
		checkField();
		message.setText("<html></html>");


	}

	private Requirement getRequirementFromId() throws NotFoundException{
		final List<Requirement> reqs = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement req: reqs){
			if(req.getId() == reqid){
				return req;
			}
		}
		throw new NotFoundException("Requirement was not found");
	}

	/**
	 * This method checks if the estimation is of the correct value (An interger) And updates the
	 * display to show an error if the value is wrong. It also checks that a deck is selected in
	 * the deck version
	 * @return True if everything is all set, return false if the value is incorrect
	 */
	public boolean checkField() {
		
		if(!deckPanel.hasDeckSelected()) {
			reportError("<html>Error: Select a card.</html>");
			voteButton.setEnabled(false);
			voteButton.setToolTipText("Please select a card");
			return false;
		}
		
		final int estimate;
		try{
			reportError("<html></html>");
			estimate = Integer.parseInt(deckPanel.getEstimateField());

		} catch (NumberFormatException e){
			reportError("<html>Error: Estimate must be an integer.</html>");
			System.err.println(e.getMessage());
			voteButton.setEnabled(false);
			voteButton.setToolTipText("Please enter an integer");
			return false;
		}

		if(estimate < 0) {
			reportError("<html>Error: Estimate must be an integer greater than 0.</html>");
			voteButton.setEnabled(false);
			voteButton.setToolTipText("Please enter an integer greater than 0");
			return false;
		}
		
		if(estimate == 0){
			reportInfo("<html>0 indicates that you are unable to estimate this requirement. </html>");
			voteButton.setEnabled(true);
			voteButton.setToolTipText("Click here to vote!");
			return true;
		}
		
		if(game.getStatus() == GameStatus.ENDED){
			reportError("<html>Error: Game has ended</html>");
			voteButton.setEnabled(false);
			voteButton.setToolTipText("You cannot vote on an ended game.");
			return false;
		}
		voteButton.setEnabled(true);
		voteButton.setToolTipText("Click here to vote!");
		return true;
	}


	/**
	 * display error message to the pane
	 * @param string error message
	 */
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
			System.err.println(e.getMessage());
		}
		return 0;

	}

	public int getReqID(){
		return reqid;
	}
	
	/**
	 * This function updates the display to report an information message.
	 * @param string value is the string
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
		message.setText("<html>Vote Updated! You voted " + value + "</html>");
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


	/**
	 * This function passes the request for card selection onto the deckpane
	 * @return The list of selected cards
	 */
	public List<Boolean> getCardSelection() {
		if(!deckPanel.isDeckView()){
			return null;
		} else{
			return deckPanel.getCardSelection();
		}
	}
	
	/**
	 * This function updates the sum of voting by changing the content of label
	 * @param sum the updated sum that needs to show up
	 */
	public void updateSum(int sum){
		currentVote.setText("Current Vote: " + sum);

		
	}

	/**
	 * @return the nothingHappened
	 */
	public boolean isNothingHappened() {
		return nothingHappened;
	}

	/**
	 * @param nothingHappened the nothingHappened to set
	 */
	public void setNothingHappened(boolean nothingHappened) {
		this.nothingHappened = nothingHappened;
	}

}
