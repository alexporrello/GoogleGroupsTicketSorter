package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.JTextComponent;

import jm.JMColor;
import jm.JMLabel;
import jm.JMTextArea;
import reader.Message;

public class MessageGUI extends JPanel {
	private static final long serialVersionUID = 6981507939951401349L;

	private Color borderColor = JMColor.DEFAULT_BORDER_COLOR;

	private Timer fadeTimer;

	/** The message to be displayed and not modified. **/
	private Message message;

	/** The label that display's the message's poster **/
	private JMLabel name;

	/** THe label that display's the message's body **/
	private JMTextArea body;

	public MessageGUI(Message message) {
		this.message = message;

		setLayout(new GridBagLayout());
		setOpaque(false);

		name = new JMLabel(this.message.getPoster());
		body = new JMTextArea(this.message.getBody());

		name.setFont(new Font(body.getFont().getName(), Font.BOLD, body.getFont().getSize() + 0));
		name.setHorizontalAlignment(SwingUtilities.LEFT);

		addFocusListenerForComponent(name);
		addFocusListenerForComponent(body);

		add(name, new GridBagConstraints(0, 0, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(4, 5, 0, 5), 0, 0));
		add(body, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(4, 5, 5, 5), 0, 0));
	}

	/**
	 * Clicking on the given component will outline the focused message in blue.
	 * @param c the component to be clicked.
	 */
	private void addFocusListenerForComponent(JTextComponent c) {
		c.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				new ColorFader().fadeColor(JMColor.HOVER_BORDER_COLOR, 0);
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				new ColorFader().fadeColor(JMColor.DEFAULT_BORDER_COLOR, 0);
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D gg = (Graphics2D) g;

		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		gg.setColor(borderColor);
		gg.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);

		gg.drawLine(5, 25, getWidth()-7, 25);
	}

	/** Responsible for the color fading animation **/
	private class ColorFader {
		private void fadeColor(Color fadeToButton, int fadeSpeed) {
			if(fadeTimer != null && fadeTimer.isRunning()) {
				fadeTimer.stop();
			}

			fadeTimer = new Timer(fadeSpeed, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					colorFader(borderColor, fadeToButton);
					repaint();
				}
			});

			fadeTimer.start();
		}

		private void colorFader(Color buttonfadeFrom, Color buttonFadeTo) {
			int[] buttonA = makeRGBArray(buttonfadeFrom);
			int[] buttonB = makeRGBArray(buttonFadeTo);

			buttonA = fader(buttonA, buttonB);

			borderColor = arrayToColor(buttonA);

			if((buttonA[0] + "" + buttonA[1] + "" + buttonA[2]).equals(buttonB[0] + "" + buttonB[1] + "" + buttonB[2])) {
				repaint();
				fadeTimer.stop();
			}
		}

		/**
		 * Adds or subtracts 1 so that values of a will equal values of b.
		 * @param a the array whose values are changed
		 * @param b the array whose values are static
		 * @return an updated
		 */
		private int[] fader(int[] a, int[] b) {
			for(int i = 0; i < 3; i++) {
				if(a[i] > b[i]) {
					a[i] = a[i]-1;
				} else if(a[i] < b[i]) {
					a[i] = a[i]+1;
				}
			}

			return a;
		}

		/**
		 * Receives a color and returns the color represented as an array of integers.
		 * @param color the color to be converted
		 * @return an array of size three where...
		 * <ul>
		 * 		<li>0: Red</li>
		 *      <li>1: Green</li>
		 *      <li>2: Blue</li>
		 * </ul>
		 */
		private int[] makeRGBArray(Color color) {
			return new int[]{color.getRed(), color.getGreen(), color.getBlue()};
		}

		/**
		 * Receives an array representation of a color and returns a color.
		 * @param rgb accepts array of integers made in {@link #makeRGBArray(Color)}.
		 * @return the Color represented by rgb.
		 */
		private Color arrayToColor(int[] rgb) {
			return new Color(rgb[0], rgb[1], rgb[2]);
		}
	}
}
