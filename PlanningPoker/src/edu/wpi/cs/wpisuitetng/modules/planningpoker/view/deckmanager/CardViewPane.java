package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Deck;

public class CardViewPane extends JScrollPane{
	private JPanel currentView;
	private ArrayList<JToggleButton> toggleBtns;
	private ImageIcon img;
	private final int NUM_OF_COLUMNS = 4;  
	
	public CardViewPane(){
		
		toggleBtns = new ArrayList<JToggleButton>();
		
		try {
			img = new ImageIcon(ImageIO.read(getClass().getResource(
					"cardtemplate.png")));
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
		}
		
		currentView = new JPanel();
		
	}
	
	public JPanel cardView(Deck deck){
		this.setViewportView(currentView);
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		
		currentView.removeAll();

		for(Integer card: deck.getCards()){
			
			final JToggleButton cardToAdd = new JToggleButton(Integer.toString(card), img);
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			
			currentView.add(cardToAdd, constraints); 
			
			if(constraints.gridx % (NUM_OF_COLUMNS-1) == 0){
				constraints.gridx = 0;
				constraints.gridy++;
				constraints.anchor = GridBagConstraints.PAGE_END;
			}else{
				constraints.gridx++;
			}
		}
		
		return currentView;
		
		
	}
	
	public void updateView(Deck deck){
		currentView = cardView(deck);
	}

}
