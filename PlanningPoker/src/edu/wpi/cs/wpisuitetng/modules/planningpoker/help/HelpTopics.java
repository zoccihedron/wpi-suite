/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.planningpoker.help;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * @author Codon Bleu
 *
 */
@SuppressWarnings("serial")
public class HelpTopics extends JPanel {
	public String topicTitle;
	public String showThisText;
	
	public JPanel helpInfo;
	public JLabel titleText;
	public JTextArea textHere;
	
	public HelpTopics() {
		final Dimension minimumSize = new Dimension(250, 300);
		this.setMinimumSize(minimumSize);
		textHere = null;
		
		Border border = BorderFactory.createLineBorder(Color.GREEN, 3);
		this.setBorder(border);
		
		if (this.getComponentCount() > 0) {
			this.remove(titleText);
			this.remove(textHere);
		}
		
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
		add(titleText, constraints);
		constraints.ipady = 0;
		
		textHere = new JTextArea();
		textHere.setFont(new Font("Tahoma", Font.PLAIN, 13));
		textHere.setLineWrap(true);
		textHere.setWrapStyleWord(true);
		textHere.setEditable(false);
		textHere.setBackground(this.getBackground());
		constraints.fill = GridBagConstraints.BOTH;
		constraints.gridwidth = 1;
		constraints.weightx = 1.0;
		constraints.weighty = 1.0;
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(textHere, constraints);
	}
	
	public void pullHelpInfo(HelpTopicObject hto) {
		titleText.setText(hto.getTitle());
		textHere.setText(hto.getFileText());
	}

}
