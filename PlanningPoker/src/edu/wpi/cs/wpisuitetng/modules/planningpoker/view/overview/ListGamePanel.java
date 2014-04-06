package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.overview;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerEntityManager;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.PlanningPokerModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.network.Network;

public class ListGamePanel extends JScrollPane
implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private List<Game> games;
	
	/**
	 * Constructs the panel
	 * @param game Taken in to get all requirements for the game
	 */
	public ListGamePanel() {

		this.setViewportView(tree);
		this.refresh();  
		games = PlanningPokerModel.getInstance().getAllGames();

		//Create the nodes.
		this.addComponentListener(new ComponentListener()
		{

			@Override
			public void componentResized(ComponentEvent e) {

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent e) {
				try{
					if(Network.getInstance().getDefaultNetworkConfiguration() != null){
						refresh();
					}
				}

				catch(RuntimeException exception){
				}
				
				
				
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;
		//TODO: see about implementing DoublieClick to send data to estimate panel
//		refresh();
	}


	/**
	 * This method is used to refresh the requirements tree
	 */
	public void refresh(){

		final DefaultMutableTreeNode top = new DefaultMutableTreeNode("Games"); //makes a starting node
		games = PlanningPokerModel.getInstance().getAllGames();
		DefaultMutableTreeNode gameNode = null;

		DefaultMutableTreeNode gameInProgressCategory = new DefaultMutableTreeNode("In Progress");
		DefaultMutableTreeNode gameEndedCategory = new DefaultMutableTreeNode("Ended");
		DefaultMutableTreeNode gameDraftCategory = new DefaultMutableTreeNode("Draft");
		
		for(Game game: games){

			// add new node to requirement tree
			gameNode = new DefaultMutableTreeNode(game);
			top.add(gameNode);
			switch (game.getStatus()){
				case IN_PROGRESS: 
					gameInProgressCategory.add(gameNode);
					break;
				case DRAFT: 
					gameDraftCategory.add(gameNode);
					break;
				case ENDED: 
					gameEndedCategory.add(gameNode);
					break;
			}

			top.add(gameDraftCategory);
			top.add(gameInProgressCategory);
			top.add(gameEndedCategory);
		}

	
		tree = new JTree(top); //create the tree with the top node as the top
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //tell it that it can only select one thing at a time
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer()); //set to custom cell renderer so that icons make sense
		tree.addTreeSelectionListener(this);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);

		this.setViewportView(tree); //make panel display the tree

		System.out.println("# of Games:" + games.size());
		System.out.println("finished refreshing the tree");
	}
}
