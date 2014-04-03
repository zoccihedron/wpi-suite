/*******************************************************************************
 * Copyright (c) 2014 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Code On Bleu
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.controller.GetGamesController;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;

/**
 * @author irshusdock, acchaulk, bobby9002
 * @version $Revision: 1.0 $
 */
public class OverviewTable extends JTable
{
	private DefaultTableModel tableModel = null;
	private boolean changedByRefresh = false;	
	private Border paddingBorder = BorderFactory.createEmptyBorder(0, 4, 0, 0);
	
	/**
	 * Sets initial table view
	 * 
	 * @param data	Initial data to fill OverviewTable
	 * @param columnNames	Column headers of OverviewTable
	 */
	public OverviewTable(Object[][] data, String[] columnNames)
	{
		this.tableModel = new DefaultTableModel(data, columnNames)
		{
			@Override
            public boolean isCellEditable(int row, int column) {
               //all cells false
               return false;
            }
		};
		this.setModel(tableModel);
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setDragEnabled(true);
        this.setDropMode(DropMode.ON);
        
		this.getTableHeader().setReorderingAllowed(false);
		this.setAutoCreateRowSorter(true);
		setFillsViewportHeight(true);
	
		/* Create double-click event listener */
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				
				if(getRowCount() > 0)
				{
					int mouseY = e.getY();
					Rectangle lastRow = getCellRect(getRowCount() - 1, 0, true);
					int lastRowY = lastRow.y + lastRow.height;

					if(mouseY > lastRowY) 
					{
						getSelectionModel().clearSelection();
						repaint();
					}
				}
				
			}
		});
	}
	
	/**
	 * updates OverviewTable with the contents of the requirement model	 * 
	 */
	public void refresh() {
		
		List<Game> games = PlanningPokerModel.getInstance().getAllGames();
							
			// indicate that refresh is about to affect the table
			setChangedByRefresh(true);
				
		// clear the table
		tableModel.setRowCount(0);		
		
		for (int i = 0; i < games.size(); i++) {
			Game game = games.get(i);			
					
			tableModel.addRow(new Object[]{ 
					game.getName(),
					game.getStatus().toString(),
					game.getEnd(),
					game.getRequirements().size(),
					game.getGameCreator()
			});	
		}
		// indicate that refresh is no longer affecting the table
		setChangedByRefresh(false);
	}
		
	/**
	 * @return the state of being changed by refresh
	 */
	public boolean wasChangedByRefresh() {
		return changedByRefresh;
	}

	/**
	 * @param changedByRefresh the changedByRefresh to set
	 */
	public void setChangedByRefresh(boolean changedByRefresh) {
		this.changedByRefresh = changedByRefresh;
	}
}
	
	

	
	
