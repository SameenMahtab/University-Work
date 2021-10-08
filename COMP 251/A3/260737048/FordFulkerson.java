import java.io.*;
import java.util.*;

public class FordFulkerson {
	
	public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
		ArrayList<Integer> Stack = new ArrayList<Integer>();
		
		boolean visited[] = new boolean[graph.getNbNodes()];
		for(int i=0; i < graph.getNbNodes(); ++i)
			visited[i]=false; //initialize all nodes as not visited

		boolean flow=false;
		
		Stack<Integer> DFSstack = new Stack<>();
		DFSstack.push(source);
			
	    int a = 0;
			
		
			
		while(!DFSstack.empty()){
			
			a=DFSstack.peek(); //top element in stack
			DFSstack.pop(); 
			
			if(visited[a]==false){
				visited[a]=true;
			    Stack.add(a);
		
			}
			
			if (a==destination){
				return Stack;
			}
			
			flow=false;
			
			for(int i = graph.getNbNodes()-1; i >= 0; i--){

				if(graph.getEdge(a, i)!=null){
					if (graph.getEdge(a, i).weight>0 && visited[i]==false){			
					     DFSstack.push(i);
					     flow=true;	
				    }	
				}
			}
			
			if(flow==false){
				if(Stack.isEmpty()==false)
				Stack.remove(Stack.size()-1);
				
				if(Stack.isEmpty()==false)
					DFSstack.push(Stack.get(Stack.size()-1));				
							
			}	
		}
		
		if(a!=destination){
			while(!Stack.isEmpty()){
				Stack.remove(Stack.size()-1);
			}
		}

		return Stack;
	}
	
	
	
	public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
		String answer="";
		String myMcGillID = "260737048"; //Please initialize this variable with your McGill ID
		int maxFlow = 0;
		
		WGraph rGraph = new WGraph(graph);
		
		ArrayList<Integer> p = new ArrayList<Integer>();
		
		p=pathDFS(source, destination, rGraph);
			
		int resCap=0;
		
		while(p.isEmpty()==false ){  //there is a path 
			
		    resCap =Integer.MAX_VALUE; //set up the resCap		
			for(int i=0; i<p.size()-1;i++){		
				if(rGraph.getEdge(p.get(i), p.get(i+1)).weight<resCap){	
					     resCap= rGraph.getEdge(p.get(i), p.get(i+1)).weight; //edge with the least weight	
				}
			}
			
			for(int i=0;i<p.size()-1;i++){
				
				rGraph.getEdge(p.get(i), p.get(i+1)).weight = rGraph.getEdge(p.get(i), p.get(i+1)).weight - resCap;
				
				if  (rGraph.getEdge(p.get(i+1), p.get(i)) == null){
					
					Edge e = new Edge(p.get(i+1),p.get(i),resCap);
					rGraph.addEdge(e);
				}
				else{
				
					rGraph.getEdge(p.get(i+1), p.get(i)).weight = rGraph.getEdge(p.get(i+1), p.get(i)).weight + resCap; //for edges directed in the opposite direction
					
					
				}
			}
			
			maxFlow += resCap;
			p=pathDFS(source, destination, rGraph);
			
			
		
			}
		
           for(Edge e : graph.getEdges()){
			
			if(rGraph.getEdge(e.nodes[1], e.nodes[0]) != null){
				
				if(rGraph.getEdge(e.nodes[1], e.nodes[0]).weight < e.weight) {
					e.weight = rGraph.getEdge(e.nodes[1], e.nodes[0]).weight;
				}else if(rGraph.getEdge(e.nodes[1], e.nodes[0]).weight > e.weight){
					e.weight = e.weight;				
				}			
				
			}else{
				e.weight = 0; 
			}				
		}
		
		answer += maxFlow + "\n" + graph.toString();	
		writeAnswer(filePath+myMcGillID+".txt",answer);
		System.out.println(answer);
	}
	
	
	public static void writeAnswer(String path, String line){
		BufferedReader br = null;
		File file = new File(path);
		// if file doesn't exists, then create it
		
		try {
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(line+"\n");	
		bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	 public static void main(String[] args){
		 String file = args[0];
		 File f = new File(file);
		 WGraph g = new WGraph(file);
		 fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
	 }
}
