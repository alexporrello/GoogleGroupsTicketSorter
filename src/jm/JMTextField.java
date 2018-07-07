package jm;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class JMTextField extends JTextField {
	private static final long serialVersionUID = -2346021547935315452L;
	
	public JMTextField() {
		setupTextField();
	}

	public JMTextField(String s) {
		super(s);
		
		setupTextField();
	}
	
	private void setupTextField() {
		setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		setOpaque(false);
	}
}
