/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Code On Bleu
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.ChangeDeadline;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.MainViewTabController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.view.NewGamePanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**
 * This class is a JPanel. 
 * It contains a table of requirements in a game.
 * 
 * @author Code On Bleu
 * @version 1.0
 */
@SuppressWarnings({ "serial" })
public class GameSummaryReqPanel extends JPanel {

	private JTable requirementsTable = null;
	private DefaultTableModel modelReqs;

	/**
	 * This constructor is to be used when starting from a new game
	 * 
	 */
	public GameSummaryReqPanel() {
		
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.NORTHWEST;
		
		// Top section of panel
		final String[] columnNames = { "ID", "Name", "Description" };
		final Object[][] data = {};
				
		requirementsTable = new JTable(new DefaultTableModel(data,columnNames) {
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		

		// Hide the column with IDs
		requirementsTable.removeColumn(requirementsTable
				.getColumnModel().getColumn(0));
		
		final JScrollPane requirementsTablePanel = new JScrollPane(requirementsTable);
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 1;
		constraints.weighty = 1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(requirementsTablePanel, constraints);

	}
	
	/**
	 * fills the requirements table in this GameSummaryReqPanel
	 * @param game to get requirements from
	 */
	private void fillRequirementsTable(Game game){
		//TODO Empty table before filling, maybe separate function called in updateReqSummary?
		modelReqs = (DefaultTableModel) requirementsTable.getModel();
		List<Integer> reqIDs = game.getRequirements();
		for(Requirement req : RequirementManagerFacade.getInstance().getPreStoredRequirements()){
			if(reqIDs.contains(req.getId())){
				modelReqs.addRow(new Object[] {
						Integer.toString(req.getId()), req.getName(),
						req.getDescription()});
			}
		}
	}
	
	/**
	 * fills fields on this panel with info specific to game 
	 * @param game to get info from
	 */
	public void updateReqSummary(Game game){
		fillRequirementsTable(game);
		
	}
	
}