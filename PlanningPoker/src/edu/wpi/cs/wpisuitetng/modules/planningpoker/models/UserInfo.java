/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author yyan, Robert
 *
 */

public class UserInfo extends AbstractModel{
	private final User user;
	private String IM;
	private String email;
	private Boolean allowIM = false;
	private Boolean allowEmail = false;


	public UserInfo(User u, String im, String e, boolean allowim, boolean allowemail){
		user = u;
		IM = im;
		email = e;
		allowIM = allowim;
		allowEmail = allowemail;
				
	}
	
	public UserInfo(User u){
		user = u;
		IM = "";
		email = "";
	}
	

	
	/**
	 * produce hash code for this object
	 * @return hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.getIdNum());//not sure what user IdNum range is
		return result;
	}

	/** 
	 * check if the given object is equal to this UserInfo object
	 * @return true if it's the same user
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj!=null && obj instanceof UserInfo){
			if(((UserInfo)obj).getUser().equals(user))	return true;
		}
		return false;
	}
	
	

	

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	public String getIM() {
		return IM;
	}

	public void setIM(String iM) {
		IM = iM;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the allowIM
	 */
	public Boolean getAllowIM() {
		return allowIM;
	}

	/**
	 * @param allowIM the allowIM to set
	 */
	public void setAllowIM(Boolean allowIM) {
		this.allowIM = allowIM;
	}

	/**
	 * @return the allowEmail
	 */
	public Boolean getAllowEmail() {
		return allowEmail;
	}

	/**
	 * @param allowEmail the allowEmail to set
	 */
	public void setAllowEmail(Boolean allowEmail) {
		this.allowEmail = allowEmail;
	}

	/** Saves the game (to be implemented)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/** Deletes the game (to be implemented)
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Encodes the user info as a  JSON-encoded string
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	public String toJSON() {
		return new Gson().toJson(this, UserInfo.class);
	}
	
	public static UserInfo fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, UserInfo.class);
	}

	/**
	 * Checks if Object o is equal to the game by checking 
	 * the id of the user, which should be unique 
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(java.lang.Object)
	 */
	@Override
	public Boolean identify(Object o) {
		if(o instanceof Integer){		
			return (getUser().getIdNum() == (Integer)(o));	
			
		} else if (o instanceof User){
			return (getUser().getIdNum() == ((User)(o)).getIdNum());
			
		} else if (o instanceof String){			
			return (Integer.toString(getUser().getIdNum()).equals((String)o));
			
		}
		return false;
	}
	
}
