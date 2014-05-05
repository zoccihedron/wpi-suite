package edu.wpi.cs.wpisuitetng.modules.planningpoker.viewtest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.playgame.PlayGameController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.EstimationPane;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame.ListRequirementsPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class EstimationPaneTest {
	EstimationPane textPanel;
	EstimationPane cardPanel;
	Game textgame;
	Game cardgame;
	ListRequirementsPanel reqs;
	PlayGameController controller;
	
	@Before
	public void setup() {
		
		// Mock network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));
		
		//Mock Reqs
		
		Requirement req = new Requirement(1, "test", "desc");
		Requirement req2 = new Requirement(2, "test2", "desc2");
		
		ArrayList<Integer> reqArrayList = new ArrayList<Integer>();
		reqArrayList.add(req.getId());
		reqArrayList.add(req2.getId());
		
		Requirement[] reqList = new Requirement[2];
		reqList[0] = req;
		reqList[1] = req2;
		
		RequirementManagerFacade.getInstance().setRequirements(reqList);
		
		//Setup the panel
		Date startTime = new Date();

		Calendar endTime = new GregorianCalendar();
		endTime.set(startTime.getYear() + 1, 1, 1);
		
		controller = new PlayGameController();
		
	    textgame = new Game("test", startTime, endTime.getTime(), -1);
	    cardgame = new Game("test", startTime, endTime.getTime(), -2);
	    
	    textgame.setRequirements(reqArrayList);
	    cardgame.setRequirements(reqArrayList);
	    
	    Estimate est1 = new Estimate(1, textgame.getId());	
	    textgame.addEstimate(est1);
	    cardgame.addEstimate(est1);
	    
	    Estimate est2 = new Estimate(2, textgame.getId());	
	    textgame.addEstimate(est2);
	    cardgame.addEstimate(est2);
	    
	    reqs = new ListRequirementsPanel(textgame, controller);
	    
		textPanel = new EstimationPane(reqs , textgame);
		cardPanel = new EstimationPane(reqs , textgame);
	}
	
	@Test
	public void constructorTest(){
		System.setProperty("java.awt.headless", "false");
		assertNotNull(textPanel);
		assertNotNull(cardPanel);
	}
	
	@Test
	public void setGameAndReqTest(){
		textPanel.initEstimationPane();
		textPanel.setGameAndRequirement(1, textgame);
		assertEquals(1, textPanel.getReqID());
		textPanel.setGameAndRequirement(2, textgame);
		assertEquals(2, textPanel.getReqID());
	}
}
