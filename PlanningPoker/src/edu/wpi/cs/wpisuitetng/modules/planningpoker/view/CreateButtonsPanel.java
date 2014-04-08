/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;

/**
 * This class creates the buttons for creating
 * a new game and a new deck.
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CreateButtonsPanel extends ToolbarGroupView{
	
	private final JButton crtGameBtn = new JButton("<html>Create<BR />Game</html>");
	private final JButton crtDeckBtn = new JButton("<html>Manage<BR />Decks</html>");
	private final JPanel buttonPanel = new JPanel();
	
	/**
	 * Constructor for the CreateButtonsPanel
	 */
	public CreateButtonsPanel()
	{
		super("");
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(550);
		
		buttonPanel.add(crtGameBtn);
		buttonPanel.add(crtDeckBtn);
		buttonPanel.setOpaque(false);
		
		this.add(buttonPanel);
		this.setupListeners();
	}
	
	/**
	 * sets up the listeners for the panel
	 */
	private void setupListeners()
	{
		crtGameBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MainViewTabController.getInstance().createGameTab();
			}
		});
	}
}
