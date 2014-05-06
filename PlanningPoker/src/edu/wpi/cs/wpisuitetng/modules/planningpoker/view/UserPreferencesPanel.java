/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateUserPreferenceObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/**
 * Creates the user preference panel, which allows the users to denote whether or not
 * they want to be notified by email and, if they do, input their email.
 *
 * @author Code on Bleu
 * @version Apr 10, 2014
 */
@SuppressWarnings("serial")
public class UserPreferencesPanel extends JPanel {

	private final JPanel preferencesPanel;
	private final JPanel titlePanel;
	private final JPanel emailPanel;
	private final JPanel buttonPanel;
	private final JTextField emailField;
	private final JLabel lblTitle;
	private final JLabel lblAllow;
	private final JLabel lblEmailCheck;
	private final JLabel lblPrefstatus;
	private final JLabel lblEmail;
	private final JCheckBox checkBoxEmail;
	private final JButton btnSubmit;
	private final JButton btnCancel;
	private Pattern pattern;
	private Matcher matcher;
	private final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+([_A-Za-z0-9-])*+(\\.[_A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private boolean emailVerified = true;
	private final UserPreferencesPanel userPreferencesPane = this;
	private String initEmail = "";
	private boolean initAllowEmail;

	/**
	 * Create the panel.
	 */
	public UserPreferencesPanel() {
		setLayout(new BorderLayout(0, 0));


		preferencesPanel = new JPanel();
		add(preferencesPanel, BorderLayout.CENTER);
		final GridBagLayout gbl_panel = new GridBagLayout();

		gbl_panel.columnWidths = new int[]{15, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{39, 21, 33, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]
				{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]
				{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		preferencesPanel.setLayout(gbl_panel);

		titlePanel = new JPanel();
		final GridBagConstraints gbc_titlePanel = new GridBagConstraints();
		gbc_titlePanel.insets = new Insets(0, 33, 5, 0);
		gbc_titlePanel.gridwidth = 4;
		gbc_titlePanel.gridx = 1;
		gbc_titlePanel.gridy = 0;
		gbc_titlePanel.anchor = GridBagConstraints.WEST;
		preferencesPanel.add(titlePanel, gbc_titlePanel);
		lblTitle = new JLabel("User Preferences");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		titlePanel.add(lblTitle);
		
		lblAllow = new JLabel("Receive Notifications Via:");
		lblAllow.setVerticalAlignment(SwingConstants.BOTTOM);
		final GridBagConstraints gbc_lblAllow = new GridBagConstraints();
		gbc_lblAllow.insets = new Insets(0, 5, 5, 5);
		gbc_lblAllow.gridx = 1;
		gbc_lblAllow.gridy = 1;
		preferencesPanel.add(lblAllow, gbc_lblAllow);
		
		emailPanel = new JPanel();
		final GridBagConstraints gbc_emailPanel = new GridBagConstraints();
		gbc_emailPanel.gridy = 2;
		gbc_emailPanel.gridx = 1;
		gbc_emailPanel.gridwidth = 5;
		gbc_emailPanel.fill = GridBagConstraints.CENTER;
		gbc_emailPanel.anchor = GridBagConstraints.WEST;
		preferencesPanel.add(emailPanel, gbc_emailPanel);
		
		checkBoxEmail = new JCheckBox("");
		final GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.fill = GridBagConstraints.CENTER;
		gbc_checkBox.anchor = GridBagConstraints.WEST;
		gbc_checkBox.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 1;
		emailPanel.add(checkBoxEmail, gbc_checkBox);

		lblEmail = new JLabel("Email: ");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		final GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.fill = GridBagConstraints.CENTER;
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 1;
		emailPanel.add(lblEmail, gbc_lblEmail);

		emailField = new JTextField();
		final GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.CENTER;
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		emailPanel.add(emailField, gbc_textField);
		emailField.setColumns(18);
		emailField.setEnabled(false);

		lblEmailCheck = new JLabel("Error*");
		final GridBagConstraints gbc_lblEmailCheck = new GridBagConstraints();
		gbc_lblEmailCheck.anchor = GridBagConstraints.WEST;
		gbc_lblEmailCheck.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmailCheck.gridx = 4;
		gbc_lblEmailCheck.gridy = 1;
		lblEmailCheck.setVisible(false);
		emailPanel.add(lblEmailCheck, gbc_lblEmailCheck);
		
		buttonPanel = new JPanel();
		final GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.gridx = 1;
		gbc_buttonPanel.gridy = 3;
		gbc_buttonPanel.gridwidth = 5;
		gbc_buttonPanel.anchor = GridBagConstraints.WEST;
		gbc_buttonPanel.insets = new Insets(10, 0, 10, 0);
		
		btnSubmit = new JButton("Submit");
		final GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.WEST;
		gbc_btnSubmit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 0);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 0;
		btnSubmit.setEnabled(false);
		buttonPanel.add(btnSubmit, gbc_btnSubmit);

		btnCancel = new JButton("Cancel");
		final GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.anchor = GridBagConstraints.WEST;
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.gridx = 1;
		gbc_btnCancel.gridy = 0;
		buttonPanel.add(btnCancel, gbc_btnCancel);
		
		preferencesPanel.add(buttonPanel, gbc_buttonPanel);

		lblPrefstatus = new JLabel("");
		final GridBagConstraints gbc_lblPrefstatus = new GridBagConstraints();
		gbc_lblPrefstatus.gridwidth = 4;
		gbc_lblPrefstatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrefstatus.gridx = 1;
		gbc_lblPrefstatus.gridy = 4;
		gbc_lblPrefstatus.anchor = GridBagConstraints.WEST;
		preferencesPanel.add(lblPrefstatus, gbc_lblPrefstatus);


		ConfigManager.getInstance();
		final Request request = Network.getInstance().makeRequest("core/user/" + 
				ConfigManager.getConfig().getUserName(), HttpMethod.GET);
		request.addObserver(new UpdateUserPreferenceObserver(userPreferencesPane));
		request.send();




		emailField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				reportEmailValidation(emailField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				reportEmailValidation(emailField.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				reportEmailValidation(emailField.getText());
			}
		});

		/*
		 * Set up an action listener for the Email checkbox that will
		 * enable and disable the email text field depending on if it is selected.
		 * Use ItemListener so that when the mouse hovers over the checkbox,
		 * it would not be called.
		 */
		checkBoxEmail.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(checkBoxEmail.isSelected()){
					emailField.setEnabled(true);
					reportEmailValidation(emailField.getText());
				}
				else{
					emailField.setEnabled(false);
					lblEmailCheck.setVisible(false);
					emailVerified = true;
					configSubmitButton();
				}

			}
		});

		btnCancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mainViewTabController =
						MainViewTabController.getInstance();
				mainViewTabController.closeTab(userPreferencesPane);

			}

		});

		btnSubmit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				final User dummyUser = new User();

				dummyUser.setIdNum(0);
				dummyUser.setUserName("Username");
				dummyUser.setName("Name");
				dummyUser.setRole(Role.USER);
				dummyUser.setEmail(emailField.getText());
				dummyUser.setAllowEmail(checkBoxEmail.isSelected());
				final Request request =
						Network.getInstance().makeRequest("Advanced/core/user/changeInPreference",
								HttpMethod.POST);
				request.setBody(dummyUser.toJSON());
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
				userPreferencesPane.setCurrentEmail(
						emailField.getText(),
						checkBoxEmail.isSelected());
				final MainViewTabController mainViewTabController =
						MainViewTabController.getInstance();
				mainViewTabController.closeTab(userPreferencesPane);

			}

		});


		try {
			Image img = ImageIO.read(getClass().getResource("submit.png"));
			btnSubmit.setIcon(new ImageIcon(img));

			img = ImageIO.read(getClass().getResource("redX.png"));
			btnCancel.setIcon(new ImageIcon(img));


		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}

		emailField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final KeyEvent e) {
				super.keyTyped(e);

				// Check if the user pressed Enter
				if (e.getKeyChar() == '\n') {
					btnSubmit.doClick(1);
				}
			}
		});
		
		emailField.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent e) {	
				lblEmailCheck.setVisible(false);
			}

			@Override
			public void focusLost(FocusEvent e) {
				lblEmailCheck.setVisible(true);
			}

		});

	}

	/**
	 * Determines if an email matches the regex pattern. Emails should be 
	 * in the form of <code>sometext@email.com</code> or <code>some.text@email.net</code>.
	 *
	 * @param email the email string
	 * @return true if the string matches the pattern, false otherwise
	 */
	private boolean isCorrectEmailFormat(String email){
		pattern = Pattern.compile(emailPattern);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/**
	 * Configures the submit button depending on the validity of the email, and
	 * if not valid will 
	 *
	 * @param email the email to validate
	 */
	private void reportEmailValidation(String email){
		if(email.equals("")){
			lblEmailCheck.setText("<html>Please enter an email.<html>");
			lblEmailCheck.setVisible(true);
			emailVerified = false;
			configSubmitButton();
			printStatus();
			return;
		}
		if(!isCorrectEmailFormat(email)){
			lblEmailCheck.setText("<html>*Error: Email is not formatted correctly.<html>");
			lblEmailCheck.setVisible(true);
			emailVerified = false;
			configSubmitButton();
			printStatus();
		}
		else{
			lblEmailCheck.setText("");
			lblEmailCheck.setVisible(false);
			emailVerified = true;
			configSubmitButton();
			printStatus();
		}
	}

	/**
	 * Enables or disables the submit button depending on whether notifications are selected, and
	 * the validity of the selected notification systems.
	 *
	 */
	private void configSubmitButton(){
		if(!changeHasBeenMade()){
			btnSubmit.setEnabled(false);
		} else {
			if(emailVerified){
				btnSubmit.setEnabled(true);
			}
			else btnSubmit.setEnabled(false);

		}
	}

	/**
	 * Sets the current Email for the user, and displays the current values in the 
	 * textfield and the checkbox.
	 *
	 * @param currentEmail the user's current stored email
	 * @param allowEmail the user's current stored email preference
	 */
	public void setCurrentEmail(String currentEmail, boolean allowEmail){
		initEmail = currentEmail;
		emailField.setText(currentEmail);
		checkBoxEmail.setSelected(allowEmail);
		initAllowEmail = allowEmail;
	}

	/**
	 * Checks if the preference tab is ready to close, using a popup if there
	 * are unsaved changes on the panel in the text fields
	 *
	 * @return whether the tab is ready to close or not.
	 */
	public boolean isReadyToClose() {
		boolean result = true;
		if(changeHasBeenMade()){
			final Object[] options = {"Yes", "No"};
			final int i = JOptionPane.showOptionDialog(this, 
					"Any unsaved changes will be lost, would you like to exit anyways?",
					"Exit?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null, options, options[1]);

			result = (i == 0);
		}
		return result;
	}

	/**
	 * @param initEmail the initEmail to set
	 */
	public void setInitEmail(String initEmail) {
		this.initEmail = initEmail;
	}

	/**
	 * @param initAllowEmail the initAllowEmail to set
	 */
	public void setInitAllowEmail(boolean initAllowEmail) {
		this.initAllowEmail = initAllowEmail;
	}

	/**
	 * 
	 * @return true if any change has been made
	 */
	public boolean changeHasBeenMade(){
		return (!(initEmail != null
				&& initEmail.equals(emailField.getText()) 
				&& initAllowEmail == checkBoxEmail.isSelected()));
	}
	
	/**
	 * @return the btnSubmit
	 */
	public JButton getBtnSubmit() {
		return btnSubmit;
	}

	/**
	 * Changes the status label for the panel, to indicate if you can
	 * save changes or not. This will also indicate if changes were
	 * made.
	 */
	public void printStatus(){
		if(changeHasBeenMade()){
			if(emailVerified){
				lblPrefstatus.setText("");
			}
			else {
				lblPrefstatus.setText("<html>Please correct changes to your preferences</html>");
				lblPrefstatus.setBackground(Color.RED);
			}
		}
		else {
			lblPrefstatus.setText("<html>You have made no changes to your preferences.</html>");
			lblPrefstatus.setBackground(Color.BLUE);
		}
	}
}
