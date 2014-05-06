package edu.wpi.cs.wpisuitetng.modules.planningpoker.viewtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame.SelectRequirementsPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class SelectRequirementsPanelTest {
	SelectRequirementsPanel freshPanel;
	SelectRequirementsPanel existingPanel;
	Game game;
	
	@Before
	public void setup() {
		// Mock network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		//Mock Reqs
		Requirement req1 = new Requirement(0, "zero", "desc");
		Requirement req2 = new Requirement(1, "one", "desc2");
		Requirement[] reqs = new Requirement[2];
		reqs[0] = req1;
		reqs[1] = (req2);
		
		RequirementManagerFacade.getInstance().setRequirements(reqs);
		freshPanel = new SelectRequirementsPanel();
		Date startTime = new Date();

		Calendar endTime = new GregorianCalendar();
		endTime.set(startTime.getYear() + 1, 1, 1);

	    game = new Game("test", startTime, endTime.getTime(), 0);

		Estimate est1 = new Estimate(1, game.getId());		
		game.addEstimate(est1);
		
		existingPanel = new SelectRequirementsPanel(game);
	}

	@Test
	public void constructorTest() {
		assertNotNull(freshPanel);
	}
	
	@Test
	public void existingGameConstructorTest() {
		assertNotNull(existingPanel);
	}
	
	@Test
	public void getSelectedReqsTest() {
		//0 because we haven't selected any reqs
		assertEquals(0, existingPanel.getSelectedRequirementIds().size());
	}
}
