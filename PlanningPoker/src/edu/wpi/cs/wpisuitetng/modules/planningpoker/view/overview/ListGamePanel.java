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
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
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
	private boolean hasRefreshed = false;
	private Object selectedObject;
	private boolean fakeSelected = false;


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

		if(node.isRoot()){
			refresh();
		}
		if(node.isLeaf() && !node.isRoot()  && !fakeSelected){
			final Object nodeInfo = node.getUserObject();
			if(nodeInfo instanceof Game) {
				final Game gme = (Game) nodeInfo;
				OverviewPanelController.getInstance().updateGameSummary(gme);
			}
		}
		fakeSelected = false;
	}


	/**
	 * This method is used to refresh the requirements tree
	 */
	public void refresh(){


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

	/**
	 * This function updates the listGame tree
	 */
	public void updateTree(){
		//makes a starting node
		DefaultMutableTreeNode node = null;
		if(hasRefreshed){
			try{
			node = (DefaultMutableTreeNode)
					tree.getLastSelectedPathComponent();
			selectedObject = node.getUserObject();
			}
			catch(NullPointerException e){
				//Intentionally Left Blank
			}
		}
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Games"); 
		games = PlanningPokerModel.getInstance().getAllGames();
		DefaultMutableTreeNode gameNode = null;

		final DefaultMutableTreeNode gameInProgressCategory =
				new DefaultMutableTreeNode("In Progress");
		final DefaultMutableTreeNode gameEndedCategory = new DefaultMutableTreeNode("Ended");
		final DefaultMutableTreeNode gameDraftCategory = new DefaultMutableTreeNode("Draft");
		final DefaultMutableTreeNode gameClosedCategory = new DefaultMutableTreeNode("Archive");

		for(Game game: games){

			String user = ConfigManager.getConfig().getUserName();
			game.setMyGame(game.isCreator(user));
			
			// add new node to requirement tree
			gameNode = new DefaultMutableTreeNode(game);
			if(selectedObject != null) {
				if(selectedObject instanceof Game){
					if(((Game) selectedObject).getId() == game.getId()){
						node = gameNode;
					}
				}
			}
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
			if(selectedObject != null) {
				if(selectedObject instanceof String){
					if(((String) selectedObject).equals("Ended")){
						node = gameEndedCategory;
					}
					else if(((String) selectedObject).equals("Draft")){
						node = gameDraftCategory;
					}
					else if(((String) selectedObject).equals("Archive")){
						node = gameClosedCategory;
					}
					else if(((String) selectedObject).equals("In Progress")){
						node = gameInProgressCategory;
					}
				}
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
		tree.setCellRenderer(new GameTreeCellRenderer());
		tree.addTreeSelectionListener(this);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		if(node != null){
			TreeNode[] nodes = ((DefaultTreeModel) tree.getModel()).getPathToRoot(node);
			TreePath tpath = new TreePath(nodes);
			fakeSelected = true;
			tree.setSelectionPath(tpath);
		}

		this.setViewportView(tree); //make panel display the tree
		
		hasRefreshed = true;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
}
