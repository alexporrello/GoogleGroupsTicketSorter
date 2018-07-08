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

	ArrayList<TicketButton> buttons = new ArrayList<TicketButton>();

	JComponent content;

	public SupportPageListGUI(JComponent content) {
		this.content = content;
		
		setLayout(new GridBagLayout());

		addAllTickets();
		watchService();
	}

	public void addAllTickets() {
		buttons.clear();
		
		for (File f : SupportPage.SUPPORT_PAGES.listFiles()) {
			buttons.add(new TicketButton(f));
		}

		refreshTicketList();
	}
	
	public void refreshTicketList() {
		this.removeAll();

		int x = 0;

		for(TicketButton tl : buttons) {
			add(tl, new GridBagConstraints(0, x, 1, 1, 1.0, 0.0, GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
			x++;
		}

		revalidate();
		repaint();
	}

	/**
	 * Watches the Desktop Publisher Assistant for changes and updates the log
	 * if any occur.
	 */
	private void watchService() {
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
								//TODO Do stuff when the file is modified.
								
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
	
	public class TicketButton extends JMButton {
		private static final long serialVersionUID = 5543833053519594661L;

		File url;

		public TicketButton(File url) {
			super(url.getName());
			
			this.url = url;
			
			addActionListner(e-> {
				content.removeAll();
				
				content.add(new SupportPageGUI(new SupportPage(url)), BorderLayout.CENTER);
				content.revalidate();
				content.repaint();
			});
		}
	}
}
