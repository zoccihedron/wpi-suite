package edu.wpi.cs.wpisuitetng.modules.planningpoker.viewtest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.CreateGameInfoPanel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.NewGamePanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class CreateGameInfoPanelTest {
	NewGamePanel freshPanel;
	NewGamePanel existingPanel;
	CreateGameInfoPanel freshCreateGameInfoPanel;
	CreateGameInfoPanel existingCreateGameInfoPanel;
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
		
		freshCreateGameInfoPanel = new CreateGameInfoPanel(freshPanel);
		existingCreateGameInfoPanel = new CreateGameInfoPanel(existingPanel, game);
	}

	@Test
	public void constructorTest() {
		assertNotNull(freshCreateGameInfoPanel);
		//Contains default string Game Date
		assertTrue(freshCreateGameInfoPanel.getGameObject().getName().contains("Game"));
	}

	@Test
	public void constructorWithExistingGameTest() {
		assertNotNull(existingCreateGameInfoPanel);
		assertEquals("test", existingCreateGameInfoPanel.getGameObject().getName());
	}

	@Test
	public void editGameTest() {
		//Test that we don't ask for a confirmation on unchanged pages
		assertFalse(freshCreateGameInfoPanel.isPageEdited());
		assertFalse(existingCreateGameInfoPanel.isPageEdited());
	}
	
	@Test
	public void EnableOrDisableDeadlineTest(){
		existingCreateGameInfoPanel.EnableOrDisableDeadline();
		//This is false because enable or disable takes checkbox status and doesnt change it on above call
		assertFalse(existingCreateGameInfoPanel.isPageEdited());
	}
}
