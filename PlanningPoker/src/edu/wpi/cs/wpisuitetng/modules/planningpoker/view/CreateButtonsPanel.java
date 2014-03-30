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

import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;

/**
 * This class creates the buttons for creating
 * a new game and a new deck.
 * 
 * @author Ryan Baker
 *
 */
@SuppressWarnings("serial")
public class CreateButtonsPanel extends ToolbarGroupView{
	
	private JButton crtGameBtn = new JButton("<html>Create<BR />Game</html>");
	private JButton crtDeckBtn = new JButton("<html>Create<BR />Deck</html>");
	//private GridLayout lyt = new GridLayout(2,5);
	private final JPanel buttonPanel = new JPanel();
	

	public CreateButtonsPanel()
	{
		
		super("");
		
		this.buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		this.setPreferredWidth(350);
		
		buttonPanel.add(crtGameBtn);
		buttonPanel.add(crtDeckBtn);
		buttonPanel.setOpaque(false);
		
		this.add(buttonPanel);
		
		/*
		this.setLayout(lyt);
		this.add(crtGameBtn);
		
		// create empty space
		for(int i=3; i<this.lyt.getColumns();i++)
		{
			this.add(new Box(0));
		}
		
		// change this to add the crtDeckBtn later (once crtGameBtn is functional)
		this.add(new JButton("placeholder, not functional"));
		*/
	}
}
