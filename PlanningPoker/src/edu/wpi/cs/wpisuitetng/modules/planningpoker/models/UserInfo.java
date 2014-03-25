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

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * @author yyan
 *
 */

public class UserInfo {
	private final User user;
	private String IM;
	private String email;

	public UserInfo(User u, String im, String e){
		user = u;
		IM = im;
		email = e;
				
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
		result = prime * result + ((user == null) ? 0 : user.getIdNum()%31);//not sure what user IdNum range is
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
	
	
	
	
}
