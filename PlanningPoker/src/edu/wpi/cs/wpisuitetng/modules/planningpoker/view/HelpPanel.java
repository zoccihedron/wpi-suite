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

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpPanel extends JPanel
implements TreeSelectionListener {
	private JPanel helpInfoPane;
	private JTree tree;

	public HelpPanel() {
		super(new GridLayout(1,0));
		
		//Create the nodes.
		DefaultMutableTreeNode top =
				new DefaultMutableTreeNode("Help Topics");
		createNodes(top);

		//Create a tree that allows one selection at a time.
		tree = new JTree(top);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer());
		tree.addTreeSelectionListener(this);
		
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);

		helpInfoPane = new HelpTopics();
		JScrollPane helpInfoView = new JScrollPane(helpInfoPane);

		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent(treeView);
		splitPane.setRightComponent(helpInfoView);

		Dimension minimumSize = new Dimension(300, 500);
		helpInfoView.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(300);

		//Add the split pane to this panel.
		add(splitPane);
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;

		Object nodeInfo = node.getUserObject();
		if (node.isLeaf()) {
			HelpTopics helpTopic = (HelpTopics)nodeInfo;
			helpInfoPane.add(helpTopic);
		}
	}

	private void createNodes(DefaultMutableTreeNode top) {
		DefaultMutableTreeNode category = null;
		DefaultMutableTreeNode topic = null;

		category = new DefaultMutableTreeNode("Creating a Game");
		top.add(category);

		//original Tutorial
		topic = new DefaultMutableTreeNode(new HelpTopics
				("Validation Fields",
						"Hello"));
		category.add(topic);
	}

	/**
	 * Says that the help tab should always be ready to close
	 * @return true
	 */
	public boolean isReadyToClose() {
		return true;
	}
}
