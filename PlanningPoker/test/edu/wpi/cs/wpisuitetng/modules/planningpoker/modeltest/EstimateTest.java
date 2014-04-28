package edu.wpi.cs.wpisuitetng.modules.planningpoker.modeltest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;

public class EstimateTest {

	Game game;
	User dummyUser;
	User dummyUser2;
	int TEST_ESTIMATE_ID = 1;
	int ESTIMATE_VALUE = 1;
	Estimate est;
	
	@Before
	public void setUp(){
		String game1name = "Game1";
		Date start = new Date();
		Calendar endTime = new GregorianCalendar();
		endTime.set(start.getYear() + 1, 1,1);
		Date end = endTime.getTime();
		game = new Game(game1name,start,end, "default");
		

		dummyUser = new User("Bob", "bob", "abc123", 1);

		dummyUser2 = new User("Bill", "bill", "abc123", 1);
		
		est = new Estimate(TEST_ESTIMATE_ID , game.getId());
	}
	
	@Test
	public void constructorTest() {
		assertNotNull(est);	
		assertEquals(game.getId() , est.getGameID());
		assertEquals(TEST_ESTIMATE_ID, est.getReqID());
	}
	
	@Test
	public void canAddUserTest() {
		assertFalse(est.hasUser(dummyUser.getUsername()));
		assertFalse(est.canAddUser(dummyUser.getUsername()));
		
		assertTrue(est.hasUser(dummyUser.getUsername()));
		assertTrue(est.canAddUser(dummyUser.getUsername()));
	}
	
	@Test
	public void userHadMadeEstimateTest(){
		assertFalse(est.hasMadeAnEstimation(dummyUser.getUsername()));
		est.addUser(dummyUser.getUsername());
		assertFalse(est.hasMadeAnEstimation(dummyUser.getUsername()));
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertTrue(est.hasMadeAnEstimation(dummyUser.getUsername()));
		
	}
	
	@Test
	public void allEstimationsMadeTest() {
		est.addUser(dummyUser.getUsername());
		est.addUser(dummyUser2.getUsername());
		assertFalse(est.areAllEstimationsMade());
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertFalse(est.areAllEstimationsMade());
		est.makeEstimate(dummyUser2.getUsername(), ESTIMATE_VALUE);
		assertTrue(est.areAllEstimationsMade());
	}
	
	@Test
	public void JSONTest() {
		String resultString = est.toJSON();
		Estimate convertedEst = Estimate.fromJson(resultString);
		assertEquals(convertedEst.getGameID() , est.getGameID());
		assertEquals(convertedEst.getReqID(), est.getReqID());
	}
	
	@Test
	public void userCanMakeEstimateTest(){
		est.addUser(dummyUser.getUsername());
		assertTrue(est.canMakeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE));
		assertFalse(est.canMakeEstimate(dummyUser2.getUsername(), ESTIMATE_VALUE));
	}
	
	@Test
	public void getUserEstimateTest(){
		est.addUser(dummyUser.getUsername());
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertEquals( ESTIMATE_VALUE, est.getEstimate(dummyUser.getUsername()));
	}
	
	@Test
	public void getAllUsersWithEstimatesTest(){
		
		est.addUser(dummyUser.getUsername());
		est.addUser(dummyUser2.getUsername());
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		Map<String,Integer> userEstimates = est.getUsersAndEstimates();
		assertTrue(userEstimates.containsKey(dummyUser.getUsername()));
		assertTrue(userEstimates.containsKey(dummyUser2.getUsername()));
	}
	
	@Test
	public void meanTest(){
		est.addUser(dummyUser.getUsername());
		est.addUser(dummyUser2.getUsername());
		
		assertEquals(0, est.getMean(), 0.1);
		
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertEquals(ESTIMATE_VALUE , est.getMean(), 0.1);
	}
	
	@Test
	public void getVoteCountTest(){
		est.addUser(dummyUser.getUsername());
		est.addUser(dummyUser2.getUsername());
		
		assertEquals(2, est.getMaxVoteCount());
		assertEquals(0, est.getVoteCount());
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertEquals(1, est.getVoteCount());
	}
	
	@Test
	public void medianTest(){
		est.addUser(dummyUser.getUsername());
		est.addUser(dummyUser2.getUsername());
		
		assertEquals(0, est.getMedian(), 0.1);
		
		est.makeEstimate(dummyUser.getUsername(), ESTIMATE_VALUE);
		assertEquals(ESTIMATE_VALUE , est.getMedian(), 0.1);
		
		est.makeEstimate(dummyUser2.getUsername(), ESTIMATE_VALUE);
		assertEquals(ESTIMATE_VALUE , est.getMedian(), 0.1);
	}
	
	@Test
	public void copyTest(){
		Estimate copy = est.getCopy();
		assertEquals(copy.getGameID(), est.getGameID());
		assertEquals(copy.getReqID(), est.getReqID());	
	}
	
	@Test
	public void settersTest(){
		est.setGameID(77);
		est.setReqID(77);
		est.setGameModifiedVersion(77);
		est.setNote("test");
		est.setFinalEstimate(77);
		
		ArrayList<Boolean> selection = new ArrayList<Boolean>();
		selection.add(true);
		selection.add(false);
		est.setUserCardSelection(dummyUser.getUsername(), selection );
		
		
		assertEquals("test", est.getNote());
		assertEquals(77, est.getGameID());
		assertEquals(77, est.getReqID());
		assertEquals(77, est.getGameModifiedVersion());
		assertEquals(77, est.getFinalEstimate());
		assertEquals(selection, est.getUserCardSelection(dummyUser.getUsername()));
	}
}
