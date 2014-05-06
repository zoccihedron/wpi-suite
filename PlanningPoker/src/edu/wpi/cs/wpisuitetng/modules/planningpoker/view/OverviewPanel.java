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
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSplitPane;
import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.overview.OverviewPanelController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.GameSummaryPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview.ListGamePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;



/**
 * Creates the overview panel, which includes
 * a button for refreshing and the table that
 * displays the game data.
 *
 * @author Team Code On Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class OverviewPanel extends JSplitPane {
	private final ListGamePanel listGamePanel;
	private final GameSummaryPanel summaryPanel;
	private final Timer updateTreeTimer;

	public OverviewPanel()

	{
		summaryPanel = new GameSummaryPanel();
		listGamePanel = new ListGamePanel();

		final Dimension minimumSize = new Dimension(250, 300);
		listGamePanel.setMinimumSize(minimumSize);

		OverviewPanelController.getInstance().setGameSummary(summaryPanel);
		OverviewPanelController.getInstance().setListGames(listGamePanel);

		setLeftComponent(listGamePanel);
		setRightComponent(summaryPanel);
		setDividerLocation(300);

		final ActionListener updateTreeListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					final Request request = Network.getInstance().makeRequest(
							"planningpoker/game", HttpMethod.GET);
					request.addObserver(new RequestObserver() {

						@Override
						public void responseSuccess(IRequest iReq) {

							final ResponseModel response = iReq.getResponse();
							final Game[] games = Game.fromJsonArray(response.getBody());
							final List<Game> updatedGames = new ArrayList<Game>();
							for(Game game: games){
								updatedGames.add(game);
							}
							final List<Game> listPanelGames = listGamePanel.getGames();
							
							final String user = ConfigManager.getConfig().getUserName();
							
							if(updatedGames.size() == listPanelGames.size()){
								boolean hasChange = false;
								for(Game game: updatedGames){
									if(hasChange) break;
									for(Game g: listPanelGames){
										if(g.getId() == game.getId()){
											if(!g.isSameModifiedVersion(game.getModifiedVersion())
													|| !g.getStatus().equals(game.getStatus())
													|| g.isChanged(game, user)){
												listGamePanel.refresh();
												hasChange = true;
												break;
											}
										}
									}
									
								}
							}
							else {
								listGamePanel.refresh();
							}
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
				catch(NullPointerException exception){
					System.err.println(exception.getMessage());
				}

			}

		};

		updateTreeTimer = new Timer(5000, updateTreeListener);
		updateTreeTimer.start();

	}
}
