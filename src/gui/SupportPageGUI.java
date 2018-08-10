package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import jm.JMButton;
import jm.JMColor;
import jm.JMLabel;
import jm.JMPanel;
import jm.JMScrollPane;
import reader.Message;
import reader.SupportPage;
import reader.SupportPageWriter;

public class SupportPageGUI extends JMPanel {
	private static final long serialVersionUID = -4797164765452943998L;

	SupportPage supportPage;

	JComboBox<String> existingTags ;
	
	public SupportPageGUI(SupportPage ticket, String[] tags) {
		this.supportPage  = ticket;
		this.existingTags = new JComboBox<String>(tags);
		this.existingTags.setOpaque(false);
		this.existingTags.setBorder(BorderFactory.createEmptyBorder());
		
		setLayout(new BorderLayout());

		add(createTitleBar(),     BorderLayout.NORTH);
		add(createTicketsPanel(), BorderLayout.CENTER);
		add(new ControlPanel(),   BorderLayout.SOUTH);
	}
	
	/**
	 * Creates the title bar, where the support page's name is displayed, 
	 * and returns it.
	 * @return the created title bar for this support page.
	 */
	private JMLabel createTitleBar() {
		JMLabel label = new JMLabel(supportPage.getName()) {
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
		label.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(new URI(supportPage.getURL()));
				} catch (IOException | URISyntaxException e1) {
					System.err.println("The page for '" + supportPage.getName() + "' coul not be opened.");
				}
			}
		});
		
		return label;
	}
	
	private JMScrollPane createTicketsPanel() {
		JMPanel tickets = new JMPanel();
		tickets.setLayout(new GridBagLayout());
		tickets.setBackground(JMColor.DEFAULT_BACKGROUND);
		
		int y = 0;
		for(Message m : supportPage) {
			tickets.add(
					new MessageGUI(m),
					new GridBagConstraints(0, y++, 1, 1, 1.0, 1.0,
							GridBagConstraints.NORTH,
							GridBagConstraints.HORIZONTAL, new Insets(5,10,5,10), 0, 0));
		}
		
		return new JMScrollPane(tickets);
	}

	public void clearAfterDelete() {
		removeAll();
		revalidate();
		repaint();
	}

	private class ControlPanel extends JMPanel {
		private static final long serialVersionUID = -3338387457467355460L;

		JMButton delete = new JMButton(" Delete Support Page");

		JMButton addTag = new JMButton(" + ");
		
		TagArea tagArea = new TagArea(supportPage);
		
		public ControlPanel() {
			setLayout(new GridBagLayout());

			int x = 0;
			
			addWithGBC(existingTags, x++, 0.0, 10, 5);
			addWithGBC(addTag, x++, 0.0, 0, 5);
			addWithGBC(tagArea, x++, 1.0, 0, 5);
			
			addWithGBC(new JMLabel("|"), x++, 0.0, 0, 5);
			addWithGBC(delete, x++, 0.0, 0, 10);

			setBackground(Color.WHITE);

			addTag.addActionListner(e -> {
				supportPage.addTag((String) existingTags.getSelectedItem()); 
				tagArea.repopulate(supportPage);
				
				try {
					SupportPageWriter.write(supportPage);
				} catch (ParserConfigurationException | TransformerException e1) {
					e1.printStackTrace();
				}
			});
			
			delete.addActionListner(e -> {
				new File(supportPage.getDirectory()).delete();
				clearAfterDelete();
			});
		}
		
		public void addWithGBC(JComponent c, int x, double weightX, int lPadding, int rPadding) {
			add(c, new GridBagConstraints(x, 0, 1, 1, weightX, 1.0,
							GridBagConstraints.WEST, GridBagConstraints.BOTH,
							new Insets(5,lPadding,5,rPadding), 0, 0));
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.setColor(JMColor.DEFAULT_BORDER_COLOR);
			g.drawLine(0, 0, getWidth(), 0);
		}
	}
}
