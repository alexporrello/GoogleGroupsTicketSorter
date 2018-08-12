package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JDialog;

import jm.JMButton;
import jm.JMTextField;

public class AddTagDialog extends JDialog {
	private static final long serialVersionUID = 8536014918947141398L;

	public AddTagDialog(int x, int y) {
		setIconImages(Tools.imageIcon());
		setSize(new Dimension(250, 75));
		setLayout(new GridBagLayout());
		setTitle("Add New Tag");
		setLocation(x, y);
		setModal(true);


		JMTextField areaToAddText = new JMTextField("");
		areaToAddText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					writeNewTag(areaToAddText.getText());
				}
			}
		});

		JMButton addButton = new JMButton(" Add Tag ");
		addButton.addActionListner(e -> {
			writeNewTag(areaToAddText.getText());
		});


		add(areaToAddText, new GridBagConstraints(
				0, 0, 1, 1, 1.0, 0, 
				GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(5, 10, 5, 5), 0, 0));
		add(addButton, new GridBagConstraints(
				1, 0, 1, 1, 0, 0, 
				GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(5, 0, 5, 5), 0, 0));


		setVisible(true);
	}

	private void writeNewTag(String tag) {
		try {
			if(tag.length() > 0)
				Tags.writeNewTag(tag);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
