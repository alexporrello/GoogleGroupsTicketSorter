package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.BorderFactory;

import jm.JMButton;
import jm.JMColor;
import jm.JMLabel;
import jm.JMPanel;
import jm.JMScrollPane;
import reader.Message;
import reader.SupportPage;

public class SupportPageGUI extends JMPanel {
	private static final long serialVersionUID = -4797164765452943998L;

	SupportPage ticket;
	
	public SupportPageGUI(SupportPage ticket) {
		this.ticket = ticket;

		setLayout(new BorderLayout());

		JMPanel tickets = new JMPanel();
		tickets.setLayout(new GridBagLayout());
		tickets.setBackground(JMColor.DEFAULT_BACKGROUND);

		int y = 0;
		for(Message m : ticket) {
			tickets.add(
					new MessageGUI(m),
					new GridBagConstraints(0, y++, 1, 1, 1.0, 1.0,
							GridBagConstraints.NORTH,
							GridBagConstraints.HORIZONTAL, new Insets(5,10,5,10), 0, 0));
		}

		JMScrollPane jms = new JMScrollPane(tickets);
		add(jms, BorderLayout.CENTER);

		JMLabel label = new JMLabel(ticket.getName()) {
			private static final long serialVersionUID = -8300740866901116067L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.GRAY);
				g.drawLine(0, getHeight()-1, getWidth(), getHeight()-1);
			}
		};
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize() + 2));
		label.setBackground(JMColor.DEFAULT_BACKGROUND);
		label.setOpaque(true);
		add(label, BorderLayout.NORTH);
		
		add(new ControlPanel(), BorderLayout.SOUTH);
	}
	
	public void clearAfterDelete() {
		removeAll();
		revalidate();
		repaint();
	}
	
	private class ControlPanel extends JMPanel {
		private static final long serialVersionUID = -3338387457467355460L;
		
		JMButton delete = new JMButton("Delete");
		
		public ControlPanel() {
			add(delete);
			setBackground(Color.WHITE);
			
			delete.addActionListner(e -> {
				new File(ticket.getDirectory()).delete();
				clearAfterDelete();
			});
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(JMColor.DEFAULT_BORDER_COLOR);
			g.drawLine(0, 0, getWidth(), 0);

		}
	}
}
