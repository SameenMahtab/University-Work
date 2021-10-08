package assignment4Graph;

public class Graph {
	
	boolean[][] adjacency;
	int nbNodes;  
	
	public Graph (int nb){
		this.nbNodes = nb;
		this.adjacency = new boolean [nb][nb];
		for (int i = 0; i < nb; i++){
			for (int j = 0; j < nb; j++){
				this.adjacency[i][j] = false;
			}
		}
	}
	
	public void addEdge (int i, int j){
		// ADD YOUR CODE HERE
		int numV = this.nbNodes;
		if((i < numV && j < numV) && (i >= 0 && j >= 0)) {
			this.adjacency[i][j] = true;
			this.adjacency[j][i] = true;
		}
	
	}
	
	public void removeEdge (int i, int j){
		// ADD YOUR CODE HERE
		int numV = this.nbNodes;
		//Sets adjacency as false
		if((i < numV && j < numV) && (i >= 0 && j >= 0)){
			this.adjacency[i][j] = false;
			this.adjacency[j][i] = false;
		}
	
	}
	
	public int nbEdges(){
		// ADD YOUR CODE HERE
		int edge = 0;
		for(int i = 0; i < this.nbNodes; i++){
			for(int j = i; j < this.nbNodes; j++){	
				if(this.adjacency[i][j] == true) edge++;
			}
		}	
		return edge; // DON'T FORGET TO CHANGE THE RETURN
	}
	
	public boolean cycle(int start){
		// ADD YOUR CODE HERE
	    int parent = -1;
	    
		boolean visited[] = new boolean[this.nbNodes];
		for(int i = 0; i < this.nbNodes; i++){
			visited[i] = false;
		}
		
		boolean isCyclic[] = {false};
	 	
		DFS(start,start,parent,visited,isCyclic);
		
		return isCyclic[0];
		
	}
	
	public int shortestPath(int start, int end){
		// ADD YOUR CODE HERE
		int pathLength = 0;
		boolean[] visited = new boolean[nbNodes];
		
		for (int i = 0; i < nbNodes; i++) {
			visited[i] = false;
		}
		int[] queue = new int[nbNodes];
		for (int j =0 ; j < queue.length; j++) {
			queue[j] = -1;
		}
		
		visited[start] = true;
		this.enQueue(queue, start);
		
		while (!isEmpty(queue)) {
			int parent = deQueue(queue);
			for (int n = 0; n < nbNodes; n++) {
				if (adjacency[parent][n] == true && !visited[n]) {
					if (n == end) {
						pathLength++;
						return pathLength;
					}
					visited[n] = true;
					enQueue(queue, n);
				}
			}
			pathLength++;
		}
		return nbNodes + 1;
	
	}
	
    
    public void DFS(int src,int v,int parent, boolean visited[],boolean isCyclic[])
    {
        visited[v] = true;
 
		    for(int x : findNeighbours(v))
			{
		    	if(visited[x] == false){
		    	  parent = v;
				  visited[x] = true;
				  DFS(src,x,parent,visited,isCyclic);
		    	}else if(x!=parent && visited[x] == true && x==src)  {
		    		isCyclic[0] = true;		
		    		break;
		    	}
			}	
    }
    
	public void enQueue(int[] queue, int node) {
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] == -1) {
				queue[i] = node;
				return;
			}
		}
	}
	

	public int deQueue(int[] queue) {
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] != -1) {
				int out = queue[i];
				queue[i] = -1;
				return out;
			}
		}
		return -1;
	}
	
	public boolean isEmpty(int[] queue) {
		for (int i = 0; i < queue.length; i++) {
			if (queue[i] != -1) {
				return false;
			}
		}
		return true;
	}
	
    public int[] findNeighbours(int x)
	{
    	int count = 0;
			for (int i = 0; i < this.nbNodes; i++) {
				if(this.adjacency[x][i] == true && i!=x)
				{
					count++;
				}
			}		
		int neighbours[] = new int[count];
		int j = 0;
		for (int i = 0; i < this.nbNodes; i++) {
			if(this.adjacency[x][i] == true && i!=x)
			{
				neighbours[j] = i;
				j++;
			}
		}
		
		return neighbours;
	}	
	
   
}