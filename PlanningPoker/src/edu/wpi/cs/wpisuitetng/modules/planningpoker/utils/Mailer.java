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
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

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
	private Properties properties;
	private Session session;
	private final String from = "planningpoker@yahoo.com";
	private final String host = "localhost"; // send email from localhost

	/**
	 * Constructor for Mailer
	 * @param game the game from which we get the info
	 */
	public Mailer(Game game)
	{
		this.game = game;
		users = game.getProject().getTeam();

		// Get system properties
		properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);
		session = Session.getDefaultInstance(properties);
	}

	/**
	 * Function which notifies users that 
	 * the game has started
	 */
	public void notifyStart()
	{
		String message = "";

		for(User u : users)
		{
			if(u.isAllowEmail())
			{
				message = "Hello, " + u.getName() + 
						"\n Your game: " + game.getName() + " has started";
				
				sendEmail(u.getEmail(), message);
			}
		}
	}

	/**
	 * Function that sends an email to the given address
	 * @param sendToEmail email address we want to send mail to
	 * @param messageText message sent to the email given
	 */
	public void sendEmail(String sendToEmail, String messageText)
	{
		try
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(sendToEmail));

			// Set Subject: header field
			message.setSubject(game.getName() + "has started!");

			// Now set the actual message
			message.setText(messageText);

			// Send message
			Transport.send(message);
			System.out.println("Sent message to " + sendToEmail + " successfully....");
		}
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
	}
}
