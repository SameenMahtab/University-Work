package ca.mcgill.ecse223.resto.view;

import ca.mcgill.ecse223.resto.model.Table;

public class bla {

	public static boolean doesOverlap(Table t, int x, int y, int width, int length) {
		boolean overlaps = false;
		int seatSize = 2;
		int minX = x - width / 2 - seatSize, maxX = x + width / 2 + seatSize;
		int minY = y - length / 2 - seatSize, maxY = y + width / 2 + seatSize;
		int tMinX = t.getX() - t.getWidth() / 2 - seatSize, tMaxX = t.getX() + t.getWidth() / 2 + seatSize;
		int tMinY = t.getY() - t.getLength() / 2 - seatSize, tMaxY = t.getY() + t.getLength() / 2 + seatSize;
		if (isBetween(maxX, tMinX, tMaxX) && isBetween(minY, tMinY, tMaxY)) { // from upper left
			overlaps = true;
		}
		if (isBetween(minX, tMinX, tMaxX) && isBetween(minY, tMinY, tMaxY)) { // from upper right
			overlaps = true;
		}
		if (isBetween(maxX, tMinX, tMaxX) && isBetween(maxY, tMinY, tMaxY)) { // from lower left
			overlaps = true;
		}
		if (isBetween(minX, tMinX, tMaxX) && isBetween(maxY, tMinY, tMaxY)) { // from lower right
			overlaps = true;
		}		
		return overlaps;
	}
	
	private static boolean isBetween(int val, int small, int big) {
		return val >= small && val <= big;
	}
	
	
}
