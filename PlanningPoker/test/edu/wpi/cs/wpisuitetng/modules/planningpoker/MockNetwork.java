package edu.wpi.cs.wpisuitetng.modules.planningpoker;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.MockRequest;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class MockNetwork extends Network{

		
		protected MockRequest lastRequestMade = null;
		
		/**
		 * Method makeRequest.
		 * @param path String
		 * @param requestMethod HttpMethod
		
		 * @return Request */
		@Override
		public Request makeRequest(String path, HttpMethod requestMethod) {
			if (requestMethod == null) {
				throw new NullPointerException("requestMethod may not be null");
			}
			
			lastRequestMade = new MockRequest(defaultNetworkConfiguration, path, requestMethod); 
			
			return lastRequestMade;
		}
		
		/**
		 * Method getLastRequestMade.
		
		 * @return MockRequest */
		public MockRequest getLastRequestMade() {
			return lastRequestMade;
		}
}

