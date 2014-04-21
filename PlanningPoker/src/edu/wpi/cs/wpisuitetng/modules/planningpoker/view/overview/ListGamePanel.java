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

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

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

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * This class stores all of the games from the database in a
 * JTree, and sorts them by status (progress, draft, ended).
 *
 * @author Team Codon Bleu
 * @version Apr 7, 2014
 */
public class ListGamePanel extends JScrollPane
implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private List<Game> games;
	
	/**
	 * Constructs the panel
	 * @param draftGame Taken in to get all requirements for the game
	 */
	public ListGamePanel() {

		this.setViewportView(tree);
		

		//Create the nodes.
		this.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentResized(ComponentEvent e) {

				refresh();
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

				refresh();
			}
		});
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if(!node.isLeaf() || node.isRoot()){
			refresh();
		}
		if(node.isLeaf() && !node.isRoot()){
			final Object nodeInfo = node.getUserObject();
			if(nodeInfo instanceof Game) {
				final Game gme = (Game) nodeInfo;
				OverviewPanelController.getInstance().updateGameSummary(gme);
			}
		}
	}


	/**
	 * This method is used to refresh the requirements tree
	 */
	public void refresh(){
		System.out.println("Refreshing Game Tree...");
		

		try{
			if(Network.getInstance().getDefaultNetworkConfiguration() != null){
				final GetGamesController gamesController = new GetGamesController();
				gamesController.initializeTable();
				RequirementManagerFacade.getInstance().getPreStoredRequirements();
			}
		}

		catch(RuntimeException exception){
			exception.printStackTrace();
		}
		
	}
	
	public void updateTree(){
		//makes a starting node
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Games"); 
		games = PlanningPokerModel.getInstance().getAllGames();
		DefaultMutableTreeNode gameNode = null;

		final DefaultMutableTreeNode gameInProgressCategory =
				new DefaultMutableTreeNode("In Progress");
		final DefaultMutableTreeNode gameEndedCategory = new DefaultMutableTreeNode("Ended");
		final DefaultMutableTreeNode gameDraftCategory = new DefaultMutableTreeNode("Draft");
		final DefaultMutableTreeNode gameClosedCategory = new DefaultMutableTreeNode("Archive");
		
		for(Game game: games){

			// add new node to requirement tree
			gameNode = new DefaultMutableTreeNode(game);
			switch (game.getStatus()){
				case IN_PROGRESS: 
					gameInProgressCategory.add(gameNode);
					break;
				case DRAFT: 
					gameDraftCategory.add(gameNode);
					break;
				case ENDED: 
					gameEndedCategory.add(gameNode);
					break;
				case CLOSED:
					gameClosedCategory.add(gameNode);
					break;
			}

			top.add(gameDraftCategory);
			top.add(gameInProgressCategory);
			top.add(gameEndedCategory);
			top.add(gameClosedCategory);
		}
		
		//create the tree with the top node as the top
		tree = new JTree(top); 
		//have all of the nodes expand automatically after refreshing, except for the
		//last row, which stores the closed (archived) games
		for(int i = 0; i < tree.getRowCount() - 1; i++) {
			tree.expandRow(i);
		}
		//tell it that it can only select one thing at a time
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); 
		tree.setToggleClickCount(0);

		//set to custom cell renderer so that icons make sense
		tree.setCellRenderer(new CustomTreeCellRenderer());
		tree.addTreeSelectionListener(this);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		this.setViewportView(tree); //make panel display the tree

		System.out.println("# of Games:" + games.size());
		System.out.println("finished refreshing the tree");
	}
}
