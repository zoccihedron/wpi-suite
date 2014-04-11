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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Creates the user preference panel, which allows the users to denote whether or not
 * they want to be notified by email or IM and, if they do, input their email and IM.
 *
 * @author Code on Bleu
 * @version Apr 10, 2014
 */
public class UserPreferencesPanel extends JPanel {
	private JTextField emailField;
	private JTextField imField;

	/**
	 * Create the panel.
	 */
	public UserPreferencesPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{21, 31, 86, 40, 86, 0};
		gbl_panel.rowHeights = new int[]{39, 21, 33, 21, 23, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblAllow = new JLabel("Allow:");
		lblAllow.setVerticalAlignment(SwingConstants.BOTTOM);
		GridBagConstraints gbc_lblAllow = new GridBagConstraints();
		gbc_lblAllow.insets = new Insets(0, 0, 5, 5);
		gbc_lblAllow.gridx = 1;
		gbc_lblAllow.gridy = 0;
		panel.add(lblAllow, gbc_lblAllow);
		
		JCheckBox checkBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.anchor = GridBagConstraints.WEST;
		gbc_checkBox.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox.gridx = 1;
		gbc_checkBox.gridy = 1;
		panel.add(checkBox, gbc_checkBox);
		
		JLabel lblEmail = new JLabel("Email: ");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.WEST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 2;
		gbc_lblEmail.gridy = 1;
		panel.add(lblEmail, gbc_lblEmail);
		
		emailField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel.add(emailField, gbc_textField);
		emailField.setColumns(10);
		
		JCheckBox checkBox_1 = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.anchor = GridBagConstraints.WEST;
		gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_checkBox_1.gridx = 1;
		gbc_checkBox_1.gridy = 2;
		panel.add(checkBox_1, gbc_checkBox_1);
		
		JLabel IM = new JLabel("IM: ");
		GridBagConstraints gbc_IM = new GridBagConstraints();
		gbc_IM.anchor = GridBagConstraints.WEST;
		gbc_IM.insets = new Insets(0, 0, 5, 5);
		gbc_IM.gridx = 2;
		gbc_IM.gridy = 2;
		panel.add(IM, gbc_IM);
		
		imField = new JTextField();
		imField.setToolTipText("");
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 2;
		panel.add(imField, gbc_textField_1);
		imField.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.anchor = GridBagConstraints.NORTH;
		gbc_btnSubmit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
		gbc_btnSubmit.gridx = 2;
		gbc_btnSubmit.gridy = 4;
		panel.add(btnSubmit, gbc_btnSubmit);
		
		JButton btnCancel = new JButton("Cancel");
		GridBagConstraints gbc_btnCancel = new GridBagConstraints();
		gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancel.anchor = GridBagConstraints.NORTH;
		gbc_btnCancel.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCancel.gridx = 3;
		gbc_btnCancel.gridy = 4;
		panel.add(btnCancel, gbc_btnCancel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.NORTH);
		
		JLabel lblPreferences = new JLabel("Preferences");
		lblPreferences.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(lblPreferences);

	}

}
