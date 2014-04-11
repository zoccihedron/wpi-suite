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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ChangeDeadline;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is a JPanel. It contains all the fields needed to create a new
 * game
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class CreateGameInfoPanel extends JPanel {
	private final MainViewTabController mainViewTabController;
	private final NewGamePanel parentPanel;

	private final JLabel lblName;
	private final JTextField gameNameText;
	private final JTextArea description;
	private final JLabel lblDeadline;
	private final JDatePickerImpl datePicker;
	private final JLabel lblTime;
	private final JComboBox hourSelector;
	private final JComboBox minuteSelector;
	private final JRadioButton rdbtnAm;
	private final JRadioButton rdbtnPm;
	private final ButtonGroup AMPMSelection;
	private final JLabel lblDeck;
	private final JComboBox deck;
	private final JCheckBox chckbxDeadline;
	private Game editingGame;
	private final JLabel lblTitle;
	private final JLabel lblDescription;

	/**
	 * This constructor is to be used when starting from a new game
	 * 
	 * @param parent
	 *            the parent panel for this panel
	 */
	public CreateGameInfoPanel(NewGamePanel parent) {
		mainViewTabController = MainViewTabController.getInstance();
		parentPanel = parent;
		setBounds(5, 5, 307, 393);
		this.setLayout(new GridBagLayout());

		// Adds the fields and button to the main panel.
		gameNameText = new JTextField();
		gameNameText.setText("Game " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		final Border jtextFieldBorder = gameNameText.getBorder();

		description = new JTextArea();
		description.setBorder(jtextFieldBorder);


		lblTitle = new JLabel("Game Information");

		lblName = new JLabel("Name:       ");

		lblDeadline = new JLabel("Deadline:");

		// creates a date picker and sets its position
		final UtilDateModel model = new UtilDateModel();
		final JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);

		final String[] hours = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		hourSelector = new JComboBox(hours);

		final String[] minutes = { "00", "15", "30", "45" };

		minuteSelector = new JComboBox(minutes);

		lblTime = new JLabel("Time:");

		AMPMSelection = new ButtonGroup();
		rdbtnAm = new JRadioButton("AM");
		rdbtnPm = new JRadioButton("PM");
		rdbtnAm.setSelected(true);
		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);

		lblDeck = new JLabel("Deck:");

		final String[] decks = { "default" };
		deck = new JComboBox(decks);

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		chckbxDeadline.setSelected(false);
		EnableOrDisableDeadline();
		lblDescription = new JLabel("Description:");
		panelSetup();
	}

	/**
	 * Constructor for Edit Game Panel
	 * 
	 * @param parent
	 *            the parent panel
	 * @param passedInGame
	 *            the game from whose info we create the pane
	 */
	public CreateGameInfoPanel(NewGamePanel parent, Game passedInGame) {
		mainViewTabController = MainViewTabController.getInstance();
		parentPanel = parent;
		editingGame = passedInGame;
		setBounds(5, 5, 307, 393);
		this.setLayout(new GridBagLayout());

		lblTitle = new JLabel("Game Information");

		lblName = new JLabel("Name:       ");
		gameNameText = new JTextField();
		gameNameText.setText(editingGame.getName());

		final Border jtextFieldBorder = gameNameText.getBorder();


		lblDescription = new JLabel("Description:");
		description = new JTextArea();
		description.setBorder(jtextFieldBorder);
		description.setText(editingGame.getDescription());
		description.setBorder(jtextFieldBorder);

		lblDeck = new JLabel("Deck:");
		final String[] decks = { "default" };
		deck = new JComboBox(decks);

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(new ChangeDeadline(this));
		lblDeadline = new JLabel("Deadline:");

		// creates a date picker and sets its position
		final UtilDateModel model = new UtilDateModel();
		final Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(editingGame.getEnd());
		model.setDate(tempCalendar.get(Calendar.YEAR),
				tempCalendar.get(Calendar.MONTH),
				tempCalendar.get(Calendar.DATE));
		model.setSelected(true);
		final JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);

		lblTime = new JLabel("Time:");
		final String[] hours = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		hourSelector = new JComboBox(hours);
		hourSelector.setSelectedItem(getHourStringFromCalendar(tempCalendar
				.get(Calendar.HOUR)));
		final String[] minutes = { "00", "15", "30", "45" };
		minuteSelector = new JComboBox(minutes);
		minuteSelector.setSelectedItem(getMinuteStringFromCalendar(tempCalendar
				.get(Calendar.MINUTE)));

		AMPMSelection = new ButtonGroup();
		rdbtnAm = new JRadioButton("AM");
		AMPMSelection.add(rdbtnAm);
		rdbtnPm = new JRadioButton("PM");
		AMPMSelection.add(rdbtnPm);

		if (tempCalendar.get(Calendar.AM_PM) == Calendar.PM) {
			rdbtnAm.setSelected(false);
			rdbtnPm.setSelected(true);
		} else {
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
	public void panelSetup() {
		// DEFINE CONSTAINTS
		final GridBagConstraints constraints = new GridBagConstraints();

		final JPanel fakePanel1 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel1, constraints);
		final JPanel fakePanel2 = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 4;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(fakePanel2, constraints);

		// GAME INFORMATION LABEL
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

		// NAME LABEL
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 1;
		add(lblName, constraints);

		// NAME FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 1;
		add(gameNameText, constraints);

		// DESCRIPTION LABEL
		lblDescription.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 10;
		add(lblDescription, constraints);

		// DESCRIPTION
		description.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 0.90;
		constraints.weighty = 0.90;
		constraints.gridx = 1;
		constraints.gridy = 11;
		add(description, constraints);

		// DECK LABEL
		lblDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 9;
		add(lblDeck, constraints);

		// DECK SELECTOR
		deck.setFont(new Font("Tahoma", Font.PLAIN, 13));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 9;
		add(deck, constraints);

		// DEADLINE CHECKBOX
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 5;
		add(chckbxDeadline, constraints);

		// DEADLINE LABEL
		lblDeadline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 6;
		constraints.anchor = GridBagConstraints.WEST;
		add(lblDeadline, constraints);

		// DATE PICKER
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 6;
		add(datePicker, constraints);

		// TIME LABEL
		lblTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 7;
		add(lblTime, constraints);

		// HOUR SELECTOR
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

		// MINUTE SELECTOR
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

		// AM BUTTON
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 8;
		rdbtnAm.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnAm, constraints);

		// PM Button
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
	 * A function to convert an int minute to a string minute (rounded to the
	 * nearest quarter-hour)
	 * 
	 * @param minute
	 *            the int value from a calendar object
	 * @return the string corresponding to the minuteSelector object
	 */
	private static String getMinuteStringFromCalendar(int minute) {
		System.out.println("Minute:" + minute);
		String result = "00";
		if (minute < 15) {
			result = "00";
		} else if (minute < 30) {
			result = "15";
		} else if (minute < 45) {
			result = "30";
		} else if (minute < 60) {
			result = "45";
		}
		return result;
	}

	/**
	 * A function to convert an int hour to a string minute
	 * 
	 * @param hour
	 *            the int value from a calendar object
	 * @return the string corresponding to the hourSelector object
	 */
	private static String getHourStringFromCalendar(int hour) {
		System.out.println("Hour:" + hour);
		hour %= 12;
		System.out.println("Hour % 12:" + hour);
		String result = "";
		if (hour < 10) {
			result = "0" + String.valueOf(hour);
		} else {
			result = String.valueOf(hour);
		}
		return result;
	}

	/**
	 * Checks to see if the fields for the game are selected properly
	 * '<html>error</html>' format needed to allow word wrap in the error label
	 * 
	 * @return check - true if the fields are selected properly, otherwise false
	 */
	public boolean checkFields() {
		boolean result = true;
		if (gameNameText.getText().trim().isEmpty()) {
			reportError("<html>*Error: Please choose a name!</html>");
			result = false;
		}
		if (chckbxDeadline.isSelected()) {
			if (datePicker.getModel().getValue() == null) {
				reportError("<html>*Error: Please choose a date or turn off the deadline.</html>");
				result = false;
			}
			if (getDeadline().compareTo(new Date()) <= 0) {
				reportError("<html>*Error: The deadline must not be in the past.</html>");
				result = false;
			}
		}
		if (parentPanel.getGameRequirements().size() == 0) {
			reportError("<html>*Error: Pick at least one requirement.</html>");
			result = false;
		}
		return result;
	}

	/**
	 * Will switch the deadline fields to enabled or disabled based on the
	 * Deadline checkbox
	 */
	public void EnableOrDisableDeadline() {
		datePicker.setEnabled(chckbxDeadline.isSelected());
		datePicker.setVisible(chckbxDeadline.isSelected());
		hourSelector.setEnabled(chckbxDeadline.isSelected());
		hourSelector.setVisible(chckbxDeadline.isSelected());
		minuteSelector.setEnabled(chckbxDeadline.isSelected());
		minuteSelector.setVisible(chckbxDeadline.isSelected());
		rdbtnPm.setEnabled(chckbxDeadline.isSelected());
		rdbtnPm.setVisible(chckbxDeadline.isSelected());
		rdbtnAm.setEnabled(chckbxDeadline.isSelected());
		rdbtnAm.setVisible(chckbxDeadline.isSelected());
		lblDeadline.setVisible(chckbxDeadline.isSelected());
		lblTime.setVisible(chckbxDeadline.isSelected());
	}

	/**
	 * Will switch the deadline fields to enabled or disabled based on the value
	 * passed
	 * 
	 * @param hasDeadline
	 *            variable from the game object that sets the condition for the
	 *            setting
	 */
	private void ForceEnableOrDisableDeadline(boolean hasDeadline) {
		chckbxDeadline.setSelected(hasDeadline);
		datePicker.setEnabled(hasDeadline);
		datePicker.setVisible(hasDeadline);
		hourSelector.setEnabled(hasDeadline);
		hourSelector.setVisible(hasDeadline);
		minuteSelector.setEnabled(hasDeadline);
		minuteSelector.setVisible(hasDeadline);
		rdbtnPm.setEnabled(hasDeadline);
		rdbtnPm.setVisible(hasDeadline);
		rdbtnAm.setEnabled(hasDeadline);
		rdbtnAm.setVisible(hasDeadline);
		lblDeadline.setVisible(hasDeadline);
		lblTime.setVisible(hasDeadline);
	}

	/**
	 * Fills the text box with a red warning based on the error Message
	 * 
	 * @param error
	 *            the message to be printed, should be in <html>text</html>
	 *            format
	 */
	public void reportError(String error) {
		parentPanel.reportError(error);
	}

	/**
	 * Fills the text box with a green message based on the input
	 * 
	 * @param message
	 *            the message to be printed, should be in <html>text</html>
	 *            format
	 */
	public void reportMessage(String message) {
		parentPanel.reportMessage(message);
	}

	/**
	 * Sends the signal to Mainview to close the NewgameTab
	 */
	public void closeNewGameTab() {
		mainViewTabController.closeTab(parentPanel);
	}

	/**
	 * Fills in the game object with the necessary data
	 * 
	 * @return newGame
	 */
	public Game getGameObject() {
		int id = 0;
		if (editingGame != null) {
			id = editingGame.getId();
		}

		final Game newGame = new Game(getGameName(), new Date(), new Date());
		newGame.setRequirements(parentPanel.getGameRequirements());
		newGame.setDescription(description.getText());
		newGame.setId(id);

		if (chckbxDeadline.isSelected()) {
			newGame.setHasDeadline(true);
			newGame.setEnd(getDeadline());
		} else {
			newGame.setHasDeadline(false);
		}
		return newGame;
	}

	/**
	 * Returns the name from the Name Text Field
	 * 
	 * @return the name
	 */
	private String getGameName() {
		return gameNameText.getText();
	}

	/**
	 * returns deadline value including calendar date and time
	 * 
	 * @return Date
	 */
	public Date getDeadline() {
		final Date selectedDate = (Date) datePicker.getModel().getValue();
		final Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(selectedDate);

		tempCalendar.set(tempCalendar.get(Calendar.YEAR),
				tempCalendar.get(Calendar.MONTH),
				tempCalendar.get(Calendar.DATE), getHour(), getMinute(), 0);

		return tempCalendar.getTime();
	}

	/**
	 * gets hour value from hour selector; converts to 24 hour format
	 * 
	 * @return hourInt
	 */
	public int getHour() {
		final String hourString = (String) hourSelector.getSelectedItem();
		final int hourInt = Integer.parseInt(hourString);

		if (rdbtnPm.isSelected()) {
			return hourInt + 12;
		} 

		return hourInt;
	}

	/**
	 * gets minute value from minute selector
	 * 
	 * @return minuteInt
	 */
	public int getMinute() {
		final String minuteString = (String) minuteSelector.getSelectedItem();
		final int minuteInt = Integer.parseInt(minuteString);
		return minuteInt;
	}
}
