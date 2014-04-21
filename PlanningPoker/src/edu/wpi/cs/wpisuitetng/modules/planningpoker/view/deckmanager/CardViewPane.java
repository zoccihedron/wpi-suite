package edu.wpi.cs.wpisuitetng.modules.planningpoker.view.deckmanager;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
		currentView.setLayout(new GridBagLayout());
		this.setViewportView(currentView);
	}
	
	public JPanel cardView(Deck deck){
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridx = -1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.weighty = 1.0;
		
		currentView.removeAll();

		for(Integer card: deck.getCards()){
			
			final JToggleButton cardToAdd = new JToggleButton(Integer.toString(card), img);
			cardToAdd.setHorizontalTextPosition(SwingConstants.CENTER);
			cardToAdd.setVerticalTextPosition(SwingConstants.CENTER);
			
			
			if(constraints.gridx % (NUM_OF_COLUMNS-1) == 0 && constraints.gridx != 0){
				constraints.gridx = 0;
				constraints.gridy++;
			}else{
				constraints.gridx++;
			}
			
			//Set the anchor to be the last row
			if(constraints.gridy == deck.getCards().size() / NUM_OF_COLUMNS){
				constraints.anchor = GridBagConstraints.PAGE_END;
			}
			currentView.add(cardToAdd, constraints); 
		}
		
		return currentView;
		
		
	}
	
	public void updateView(Deck deck){
		currentView = cardView(deck);
		this.setViewportView(currentView);
	}

}
