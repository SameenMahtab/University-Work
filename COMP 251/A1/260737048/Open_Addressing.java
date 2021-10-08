package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        
    	return (((((key*A) % power2(w)) >> (w-r)) + i) % power2(r));
    	
    	
    	
    	
    	
    	//return -1;
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
     int i;
     int collision;
     int hashValue;
     
    	for(i=0;i<m;i++){ 	
    		hashValue = probe(key,i);
    		if(Table[hashValue] == key) return -1;			
    	}
    	
		for(collision=0; collision<m; collision++){
			hashValue = probe(key,collision);
			if(Table[hashValue] == -1 || Table[hashValue] == -7){
				Table[hashValue] = key;
    	        return collision;
    	   }	   
       }
      return -1;
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    
    
    
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
    	
        int i = 0;
        int hashValue = probe(key,i);
        
        while(isSlotEmpty(hashValue) == false && i<this.m){  
        
    	if(Table[hashValue] == key){
    		Table[hashValue] = -7;
    		return i;
    	}else{
    		i++;
    		hashValue = probe(key,i);
    	 }
        
        }
    	
    	return i+1;
    }
}


