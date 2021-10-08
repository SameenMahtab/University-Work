package A2;
import java.io.*;
import java.util.*;


/****************************
*
* COMP251 template file
*
* Assignment 2, Question 1
*
*****************************/


public class DisjointSets {

    private int[] par;
    private int[] rank;
    
    /* contructor: creates a partition of n elements. */
    /* Each element is in a separate disjoint set */
    DisjointSets(int n) {
        if (n>0) {
            par = new int[n];
            rank = new int[n];
            for (int i=0; i<this.par.length; i++) {
                par[i] = i;
            }
        }
    }
    
    public String toString(){
        int pari,countsets=0;
        String output = "";
        String[] setstrings = new String[this.par.length];
        /* build string for each set */
        for (int i=0; i<this.par.length; i++) {
            pari = find(i);
            if (setstrings[pari]==null) {
                setstrings[pari] = String.valueOf(i);
                countsets+=1;
            } else {
                setstrings[pari] += "," + i;
            }
        }
        /* print strings */
        output = countsets + " set(s):\n";
        for (int i=0; i<this.par.length; i++) {
            if (setstrings[i] != null) {
                output += i + " : " + setstrings[i] + "\n";
            }
        }
        return output;
    }
    
    /* find representative of element i */
    public int find(int i) {
    	if (par[i]==i)
    		return i;
    	else 
    		return find( par[i]);
    }

    /* merge sets containing elements i and j */
    public int union(int i, int j) {
    	int repOfi = this.find(i);
    	int repOfj = this.find(j);
    	if (repOfi == repOfj)
    		return repOfi;
    	
    	rank[i]=rank[repOfi]; //get the rank of i's tree
    	rank[j]=rank[repOfj]; //get the rank of j's tree
    	
    	
    	
    	
    	if(rank[i]<rank[j]){
    		this.par[repOfi]=repOfj;
    		return repOfj;
    	}
    	
    	else if(rank[j]<rank[i]){
    		this.par[repOfj]=repOfi;
    		return repOfi;
    	}
    	
    	else{
    		this.par[repOfi]=repOfj;
    		rank[repOfj]++;
    		return repOfj;
    	}
    }
    
    public static void main(String[] args) {
        DisjointSets myset = new DisjointSets(6);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 3");
        myset.union(2,3);
        System.out.println(myset);
        System.out.println("-> Union 2 and 1");
        myset.union(2,1);
        System.out.println(myset);
        System.out.println("-> Union 4 and 5");
        myset.union(4,5);
        System.out.println(myset);
        System.out.println("-> Union 3 and 1");
        myset.union(3,1);
        System.out.println(myset);
        System.out.println("-> Union 2 and 4");
        myset.union(2,4);
        System.out.println(myset);
    }
}