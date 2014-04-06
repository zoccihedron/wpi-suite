/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame;

import javax.swing.DropMode;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import java.util.List;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * This class is used to create a requirements tree which will be displayed in the play game pannel
 * 
 * @author Codon Bleu
 *
 */
public class ListRequirementsPanel extends JScrollPane
implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	/**
	 * Constructs the panel
	 * @param game Taken in to get all requirements for the game
	 */
	public ListRequirementsPanel(final Game game) {

		this.setViewportView(tree);
		this.refresh();  

		//Create the nodes.
		this.addComponentListener(new ComponentListener()
		{

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
				// TODO Auto-generated method stub

			}
		});
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;
		//TODO: see about implementing DoublieClick to send data to estimate panel
	}


 /**
  * This method is used to refresh the requirements tree
  */
	public void refresh(){

		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Requirements"); //makes a starting node
		final List<Requirement> requirements = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		DefaultMutableTreeNode reqNode = null;

		DefaultMutableTreeNode votedCategory = null;
		votedCategory = new DefaultMutableTreeNode("Not Voted On");

		for(Requirement req: requirements){

			// add new node to requirement tree
			reqNode = new DefaultMutableTreeNode(req);
			votedCategory.add(reqNode);
		}

		top.add(votedCategory);
		votedCategory = new DefaultMutableTreeNode("Voted On");
		top.add(votedCategory);

		tree = new JTree(top); //create the tree with the top node as the top
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //tell it that it can only select one thing at a time
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer()); //set to custom cell renderer so that icons make sense
		tree.addTreeSelectionListener(this);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		this.setViewportView(tree); //make panel display the tree

		System.out.println("finished refreshing the tree");
	}
}