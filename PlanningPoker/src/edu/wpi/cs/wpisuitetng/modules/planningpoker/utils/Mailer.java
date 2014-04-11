/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.utils;

import java.util.ArrayList;

import javax.activation.*;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is used to send emails to notify users
 * when notifications need to be sent
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
public class Mailer {
	private Game game;
	private User[] users;
	
	/**
	 * Constructor for Mailer
	 * @param game the game from which we get the info
	 */
	public Mailer(Game game)
	{
		this.game = game;
		users = game.getProject().getTeam();
	}
	
	/**
	 * Function which notifies users that 
	 * the game has started
	 */
	public void notifyStart()
	{
		final ArrayList<String> emails = new ArrayList<String>();
		
		for(User u : users)
		{
			if(u.isAllowEmail())
			{
				emails.add(u.getEmail());
			}
		}
		for (String email : emails)
		{
			// send the email with a given message
			//sendEmail(email, message);
		}
	}

}
