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
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
	private List<User> users;
	private Properties properties;
	private Session session;
	private final String from = "codonbleu@gmail.com";
	private final String host = "smtp.gmail.com";

	/**
	 * Constructor for Mailer
	 * @param game the game from which we get the info
	 */
	public Mailer(Game game, List<User> users)
	{
		this.game = game;
		this.users = users;

		// Get system properties
        properties = System.getProperties();

        // Set the properties
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.ssl.trust", host);
        properties.put("mail.smtp.starttls.enable", "true");
        
        session = Session.getInstance(properties,
      		  new javax.mail.Authenticator() {
      			protected PasswordAuthentication getPasswordAuthentication() {
      				return new PasswordAuthentication(from, "CodonBleu1");
      			}
      		  });
		
		
		session.setDebug(true);
	}

	/**
	 * Function which notifies users that 
	 * the game has started
	 */
	public void notifyStart()
	{
		for(User u : users)
		{
			String message = "";
			if(u.isAllowEmail())
			{
				message = "Hello, " + u.getName() + 
						"\n Your game: " + game.getName() + " has started";

				sendEmail(u.getEmail(), message, true);
			}
		}
				
	}
	
	/**
	 * Function which notifies users that 
	 * the game has ended
	 */
	public void notifyEnd()
	{
		for(User u : users)
		{
			String message = "";
			if(u.isAllowEmail())
			{
				message = "Hello, " + u.getName() + 
						"\n Your game: " + game.getName() + " has ended";

				sendEmail(u.getEmail(), message, false);
			}
		}
	}

	/**
	 * Function that sends an email to the given address
	 * @param sendToEmail email address we want to send mail to
	 * @param messageText message sent to the email given
	 * @param isStarting indicates whether the game is starting or ending
	 */
	public void sendEmail(String sendToEmail, String messageText, boolean isStarting)
	{
		try
		{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));

			// Set Subject: header field
			if(isStarting)
			{
				message.setSubject(game.getName() + " has started!");
			} 
			else
			{
				message.setSubject(game.getName() + " has ended!");
			}

			// Now set the actual message
			message.setText(messageText);

			// Send message
			Transport.send(message);
			System.out.println("Sent message to " + sendToEmail + " successfully!");
		}
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
	}
	
	
}
