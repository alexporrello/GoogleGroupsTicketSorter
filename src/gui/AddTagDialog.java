package gui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JDialog;

import jm.JMButton;
import jm.JMTextField;

public class AddTagDialog extends JDialog {
	private static final long serialVersionUID = 8536014918947141398L;

	public AddTagDialog(int x, int y) {
		setLayout(new BorderLayout());
		
		JMTextField areaToAddText = new JMTextField("");
		
		JMButton addButton = new JMButton("Add Tag");
		addButton.addActionListner(e -> {
			try {
				Tags.writeNewTag(areaToAddText.getText());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		add(areaToAddText, BorderLayout.CENTER);
		add(addButton, BorderLayout.EAST);
		
		//setLocationRelativeTo(this.getParent());
		setLocation(x, y);
		pack();
		setModal(true);
		setVisible(true);
	}
}
