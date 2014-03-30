/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.AddGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ChangeDeadline;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.CloseNewGameTabController;
import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.JPlaceholderTextField;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

import javax.swing.JLabel;

import java.awt.Font;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.JCheckBox;




/**
 * This class is a JPanel. It contains all the fields needed to 
 * create a new game
 * 
 * @author Joshua Allard
 *
 */
@SuppressWarnings({"serial"})
public class CreateGameInfoPanel extends JPanel {
	private MainView parentWindow;
	
	private JLabel lblName;
	private JTextField gameNameText;
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
	
	
	public CreateGameInfoPanel(PlanningPokerModel gamesModel, MainView parentWindow) {
		this.parentWindow = parentWindow;
		setBounds(5,5,307,345);

		setBorder(BorderFactory.createLineBorder(Color.orange, 2));
		setLayout(null);
		
		
		//Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		gameNameText.setBounds(119, 56, 130, 23);
		add(gameNameText);

		btnStart = new JButton("Start");
		btnStart.setBounds(206, 294, 86, 23);
		add(btnStart);
		
		JLabel lblNewLabel = new JLabel("Game Information");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(46, 11, 210, 33);
		add(lblNewLabel);
		

		
		lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(35, 58, 86, 14);
		add(lblName);
		
		
		lblDeadline = new JLabel("Deadline:");
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDeadline.setBounds(35, 126, 86, 20);
		add(lblDeadline);
		
		
		
		// creates a date picker and sets its position
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setBounds(119, 126, 130, 30);
		add(datePicker);
		
		
		
		String[] hours = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		hourSelector = new JComboBox(hours);
		hourSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		hourSelector.setBounds(119, 157, 52, 20);
	
		add(hourSelector);
		
		String[] minutes = {"00","15","30","45"};

		minuteSelector = new JComboBox(minutes);
		minuteSelector.setFont(new Font("Tahoma", Font.PLAIN, 13));
		minuteSelector.setBounds(196, 157, 53, 20);
		add(minuteSelector);
		
		lblTime = new JLabel("Time:");
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTime.setBounds(35, 157, 86, 20);
		add(lblTime);
		
		
		
		AMPMSelection = new ButtonGroup();
		
		rdbtnAm = new JRadioButton("AM");
		rdbtnAm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnAm.setBounds(119, 188, 46, 23);
		rdbtnAm.setSelected(true);
		
		rdbtnPm = new JRadioButton("PM");
		rdbtnPm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnPm.setBounds(163, 188, 46, 23);

		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);
		
		add(rdbtnAm);
		add(rdbtnPm);
		
		lblDeck = new JLabel("Deck:");
		lblDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDeck.setBounds(35, 189, 86, 20);
		add(lblDeck);
		
		String[] decks = {"default"};
		deck = new JComboBox(decks);
		deck.setFont(new Font("Tahoma", Font.PLAIN, 13));
		deck.setBounds(119, 222, 130, 20);
		add(deck);
		
		btnNewButton = new JButton("Add new deck");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNewButton.setBounds(119, 250, 130, 23);
		add(btnNewButton);
		
		btnSave = new JButton("Save");
		btnSave.setBounds(111, 294, 89, 23);
		add(btnSave);
		
		// Maps Create Game button to AddGameController class
		btnSave.addActionListener(new AddGameController(gamesModel, this));
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(12, 294, 89, 23);
		btnCancel.addActionListener(new CloseNewGameTabController(this));
		add(btnCancel); 
		
		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.setBounds(36, 95, 129, 23);
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		chckbxDeadline.setSelected(true);
		add(chckbxDeadline);
		


	}
	
	/**
	 * @return the txtNewMessage JTextField
	 */

	// creates a new game object to be sent to the database
	public Game getGameObject() {
		if(chckbxDeadline.isSelected()){
			Game newGame = new Game(getGameName(), new Date(), getDeadline());
			return newGame;
		}
		else{
			Game newGame = new Game(getGameName(), new Date(), new Date());
			return newGame;
		}
	}
	
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
							getHour(),
							getMinute(),
							0);
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

	public boolean checkFields() {
		if (gameNameText.getText() == "") return false;
		if(chckbxDeadline.isSelected()){
			if(datePicker.getModel().getValue() == null) return false;
			if(hourSelector.getSelectedItem() == null) return false;
			if(minuteSelector.getSelectedItem() == null) return false;
		}
		return true;
	}

	public void EnableOrDisableDeadline() {
		if (chckbxDeadline.isSelected()){
			datePicker.setEnabled(true);
			hourSelector.setEnabled(true);
			minuteSelector.setEnabled(true);
			rdbtnPm.setEnabled(true);
			rdbtnAm.setEnabled(true);
		}
		else {
			datePicker.setEnabled(false);
			hourSelector.setEnabled(false);
			minuteSelector.setEnabled(false);
			rdbtnPm.setEnabled(false);
			rdbtnAm.setEnabled(false);
		}
		
	}

	public void closeNewGameTab() {
		parentWindow.CloseNewGameTabFromMain();
	}
}
