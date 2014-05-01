/*******************************************************************************
 * Copyright (c) 2014 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Creator:
 *    Team Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.newgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;

/** This class is the view from which the
 * user can select requirements to add to a game
 * 
 * @author Code On Bleu
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class SelectRequirementsPanel extends JPanel {
	private JTable existingRequirementsTable;
	private JTable requirementsToAddTable = null;
	private final boolean DISABLED = false;
	private final boolean ENABLED = true;
	private boolean firstTimeCreating = true;
	private JButton btnAddSelectedReq;
	private JButton btnNewRequirement;
	private JButton btnCreateAndAdd;
	private JButton btnCancelNewReq;
	private DefaultTableModel modelExisting;
	private DefaultTableModel modelAdded;
	private JPanel buttonsPanel;
	private JPanel newReqPanel;
	private JPanel newReqButtonsPanel;
	private JScrollPane existingRequirementsTablePanel;
	private JScrollPane requirementsToAddTablePanel;
	private JLabel lblRequirementsToEstimate;
	private JLabel existingRequirementsLabel;
	private JTextField fldName;
	private JTextArea fldDescription;
	private boolean newReqNameValid = false;
	private boolean newReqDescValid = false;
	private boolean creatingNewReq = false;

	private final GridBagConstraints constraints = new GridBagConstraints();
	
	private Game game;
	
	public SelectRequirementsPanel() {
		populatePanel();
		this.setMinimumSize(new Dimension(500, 1));
	}
	
	/**
	 * Constructor for the select requirements panel
	 * @param editingGame the game for which the requirements will be edited
	 */
	public SelectRequirementsPanel(Game editingGame) {
		game = editingGame;
		populatePanel();
	}
	
	/**
	 * Sets up shared constraints between both constructors
	 */
	public void populatePanel(){
		// set layout
		this.setLayout(new GridBagLayout());
	
		
		/*---------------------------------------------------------------------
		 *                   EXISTING REQUIREMENTS TABLE
		 * --------------------------------------------------------------------
		 */
		
		// Columns and data for table
		final String[] columnNames = { "ID", "Name", "Description" };
		final Object[][] data = {};
	
		// Label
		existingRequirementsLabel = new JLabel("Existing Requirements");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		this.add(existingRequirementsLabel, constraints);
		
		// Table
		existingRequirementsTable = new JTable(new DefaultTableModel(data, columnNames) {
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		
		// Set buttons to respond to selection on table
		existingRequirementsTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
			@Override
			
			public void valueChanged(ListSelectionEvent arg0) {
				if(existingRequirementsTable.getSelectedRow() == - 1){
					btnAddSelectedReq.setEnabled(DISABLED);
					btnAddSelectedReq.setToolTipText("Please select at least one of the above requirements to be added.");
				}
				else {
					requirementsToAddTable.clearSelection();
					btnAddSelectedReq.setEnabled(ENABLED);
					btnAddSelectedReq.setToolTipText("Click here to add the selected requirement(s).");
				}
			}

		});
		
		// Hide the column with IDs
		existingRequirementsTable.removeColumn(
				existingRequirementsTable.getColumnModel().getColumn(0));

		// Filling with some initial data for testing
		modelExisting = (DefaultTableModel) existingRequirementsTable.getModel();

		// Put in scroll pane for overflow
		existingRequirementsTablePanel = new JScrollPane(existingRequirementsTable);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 4;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(existingRequirementsTablePanel, constraints);
					
		/*---------------------------------------------------------------------
		 *                       - BUTTONS PANEL -
		 * --------------------------------------------------------------------
		 */
		
		// Panel to hold add, remove, and new requirement buttons in center
		buttonsPanel = new JPanel();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(buttonsPanel, constraints);

		// Add requirement button
		btnAddSelectedReq = new JButton("Add");
		btnAddSelectedReq.setEnabled(DISABLED);
		btnAddSelectedReq.setToolTipText("Please select at least one of the above requirements to be added.");
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		buttonsPanel.add(btnAddSelectedReq, constraints);
		
		// Add requirement button
		btnNewRequirement = new JButton("New Requirement");
		btnNewRequirement.setEnabled(ENABLED);
		btnNewRequirement.setToolTipText("Click here to create a new requirement.");
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		buttonsPanel.add(btnNewRequirement, constraints);
		
		// Remove requirement button
		final JButton btnRemoveSelectedReq = new JButton("Remove");
		btnRemoveSelectedReq.setEnabled(DISABLED);
		btnRemoveSelectedReq.setToolTipText("Please select at least one of the below requirements to be removed.");
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 2;
		constraints.gridy = 0;
		buttonsPanel.add(btnRemoveSelectedReq, constraints);
		
		btnAddSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(existingRequirementsTable,
						requirementsToAddTable);
				btnAddSelectedReq.setEnabled(DISABLED);
				btnAddSelectedReq.setToolTipText("Please select at least one of the above requirements to be added.");
			}
		});
		
		btnNewRequirement.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(firstTimeCreating)
				{ 
					generateNewRequirementPanel();
					firstTimeCreating = false;
				}
				else
				{
					showNewRequirementPanel();
				}
			}
		});
		
		btnRemoveSelectedReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				moveRequirementsBetweenTables(requirementsToAddTable,
						existingRequirementsTable);
				btnRemoveSelectedReq.setEnabled(DISABLED);
				btnRemoveSelectedReq.setToolTipText("Please select at least one of the below requirements to be removed.");
			}

		});
		
		final Timer refreshTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				fillTable();
				
			}
		});
		refreshTimer.start();
	
		/*---------------------------------------------------------------------
		 *                   SELECTED REQUIREMENTS TABLE
		 * --------------------------------------------------------------------
		 */	
	
		// Label
		lblRequirementsToEstimate = new JLabel("Requirements to Estimate");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 3;
		this.add(lblRequirementsToEstimate, constraints);
		
		// columns and data for table
		final String[] addColumnNames = { "ID", "Name", "Description" };
		final Object[][] addData = {};

		// Table
		requirementsToAddTable = new JTable(new DefaultTableModel(addData,
				addColumnNames){
			public boolean isCellEditable(int row, int column){
				return false;
			}
		});
		
		// Set up buttons to interact with table selection
		requirementsToAddTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(requirementsToAddTable.getSelectedRow() == - 1){
					btnRemoveSelectedReq.setEnabled(DISABLED);
					btnRemoveSelectedReq.setToolTipText("Please select at least one of the below requirements to be removed.");
				}
				else {
					existingRequirementsTable.clearSelection();
					btnRemoveSelectedReq.setEnabled(ENABLED);
					btnRemoveSelectedReq.setToolTipText("Click here to remove the selected requirement(s).");
				}
			}

		});
		
		// Hide the column with IDs
		requirementsToAddTable.removeColumn(requirementsToAddTable.getColumnModel().getColumn(0));

		// Add to scroll pane for overflow
		requirementsToAddTablePanel = new JScrollPane(requirementsToAddTable);
		constraints.fill = GridBagConstraints.BOTH;
		constraints.anchor = GridBagConstraints.PAGE_END;
		constraints.gridwidth = 4;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 4;
		this.add(requirementsToAddTablePanel, constraints);

		
		/*---------------------------------------------------------------------
		 *                   	      ICONS
		 * --------------------------------------------------------------------
		 */	
	
		try {
		    Image img = ImageIO.read(getClass().getResource("downArrow.png"));
		    btnAddSelectedReq.setIcon(new ImageIcon(img));
		    
		    
		    img = ImageIO.read(getClass().getResource("upArrow.png"));
		    btnRemoveSelectedReq.setIcon(new ImageIcon(img)); 
		
		    img = ImageIO.read(getClass().getResource("new_req.png"));
		    btnNewRequirement.setIcon(new ImageIcon(img));
		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
				
		fillTable();
	}

	/**
	 * creates panel to add a new requirement while creating a planning poker session
	 */
	private void generateNewRequirementPanel(){
		
		creatingNewReq = true;
		
		existingRequirementsTablePanel.setVisible(false);
		existingRequirementsTablePanel.setEnabled(false);
		buttonsPanel.setVisible(false);
		buttonsPanel.setEnabled(false);
		
		newReqPanel = new JPanel();
		
		newReqButtonsPanel = new JPanel();
		
		existingRequirementsLabel.setText("New Requirement");
		
		final JLabel lblName = new JLabel("Name: *");
		fldName = new JTextField();
		final JLabel lblDescription = new JLabel("Description: *");
		fldDescription = new JTextArea();
		fldDescription.setLineWrap(true);
		
		newReqPanel.setLayout(new GridBagLayout());
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		newReqPanel.add(lblName, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		newReqPanel.add(fldName, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 1;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.fill = GridBagConstraints.NONE;
		newReqPanel.add(lblDescription, constraints);
		

		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.fill = GridBagConstraints.BOTH;
		newReqPanel.add(fldDescription, constraints);
		
		// Put in scroll pane for overflow
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 4;
		constraints.weightx = 1;
		constraints.weighty = 0.5;
		constraints.gridx = 0;
		constraints.gridy = 1;
		this.add(newReqPanel, constraints);
		
		btnCreateAndAdd = new JButton("Create and Add");
		btnCreateAndAdd.setToolTipText("Click here to create the new requirement.");
		btnCancelNewReq = new JButton("Cancel New Requirement");
		btnCancelNewReq.setToolTipText("Click here to go back to the select requirements screen.");
		
		try {
		    Image img = ImageIO.read(getClass().getResource("create_and_add.png"));
		    btnCreateAndAdd.setIcon(new ImageIcon(img));
		    
		    img = ImageIO.read(getClass().getResource("red_circle_x.png"));
		    btnCancelNewReq.setIcon(new ImageIcon(img));
		} 
		catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		// Add requirement button
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		newReqButtonsPanel.add(btnCreateAndAdd, constraints);
		
		// Add requirement button
		btnCancelNewReq.setSize(btnCancelNewReq.getWidth(), btnCreateAndAdd.getHeight());
		constraints.fill = GridBagConstraints.NONE;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 1;
		constraints.gridy = 0;
		newReqButtonsPanel.add(btnCancelNewReq, constraints);
		
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.weightx = 0.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 2;
		this.add(newReqButtonsPanel, constraints);
		
		fldName.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		fldDescription.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
		
		btnCreateAndAdd.setEnabled(false);
		
		fldName.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(fldName.getText().trim().equals("")){
					fldName.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					newReqNameValid = false;
				}
				else {
					final JTextField temp = new JTextField();
					fldName.setBorder(temp.getBorder());
					newReqNameValid = true;
				}
				btnCreateAndAdd.setEnabled(newReqNameValid && newReqDescValid);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(fldName.getText().trim().equals("")){
					fldName.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					newReqNameValid = false;
				}
				else {
					final JTextField temp = new JTextField();
					fldName.setBorder(temp.getBorder());
					newReqNameValid = true;
				}
				btnCreateAndAdd.setEnabled(newReqNameValid && newReqDescValid);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		fldDescription.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				if(fldDescription.getText().trim().equals("")){
					fldDescription.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					newReqDescValid = false;
				}
				else {
					final JTextField temp = new JTextField();
					fldDescription.setBorder(temp.getBorder());
					newReqDescValid = true;
				}
				btnCreateAndAdd.setEnabled(newReqNameValid && newReqDescValid);
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(fldDescription.getText().trim().equals("")){
					fldDescription.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					newReqDescValid = false;
				}
				else {
					final JTextField temp = new JTextField();
					fldDescription.setBorder(temp.getBorder());
					newReqDescValid = true;
				}
				btnCreateAndAdd.setEnabled(newReqNameValid && newReqDescValid);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		btnCancelNewReq.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cancelNewReq();
			}

		});
		
		btnCreateAndAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				final Requirement req = new Requirement(10, fldName.getText(), 
						fldDescription.getText());
				final RequirementManagerFacade RMF = RequirementManagerFacade.getInstance();
				RMF.createNewRequirement(req);
				addNewRequirementToTable(req);
				fillTable();
				cancelNewReq();
			}

		});
		
		this.revalidate();
		this.repaint();
		
	}
	
	/**
	 * This is a method which makes the panel visible which
	 * creates a new requirement
	 */
	public void showNewRequirementPanel()
	{

		existingRequirementsTablePanel.setVisible(false);
		existingRequirementsTablePanel.setEnabled(false);
		buttonsPanel.setVisible(false);
		buttonsPanel.setEnabled(false);
		
		newReqButtonsPanel.setVisible(true);
		newReqButtonsPanel.setEnabled(true);
		newReqPanel.setVisible(true);
		newReqPanel.setEnabled(true);
		creatingNewReq = true;
	}
	
	/**
	 * add requirement to the added table
	 * @param req to add
	 */
	private void addNewRequirementToTable(Requirement req){
		modelAdded.addRow(new Object[] {
				Integer.toString(req.getId()), req.getName(),
				req.getDescription() });
	}
	
	/**
	 * replaces the new req panel with the existing requirements panel
	 */
	private void cancelNewReq(){
		
		// disable the create new requirement panel
		newReqButtonsPanel.setVisible(false);
		newReqButtonsPanel.setEnabled(false);
		newReqPanel.setVisible(false);
		newReqPanel.setEnabled(false);
		
		creatingNewReq = false;
		
		// empty the newReqPanel
		fldDescription.setText("");
		fldName.setText("");
		
		
		// Put in scroll pane for overflow
		existingRequirementsTablePanel.setVisible(true);
		existingRequirementsTablePanel.setEnabled(true);

		// Panel to hold add, remove, and new requirement buttons in center
		buttonsPanel.setVisible(true);
		buttonsPanel.setEnabled(true);
		
		this.invalidate();
		this.repaint();
		
	}

	/**
	 * Fills the table with a list of requirements
	 */
	public void fillTable() {
		modelAdded = (DefaultTableModel) requirementsToAddTable
				.getModel();
		final List<Requirement> existingRequirements = new ArrayList<Requirement>(
				RequirementManagerFacade.getInstance().getPreStoredRequirements());
		final List<Integer> pendingReqs = 
				getRequirementIdsFromTable(requirementsToAddTable);
		final List<Integer> existingReqs = 
				getRequirementIdsFromTable(existingRequirementsTable);
		for (Requirement req : existingRequirements) {
			if (!(req.getIteration().equals("Backlog")) ||
								req.getStatus() == RequirementStatus.DELETED) {
				if (existingReqs.contains(req.getId())) {
					removeRowByValue(req, existingRequirementsTable);
				} else if (pendingReqs.contains(req.getId())) {
					removeRowByValue(req, requirementsToAddTable);
				}
				
			/* Checks that the pulled requirements are
			 * Not in the pendingRequirementsTable already
			 * Not in the existingRequirementsTable already
			 */
			} else if (game != null && 
					!existingReqs.contains(req.getId()) && 
					!pendingReqs.contains(req.getId())) {
				if(game.getRequirements().contains(req.getId())){
					modelAdded.addRow(new Object[] {
							Integer.toString(req.getId()), req.getName(),
							req.getDescription() });
				}
				else{
					modelExisting.addRow(new Object[] {
							Integer.toString(req.getId()), req.getName(),
							req.getDescription() });
				}
			} else if (!existingReqs.contains(req.getId()) && !pendingReqs.contains(req.getId())) {
				modelExisting.addRow(new Object[] {
						Integer.toString(req.getId()), req.getName(),
						req.getDescription() });
			}
		}
	}

	private static void removeRowByValue(Requirement req, JTable src) {
		final int reqID = req.getId();
		final int entries = src.getRowCount();
		for(int i = 0; i < entries; i++) {
			int idToCompare =  Integer.valueOf((String) src.getModel().getValueAt(i, 0));
			if (idToCompare == reqID){
				((DefaultTableModel) src.getModel()).removeRow(i);
			}
		}
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
	private static void moveRequirementsBetweenTables(JTable src, JTable dest) {
		//Pull all the information we need to make this decision
		int selection = src.getSelectedRow();
		final DefaultTableModel modelDest = (DefaultTableModel) dest.getModel();
		final DefaultTableModel modelSrc = (DefaultTableModel) src.getModel();
		
		
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
	 * Either enables or disables a pink border around elements
	 * on the panel if there is problem with the requirements.
	 * @param check
	 */
	public void displayErrorBorders(boolean check) {
		if(check){
			requirementsToAddTablePanel.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
		}
		else{
			requirementsToAddTablePanel.setBorder(existingRequirementsTablePanel.getBorder());
		}
	}

	/**
	 * This function iterates through the src table requirements and returns the
	 * currently selected ones
	 * 
	 * @param src The table to extract Requirement IDs from
	 * @return The requirements in the src table
	 */
	public static List<Integer> getRequirementIdsFromTable(JTable src) {
		final int countOfEntries = src.getRowCount();
		final int REQID = 0;
		final List<Integer> reqIDs = new ArrayList<Integer>();
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
	public List<Integer> getSelectedRequirementIds() {
		return getRequirementIdsFromTable(requirementsToAddTable);
	}
	
	/**
	 * Checks if the panel is currently creating a new requirement.
	 *
	 * @return if the user is creating a new requirement.
	 */
	public boolean isCreatingNewReq() {
		return creatingNewReq;
	}
}
