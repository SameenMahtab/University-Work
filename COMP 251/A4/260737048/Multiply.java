import java.util.*;
import java.io.*;

public class Multiply{

    private static int randomInt(int size) {
        Random rand = new Random();
        int maxval = (1 << size) - 1;
        return rand.nextInt(maxval + 1);
    }
    
    public static int[] naive(int size, int x, int y) {
    		
    	if ( size == 1 ){
    		
    		int res[] = new int[2] ;
    		res[0] = x*y; //store product of x and y
    		res[1] = 1;
    		return res;  		
    		
    	}else{
    		
    		int mid = (int)Math.ceil(size/2.00);
    		
    		int a = (int)Math.floor(x >> mid);
    		
            int b = x & (int)(Math.pow(2.00, (double)mid) - 1);
            
            int c = (int)Math.floor(y >> mid);
            
            int d = y & (int)(Math.pow(2.00, (double)mid) - 1);
            
    
            int e[] = naive(mid, a, c);
            
            int f[] = naive(mid, b, d);
            
            int g[] = naive(mid, b, c);
            
            int h[] = naive(mid, a, d);
            
 
            int res[] = new int[2] ;
    		res[0] = (e[0] << 2*mid) + ((g[0] + h[0]) << mid) + f[0];
    		res[1] = 4*e[1] + 3*mid;
    		return res;    
    		
    	}
 	
    }

    public static int[] karatsuba(int size, int x, int y) {

        if ( size == 1 ){
    		
    		int res[] = new int[2] ;
    		res[0] = x*y;
    		res[1] = 1;
    		return res;
    		
    	}else{
    		int mid = (int)Math.ceil(size/2.00);
    		
    		int a = (int)Math.floor(x >> mid);
    		
    		int b = x & (int)(Math.pow(2.00, (double)mid) - 1);
    		
            int c = (int)Math.floor(y >> mid);
            
            int d = y & (int)(Math.pow(2.00, (double)mid) - 1);
            
            int e[] = karatsuba(mid, a, c);
            
            int f[] = karatsuba(mid, b, d);
            
            int g[] = karatsuba(mid, a-b, c-d);
            
            int res[] = new int[2] ;
    		res[0] = (e[0] << 2*mid) + ((e[0] + f[0] - g[0]) << mid) + f[0];
    		res[1] = 3*e[1] + 6*mid;

    		return res;        
    	}

    }
    
    public static void main(String[] args){

        try{
            int maxRound = 20;
            int maxIntBitSize = 16;
            for (int size=1; size<=maxIntBitSize; size++) {
            	
  
                int sumOpNaive = 0;
                int sumOpKaratsuba = 0;
                for (int round=0; round<maxRound; round++) {
                    int x = randomInt(size);
                    int y = randomInt(size);

                    int[] resNaive = naive(size,x,y);
                    int[] resKaratsuba = karatsuba(size,x,y);
            
                    if (resNaive[0] != resKaratsuba[0]) {
                        throw new Exception("Return values do not match! (x=" + x + "; y=" + y + "; Naive=" + resNaive[0] + "; Karatsuba=" + resKaratsuba[0] + ")");
                    }
                    
                    if (resNaive[0] != (x*y)) {
                        int myproduct = x*y;
                        throw new Exception("Evaluation is wrong! (x=" + x + "; y=" + y + "; Your result=" + resNaive[0] + "; True value=" + myproduct + ")");
                    }
                    
                    sumOpNaive += resNaive[1];
                    sumOpKaratsuba += resKaratsuba[1];
                }
                int avgOpNaive = sumOpNaive / maxRound;
                int avgOpKaratsuba = sumOpKaratsuba / maxRound;
                System.out.println(size + "," + avgOpNaive + "," + avgOpKaratsuba);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

   } 
}