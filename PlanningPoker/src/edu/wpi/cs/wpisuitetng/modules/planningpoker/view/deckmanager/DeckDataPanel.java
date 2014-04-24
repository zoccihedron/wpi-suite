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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import javax.swing.JPanel;

/**
 * Panel on the right hand side of the deck manager. Holds
 * the list of decks panel and the create deck button panel.
 * @author Code On Bleu
 *
 */
public class DeckDataPanel extends JPanel{
	private ListDecksPanel listDecksPanel;
	
	public DeckDataPanel(){
		listDecksPanel = new ListDecksPanel();
		add(listDecksPanel);
	}
	public ListDecksPanel getListDecksPanel() {
		return listDecksPanel;
	}

}
