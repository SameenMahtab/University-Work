package ca.mcgill.ecse223.resto.view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;

public class Draw extends JPanel {
	
	private List<Rectangle> toDraw;

	private static final long serialVersionUID = 3882621915825263638L;
	
	public Draw(List<Rectangle> tD) {
		toDraw = tD;
	}

	public void drawing() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		for (Rectangle rect : toDraw) {
			g.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		}
	}
	
}
