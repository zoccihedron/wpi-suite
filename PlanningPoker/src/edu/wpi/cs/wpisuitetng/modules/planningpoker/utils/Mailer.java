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

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetng.janeway.config.ConfigManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;


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
	private final String from = "codonbleu@gmail.com";
	private final String host = "smtp.gmail.com";

	/**
	 * Constructor for Mailer
	 * @param game the game from which we get the info
	 */
	public Mailer(Game game)
	{
		this.game = game;

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
		//users = game.getProject().getTeam();
		Request request = Network.getInstance().makeRequest("core/user", HttpMethod.GET);
		request.addObserver(new RequestObserver() {
			
			@Override
			public void responseSuccess(IRequest iReq) {
				ResponseModel response = iReq.getResponse();
				User[] users = User.fromJsonArray(response.getBody());
				for(User u : users)
				{
					System.out.println("Number of Users in project: " + users.length);
					String message = "";
					if(u.isAllowEmail())
					{
						message = "Hello, " + u.getName() + 
								"\n Your game: " + game.getName() + " has started";
						
						sendEmail(u.getEmail(), message);
					}
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
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));

			// Set Subject: header field
			message.setSubject(game.getName() + " has started!");

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
