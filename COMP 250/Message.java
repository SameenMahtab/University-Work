package assignment1;

public class Message {
	
	public String message;
	public int lengthOfMessage;

	public Message (String m){
		message = m;
		lengthOfMessage = m.length();
		this.makeValid();
	}
	
	public Message (String m, boolean b){
		message = m;
		lengthOfMessage = m.length();
	}
	
	/**
	 * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
	 */
	public void makeValid(){
		//INSERT YOUR CODE HERE
        
        String str = "" ;
        this.message = this.message.toLowerCase();
        
        for(int i = 0 ; i < this.message.length(); i++)
        {
            int c = this.message.charAt(i);
            if(c>=97 && c<=122) str = str + (char)c;
        }    
        this.message = str;
        this.lengthOfMessage = this.message.length();
	}
	
	/**
	 * prints the string message
	 */
	public void print(){
		System.out.println(message);
	}
	
	/**
	 * tests if two Messages are equal
	 */
	public boolean equals(Message m){
		if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
			return true;
		}
		return false;
	}
	
	/**
	 * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
	 * @param key
	 */
	public void caesarCipher(int key){
		// INSERT YOUR CODE HERE
        
        String str = "";
        for(int i = 0 ; i < message.length(); i++)
        {
            int c = this.message.charAt(i) + key;
            if(c>122) c = (c % 122) + 96;
            if(c<97)  c = 123 - ((97-c) % 97) ;
            str = str + (char)(c);
        }
        
        this.message = str;
	}
	
	public void caesarDecipher(int key){
		this.caesarCipher(- key);
	}
	
	/**
	 * caesarAnalysis breaks the Caesar cipher
	 * you will implement the following algorithm :
	 * - compute how often each letter appear in the message
	 * - compute a shift (key) such that the letter that happens the most was originally an 'e'
	 * - decipher the message using the key you have just computed
	 */
	public void caesarAnalysis(){
		// INSERT YOUR CODE HERE
        
        int frequency[] = new int[26];
        int j = 97;
        while(j<=122){
            for(int i=0; i < this.message.length(); i++)
            {
                int c = this.message.charAt(i);
                if(j == c ){
                    frequency[j-97]++;
                }
            }
            j++;
        }
        int maxIndex=0, max = 0;
        for(int i = 0 ; i < 26 ; i++){
            if(max < frequency[i]){
                max = frequency[i];
                maxIndex = i;
            }
        }
        char ch = (char) (97 + maxIndex);
        int key = 'e' - ch;
        
        String alt = "";
        for(int i = 0 ; i < this.message.length(); i++)
        {
            int c = this.message.charAt(i) + key;
            if(c>122) c = (c % 122) + 96;
            if(c<97)  c = 123 - ((97-c) % 97) ;
            alt = alt + (char)(c);
        }
	       
        this.message = alt;
	}
	
	/**
	 * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
	 * @param key
	 */
	public void vigenereCipher (int[] key){
		// INSERT YOUR CODE HERE
        
        
        int length = key.length;
        String alt = "";
        int j = 0;
        
        for(int i = 0 ; i < this.message.length(); i++)
        {
            int c = this.message.charAt(i) + key[j];
            if(c>122) c = (c % 122) + 96;
            if(c<97)  c = 123 - ((97-c) % 97) ;
            alt = alt + (char)c;
            if(j == length-1){
                j = 0;
            }else j++;
        }
        
        this.message = alt;
	}

	/**
	 * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
	 * @param key
	 */
	public void vigenereDecipher (int[] key){
		// INSERT YOUR CODE HERE
        
        int length = key.length;
        String alt = "";
        int j = 0;
        
        for(int i = 0 ; i < length ; i++){
            key[i] = (-1)*key[i];
        }
        for( int i = 0; i < this.message.length() ; i++){
            int c = this.message.charAt(i) + key[j];
            if(c>122) c = (c % 122) + 96;
            if(c<97)  c = 123 - ((97-c) % 97) ;
            alt = alt + (char)c;
            if(j == (length-1)){
                j = 0;
            }else j++;
        }        
        this.message = alt; 
	}
	
	/**
	 * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
	 * @param key
	 */
	public void transpositionCipher (int key){
		// INSERT YOUR CODE HERE
        
        String a = this.message;
        double r = (double)a.length()/6;
        int rows = (int)Math.ceil(r);
        
        char arr[][] = new char[key][rows];
        
        int j = 0 , k = 0;
        
        while(j<rows)
        {
            for(int i = 0; i < key ; i++){
                if(k>=a.length()){
                    arr[i][j] = '*';
                }else{
                    arr[i][j] = a.charAt(k);
                }
                k++;
            }
            j++;
        }
        String alt = "";
        for(int i = 0 ; i < key ; i++){
            for(int s = 0 ; s < rows ; s++){
                alt = alt + arr[i][s];
            }
        }
        this.message = alt;
        this.lengthOfMessage = this.message.length();
	}
	
	/**
	 * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
	 * @param key
	 */
	public void transpositionDecipher (int key){
		// INSERT YOUR CODE HERE
        
        String a = this.message;
        double r = (double)a.length()/6;
        int rows = (int)Math.ceil(r);
        
        char arr[][] = new char[key][rows];

        int k = 0;
        int i = 0;
        
        while(i < key){
            for( int j = 0 ; j < rows ; j++)
            {
                if(k>=a.length())
                {
                    arr[i][j] = '*';
                                   }
                else
                {
                    arr[i][j] = a.charAt(k);
                                    }
                k++;
            }
            i++;
        }
        
        String alt = "";
        for(int p = 0 ; p < r; p++){
            for(int t = 0 ; t < key; t++){
                if(arr[t][p] == '*') break;
                
                alt = alt + arr[t][p];
             }
        }
        
        this.message = alt;
        this.lengthOfMessage = this.message.length();
        
	}
	
}
