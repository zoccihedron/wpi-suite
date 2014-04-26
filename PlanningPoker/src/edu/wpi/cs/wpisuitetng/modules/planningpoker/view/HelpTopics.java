/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpTopics extends JPanel {
	public String topicTitle;
	public String showThisText;
	
	public JPanel helpInfo;
	public JLabel textHere;
	
	public HelpTopics() {
		final Dimension minimumSize = new Dimension(250, 300);
		this.setMinimumSize(minimumSize);
		textHere = null;
		setUpHelpTopic();
	}

	public HelpTopics(String topic, String showThisText) {
		topicTitle = topic;
		this.showThisText = showThisText;
		
		setUpHelpTopic();
	}

	public String toString() {
		return topicTitle;
	}

	public String getText() {
		return showThisText;
	}

	public void setUpHelpTopic() {
		this.setLayout(new GridBagLayout());
		
		textHere = new JLabel();
		textHere.setText(showThisText);
		add(textHere);
	}

}
