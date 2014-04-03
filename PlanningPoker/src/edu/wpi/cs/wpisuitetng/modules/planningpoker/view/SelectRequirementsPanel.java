package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class SelectRequirementsPanel extends JPanel {
	private JTable existingRequirementsTable;
	private JTable requirementsToAddTable;
	private boolean DISABLED = false;
	private boolean ENABLED = true;
	private final JButton btnAddSelectedReq;
	
	public SelectRequirementsPanel() {
		this.setLayout(new GridBagLayout());
		// Parent Container
		GridBagConstraints constraints = new GridBagConstraints();

		// Top section of panel

		String[] columnNames = { "ID", "Name", "Description" };

		Object[][] data = {};

		JLabel existingRequirementsLabel = new JLabel("Existing Requirements");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.33;
		constraints.weighty = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(existingRequirementsLabel, constraints);

		JButton btnNewRequirement = new JButton("Refresh");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.33;
		constraints.weighty = 0.1;
		constraints.gridx = 2;
		constraints.gridy = 0;
		this.add(btnNewRequirement, constraints);

		existingRequirementsTable = new JTable(new DefaultTableModel(data,
				columnNames));
		
		existingRequirementsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(existingRequirementsTable.getSelectedRow() == - 1){
					btnAddSelectedReq.setEnabled(DISABLED);
				}
				else {
					btnAddSelectedReq.setEnabled(ENABLED);
				}
			}

		});
		
		// Hide the column with IDs
		existingRequirementsTable.removeColumn(existingRequirementsTable
				.getColumnModel().getColumn(0));

		// Filling with some initial data for testing
		final DefaultTableModel model = (DefaultTableModel) existingRequirementsTable
				.getModel();
		btnNewRequirement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Initially populates the existingReqs Table
				ArrayList<Requirement> existingRequirements = new ArrayList<Requirement>(
						RequirementManagerFacade.getInstance().getRequirments());
				ArrayList<Integer> pendingReqs = getRequirementIdsFromTable(requirementsToAddTable);
				ArrayList<Integer> existingReqs = getRequirementIdsFromTable(existingRequirementsTable);
				for (Requirement req : existingRequirements) {
					
					//Checks that the pulled requirements are
					//In the backlog
					//Not in the pendingRequirementsTable already
					//Not in the existingRequirementsTable already
					if (!existingReqs.contains(req.getId()) && !pendingReqs.contains(req.getId()) && req.getIteration().equals("Backlog")) {
						model.addRow(new Object[] {
								Integer.toString(req.getId()), req.getName(),
								req.getDescription() });
					}
				}
			}

		});

		JScrollPane existingRequirementsTablePanel = new JScrollPane(
				existingRequirementsTable);
		
		
		
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		constraints.weighty = 0.4;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(existingRequirementsTablePanel, constraints);

		// Bottom section of panel
		JLabel lblRequirementsToEstimate = new JLabel(
				"Requirements to Estimate");
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 0.33;
		constraints.weighty = 0.1;
		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(lblRequirementsToEstimate, constraints);

		btnAddSelectedReq = new JButton("Add");
		btnAddSelectedReq.setEnabled(DISABLED);
		
		btnAddSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(existingRequirementsTable,
						requirementsToAddTable);
				btnAddSelectedReq.setEnabled(DISABLED);
			}
		});
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.33;
		constraints.weighty = 0.1;
		constraints.gridx = 1;
		constraints.gridy = 2;
		this.add(btnAddSelectedReq, constraints);

		final JButton btnRemoveSelectedReq = new JButton("Remove");
		btnRemoveSelectedReq.setEnabled(DISABLED);
		btnRemoveSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(requirementsToAddTable,
						existingRequirementsTable);
				btnRemoveSelectedReq.setEnabled(DISABLED);
			}

		});
		constraints.fill = GridBagConstraints.BOTH;
		constraints.weightx = 0.33;
		constraints.weighty = 0.1;
		constraints.gridx = 2;
		constraints.gridy = 2;
		this.add(btnRemoveSelectedReq, constraints);

		String[] addColumnNames = { "ID", "Name", "Description" };
		Object[][] addData = {};

		requirementsToAddTable = new JTable(new DefaultTableModel(addData,
				addColumnNames));

		requirementsToAddTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(requirementsToAddTable.getSelectedRow() == - 1){
					btnRemoveSelectedReq.setEnabled(DISABLED);
				}
				else {
					btnRemoveSelectedReq.setEnabled(ENABLED);
				}
			}

		});
		
		// Hide the column with IDs
		requirementsToAddTable.removeColumn(requirementsToAddTable
				.getColumnModel().getColumn(0));

		JScrollPane requirementsToAddTablePanel = new JScrollPane(
				requirementsToAddTable);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_END;
		constraints.gridwidth = 3;
		constraints.weightx = 1;
		constraints.weighty = 0.4;
		constraints.gridx = 0;
		constraints.gridy = 3;
		this.add(requirementsToAddTablePanel, constraints);

	}

	/**
	 * This function is used to move requirements from one table to another The
	 * dest table must have as many columns or more then the first
	 * 
	 * @param src
	 *            the initial table to copy from
	 * @param dest
	 *            the table to copy to
	 */
	private void moveRequirementsBetweenTables(JTable src, JTable dest) {
		//Pull all the information we need to make this decision
		int selection = src.getSelectedRow();
		DefaultTableModel modelDest = (DefaultTableModel) dest.getModel();
		DefaultTableModel modelSrc = (DefaultTableModel) src.getModel();
		
		
		while (selection != -1) {
			int columnCount = modelSrc.getColumnCount();
			String[] values = new String[columnCount];
				
			//Pull All the column values
			for (int j = 0; j < columnCount; j++) {
				values[j] = (String) modelSrc.getValueAt(selection, j);
			}

			//Remove the entry we are moving
			modelSrc.removeRow(selection);

			//Insert the entry into the new table
			modelDest.addRow(new Object[] { values[0], values[1], values[2] });
			
			selection = src.getSelectedRow();
		}
	}

	/**
	 * This function iterates through the src table requirements and returns the
	 * currently selected ones
	 * 
	 * @param src The table to extract Requirement IDs from
	 * @return The requirements in the src table
	 */
	public ArrayList<Integer> getRequirementIdsFromTable(JTable src) {
		int countOfEntries = src.getRowCount();
		int REQID = 0;
		ArrayList<Integer> reqIDs = new ArrayList<Integer>();
		for (int i = 0; i < countOfEntries; i++) {
			reqIDs.add(Integer.valueOf((String) (src.getModel().getValueAt(i, REQID))));
		}
		return reqIDs;
	}
	
	/**
	 * This function iterates through the pending requirements and returns the
	 * currently selected ones
	 * 
	 * @return The requirements in the pending table
	 */
	public ArrayList<Integer> getSelectedRequirementIds() {
		return getRequirementIdsFromTable(requirementsToAddTable);
	}
}
