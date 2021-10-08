package ca.mcgill.ecse223.resto.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class TablesLayoutPage {

	private JPanel tablesPanel;
	private Draw drawer;

	List<Rectangle> toDraw;

	public TablesLayoutPage() {
		toDraw = new ArrayList<Rectangle>();
		initComponents();
	}

	private void initComponents() {
		tablesPanel = new JPanel();
		
		toDraw.add(new Rectangle(10, 10, 100, 100));
		toDraw.add(new Rectangle(20, 20, 200, 200));
		
		drawer = new Draw(toDraw);
		drawer.drawing();
		tablesPanel.add(drawer);
		
		tablesPanel.setSize((int) (0.75 * 1370), (int) (0.75 * 732));
	}
	
	public JPanel getTableLayoutPanel() {
		return tablesPanel;
	}

}

class Rectangle {
	int ulX, ulY, width, height;

	public Rectangle(int ulx, int uly, int w, int h) {
		this.ulX = ulx;
		this.ulY = uly;
		this.width = w;
		this.height = h;
	}

	public int getX() {
		return ulX;
	}

	public int getY() {
		return ulY;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
