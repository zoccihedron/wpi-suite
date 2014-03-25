/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Codon Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.modeltest;

import org.junit.*;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Game testing class for Planning Poker
 *
 * @author Ryan
 * @version Mar 23, 2014
 */
public class GameTest {
	@Test
	public void generateNameTest()
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date now = Calendar.getInstance().getTime();
		Game g = new Game();
		assertEquals(g.getName(), df.format(now));
	}
}
