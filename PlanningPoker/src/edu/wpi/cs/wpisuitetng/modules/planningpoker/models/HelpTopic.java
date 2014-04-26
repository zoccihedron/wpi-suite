/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Creator:
 *    Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

/**
 * @author Codon Bleu
 *
 */
public class HelpTopic extends AbstractModel {

	public enum TopicCategory {
		CREATE("Creating a Game"), PLAY("Playing a Game");

		private final String text;

		private TopicCategory(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}
	private TopicCategory category;
	private String title;
	private String text;

	//the static object allow the planning poker model to become a singleton
	private static HelpTopic instance = null;

	/**
	 * 
	 */
	public HelpTopic() {
		this.category = category;
		this.title = title;
		this.text = text;
		
		
	}

	public TopicCategory getCategory() {
		return category;
	}

	public String getTitle() {
		return title;
	}

	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	public static HelpTopic getInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(HelpTopic instance) {
		HelpTopic.instance = instance;
	}

}