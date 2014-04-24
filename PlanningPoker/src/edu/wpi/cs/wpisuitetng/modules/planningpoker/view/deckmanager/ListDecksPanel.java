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

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class ListDecksPanel extends JScrollPane{
	
	private JList<Deck> deckList;

	public ListDecksPanel() {
		
		this.refresh();
		this.setViewportView(deckList);

	}
		
	/**
	 * Refresh the list with the decks in the database
	 */
	public void refresh() {
		
		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.GET);
		request.addObserver(new RequestObserver()
		{
			@Override
			public void responseSuccess(IRequest iReq) {
				ResponseModel r = iReq.getResponse();
				final Deck[] decks = Deck.fromJsonArray(r.getBody());
				populateDeckList(decks); 
				
			}
			@Override
			public void responseError(IRequest iReq) {
				// TODO Auto-generated method stub	
			}
			@Override
			public void fail(IRequest iReq, Exception exception) {
				// TODO Auto-generated method stub
				
			}
			
		});
		request.send();
		
	}

	/**
	 * get the selected deck
	 * @return the selected deck
	 */
	public Deck getSelected() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Set the selected deck to the one passed in (based on name)
	 * Do nothing if the deck is not in the database
	 * @param deck to be selected
	 */
	public void setSelected(Deck deck) {
		// TODO Auto-generated method stub

	}
	
	public void populateDeckList(Deck[] decks) {
		
		deckList = new JList<Deck>(decks);

		this.setViewportView(deckList); //make panel display the list
	}


	

}
