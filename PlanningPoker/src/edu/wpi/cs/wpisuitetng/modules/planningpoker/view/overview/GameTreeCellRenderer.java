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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;


/**
 * This class is used to customize the nodes
 * in the tree for specific cases.
 * 
 * based off: http://www.java2s.com/Tutorial
 * /Java/0240__Swing/CreatingaCustomRenderer.htm
 *
 * @author Team Codon Bleu
 * @version Apr 30, 2014
 */
public class GameTreeCellRenderer extends CustomTreeCellRenderer{
	JLabel label = new JLabel();
	DefaultTreeCellRenderer defaultRenderer = new CustomTreeCellRenderer();

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
	    Component returnValue = defaultRenderer.getTreeCellRendererComponent(
	    		tree, value, selected, expanded, leaf, row, hasFocus);
	    if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
	      final Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
	      if (userObject instanceof Game) {
	        final Game g = (Game) userObject;
	        returnValue.setForeground(Color.BLACK);
	        if(g.getStatus().equals(GameStatus.IN_PROGRESS) && 
	        		(g.getUserVoteCount(ConfigManager.getConfig().getUserName()) 
	        			!= g.getUserMaxVotes())){
	        	returnValue.setFont(returnValue.getFont().deriveFont(Font.BOLD));
	        }
	        else{
	        	returnValue.setFont(defaultRenderer.getFont().deriveFont(Font.PLAIN));
	        }
	        
	      }
	      else{
	    	  returnValue = new CustomTreeCellRenderer().getTreeCellRendererComponent(
	    			  tree, value, selected, expanded,
	    	          	leaf, row, hasFocus);
	      }
	    }
	    if (returnValue == null) {
	      returnValue = new CustomTreeCellRenderer().getTreeCellRendererComponent(
	    		  tree, value, selected, expanded,
	    		  	leaf, row, hasFocus);
	    }
	    return returnValue;
	}
}
