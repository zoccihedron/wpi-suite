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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

public class ListDecksPanel  extends JScrollPane implements
TreeSelectionListener {
	
//	private JList<Deck> deckList;
//	private JLabel decksLabel;
	private JTree deckTree;

	public ListDecksPanel() {
		
//		this.refresh();
//		this.setLayout(new GridBagLayout());
//		final GridBagConstraints constraints = new GridBagConstraints();
//		
		this.setViewportView(deckTree);
		this.refresh();

		// Create the nodes.
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				refresh();
			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});
		
//		//label
//		decksLabel = new JLabel("Available Decks");
//		constraints.anchor = GridBagConstraints.NORTHWEST;
//		constraints.fill = GridBagConstraints.NONE;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		add(decksLabel, constraints);
//
//		// list
////		deckList = new JList<Deck>(d);
////		deckList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
////		deckList.setLayoutOrientation(JList.VERTICAL);
////		deckList.setVisibleRowCount(-1);
		
//		constraints.fill = GridBagConstraints.BOTH;
//		constraints.weightx = 1.0;
//		constraints.weighty = 1.0;
//		constraints.gridx = 0;
//		constraints.gridy = 0;
//		add(deckList, constraints);
		

		
//		this.setViewportView(deckList);

	}
		
	/**
	 * Refresh the list with the decks in the database
	 */
	public void refresh() {
		
		Deck deck1 = new Deck("Deck1", true, Arrays.asList(1, 1, 4));
		Deck deck2 = new Deck("Deck2", true, Arrays.asList(1, 2, 4));
		Deck deck3 = new Deck("Deck3", true, Arrays.asList(1, 3, 4));
		Deck deck4 = new Deck("Deck4", true, Arrays.asList(1, 4, 4));
		Deck deck5 = new Deck("Deck5", true, Arrays.asList(1, 5, 6));
		Deck[] decks = new Deck[]{deck1,deck2,deck3,deck4,deck5};
		
//		List<Deck> decks = ManageDeckController.getInstance().getDecks();
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode(
				"Decks"); // makes a starting node
	
		DefaultMutableTreeNode deckNode = null;
		DefaultMutableTreeNode createdDecksCategory = null;
		DefaultMutableTreeNode viewableDecksCategory = null;
		createdDecksCategory = new DefaultMutableTreeNode("Created Decks");
		viewableDecksCategory = new DefaultMutableTreeNode("Viewable Decks");
		
		final String user = ConfigManager.getInstance().getConfig().getUserName();
		
		for(Deck d : decks) {
			if(d.getDeckCreator().equals(user)) {
				deckNode = new DefaultMutableTreeNode(d);
				createdDecksCategory.add(deckNode);
			}
			else {
				deckNode = new DefaultMutableTreeNode(d);
				viewableDecksCategory.add(deckNode);
			}
		}
		
		top.add(createdDecksCategory);
		top.add(viewableDecksCategory);

		deckTree = new JTree(top); // create the tree with the top node as the top
		for (int i = 0; i < deckTree.getRowCount(); i++) {
			deckTree.expandRow(i);
		}
		
		deckTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		// tell it that it can only select one thing at a time
		deckTree.setToggleClickCount(0);

		deckTree.setCellRenderer(new CustomTreeCellRenderer());
		// set to custom cell renderer so that icons make sense
		deckTree.addTreeSelectionListener(this);

		deckTree.setDragEnabled(true);
		deckTree.setDropMode(DropMode.ON);

		this.setViewportView(deckTree); // make panel display the tree
		
//		final Request request = Network.getInstance().makeRequest("planningpoker/deck", HttpMethod.GET);
//		request.addObserver(new RequestObserver()
//		{
//			@Override
//			public void responseSuccess(IRequest iReq) {
//				ResponseModel r = iReq.getResponse();
//				final Deck[] decks = Deck.fromJsonArray(r.getBody());
//				populateDeckList(decks); 
//				
//			}
//			@Override
//			public void responseError(IRequest iReq) {
//				// TODO Auto-generated method stub	
//			}
//			@Override
//			public void fail(IRequest iReq, Exception exception) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		request.send();
		
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
//		
//		deckList = new JList<Deck>(decks);
//
//		this.setViewportView(deckList); //make panel display the list
	}

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
