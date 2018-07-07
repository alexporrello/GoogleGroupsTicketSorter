package main;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.TicketListGUI;
import jm.JMPanel;
import jm.JMScrollPane;

public class Runner extends JFrame {
	private static final long serialVersionUID = 5514779712176296826L;

	public Runner() {
		SwingUtilities.invokeLater(() -> {
			setLookAndFeel();
			setJMenuBar(new MenuBar());
			createJFrame();
			
			JComponent content = new JMPanel();
			content.setLayout(new BorderLayout());
			
			JScrollPane scroll = new JMScrollPane(new TicketListGUI(content));
			scroll.setPreferredSize(new Dimension(300, 300));
			
			add(content, BorderLayout.CENTER);
			add(scroll, BorderLayout.WEST);
			
			setVisible(true);
		});
	}

	public class MenuBar extends JMenuBar {
		private static final long serialVersionUID = -5585662550064738484L;
		
		
	}
	
	public void createJFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Google Groups Ticket Sorter");
		setSize(new Dimension(1000, 535));
		//frame.setIconImages(Tools.imageIcon());
		setLayout(new BorderLayout());
		setLocationByPlatform(true);
	}

	public static void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Runner();
	}
}
