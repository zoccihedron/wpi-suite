// $codepro.audit.disable accessorMethodNamingConvention
/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.help;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * Facade for help topic panes
 * @author Codon Bleu
 * @version 1.0
 */
@SuppressWarnings("serial")
public class HelpTopic extends JPanel {
	private String topicTitle;
	private String showThisText;
	
	private JLabel titleText;
	private JTextArea textHere;
	
	public HelpTopic() {
		textHere = null;
		
		setUpHelpTopic();
	}

	/**
	 * An explanation or walk-through of a topic (a help file)
	 * @param topic - title of topic
	 * @param showThisText - text (explanation, etc)
	 */
	public HelpTopic(String topic, String showThisText) {
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

	/**
	 * Sets up the GUI for a help topic screen
	 */
	public void setUpHelpTopic() {
		this.setLayout(new GridBagLayout());
		final GridBagConstraints constraints = new GridBagConstraints();
		
		titleText = new JLabel();
		titleText.setHorizontalAlignment(SwingConstants.CENTER);
		titleText.setFont(new Font("Tahoma", Font.BOLD, 17));
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 0.0;
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipady = 20;
		constraints.insets = new Insets(5, 0, 0, 0);
		add(titleText, constraints);
		constraints.ipady = 0;
		constraints.insets = new Insets(0, 0, 0, 0);
		
		textHere = new JTextArea();
		textHere.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textHere.setLineWrap(true);
		textHere.setWrapStyleWord(true);
		textHere.setAutoscrolls(true);
		textHere.setEditable(false);
		textHere.setBackground(this.getBackground());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 20, 10, 20);
		add(textHere, constraints);
		constraints.insets = new Insets(0, 0, 0, 0);
	}
	
	/**
	 * Gets the information from a help topic object
	 * @param hto - help topic object in question
	 */
	public void pullHelpInfo(HelpTopicObject hto) {
		titleText.setText(hto.getTitle());
		textHere.setText(hto.getFileText());
		textHere.setCaretPosition(0);
	}
}
