package edu.wpi.cs.wpisuitetng.modules.planningpoker.viewtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGamePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class NewGameViewTest {
	NewGamePanel freshPanel;
	NewGamePanel existingPanel;
	Game game;
	
	@Before
	public void setup() {
		// Mock network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		freshPanel = new NewGamePanel();
		Date startTime = new Date();

		Calendar endTime = new GregorianCalendar();
		endTime.set(startTime.getYear() + 1, 1, 1);

	    game = new Game("test", startTime, endTime.getTime(), 0);
		existingPanel = new NewGamePanel(game, true);
	}

	@Test
	public void constructorTest() {
		assertNotNull(freshPanel);
	}

	@Test
	public void constructorWithExistingGameTest() {
		assertNotNull(existingPanel);
	}

	@Test
	public void editGameTest() {
		//Test that we don't ask for a confirmation on unchanged pages
		assertTrue(freshPanel.isReadyToClose());
		assertTrue(existingPanel.isReadyToClose());
	}
	
	@Test
	public void errorStringTest() {
		freshPanel.reportError("test error");
		assertEquals("test error" , freshPanel.getMessageField().getText());
	}
}
