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

import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

/**
 * This class is used to send emails to notify users when notifications need to
 * be sent
 * 
 * @author Team Code On Bleu
 * @version 1.0
 */
public class Mailer implements Callable<Boolean> {
	private final Game game;
	private final List<User> users;
	private Properties properties;
	private Session session;
	private final Notification method;
	private final String from;
	private final String host;
	private final String port;
	private final String password;

	public enum Notification {
		STARTED("Started"), ENDED("Ended"), TEST("Test");

		private final String text;

		private Notification(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * Constructor for Mailer
	 * 
	 * @param game
	 *            the game from which we get the info
	 * @param users
	 *            the list of users to which we send
	 * @param method
	 *            is this game just started or ended
	 * @param project
	 * 			  project to send mail from
	 */
	public Mailer(Game game, List<User> users, Notification method,
			Project project) {
		this.game = game;
		this.users = users;
		this.method = method;

		from = project.getMailAccount();
		host = project.getMailServer();
		port = project.getPort();
		password = project.getPassword();
	}

	@Override
	public Boolean call() {
		Boolean result = false;
		if (!from.equals("")) {
			// Get system properties
			properties = System.getProperties();

			// Set the properties
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.port", port);
			properties.put("mail.smtp.host", host);
			properties.put("mail.smtp.ssl.trust", host);
			properties.put("mail.smtp.starttls.enable", "true");

			session = Session.getInstance(properties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(from, password);
						}
					});

			if (method.equals(Notification.STARTED)) {
				notifyStart();
				result = true;
			} else if (method.equals(Notification.ENDED)) {
				notifyEnd();
				result = true;
			} else if (method.equals(Notification.TEST)) {
				try {
					notifyTest();
					result = true;
				} catch (Exception e) {
					e.printStackTrace();
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * Function which notifies users that the game has started
	 */
	public void notifyStart() {
		for (User u : users) {
			String message = "";
			if (u.isAllowEmail()) {
				message = "Hello, " + u.getName() + "\nThe game: "
						+ game.getName() + " has started\n"
						+ game.getDescription();

				sendEmail(u.getEmail(), message, true);
			}
		}
	}

	/**
	 * Function which notifies users that the game has ended
	 */
	public void notifyEnd() {
		for (User u : users) {
			String message = "";
			if (u.isAllowEmail()) {
				message = "Hello, " + u.getName() + "\nThe game: "
						+ game.getName() + " has ended\n"
						+ game.getDescription();

				sendEmail(u.getEmail(), message, false);
			}
		}
	}

	/**
	 * Function which tests the validity of the email credential
	 * 
	 * @throws MessagingException
	 * @throws AddressException
	 */
	public void notifyTest() throws AddressException, MessagingException {
		final String messageText = "Hello, this is a test email to verify that" + 
				"the credentials provided are functional.";

		final MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.addRecipient(Message.RecipientType.TO,
				new InternetAddress(from));
		message.setSubject("WPI Suite Test Email");

		// Now set the actual message
		message.setText(messageText);

		// Send message
		Transport.send(message);
	}

	/**
	 * Function that sends an email to the given address
	 * 
	 * @param sendToEmail
	 *            email address we want to send mail to
	 * @param messageText
	 *            message sent to the email given
	 * @param isStarting
	 *            indicates whether the game is starting or ending
	 */
	public void sendEmail(String sendToEmail, String messageText,
			boolean isStarting) {
		try {
			final MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					sendToEmail));

			if (isStarting) {
				message.setSubject(game.getName() + " has started!");
			} else {
				message.setSubject(game.getName() + " has ended!");
			}

			// Now set the actual message
			message.setText(messageText);

			// Send message
			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
