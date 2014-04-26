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
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.HelpTopic;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.HelpTopic.TopicCategory;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpTopicsPanel extends JScrollPane
implements TreeSelectionListener {

	private JTree tree;
	private List<HelpTopic> topics;

	/**
	 * 
	 */
	public HelpTopicsPanel() {

		this.setViewportView(tree);

		//makes a starting node
		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Help Topics");
		DefaultMutableTreeNode helpNode = null;

		final DefaultMutableTreeNode createGameHelp = new DefaultMutableTreeNode("Creating a Game");
		final DefaultMutableTreeNode playGameHelp = new DefaultMutableTreeNode("Playing a Game");

		for(HelpTopic helpTopic: topics) {

			// add new node to requirement tree
			helpNode = new DefaultMutableTreeNode(helpTopic);
			if (helpTopic.getCategory() == TopicCategory.CREATE) {
				createGameHelp.add(helpNode);
			} else if (helpTopic.getCategory() == TopicCategory.PLAY) {
				playGameHelp.add(helpNode);
			}

			top.add(createGameHelp);
			top.add(playGameHelp);
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

	public void refresh() {

	}


}
