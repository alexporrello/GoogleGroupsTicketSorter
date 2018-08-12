package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import gui.AddTagDialog;
import gui.SupportPageListGUI;
import gui.Tools;
import jm.JMPanel;
import jm.JMScrollPane;

public class Runner extends JFrame {
	private static final long serialVersionUID = 5514779712176296826L;

	SupportPageListGUI supportPageList;

	public Runner() {
		SwingUtilities.invokeLater(() -> {
			setLookAndFeel();
			setJMenuBar(new MenuBar());
			createJFrame();
			setIconImages(Tools.imageIcon());
			
			JComponent content = new JMPanel();
			content.setLayout(new BorderLayout());

			supportPageList = new SupportPageListGUI(content);
			JScrollPane scroll = new JMScrollPane(supportPageList);
			scroll.setPreferredSize(new Dimension(300, 300));
			
			add(content, BorderLayout.CENTER);
			add(scroll, BorderLayout.WEST);

			setLocationByPlatform(true);
			setVisible(true);
		});
	}

	public Point getPosn(int dialogWidth, int dialogHeight) {
		int xPosn = (int) ((double) (this.getWidth()-dialogWidth)/2);
		int yPosn = (int) ((double) (this.getHeight()-dialogHeight)/2);
		
		return new Point(this.getX()+xPosn, this.getY()+yPosn);
	}
	
	public class MenuBar extends JMenuBar {
		private static final long serialVersionUID = -5585662550064738484L;

		public MenuBar() {
			add(new File());
		}

		public class File extends JMenu {
			private static final long serialVersionUID = 7464767676566339022L;

			public File() {
				super("File");

				add(newTag());			
			}

			private JMenuItem newTag() {
				JMenuItem toReturn = new JMenuItem("Create New Tag");

				toReturn.addActionListener(e -> {
					new AddTagDialog(getPosn(250, 75).x, getPosn(250, 75).y);
				});
				toReturn.setAccelerator (
						KeyStroke.getKeyStroke (
								KeyEvent.VK_N,
								Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

				return toReturn;
			}
		}
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
