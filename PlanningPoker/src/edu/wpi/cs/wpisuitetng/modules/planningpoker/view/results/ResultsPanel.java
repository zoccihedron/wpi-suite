package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.results;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class ResultsPanel extends JPanel{

	private Game game;
	private ListEstimatedRequirementsPanel listReqPanel;
	private final JLabel titleLabel;
	private final JLabel nameLabel;
	private final JLabel descriptionLabel;
	private final JScrollPane descriptionScroll;
	private final JTextArea requirementName;
	private final JTextArea descriptionText;
	private ResultsDisplayPanel resultsDisplayPanel;
	private int reqid;
	private Requirement req;




	/**
	 * Constructor for results panel
	 * @param listRequirementsPanel is the tree of requirements for the ended game
	 */
	public ResultsPanel(ListEstimatedRequirementsPanel listRequirementsPanel) {

		this.listReqPanel = listReqPanel;

		setBounds(5, 5, 307, 393);
		this.setLayout(new GridBagLayout());

		final Border jtextFieldBorder = new JTextField().getBorder();

		// Adds the fields and button to the main panel.
		requirementName = new JTextArea();
		requirementName.setEditable(false);
		requirementName.setEnabled(true);
		requirementName.setBorder(jtextFieldBorder);

		descriptionText = new JTextArea();
		descriptionText.setBorder(jtextFieldBorder);
		descriptionText.setEditable(false);
		descriptionText.setAutoscrolls(true);

		descriptionScroll = new JScrollPane();
		descriptionScroll.add(descriptionText);

		titleLabel = new JLabel("Game Information");

		nameLabel = new JLabel("Name:");

		descriptionLabel = new JLabel("Description:");


		infoPanelSetup();
	}


	/**
	 * Sets all the grid components for either constructor
	 */
	public void infoPanelSetup() {
		// DEFINE CONSTAINTS
		final GridBagConstraints constraints = new GridBagConstraints();



		// GAME INFORMATION LABEL
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 10;
		constraints.ipady = 10;
		add(titleLabel, constraints);

		// NAME LABEL
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 20, 0, 0);
		add(nameLabel, constraints);
		//nameLabel.setBorder(new EmptyBorder(5, 5, 5, 5));

		// NAME FIELD
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = Integer.MAX_VALUE;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.insets = new Insets(0, 20, 0, 20);
		add(requirementName, constraints);

		// DESCRIPTION LABEL
		descriptionLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 5;
		add(descriptionLabel, constraints);


		JScrollPane scrollPane = new JScrollPane(descriptionText); 
		descriptionText.setEditable(false);

		// DESCRIPTION
		descriptionText.setLineWrap(true);
		constraints.fill = GridBagConstraints.BOTH;

		// DESCRIPTION SCROLL
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.insets = new Insets(0, 20, 10, 20);
		add(scrollPane, constraints);

		// DISPLAY PANEL 
		// TODO make this GUI BEAUTIFUL
		resultsDisplayPanel = new ResultsDisplayPanel();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 12;
		constraints.weightx = 1.0;
		constraints.weighty = 1.5;
		constraints.insets = new Insets(10, 20, 0, 20);
		add(resultsDisplayPanel, constraints);
	}

	/**
	 * Update the panel with information from the Estimate in a game that 
	 * corresponds to the passed requirement
	 * @param reqid is the id of the requirement in req manager
	 * @param game is the game the estimate is in
	 */
	public void updateDisplay(int reqid, Game game){
		this.game = game;

		this.reqid = reqid;
		try{
			req = getRequirementFromId();
			requirementName.setText(req.getName());
			descriptionText.setText(req.getDescription());
			resultsDisplayPanel.updateData(reqid, game);
		}
		catch(NotFoundException exception){
			System.err.println("Exception: Requirement Not Found");
		}

	}

	private Requirement getRequirementFromId() throws NotFoundException{
		List<Requirement> reqs = RequirementManagerFacade.getInstance().getPreStoredRequirements();
		for(Requirement req: reqs){
			if(req.getId() == reqid){
				return req;
			}
		}
		throw new NotFoundException("Requirement was not found");
	}
	
	/**
	 * Refreshes the list requirements panel
	 */
	public void refresh(){
		listReqPanel.refresh();
	}
	
}



