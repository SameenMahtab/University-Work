import java.io.*;
import java.util.*;


public class islands {
	
	final static int[] offsets = {-1, 0, +1};
	
	public static void main(String args[]) {
		
		//long start = System.currentTimeMillis();
		
		try {
			
			Scanner file = new Scanner(new File("testIslands.txt"));
			
			int problems = Integer.parseInt(file.nextLine());
			int result[] = new int[problems];
			
			int j = 0;
			while(problems > 0) {
				String size[] = file.nextLine().split(" ");
				
				char a[][] = new char[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
				
				for(int i = 0 ; i < Integer.parseInt(size[0]) ; i++) {
					
					String s = file.nextLine();
					a[i] = s.toCharArray();	
					
				}
				
				
				result[j] = countIslands(a,Integer.parseInt(size[0]),Integer.parseInt(size[1]));
				j++;
				problems--;
							
			}
			
		
			file.close();
			
			BufferedReader br = null;
			File fileOut = new File("testIslands_solution.txt");
			
			try{
				
			if (!fileOut.exists()){
				fileOut.createNewFile();
			}else{
				fileOut.delete();
				fileOut.createNewFile();
			}
			
			FileWriter fw = new FileWriter(fileOut.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(int k = 0 ; k < result.length; k++ )
			{
				bw.write(result[k] +"\n");	
			}
			
			bw.close();
			}catch (IOException e) {
				e.printStackTrace();
			}finally{
				try{
					if (br != null)br.close();
				}catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}
		
	
	}
	
	
	public static int countIslands(char a[][], int ROW, int COL) {
		
		boolean visited[][]= new boolean[a.length][a[0].length];
		
		int count = 0;
		for (int i = 0; i < a.length; ++i){
			for(int j = 0; j < a[0].length; ++j){
				
				 if ( (!visited[i][j]) && (a[i][j]=='-') ){ 
					 count++;
                	 DFS(a,i,j,visited);           	
                 }

			}
		}
			               
		 return count;
				 
	}
		
	
	public static void DFS(char a[][], int i, int j, boolean visited[][]) {
		
	
			
		if (visited[i][j])
        {
            return;
        }
         
       
        visited[i][j] = true;
 
        int xOffset, yOffset;
        
        for (int l = 0; l < offsets.length; l++)
        {
            xOffset = offsets[l];
            for (int m = 0; m < offsets.length; m++)
            {
                yOffset = offsets[m];
                 
               
                if (yOffset == 0 && xOffset == 0)
                {
                    continue;
                    
                }
                	
                if (xOffset == 0 || yOffset == 0){
                	if (isSafe(a, i + xOffset, j + yOffset))
                     {
                           DFS(a, i + xOffset, j + yOffset, visited);
                     }
                }else continue;

            }
      
        }
		
	}
		
	public static boolean isSafe(char a[][], int row, int col){
		
		
		if ((row >= 0) && (row < a.length) && (col >= 0) && (col < a[0].length))
        {
            if (a[row][col] == '-')
            {
                return true;
            }
        }
         
        return false;
			
	}
	
}
	


	
