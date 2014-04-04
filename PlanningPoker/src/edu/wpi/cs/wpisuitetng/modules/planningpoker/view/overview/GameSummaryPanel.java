/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/** GameSummaryPanel is a class which displays the summary
 * of a game that can be chosen to edit and/or play by a user
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class GameSummaryPanel extends JPanel{
	
	JLabel titleLabel;
	JLabel deadlineLabel;
	JLabel descriptionLabel;
	JButton playGameBtn;
	JButton editGameBtn;
	JTextArea requirementsList;
	
	public GameSummaryPanel() 
	{
		setLayout(null);
		
		titleLabel = new JLabel("Title");
		titleLabel.setBounds(6, 6, 295, 56);
		add(titleLabel);
		
		deadlineLabel = new JLabel("Deadline");
		deadlineLabel.setBounds(313, 6, 131, 56);
		add(deadlineLabel);
		
		descriptionLabel = new JLabel("Description");
		descriptionLabel.setBounds(6, 74, 438, 56);
		add(descriptionLabel);
		
		playGameBtn = new JButton("Play Game");
		playGameBtn.setBounds(327, 265, 117, 29);
		add(playGameBtn);
		playGameBtn.setEnabled(false);
		
		editGameBtn = new JButton("Edit Game");
		editGameBtn.setBounds(184, 265, 117, 29);
		add(editGameBtn);
		editGameBtn.setEnabled(false);
		
		requirementsList = new JTextArea("Requirements");
		requirementsList.setBounds(6, 147, 438, 106);
		add(requirementsList);
	}
	
	/** Function which takes a game as a parameter and
	 * updates the summary of the game that is displayed
	 * 
	 * @param game
	 */
	public void updateSummary(Game game)
	{
		this.titleLabel.setText(game.getName());
		this.deadlineLabel.setText(game.getEnd().toString());
		this.descriptionLabel.setText(game.getDescription());
		String appendedReqs = GameSummaryPanel.getRequirementNames(game);
		this.requirementsList.setText(appendedReqs);
		
		// if the game is a draft and the user is the creator
		// allow it to be editable
		if(game.getStatus().equals(GameStatus.DRAFT) && game.getGameCreator().equals(ConfigManager.getConfig().getUserName()))
		{
			this.editGameBtn.setEnabled(true);
		}
		else
		{
			this.editGameBtn.setEnabled(false);
		}
		
		// if the game is started
		if(game.getStatus().equals(GameStatus.IN_PROGRESS))
		{
			this.playGameBtn.setEnabled(true);
		}
		else
		{
			this.playGameBtn.setEnabled(false);
		}
	}

	/** Function which returns a string with all requirement names
	 * for the given game appended (with new lines) into ones
	 * string
	 * 
	 * @param game holds the requirements that will be returned
	 * @return String of appended requirement names for the game
	 */
	private static String getRequirementNames(Game game) {
		String temp = "";
		List<Requirement> reqs = RequirementManagerFacade.getInstance().getRequirments();
		for(Estimate e : game.getEstimates())
		{
			temp += reqs.get(e.getReqID()).getName()+"\n";
		}
		
		return temp;
	}
}
