package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import javax.swing.JButton;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.results.ViewResultsController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;









import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EstimateTreePanel extends JPanel{
	private ListEstimatedRequirementsPanel listEstimateReqPanel;
	private JButton sendEstimateToReqButton;
	private EstimateTreePanel estimateTreePanel;
	private final int gameId;
	
	public EstimateTreePanel(Game game, ViewResultsController controller){
		estimateTreePanel = this;
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();

		
		gameId = game.getId();
		
		//tree
		listEstimateReqPanel = new ListEstimatedRequirementsPanel(game, controller);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(listEstimateReqPanel,constraints);
		
		
		//button
		sendEstimateToReqButton = new JButton();
		sendEstimateToReqButton.setText("Send Estimates");
	
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		constraints.fill = GridBagConstraints.NONE;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(sendEstimateToReqButton,constraints);
		
		
		sendEstimateToReqButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				
				ArrayList<Estimate> estimates = listEstimateReqPanel.getSelectedEstimates();
				
				for(Estimate e: estimates){
					e.setGameID(gameId);
					e.estimationSent(true);
				}
				RequirementManagerFacade facade = RequirementManagerFacade.getInstance();
				facade.sendEstimates(estimates,estimateTreePanel);
				System.out.println("---------------------------------plan to refresh tree");
				listEstimateReqPanel.refresh();
			}
			
		});
		
	}
	

	public ListEstimatedRequirementsPanel getTreePanel() {
		// TODO Auto-generated method stub
		return listEstimateReqPanel;
	}
	
	




	public void refresh() {
		listEstimateReqPanel.refresh();
		
	}


	

}
