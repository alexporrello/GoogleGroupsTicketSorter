package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import jm.JMButton;
import jm.JMColor;
import jm.JMPanel;
import reader.SupportPage;

public class TagArea extends JMPanel {
	private static final long serialVersionUID = 7773524342483447123L;
	
	public TagArea() {
		setLayout(new GridBagLayout());
		setOpaque(false);
	}
	
	public TagArea(SupportPage supportPage) {
		setLayout(new GridBagLayout());
		setOpaque(false);
		repopulate(supportPage);	
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D gg = (Graphics2D) g;
		
		gg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		gg.setColor(JMColor.DEFAULT_BORDER_COLOR);
		gg.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 3, 3);
	}
	
	public void repopulate(SupportPage supportPage) {
		this.removeAll();

		int x = 0;
		
		for(String s : supportPage.getTags()) {
			JMButton tag = new JMButton(s + "   X");	
			
			this.add(tag, new GridBagConstraints(x++, 0, 1, 1, 0.0, 0.0, 
					GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 
					new Insets(2, 2, 2, 2), 0, 0));
			
			tag.addMouseListener(new MouseAdapter() {				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getX() > tag.getWidth()-10) {
						supportPage.removeTag(s);
						supportPage.save();
						
						repopulate(supportPage);
					}
				}
			});
		}
		
		this.revalidate();
		this.repaint();
	}
}
