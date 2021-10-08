package assignment2;

import javax.swing.Box;

public class Shelf {
	
	protected int height;
	protected int availableLength;
	protected int totalLength;
	protected Box firstBox;
	protected Box lastBox;

	public Shelf(int height, int totalLength){
		this.height = height;
		this.availableLength = totalLength;
		this.totalLength = totalLength;
		this.firstBox = null;
		this.lastBox = null;
	}
	
	protected void clear(){
		availableLength = totalLength;
		firstBox = null;
		lastBox = null;
	}
	
	public String print(){
		String result = "( " + height + " - " + availableLength + " ) : ";
		Box b = this.firstBox;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
	 * @param b
	 */
	public void addBox(Box b){
		//ADD YOUR CODE HERE
		b.previous = this.lastBox;
		if(b.previous == null) this.firstBox = b;
		if(b.previous != null) b.previous.next = b;
		this.lastBox = b;
	    b.next = null;
		this.availableLength = this.availableLength - b.length;
	}
	
	/**
	 * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
	 * If not, do not do anything to the Shelf and return null.
	 * @param identifier
	 * @return
	 */
	public Box removeBox(String identifier){
		//ADD YOUR CODE HERE
	  
		Box a;
		a = this.firstBox;

			while(a != null){
				if(a.id == identifier)
				{
					if(a.previous == null && a.next != null) {
						this.firstBox = a.next;
						a.next.previous = null;
					}
					if(a.previous != null && a.next == null) {
						this.lastBox = a.previous;
						a.previous.next = null;
					}
					if(a.previous != null && a.next != null){
						a.previous.next = a.next;
						a.next.previous = a.previous;
					}
					if(a.previous == null && a.next == null){
						this.firstBox = null;
						this.lastBox = null;
					}
					
					a.next = null;
					a.previous = null;
				
					this.availableLength = this.availableLength + a.length;
					
					return a;
				}
				a = a.next;
			}
			return null;
	}
	
}
