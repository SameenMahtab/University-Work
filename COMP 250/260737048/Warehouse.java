package assignment2;

import javax.swing.Box;

public class Warehouse{

	protected Shelf[] storage;
	protected int nbShelves;
	public Box toShip;
	public UrgentBox toShipUrgently;
	static String problem = "problem encountered while performing the operation";
	static String noProblem = "operation was successfully carried out";
	
	public Warehouse(int n, int[] heights, int[] lengths){
		this.nbShelves = n;
		this.storage = new Shelf[n];
		for (int i = 0; i < n; i++){
			this.storage[i]= new Shelf(heights[i], lengths[i]);
		}
		this.toShip = null;
		this.toShipUrgently = null;
	}
	
	public String printShipping(){
		Box b = toShip;
		String result = "not urgent : ";
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n" + "should be already gone : ";
		b = toShipUrgently;
		while(b != null){
			result += b.id + ", ";
			b = b.next;
		}
		result += "\n";
		return result;
	}
	
 	public String print(){
 		String result = "";
		for (int i = 0; i < nbShelves; i++){
			result += i + "-th shelf " + storage[i].print();
		}
		return result;
	}
	
 	public void clear(){
 		toShip = null;
 		toShipUrgently = null;
 		for (int i = 0; i < nbShelves ; i++){
 			storage[i].clear();
 		}
 	}
 	
 	/**
 	 * initiate the merge sort algorithm
 	 */
	public void sort(){
		mergeSort(0, nbShelves -1);
	}
	
	/**
	 * performs the induction step of the merge sort algorithm
	 * @param start
	 * @param end
	 */
	protected void mergeSort(int start, int end){
		//ADD YOUR CODE HERE
		if(start<end)
		{
			int mid = (start+end)/2;
			mergeSort(start,mid);
			mergeSort(mid+1,end);
			merge(start,mid,end);
		}  
	}
	
	/**
	 * performs the merge part of the merge sort algorithm
	 * @param start
	 * @param mid
	 * @param end
	 */
	protected void merge(int start, int mid, int end){
		//ADD YOUR CODE HERE
		
		Shelf[] a = this.storage;
		
		int s1 = mid - start + 1;
		int s2 = end - mid;
		
		Shelf[] L = new Shelf[s1];
		Shelf[] R = new Shelf[s2];
		
		for (int i = 0; i < s1; i++)
	        L[i] = a[start + i];
	    for (int j = 0; j < s2; j++)
	        R[j] = a[mid + 1 + j];
	    
	    int i = 0;
	    int j = 0;
	    int k = start;

	    while (i < s1 && j < s2)
	    {
	        if (L[i].height <= R[j].height)
	        {
	            this.storage[k] = L[i];
	            i++;
	        }
	        else
	        {
	            this.storage[k] = R[j];
	            j++;
	        }
	        k++;
	    }
	    while (i < s1)
	    {
	        this.storage[k] = L[i];
	        i++;
	        k++;
	    }
	    while (j < s2)
	    {
	        this.storage[k] = R[j];
	        j++;
	        k++;
	    }
	}
	
	/**
	 * Adds a box is the smallest possible shelf where there is room available.
	 * Here we assume that there is at least one shelf (i.e. nbShelves>0)
	 * @param b
	 * @return problem or noProblem
	 */
	public String addBox (Box b){
		//ADD YOUR CODE HERE
		for(int k = 0; k < this.nbShelves; k++)
		{
			if(this.storage[k].height >= b.height && this.storage[k].availableLength >= b.length){
				this.storage[k].addBox(b);
				return noProblem;
			}
		} return problem;
	}
	
	/**
	 * Adds a box to its corresponding shipping list and updates all the fields
	 * @param b
	 * @return problem or noProblem
	 */
	public String addToShip (Box b){
		//ADD YOUR CODE HERE
		if (b instanceof UrgentBox)
		{
			if(toShipUrgently != null)
			{
				b.previous = null;
				b.next = toShipUrgently;
				b.next.previous = b;
				toShipUrgently = (UrgentBox)b;
				return noProblem;
			}
			if(toShipUrgently == null){
				toShipUrgently = (UrgentBox)b;
				b.previous = b.next = null;
				return noProblem;
			}
		}else{
			if(toShip != null)
			{
				b.previous = null;
				b.next = toShip;
				b.next.previous = b;
				toShip = b;
				return noProblem;
			}
			if(toShip == null){
				toShip = b;
				b.previous = b.next = null;
				return noProblem;
			}
		}

		return problem;
	}
	
	/**
	 * Find a box with the identifier (if it exists)
	 * Remove the box from its corresponding shelf
	 * Add it to its corresponding shipping list
	 * @param identifier
	 * @return problem or noProblem
	 */
	public String shipBox (String identifier){
		//ADD YOUR CODE HERE
		for(int k = 0; k < this.nbShelves; k++)
		{
			Box a = this.storage[k].removeBox(identifier);
			
			if(a != null){
				addToShip(a);
				this.storage[k].removeBox(identifier);
				return noProblem;
			}
		}
		return problem;
	}
	
	/**
	 * if there is a better shelf for the box, moves the box to the optimal shelf.
	 * If there are none, do not do anything
	 * @param b
	 * @param position
	 */
	public void moveOneBox (Box b, int position){
		//ADD YOUR CODE HERE
		for(int i = 0; i < position; i++){
			Shelf a = this.storage[i];
			if(a.height >= b.height && a.availableLength >= b.length){
				this.storage[position].removeBox(b.id);
				this.storage[i].addBox(b);
				break;
		}
	}
	}
	
	/**
	 * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
	 */
	public void reorganize (){
		//ADD YOUR CODE HERE
		
		for(int i = 0 ; i < nbShelves ; i++){
			for(int j = 0 ; j < nbShelves ; j++){
				Box b = this.storage[j].firstBox;
				while(b!=null){
					moveOneBox(b,j);
					b = b.next;
				}
			}
		}
	}
		
	
}

