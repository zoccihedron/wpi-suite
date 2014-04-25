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

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpPanel extends JPanel {
	private final JPanel helpPanel;
	private final JLabel titleText;
	
	public HelpPanel() {
		setLayout(new BorderLayout(0, 0));


		helpPanel = new JPanel();
		add(helpPanel, BorderLayout.CENTER);
		
		titleText = new JLabel("How to Use Planning Poker");
		helpPanel.add(titleText);
	}

	public boolean isReadyToClose() {
		return true;
	}
}
