package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.playgame;

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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.awt.Dimension;
import java.awt.GridLayout;

public class ListRequirementsPanel extends JPanel
                      implements TreeSelectionListener {
    private JEditorPane htmlPane;
    private JTree tree;
    private URL helpURL;
    private static boolean DEBUG = false;

    //Optionally play with line styles.  Possible values are
    //"Angled" (the default), "Horizontal", and "None".
    private static boolean playWithLineStyle = false;
    private static String lineStyle = "Horizontal";
    
    //Optionally set the look and feel.
    private static boolean useSystemLookAndFeel = false;
    private Game game;

    public ListRequirementsPanel(Game game) {
        super(new GridLayout(1,0));
        
        this.game = game;

        //Create the nodes.
        DefaultMutableTreeNode top =
            new DefaultMutableTreeNode("Requirements");
        createNodes(top, game);

        //Create a tree that allows one selection at a time.
        tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Listen for when the selection changes.
        tree.addTreeSelectionListener(this);

        if (playWithLineStyle) {
            System.out.println("line style = " + lineStyle);
            tree.putClientProperty("JTree.lineStyle", lineStyle);
        }

        //Create the scroll pane and add the tree to it. 
        JScrollPane treeView = new JScrollPane(tree);
        
        Dimension minimumSize = new Dimension(100, 50);
        treeView.setMinimumSize(minimumSize);

        //Add the scroll panes to a split pane.
        JPanel reqPanel = new JPanel();
        reqPanel.add(treeView);

        
        reqPanel.setPreferredSize(new Dimension(500, 300));

        //Add the split pane to this panel.
        add(reqPanel);
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
        top.add(votedCategory);
    	
        for(Requirement req : votedOn)
        {
        	requirement = new DefaultMutableTreeNode(req.getName());
        	votedCategory.add(requirement);
        }
        
        votedCategory = new DefaultMutableTreeNode("Not Voted On");
        top.add(votedCategory);
    	
        for(Requirement req : notVotedOn)
        {
        	requirement = new DefaultMutableTreeNode(req.getName());
        	votedCategory.add(requirement);
        }
    }
        
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    
    // TODO: Figure out how to implement texturing on the tree
    /*private static void createAndShowGUI() {
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
        frame.add(new ListRequirementsPanel());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }*/
}