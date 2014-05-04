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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.newgame.ChangeDeadlineVisibilityController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is a JPanel. It contains all the fields needed to create a new
 * game
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial", "rawtypes", "unchecked"})
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
	private final JComboBox deckBox;
	private final JCheckBox chckbxDeadline;
	private Game editingGame;
	private final JLabel lblTitle;
	private final JLabel lblDescription;
	private Timer verificationChecker;
	private final List<Deck> decks;
	
	//Saved fields for checking page editing
	private String defaultName;
	private String defaultDescription;
	private Date defaultDate;
	private boolean defaultDeadlineCheck;
	private List<Integer> defaultReqs;
	private Deck defaultDeckObject;

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
		final Date now = new Date();
		gameNameText = new JTextField();
		gameNameText.setText("Game " + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(now));
		final Border jtextFieldBorder = gameNameText.getBorder();
		description = new JTextArea();
		description.setBorder(jtextFieldBorder);

		lblTitle = new JLabel("Game Information");
		lblName = new JLabel("Name:       ");

		lblDeadline = new JLabel("Deadline:");

		// creates a date picker and sets its position
		final UtilDateModel model = new UtilDateModel();

		final Calendar tempCalendar = new GregorianCalendar();
		tempCalendar.setTime(now);
		model.setDate(tempCalendar.get(Calendar.YEAR),
				tempCalendar.get(Calendar.MONTH),
				tempCalendar.get(Calendar.DATE) + 1);
		model.setSelected(true);
		final JDatePanelImpl datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);

		// creates fields for time and sets defaults
		final String[] hours = { "01", "02", "03", "04", "05", "06", "07",
				"08", "09", "10", "11", "12" };
		hourSelector = new JComboBox(hours);
		hourSelector.setSelectedItem(getHourStringFromCalendar(tempCalendar
				.get(Calendar.HOUR)));
		final String[] minutes = { "00", "15", "30", "45" };

		minuteSelector = new JComboBox(minutes);
		minuteSelector.setSelectedItem(getMinuteStringFromCalendar(tempCalendar
				.get(Calendar.MINUTE)));
		lblTime = new JLabel("Time:");

		AMPMSelection = new ButtonGroup();
		rdbtnAm = new JRadioButton("AM");
		rdbtnPm = new JRadioButton("PM");
		rdbtnAm.setSelected(true);
		AMPMSelection.add(rdbtnAm);
		AMPMSelection.add(rdbtnPm);
		if (tempCalendar.get(Calendar.AM_PM) == Calendar.PM) {
			rdbtnAm.setSelected(false);
			rdbtnPm.setSelected(true);
		} else {
			rdbtnAm.setSelected(true);
			rdbtnPm.setSelected(false);
		}

		// creates deck selector and sets it to default deck
		final List<Deck> allDecks = ManageDeckController.getInstance().getDecks();
		final Deck textEntry = new Deck("Text Entry", true, new ArrayList<Integer>());
		textEntry.setId(-1);
		final Deck defaultDeck = new Deck("default", true, new ArrayList<Integer>());
		defaultDeck.setId(-2);
		decks = new ArrayList<Deck>();
		lblDeck = new JLabel("Deck:");

		for(Deck d:allDecks){
			if(d.isUsable()){
				decks.add(d);
			}
		}
		decks.add(textEntry);
		decks.add(defaultDeck);
		deckBox = new JComboBox(decks.toArray());
		deckBox.setSelectedItem(defaultDeck);

		deckBox.setToolTipText("Please select a deck to be used in the game.");

		// creates deadline checkbox
		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(
				new ChangeDeadlineVisibilityController(this));
		chckbxDeadline.setSelected(true);
		lblDescription = new JLabel("Description:");

		// adds constraints
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

		// Adds the fields and button to the main panel and
		// fills them with game information
		lblTitle = new JLabel("Game Information");

		lblName = new JLabel("Name:       ");
		gameNameText = new JTextField(editingGame.getName());

		final Border jtextFieldBorder = gameNameText.getBorder();

		lblDescription = new JLabel("Description:");
		description = new JTextArea();
		description.setBorder(jtextFieldBorder);
		description.setText(editingGame.getDescription());
		description.setBorder(jtextFieldBorder);


		// creates deck selector and sets it to default deck
		final List<Deck> allDecks = ManageDeckController.getInstance().getDecks();
		final Deck textEntry = new Deck("Text Entry", true, new ArrayList<Integer>());
		textEntry.setId(-1);
		final Deck defaultDeck = new Deck("default", true, new ArrayList<Integer>());
		defaultDeck.setId(-2);
		decks = new ArrayList<Deck>();
		lblDeck = new JLabel("Deck:");
		for(Deck d:allDecks){
			if(d.isUsable()){
				decks.add(d);
			}
		}
		decks.add(textEntry);
		decks.add(defaultDeck);
		deckBox = new JComboBox(decks.toArray());
		for(Deck d: decks){
			if(passedInGame.getDeck() == d.getId())
			{
				deckBox.setSelectedItem(d);
			}
		}

		chckbxDeadline = new JCheckBox("Deadline?");
		chckbxDeadline.addActionListener(
				new ChangeDeadlineVisibilityController(this));
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

		// creates time selector and fills it with selected time
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

		// sets deadline checkbox to game value
		ForceEnableOrDisableDeadline(editingGame.isHasDeadline());

		chckbxDeadline.addActionListener(
				new ChangeDeadlineVisibilityController(this));
		panelSetup();
	}

	/**
	 * Sets all the grid components for either constructor
	 */
	public void panelSetup() {
		// DEFINE CONSTRAINTS
		final GridBagConstraints constraints = new GridBagConstraints();
		
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
		final JScrollPane descriptionScrollPane = new JScrollPane(description); 
		description.setEditable(true);
		description.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;

		// DESCRIPTION SCROLL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 4;
		constraints.weightx = 1.00;
		constraints.weighty = 0.90;
		constraints.gridx = 1;
		constraints.gridy = 11;
		constraints.insets = new Insets(0, 0, 0, 0);
		add(descriptionScrollPane, constraints);

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
		deckBox.setFont(new Font("Tahoma", Font.PLAIN, 13));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 2;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 9;
		add(deckBox, constraints);

		// DEADLINE CHECKBOX
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 5;
		chckbxDeadline.setBorder(new EmptyBorder(0, 10, 0, 0));
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
		datePicker.setMaximumSize(new Dimension(100, 23));
		datePicker.setMinimumSize(new Dimension(100, 23));
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
		constraints.insets = new Insets(2, 0, 0, 0);
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
		constraints.insets = new Insets(2, 0, 0, 0);
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

		verificationChecker = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				parentPanel.disableOrEnableButtons(checkFields());
			}
		});
		verificationChecker.start();
		initDefaults();
		checkFields();
		
		gameNameText.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\n') {
					description.requestFocus();
				}
			}
		});
		
		description.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\t' && !e.isShiftDown()) {
					parentPanel.switchFocusToTable();
				}
				else if (e.getKeyChar() == '\t' && e.isShiftDown()) {
					deckBox.requestFocus();
				}
			}
		});
		
		chckbxDeadline.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\n') {
					chckbxDeadline.doClick();
				}
			}
		});
		
		rdbtnAm.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\n') {
					rdbtnAm.doClick();
				}
			}
		});
		
		rdbtnPm.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\n') {
					rdbtnPm.doClick();
				}
			}
		});

		EventQueue.invokeLater(new Runnable() {

		   @Override
		     public void run() {
		         gameNameText.grabFocus();
		         gameNameText.requestFocusInWindow();//or inWindow
		         gameNameText.setSelectionStart(0);
		         gameNameText.setSelectionEnd(gameNameText.getText().length());
		     }
		});
	}

	/**
	 * Calls the function for changing the buttons' enabled status on the parent.
	 * Also stops if false and starts if true the timer from changing the status of the buttons.
	 * @param check true to enable; false to disable
	 */
	public void disableOrEnableButtonsOnParent(boolean check){
		if (check) {
			verificationChecker.start();
		}else{
			verificationChecker.stop();
		}
		parentPanel.disableOrEnableButtons(check);
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
		hour %= 12;
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

		reportError(" ");
		boolean result = true;
		parentPanel.displayErrorBorders(false);
		parentPanel.toolTipChanger("Click here to start the game.", "Click here to save the game.");
		if (parentPanel.getGameRequirements().size() == 0 && result) {
			reportError("<html>*Add at least one requirement.</html>");
			result = false;
			parentPanel.displayErrorBorders(true);

			parentPanel.toolTipChanger("Please add at least one requirement.",
					"Please add at least one requirement.");
		}

		datePicker.setBorder(null);
		if (chckbxDeadline.isSelected()) {
			if (datePicker.getModel().getValue() == null) {
				datePicker.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
				reportError("<html>*Please choose a date or turn off the deadline.</html>");
				result = false;
			} else if (getDeadline().compareTo(new Date()) <= 0) {
				datePicker.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
				reportError("<html>*The deadline must not be in the past.</html>");
				result = false;
			}
		}

		if (gameNameText.getText().trim().isEmpty()) {
			reportError("<html>*A name is required.</html>");
			gameNameText.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			result = false;
		} else {
			gameNameText.setBorder(description.getBorder());
			
		}

		return result;
	}

	/**
	 * Sets the defaults for the fields at game start up.
	 */
	public void initDefaults(){
		//Saved fields for checking page editing
		defaultName = gameNameText.getText();
		defaultDescription = description.getText();
		defaultDeadlineCheck = chckbxDeadline.isSelected();
		if(defaultDeadlineCheck){
			if (datePicker.getModel().getValue() == null) {
				defaultDate = null;
			} else{
				defaultDate = getDeadline();
			}
		} else {
			defaultDate = null;
		}
		defaultReqs = parentPanel.getGameRequirements();
		defaultDeckObject = (Deck) deckBox.getSelectedItem();
	}

	/**
	 * Checks to see if the page has changed
	 * @return true if the page has changed
	 */
	public boolean isPageEdited(){
		boolean result = defaultName.equals(gameNameText.getText());
		result &= defaultDescription.equals(description.getText());
		result &= (defaultDeadlineCheck == chckbxDeadline.isSelected());
		Date tempDate = null;
		if(result && defaultDeadlineCheck){
			if(!(datePicker.getModel().getValue() == null)){
				tempDate = getDeadline();
			}

			if (defaultDate != null){
				result &= defaultDate.equals(tempDate);
			}
		}

		result &= defaultDeckObject.equals(deckBox.getSelectedItem());
		result &= defaultReqs.equals(parentPanel.getGameRequirements());
		return !result;
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
	 * 
	 * @param checkForNewReq whether or not to check if a new req is being made
	 */
	public void closeNewGameTab(boolean checkForNewReq) {
		boolean readyToClose = true;
		if(!isPageEdited() && checkForNewReq) {
			if(getReqPanel().isCreatingNewReq()){
				final Object options[] = {
						"Yes", "No"
				};
				final int i = JOptionPane.showOptionDialog(getParent().getParent(), 
						"Your new requirement will not be saved, would you like to exit anyways?",
						"Exit?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, options, options[1]);
				readyToClose = (i == 0);
			}
		}
		if(readyToClose) mainViewTabController.closeTab(parentPanel);
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

		final Game newGame = new Game(getGameName(), new Date(), new Date(), -2);
		newGame.setRequirements(parentPanel.getGameRequirements());
		newGame.setDescription(description.getText());
		newGame.setId(id);
		newGame.setDeck(((Deck)deckBox.getSelectedItem()).getId());
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
		int hourInt = Integer.parseInt(hourString);

		if (rdbtnPm.isSelected()) {
			hourInt += 12;
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

	/**
	 * Returns the SelectRequirementsPanel associated with the NewGamePanel
	 *
	 * @return the associated SelectRequirementsPanel
	 */
	public SelectRequirementsPanel getReqPanel(){
		return parentPanel.getSelectRequirementsPanel();
	}
}
