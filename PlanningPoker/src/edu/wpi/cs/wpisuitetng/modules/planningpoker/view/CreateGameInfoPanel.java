/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ChangeDeadline;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

import javax.swing.JCheckBox;
import javax.swing.border.Border;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Code On Bleu
 *
 */
@SuppressWarnings({"serial"})
public class CreateGameInfoPanel extends JPanel {
	private MainViewTabController mainViewTabController;
	private NewGamePanel parent;
	
	private JLabel lblName;
	private JTextField gameNameText;
	private JTextArea description;
	private JLabel lblDeadline;
	private JDatePickerImpl datePicker;
	private JLabel lblTime;
	private JComboBox hourSelector;
	private JComboBox minuteSelector;
	private JRadioButton rdbtnAm;
	private JRadioButton rdbtnPm;
	private ButtonGroup AMPMSelection;
	private JLabel lblDeck;
	private JComboBox deck;
	private JButton btnNewButton;
	private JButton btnSave;
	private JButton btnCancel;
	private JButton btnStart;
	private JCheckBox chckbxDeadline;
	private JLabel lblMessage;
	private Game editingGame;
	private JLabel lblTitle;
	private JLabel lblDescription;


	/**
	 * This constructor is to be used when starting from a new game
	 */
	public CreateGameInfoPanel(NewGamePanel parent) {
		this.mainViewTabController = MainViewTabController.getInstance();
		this.parent = parent;
		setBounds(5,5,307,393);
		this.setLayout(new GridBagLayout());
		
		//Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		Border textFieldBorder = gameNameText.getBorder();
		
		description = new JTextArea();
		description.setBorder(textFieldBorder);
		
		lblTitle = new JLabel("Game Information");
		
		lblName = new JLabel("Name:");
		
		lblDeadline = new JLabel("Deadline:");
		
		// creates a date picker and sets its position
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		
		String[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hourSelector = new JComboBox(hours);
	
		String[] minutes = {"00","15","30","45"};

		minuteSelector = new JComboBox(minutes);
		
		lblTime = new JLabel("Time:");
		
		AMPMSelection = new ButtonGroup();
		rdbtnAm = new JRadioButton("AM");
		rdbtnPm = new JRadioButton("PM");
		rdbtnAm.setSelected(true);
		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);

		lblDeck = new JLabel("Deck:");
		
		String[] decks = {"default"};
		deck = new JComboBox(decks);
		
		
		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		chckbxDeadline.setSelected(true);
		
		lblDescription = new JLabel("Description:");
		panelSetup();
	}

	/**
	 * Constructor for Edit Game Panel
	 */
	public CreateGameInfoPanel(NewGamePanel parent, Game passedInGame) {
		this.mainViewTabController = MainViewTabController.getInstance();
		this.parent = parent;
		this.editingGame = passedInGame;
		setBounds(5,5,307,393); 
		this.setLayout(new GridBagLayout());
			
		lblTitle = new JLabel("Game Information");
		
		lblName = new JLabel("Name:       ");
		gameNameText = new JTextField();
		gameNameText.setText(editingGame.getName());
		Border textFieldBorder = gameNameText.getBorder();

		lblDescription = new JLabel("Description:");
		description = new JTextArea();
		description.setBorder(textFieldBorder);
		description.setText(editingGame.getDescription());
		
		lblDeck = new JLabel("Deck:");
		String[] decks = {"default"};
		deck = new JComboBox(decks);

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		lblDeadline = new JLabel("Deadline:");
			
		// creates a date picker and sets its position
		UtilDateModel model = new UtilDateModel();
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(editingGame.getEnd());
		model.setDate(tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DATE));
		model.setSelected(true);
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);	
		
		lblTime = new JLabel("Time:");
		String[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hourSelector = new JComboBox(hours);
		hourSelector.setSelectedItem(getHourStringFromCalendar(tempCalendar.get(Calendar.HOUR)));		
		String[] minutes = {"00","15","30","45"};
		minuteSelector = new JComboBox(minutes);
		minuteSelector.setSelectedItem(getMinuteStringFromCalendar(tempCalendar.get(Calendar.MINUTE)));
		
		AMPMSelection = new ButtonGroup();
		rdbtnAm = new JRadioButton("AM");
		AMPMSelection.add(rdbtnAm);
		rdbtnPm = new JRadioButton("PM");
		AMPMSelection.add(rdbtnPm);
		
		if(tempCalendar.get(Calendar.AM_PM) == Calendar.PM){
			rdbtnAm.setSelected(false);
			rdbtnPm.setSelected(true);
		}
		else{
			rdbtnAm.setSelected(true);
			rdbtnPm.setSelected(false);
		}

		ForceEnableOrDisableDeadline(editingGame.isHasDeadline());
		
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		panelSetup();
	}
	
	/**
	 * Sets all the grid components for either constructor
	 */
	public void panelSetup(){
		//DEFINE CONSTAINTS
		GridBagConstraints constraints = new GridBagConstraints();
		
		//
		JPanel fakePanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel1, constraints);
		JPanel fakePanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel2, constraints);
		
		
		//GAME INFORMATION LABEL
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(lblTitle, constraints);
		
		//NAME LABEL
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(lblName, constraints);
		
		//NAME FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 1;
		add(gameNameText, constraints);
		
		//DESCRIPTION LABEL
		lblDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 10;
		add(lblDescription, constraints);
		
		//DESCRIPTION
		description.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 0.90;
		constraints.weighty = 0.90;
		constraints.gridx = 1;
		constraints.gridy = 11;
		add(description, constraints);
		
		//DECK LABEL
		lblDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 9;
		add(lblDeck, constraints);
		
		//DECK SELECTOR
		deck.setFont(new Font("Tahoma", Font.PLAIN, 13));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 9;
		add(deck, constraints);
		
		//DEADLINE CHECKBOX
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 5;
		add(chckbxDeadline, constraints);

		
		//DEADLINE LABEL
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.WEST;
		add(lblDeadline, constraints);
		
		//DATE PICKER
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 6;
		add(datePicker, constraints);
		
		//TIME LABEL
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 7;
		add(lblTime, constraints);
		
		//HOUR SELECTOR
		hourSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 7;
		constraints.ipadx = 2;
		constraints.ipady = 2;
		add(hourSelector, constraints);
		
		//MINUTE SELECTOR
		minuteSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 7;
		constraints.ipadx = 2;
		constraints.ipady = 2;
		constraints.anchor = GridBagConstraints.WEST;
		add(minuteSelector, constraints);
		
		//AM BUTTON
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 8;
		rdbtnAm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnAm, constraints);
		
		//PM Button
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 3;
		constraints.gridy = 8;
		rdbtnPm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnPm, constraints);
	}


	/**
	 * A function to convert an int minute to a string minute (rounded to the nearest quarter-hour)
	 * @param minute the int value from a calendar object
	 * @return the string corresponding to the minuteSelector object
	 */
	private String getMinuteStringFromCalendar(int minute) {
		System.out.println("Minute:" + minute);
		if(minute < 15){
			return "00";
		}
		else if(minute < 30){
			return "15";
		}
		else if(minute < 45){
			return "30";
		}
		else if(minute < 60){
			return "45";
		}
		return "00";
	}

	/**
	 * A function to convert an int hour to a string minute
	 * @param hour the int value from a calendar object
	 * @return the string corresponding to the hourSelector object
	 */
	private String getHourStringFromCalendar(int hour) {
		System.out.println("Hour:" + hour);
		hour = hour % 12;
		System.out.println("Hour % 12:" + hour);
		if(hour == 0){
			return "12";
		}
		else if(hour == 1){
			return "01";
		}
		else if(hour == 2){
			return "02";
		}
		else if(hour == 3){
			return "03";
		}
		else if(hour == 4){
			return "04";
		}
		else if(hour == 5){
			return "05";
		}
		else if(hour == 6){
			return "06";
		}
		else if(hour == 7){
			return "07";
		}
		else if(hour == 8){
			return "08";
		}
		else if(hour == 9){
			return "09";
		}
		else if(hour == 10){
			return "10";
		}
		else if(hour == 11){
			return "11";
		}
		
		return "12";
	}

	/**
	 * Checks to see if the fields for the game are selected properly
	 * '<html>error</html>' format needed to allow word wrap in the error label
	 * @return check  - true if the fields are selected properly, otherwise false
	 */
	public boolean checkFields() {
		if (gameNameText.getText().trim().isEmpty()){
				reportError("<html>*Error: Please choose a name!</html>");
				return false;
		}
		if(chckbxDeadline.isSelected()){
			if(datePicker.getModel().getValue() == null){
				reportError("<html>*Error: Please choose a date or turn off the deadline.</html>");
				return false;
			}
			if(getDeadline().compareTo(new Date()) <= 0){
				reportError("<html>*Error: The deadline must not be in the past.</html>");
				return false;
			}
		}
		if (parent.getGameRequirements().size() == 0){
			reportError("<html>*Error: Pick at least one requirement.</html>");
			return false;
		}
		return true;
	}
	


	/**
	 * Will switch the deadline fields to enabled or disabled based on the Deadline checkbox
	 */
	public void EnableOrDisableDeadline() {
		if (chckbxDeadline.isSelected()){
			datePicker.setEnabled(true);
			datePicker.setVisible(true);
			hourSelector.setEnabled(true);
			hourSelector.setVisible(true);
			minuteSelector.setEnabled(true);
			minuteSelector.setVisible(true);
			rdbtnPm.setEnabled(true);
			rdbtnPm.setVisible(true);
			rdbtnAm.setEnabled(true);
			rdbtnAm.setVisible(true);
			lblDeadline.setVisible(true);
			lblTime.setVisible(true);
		}
		else {
			datePicker.setEnabled(false);
			datePicker.setEnabled(false);
			datePicker.setVisible(false);
			hourSelector.setEnabled(false);
			hourSelector.setVisible(false);
			minuteSelector.setEnabled(false);
			minuteSelector.setVisible(false);
			rdbtnPm.setEnabled(false);
			rdbtnPm.setVisible(false);
			rdbtnAm.setEnabled(false);
			rdbtnAm.setVisible(false);
			lblDeadline.setVisible(false);
			lblTime.setVisible(false);
		}
		
	}
	
	/**
	 * Will switch the deadline fields to enabled or disabled based on the value passed
	 * @param hasDeadline variable from the game object that sets the condition for the setting
	 */
	private void ForceEnableOrDisableDeadline(boolean hasDeadline) {
		if (hasDeadline){
			chckbxDeadline.setSelected(true);
			datePicker.setEnabled(true);
			datePicker.setVisible(true);
			hourSelector.setEnabled(true);
			hourSelector.setVisible(true);
			minuteSelector.setEnabled(true);
			minuteSelector.setVisible(true);
			rdbtnPm.setEnabled(true);
			rdbtnPm.setVisible(true);
			rdbtnAm.setEnabled(true);
			rdbtnAm.setVisible(true);
			lblDeadline.setVisible(true);
			lblTime.setVisible(true);
		}
		else {
			chckbxDeadline.setSelected(false);
			datePicker.setEnabled(false);
			datePicker.setVisible(false);
			hourSelector.setEnabled(false);
			hourSelector.setVisible(false);
			minuteSelector.setEnabled(false);
			minuteSelector.setVisible(false);
			rdbtnPm.setEnabled(false);
			rdbtnPm.setVisible(false);
			rdbtnAm.setEnabled(false);
			rdbtnAm.setVisible(false);
			lblDeadline.setVisible(false);
			lblTime.setVisible(false);
		}
	}
	
	/**Fills the text box with a red warning based on the error Message
	 * 
	 * @param error the message to be printed, should be in <html>text</html> format
	 */
	public void reportError(String error) {
		parent.reportError(error);
	}
	
	/**Fills the text box with a green message based on the input
	 * 
	 * @param message the message to be printed, should be in <html>text</html> format
	 */
	public void reportMessage(String message) {
		parent.reportMessage(message);
	}
	

	/**
	 * Sends the signal to Mainview to close the NewgameTab
	 */
	public void closeNewGameTab() {
		this.mainViewTabController.closeTab(this.parent);
	}

	/**
	 * Fills in the game object with the necessary data
	 * @return newGame
	 */
	public Game getGameObject() {
		int id = 0;
		if(editingGame != null){
			id = editingGame.getId();
		}
		if(chckbxDeadline.isSelected()){
			Game newGame = new Game(getGameName(), new Date(), getDeadline());
			newGame.setRequirements(parent.getGameRequirements());
			newGame.setDescription(description.getText());
			newGame.setHasDeadline(true);
			newGame.setId(id);
			return newGame;
		}
		else{
			Game newGame = new Game(getGameName(), new Date(), new Date());
			newGame.setRequirements(parent.getGameRequirements());
			newGame.setDescription(description.getText());
			newGame.setHasDeadline(false);
			newGame.setId(id);
			return newGame;
		}
	}
	
	/**
	 * Returns the name from the Name Text Field
	 * @return the name
	 */
	private String getGameName() {
		return gameNameText.getText();
	}

	/**
	 * returns deadline value including calendar date and time
	 * @return Date
	 */
	public Date getDeadline() {
		Date selectedDate = (Date) datePicker.getModel().getValue();
		Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(selectedDate);
		tempCalendar.set(tempCalendar.get(Calendar.YEAR),
							tempCalendar.get(Calendar.MONTH),
							tempCalendar.get(Calendar.DATE),
							getHour() % 12,
							getMinute(),
							0);
		if(getHour() >= 12){
			tempCalendar.set(Calendar.AM_PM, Calendar.PM);
		}
		else{
			tempCalendar.set(Calendar.AM_PM, Calendar.AM);
		}
		return tempCalendar.getTime();
	}
	
	/**
	 * gets hour value from hour selector; converts to 24 hour format
	 * @return hourInt
	 */
	public int getHour() {
		String hourString = (String) hourSelector.getSelectedItem();
		int hourInt = Integer.parseInt(hourString);
		if (rdbtnPm.isSelected()) {
			hourInt = hourInt + 12;
		}
		return hourInt;
	}
	
	/**
	 * gets minute value from minute selector
	 * @return minuteInt
	 */
	public int getMinute() {
		String minuteString = (String) minuteSelector.getSelectedItem();
		int minuteInt = Integer.parseInt(minuteString);
		return minuteInt;
	}
}
