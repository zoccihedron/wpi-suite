package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame;

import javax.swing.DropMode;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.facade.RequirementManagerFacade;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Estimate;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.ViewEventController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.CustomTreeCellRenderer;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.view.overview.IterationTransferHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ListRequirementsPanel extends JPanel
implements TreeSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTree tree;
	private static boolean DEBUG = false;

	//Optionally play with line styles.  Possible values are
	//"Angled" (the default), "Horizontal", and "None".
	private static boolean playWithLineStyle = false;
	private static String lineStyle = "Horizontal";

	//Optionally set the look and feel.
	private static boolean useSystemLookAndFeel = false;
	private Game game;
	
	private boolean shownOnce = false;

	public ListRequirementsPanel(final Game game) {
		super(new GridLayout(1,0));

		this.game = game;

		

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
				
				if(shownOnce){
					return;
				}
				shownOnce = true;
				refresh();				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub

			}
		});

		//Create a tree that allows one selection at a time.
		//		tree = new JTree(top);
		//		tree.getSelectionModel().setSelectionMode
		//		(TreeSelectionModel.SINGLE_TREE_SELECTION);
		//
		//		//Listen for when the selection changes.
		//		tree.addTreeSelectionListener(this);
		//
		//		if (playWithLineStyle) {
		//			System.out.println("line style = " + lineStyle);
		//			tree.putClientProperty("JTree.lineStyle", lineStyle);
		//		}
	}

	/** Required by TreeSelectionListener interface. */
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
				tree.getLastSelectedPathComponent();

		if (node == null) return;
		//TODO: see about implementing DoublieClick to send data to estimate panel
	}

	private void createNodes(DefaultMutableTreeNode top, Game game) {

		List<Requirement> requirements = RequirementManagerFacade.getInstance().getRequirments();
		System.out.println(requirements.size());
		DefaultMutableTreeNode votedCategory = null;
		DefaultMutableTreeNode requirement = null;

		List<Requirement> notVotedOn = new ArrayList<Requirement>();
		List<Requirement> votedOn = new ArrayList<Requirement>();
		List<Estimate> estimates = game.getEstimates();

		for(Requirement req : requirements)
		{
			for(Estimate est: estimates)
			{
				if (req.getId() == est.getReqID())
				{
					notVotedOn.add(req);
					// TODO: Implement check against session user for sorting by if voted or not
				}
			}
		}

		votedCategory = new DefaultMutableTreeNode("Voted On");



		for(Requirement req : votedOn)
		{
			requirement = new DefaultMutableTreeNode(req);
			votedCategory.add(requirement);
		}

		top.add(votedCategory);

		votedCategory = new DefaultMutableTreeNode("Not Voted On");


		for(Requirement req : notVotedOn)
		{
			requirement = new DefaultMutableTreeNode(req);
			votedCategory.add(requirement);
		}

		top.add(votedCategory);

	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */

	// TODO: Figure out how to implement texturing on the tree
	public void createAndShowGUI() {
		if (useSystemLookAndFeel) {
			try {
				UIManager.setLookAndFeel(
						UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				System.err.println("Couldn't use system look and feel.");
			}
		}

		//Create and set up the window.
		JFrame frame = new JFrame("TreeDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Add content to the window.
		frame.add(new ListRequirementsPanel(game));

		//Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public void refresh(){
		

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Requirements"); //makes a starting node
		List<Requirement> requirements = RequirementManagerFacade.getInstance().getRequirments(); //retreive the list of all iterations
		for(Requirement req: requirements){

			DefaultMutableTreeNode newIterNode = new DefaultMutableTreeNode(req); //make a new iteration node to add
			System.out.println(req.getName());

			top.add(newIterNode); //add the iteration's node to the top node
		}

		tree = new JTree(top); //create the tree with the top node as the top
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); //tell it that it can only select one thing at a time
		tree.setToggleClickCount(0);

		tree.setCellRenderer(new CustomTreeCellRenderer()); //set to custom cell renderer so that icons make sense
		tree.addTreeSelectionListener(this);

		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.ON);
		
		//Create the scroll pane and add the tree to it. 
		final JScrollPane treeView = new JScrollPane(tree);

		Dimension minimumSize = new Dimension(100, 50);
		treeView.setMinimumSize(minimumSize);

		//Add the split pane to this panel.
		add(treeView);

		System.out.println("finished refreshing the tree");
	}
}