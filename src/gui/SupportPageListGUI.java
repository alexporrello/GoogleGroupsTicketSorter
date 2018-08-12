package gui;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;

import javax.swing.JComponent;

import jm.JMButton;
import jm.JMPanel;
import reader.SupportPage;

public class SupportPageListGUI extends JMPanel {
	private static final long serialVersionUID = -3677343706797068487L;
	
	private ArrayList<SupportPageButton> buttons = new ArrayList<SupportPageButton>();

	private JComponent content;

	public SupportPageListGUI(JComponent content) {
		this.content = content;

		setLayout(new GridBagLayout());
		
		addAllTickets();
		startWatchService();
	}

	/** Loads all tickets from the default support pages folder **/
	public void addAllTickets() {
		buttons.clear();

		for (File f : SupportPage.SUPPORT_PAGES.listFiles()) {
			buttons.add(new SupportPageButton(f));
		}

		refreshTicketList();
	}

	/** Refresh the list view that the user has of the tickets **/
	public void refreshTicketList() {
		this.removeAll();

		int x = 0;

		for(SupportPageButton tl : buttons) {
			add(tl, new GridBagConstraints(0, x++, 1, 1, 1.0, 0.0, 
					GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
		}

		revalidate();
		repaint();
	}

	/** Watches the file list for changes and updates the log if any occur. **/
	private void startWatchService() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					WatchService watcher = FileSystems.getDefault().newWatchService();
					Path dir = SupportPage.SUPPORT_PAGES.toPath();

					dir.register(watcher, ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE);

					for(;;) {
						WatchKey key = watcher.take();
						for (WatchEvent<?> event : key.pollEvents()) {
							if (event.kind() == ENTRY_CREATE || event.kind() == ENTRY_DELETE || event.kind() == ENTRY_MODIFY) {								
								addAllTickets();
								continue;
							}
						}

						key.reset();
					}
				} catch(InterruptedException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Currently just a JMButton with an action listener. Possibly will change UI.
	 * @author Alexander Porrello
	 */
	public class SupportPageButton extends JMButton {
		private static final long serialVersionUID = 5543833053519594661L;

		public SupportPageButton(File directory) {
			super(directory.getName());

			addActionListner(e-> {
				content.removeAll();

				content.add(new SupportPageGUI(new SupportPage(directory)), BorderLayout.CENTER);

				content.revalidate();
				content.repaint();
			});
		}
	}
}
