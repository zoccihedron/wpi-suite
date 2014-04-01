package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SelectRequirementsPanel extends JPanel {
	private JTable existingRequirementsTable;
	private JTable requirementsToAddTable;

	public SelectRequirementsPanel() {
	
		// Parent Container 
		JSplitPane requirementsSelectionPane = new JSplitPane();
		requirementsSelectionPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(requirementsSelectionPane);

		JPanel existingRequirementsPanel = new JPanel();
		requirementsSelectionPane.setLeftComponent(existingRequirementsPanel);

		// Top Half of split pane

		String[] columnNames = { "ID", "Name", "Description" };

		Object[][] data = {};

		JLabel existingRequirementsLabel = new JLabel("Existing Requirements");
		existingRequirementsPanel.add(existingRequirementsLabel);
		//existingRequirementsLabel.setHorizontalAlignment(SwingConstants.LEFT);
		//existingRequirementsLabel.setVerticalAlignment(SwingConstants.TOP);

		JButton btnNewRequirement = new JButton("Create Requirement");
		//btnNewRequirement.setHorizontalAlignment(SwingConstants.RIGHT);
		//btnNewRequirement.setVerticalAlignment(SwingConstants.TOP);
		existingRequirementsPanel.add(btnNewRequirement);

		existingRequirementsTable = new JTable(new DefaultTableModel(data,
				columnNames));
		// Hide the column with IDs
		existingRequirementsTable.removeColumn(existingRequirementsTable.getColumnModel().getColumn(0));

		// Filling with some initial data for testing
		final DefaultTableModel model = (DefaultTableModel) existingRequirementsTable
				.getModel();
		btnNewRequirement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				//Initially populates the existingReqs Table
				ArrayList<Requirement> existingRequirements = new ArrayList<Requirement>(
						RequirementManagerFacade.getInstance().getRequirments());
				for (Requirement req : existingRequirements) {
					model.addRow(new Object[] { Integer.toString(req.getId()),
							req.getName(), req.getDescription() });
				}
			}

		});

		JScrollPane existingRequirementsTablePanel = new JScrollPane(
				existingRequirementsTable);
		existingRequirementsPanel.add(existingRequirementsTablePanel);
		//existingRequirementsTablePanel.setHorizontalAlignment(SwingConstants.CENTER);

		// Bottom Half of Split Pane
		JPanel pendingRequirementsPanel = new JPanel();
		requirementsSelectionPane.setRightComponent(pendingRequirementsPanel);

		JLabel lblRequirementsToEstimate = new JLabel(
				"Requirements to Estimate");
		pendingRequirementsPanel.add(lblRequirementsToEstimate);

		JButton btnAddSelectedReq = new JButton("Add");

		btnAddSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(existingRequirementsTable,
						requirementsToAddTable);
			}
		});

		pendingRequirementsPanel.add(btnAddSelectedReq);

		JButton btnRemoveSelectedReq = new JButton("Remove");
		btnRemoveSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(requirementsToAddTable,
						existingRequirementsTable);
			}

		});
		pendingRequirementsPanel.add(btnRemoveSelectedReq);

		String[] addColumnNames = { "ID", "Name", "Description" };
		Object[][] addData = {};

		requirementsToAddTable = new JTable(new DefaultTableModel(addData,
				addColumnNames));
		
		// Hide the column with IDs
		requirementsToAddTable.removeColumn(requirementsToAddTable.getColumnModel().getColumn(0));
		
		JScrollPane requirementsToAddTablePanel = new JScrollPane(
				requirementsToAddTable);
		pendingRequirementsPanel.add(requirementsToAddTablePanel);

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
		int selection = src.getSelectedRow();

		if (selection != -1) {
			DefaultTableModel model = (DefaultTableModel) src.getModel();

			int columnCount = model.getColumnCount();
			String[] values = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				values[i] = (String) model.getValueAt(selection, i);
			}

			model.removeRow(selection);

			// Add the values to that other table
			DefaultTableModel modelToAddTo = (DefaultTableModel) dest
					.getModel();
			modelToAddTo
					.addRow(new Object[] { values[0], values[1], values[2] });
		}

	}

	/**
	 * This function iterates through the pending requirements and returns the
	 * currently selected ones
	 * 
	 * @return The requirements in the pending table
	 */
	public ArrayList<Integer> getSelectedRequirementIds() {
		int countOfEntries = requirementsToAddTable.getRowCount();
		int REQID = 0;
		ArrayList<Integer> reqIDs = new ArrayList<Integer>();
		for (int i = 0; i < countOfEntries; i++) {
			reqIDs.add(Integer.valueOf((String) (requirementsToAddTable
					.getValueAt(i, REQID))));
		}
		return reqIDs;
	}
}
