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
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.ManageDeckController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.deckmanager.UpdateDeckViewController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * Panel on the left hand side of the deck manager that displays all decks
 * available
 * @author Code On Bleu
 * @version 1.0
 */
public class ListDecksPanel  extends JScrollPane implements
TreeSelectionListener {
	
	private JTree deckTree;
	private UpdateDeckViewController updateDeckViewController;

	public ListDecksPanel() {
		
		this.setViewportView(deckTree);
		try{
			if(Network.getInstance().getDefaultNetworkConfiguration() != null){
				ManageDeckController.getInstance().updateDecks();
			}
		}

		catch(RuntimeException exception){
			exception.printStackTrace();
		}
		
		this.refresh();

		// Create the nodes.
		this.addComponentListener(new ComponentListener() {

			@Override
			public void componentResized(ComponentEvent e) {
				refresh();
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				refresh();
			}
			
			@Override
			public void componentShown(ComponentEvent e) {
				refresh();
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				refresh();
			}
		});

	}
		
	/**
	 * Refresh the list with the decks in the database
	 */
	public void refresh() {

		final List<Deck> decks = ManageDeckController.getInstance().getDecks();
		
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode(
				"Decks"); // makes a starting node
	
		DefaultMutableTreeNode deckNode = null;
		DefaultMutableTreeNode notInUseDecksCategory = null;
		DefaultMutableTreeNode inUseDecksCategory = null;
		
		notInUseDecksCategory = new DefaultMutableTreeNode("Decks Not In Use");
		inUseDecksCategory = new DefaultMutableTreeNode("Decks In Use");
		
		final String user = ConfigManager.getInstance().getConfig().getUserName();
		
		for(Deck d : decks) {
			d.setMyDeck(d.getDeckCreator().equals(user));
			if(d.isInUse()) {
				deckNode = new DefaultMutableTreeNode(d);
				inUseDecksCategory.add(deckNode);
			}
			else {
				deckNode = new DefaultMutableTreeNode(d);
				notInUseDecksCategory.add(deckNode);
			}
		}
		
		top.add(notInUseDecksCategory);
		top.add(inUseDecksCategory);

		// create the tree with the top node as the top
		deckTree = new JTree(top); 
		
		// expand the tree
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
		
	}

	/**
	 * get the selected deck
	 * @return the selected deck
	 */
	public Deck getSelected() {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) deckTree
				.getLastSelectedPathComponent();

		Deck deck = null;
		final Object nodeInfo = node.getUserObject();
		
		if (node.isLeaf()) {
			if (nodeInfo instanceof Deck) {
				deck = (Deck) nodeInfo;
			}
		}
		
		return deck;
		
	}

	/**
	 * Set the selected deck to the one passed in (based on name)
	 * Do nothing if the deck is not in the database
	 * @param deck to be selected
	 */
	public void setSelected(Deck deck) {
		// TODO Auto-generated method stub

	}
	
	/** Required by TreeSelectionListener interface. */
	@Override
	public void valueChanged(TreeSelectionEvent e) {

		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) deckTree
				.getLastSelectedPathComponent();

		if (node == null) {
			return;
		}

		if (node.isRoot()) {
			refresh();
		}
		if(node.isLeaf() && !node.isRoot()) {
			final Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Deck) {
				final Deck deck = (Deck) nodeInfo;
				updateDeckViewController.updateDeckManager(deck);
			}
		}

	}
	
	/**
	 * Changes the decViewController to the passed in contoller
	 * @param updateDeckViewController
	 */
	public void setDeckViewController(UpdateDeckViewController updateDeckViewController) {
		this.updateDeckViewController = updateDeckViewController;
	}
}
