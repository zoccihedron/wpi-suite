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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.UpdateUserPreferenceObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * Creates the user preference panel, which allows the users to denote whether or not
 * they want to be notified by email or IM and, if they do, input their email and IM.
 *
 * @author Code on Bleu
 * @version Apr 10, 2014
 */
public class UserPreferencesPanel extends JPanel {
	
	private final JPanel preferencesPanel;
	private final JPanel titlePanel;
	private JTextField emailField;
	private JTextField imField;
	private final JLabel lblTitle;
	private final JLabel lblAllow;
	private final JLabel lblEmailCheck;
	private final JLabel lblEmail;
	private final JLabel lblIM;
	private final JCheckBox checkBoxEmail;
	private final JCheckBox checkBoxIM;
	private final JButton btnSubmit;
	private final JButton btnCancel;
	private Pattern pattern;
	private Matcher matcher;
	private final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private boolean emailVerified = true;
	private final boolean imVerified = true;
	private UserPreferencesPanel userPreferencesPane = this;

	/**
	 * Create the panel.
	 */
	public UserPreferencesPanel() {
		setLayout(new BorderLayout(0, 0));
		
		preferencesPanel = new JPanel();
		add(preferencesPanel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{21, 31, 86, 40, 86, 0};
		gbl_panel.rowHeights = new int[]{39, 21, 33, 21, 23, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		preferencesPanel.setLayout(gbl_panel);
		
		lblAllow = new JLabel("Allow:");
		lblAllow.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblAllow = new GridBagConstraints();
		gbc_lblAllow.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllow.gridx = 1;
		gbc_lblAllow.gridy = 0;
		preferencesPanel.add(lblAllow, gbc_lblAllow);
		
		checkBoxEmail = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.anchor = GridBagConstraints.WEST;
		gbc_checkBox.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 1;
		preferencesPanel.add(checkBoxEmail, gbc_checkBox);
		
		lblEmail = new JLabel("Email: ");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 1;
		preferencesPanel.add(lblEmail, gbc_lblEmail);
		
		emailField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		preferencesPanel.add(emailField, gbc_textField);
		emailField.setColumns(10);
		emailField.setEnabled(false);
		
		lblEmailCheck = new JLabel("Error*");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 0);
		gbc_label.gridx = 4;
		gbc_label.gridy = 1;
		lblEmailCheck.setVisible(false);
		preferencesPanel.add(lblEmailCheck, gbc_label);
		
		checkBoxIM = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.anchor = GridBagConstraints.WEST;
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_1.gridx = 1;
		gbc_checkBox_1.gridy = 2;
		preferencesPanel.add(checkBoxIM, gbc_checkBox_1);
		
		lblIM = new JLabel("IM: ");
		GridBagConstraints gbc_IM = new GridBagConstraints();
		gbc_IM.anchor = GridBagConstraints.WEST;
		gbc_IM.insets = new Insets(0, 0, 5, 5);
		gbc_IM.gridx = 2;
		gbc_IM.gridy = 2;
		preferencesPanel.add(lblIM, gbc_IM);
		
		imField = new JTextField();
		imField.setToolTipText("");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		preferencesPanel.add(imField, gbc_textField_1);
		imField.setColumns(10);
		imField.setEnabled(false);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.NORTH;
		gbc_btnSubmit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 2;
		gbc_btnSubmit.gridy = 4;
		preferencesPanel.add(btnSubmit, gbc_btnSubmit);
		
		btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 5, 5);
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 4;
		preferencesPanel.add(btnCancel, gbc_btnCancel);
		
		
		titlePanel = new JPanel();
		add(titlePanel, BorderLayout.NORTH);
		
		lblTitle = new JLabel("User Preferences");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
		titlePanel.add(lblTitle);
		
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
		
		checkBoxIM.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(checkBoxIM.isSelected()){
					imField.setEnabled(true);
				}
				else{
					imField.setEnabled(false);
				}
				
			}
		});
		
		checkBoxEmail.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				if(checkBoxEmail.isSelected()){
					emailField.setEnabled(true);
					reportEmailValidation(emailField.getText());
				}
				else{
					emailField.setEnabled(false);
					emailField.setText("");
					lblEmailCheck.setVisible(false);
					emailVerified = true;
					configSubmitButton();
				}
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final MainViewTabController mainViewTabController = MainViewTabController.getInstance();
				mainViewTabController.closeTab(userPreferencesPane);
	
			}
			
		});
		
		btnSubmit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				User dummyUser = new User();
					
				dummyUser.setIdNum(0);
				dummyUser.setUserName("Username");
				dummyUser.setName("Name");
				dummyUser.setRole(Role.USER);
				dummyUser.setEmail(emailField.getText());
				dummyUser.setIM(imField.getText());
				dummyUser.setAllowEmail(checkBoxEmail.isSelected());
				dummyUser.setAllowIM(checkBoxIM.isSelected());
				Request request = Network.getInstance().makeRequest("Advanced/core/user/changeInPreference", HttpMethod.POST);
						request.setBody(dummyUser.toJSON());
				request.addObserver(new UpdateUserPreferenceObserver());
				request.send();

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
			return;
		}
		if(!isCorrectEmailFormat(email)){
			lblEmailCheck.setText("<html>Error: Email is not formatted correctly.<html>");
			lblEmailCheck.setVisible(true);
			emailVerified = false;
			configSubmitButton();
		}
		else{
			lblEmailCheck.setText("");
			lblEmailCheck.setVisible(false);
			emailVerified = true;
			configSubmitButton();
		}
	}
	
	/**
	 * Enables or disables the submit button depending on whether notifications are selected, and
	 * the validity of the selected notification systems.
	 *
	 */
	private void configSubmitButton(){
		if(emailVerified && imVerified){
			btnSubmit.setEnabled(true);
		}
		else btnSubmit.setEnabled(false);
	}

}
