/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.help;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Codon Bleu
 *
 */
public class HelpTopicObject {
	
	public String title;
	public String fileName;
	public String fileText = "";
	
	/**
	 * 
	 * @param title
	 * @param fileName
	 */
	public HelpTopicObject(String title, String fileName) {
		this.title = title;
		this.fileName = fileName;
		BufferedReader reader = null;
		
		try {
			System.out.println("Working Directory = " + 
					System.getProperty("user.dir"));
			reader = new BufferedReader(
					new InputStreamReader(getClass().getResourceAsStream(fileName)));
			String text = null;
			
			while ((text = reader.readLine()) != null) {
				fileText = fileText + text + "\n";
			}
		}
		
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (reader != null) {
					reader.close();
				}
			}
			catch (IOException e) {
			}
		}
	}
	
	public String toString() {
		return title;
	}

	public String getTitle() {
		return title;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileText() {
		return fileText;
	}
}
