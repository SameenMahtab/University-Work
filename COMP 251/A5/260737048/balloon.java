import java.io.*;
import java.util.*;

public class balloon {

	public static void main( String args[] ) throws FileNotFoundException{

		try {
			Scanner f = new Scanner(new File("testBalloons.txt"));
			int Problems = Integer.parseInt(f.nextLine());
		    f.nextLine();
		    int result[] = new int[Problems];
		    int j = 0;
		    
		while(Problems > 0){
			String problem[] = f.nextLine().split(" ");
			ArrayList<Integer> balloons = new ArrayList<Integer>(problem.length);
			
			for( int i = 0; i < problem.length; i++ ){
				balloons.add(Integer.parseInt(problem[i]));
			}
			result[j] = numOfArrows(balloons);
			Problems--;
			j++;
			
		}
		
			f.close();
			
		BufferedReader reader = null;
		File fileOut = new File("testBalloons_solutions.txt");
		
		try{
		if (!fileOut.exists()){
			fileOut.createNewFile();
		}else{
			fileOut.delete();
			fileOut.createNewFile();
		}
		
		FileWriter writer = new FileWriter(fileOut.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(writer);
		
		for(int k = 0 ; k < result.length; k++ )
		{
			bw.write(result[k] +"\n");	
		}
		
		bw.close();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if (reader != null)reader.close();
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
			
		}catch (FileNotFoundException e){
             System.out.println("File not found!");
             System.exit(1);
		  }  

		
		
	}
	
	
    public static int numOfArrows(ArrayList<Integer> balloons){
		
		int size = balloons.size();
		int popped = 0 ;
		int numOfArrows = 0;

		while(popped!=size){
			
		     numOfArrows=numOfArrows+1;
			 int m = balloons.get(0);  //Arbitrary height
			 balloons.remove(0);
			 popped++;
	
			 if(balloons.size() == 0) break;
		     int i=0;
		     while(i < balloons.size()){
                 if(balloons.get(i) < m && balloons.get(i) > m-2 && balloons.get(0) > 0){
	        		 m = balloons.get(i);
	        		 balloons.remove(i);
	        		 popped++;
	        	 }else{
	        		 i++;        		 
	        	 }
		     }   
		 }  
		
		return numOfArrows;		
	}	
}