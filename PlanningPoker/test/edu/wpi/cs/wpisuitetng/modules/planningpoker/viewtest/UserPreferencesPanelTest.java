package edu.wpi.cs.wpisuitetng.modules.planningpoker.viewtest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockNetwork;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.UserPreferencesPanel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;

public class UserPreferencesPanelTest {
	UserPreferencesPanel userPreferencesPane;
	
	@Before
	public void setup() {
		// Mock network
		Network.initNetwork(new MockNetwork());
		Network.getInstance().setDefaultNetworkConfiguration(
				new NetworkConfiguration("http://wpisuitetng"));

		userPreferencesPane = new UserPreferencesPanel();
	}

	@Test
	public void constructorTest() {
		assertNotNull(userPreferencesPane);
	}

	@Test
	public void isReadyToCloseTest(){
		//No change from init can close
		assertTrue(userPreferencesPane.isReadyToClose());
	}
	
	@Test
	public void changeHasBeenMadeTest(){
		userPreferencesPane.printStatus();
		userPreferencesPane.setInitEmail("new Email");
		userPreferencesPane.setInitAllowEmail(true);
		userPreferencesPane.printStatus();
		assertTrue(userPreferencesPane.changeHasBeenMade());
	}
}
