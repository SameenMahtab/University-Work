package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){
    	
    	DisjointSets p = new DisjointSets(g.getNbNodes());

    	WGraph mst = new WGraph();

    	for(int i=0; i < g.listOfEdgesSorted().size();i++){
    		
    		Edge e = g.listOfEdgesSorted().get(i);
    		if(IsSafe(p,e)){
    			mst.addEdge(e);
    			p.union(e.nodes[0], e.nodes[1]);
    		}
    	}

        /* Fill this method (The statement return null is here only to compile) */
        
        return mst;
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){
    	if(p.find(e.nodes[0]) != p.find(e.nodes[1])){
    		return true;
    	}
    	
    	

        /* Fill this method (The statement return 0 is here only to compile) */
        return false;
    
    }

    public static void main(String[] args){

        String file = args[0];
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
