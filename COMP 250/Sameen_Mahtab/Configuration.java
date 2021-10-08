package assignment4Game;

public class Configuration {
    
    public int[][] board;
    public int[] available;
    boolean spaceLeft;
    
    public Configuration(){
        board = new int[7][6];
        available = new int[7];
        spaceLeft = true;
    }
    
    public void print(){
        System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
        System.out.println("+---+---+---+---+---+---+---+");
        for (int i = 0; i < 6; i++){
            System.out.print("|");
            for (int j = 0; j < 7; j++){
                if (board[j][5-i] == 0){
                    System.out.print("   |");
                }
                else{
                    System.out.print(" "+ board[j][5-i]+" |");
                }
            }
            System.out.println();
        }
    }
    
    public void addDisk (int index, int player){
        // ADD YOUR CODE HERE
        
        int i = 0;
        int flag = 0;
        
        for(i = 0; i < 6; i++)
        {
            if(this.board[index][i] == 0){
                this.board[index][i] = player;
                this.available[index] = i+1;
                break;
            }
        }
        for(i = 0; i < 7; i++){
            if(this.available[i] != 6){
                flag = 1;
            }
        }
        if(flag == 1) this.spaceLeft = true;
        else if(flag == 0) this.spaceLeft = false;
    }
    
    public boolean isWinning (int lastColumnPlayed, int player){
        // ADD YOUR CODE HERE
        
        int count = 0;
        int row = this.available[lastColumnPlayed] - 1;
        
        //horizontally
        for(int i = 0 ; i < 7; i++){
            if(this.board[i][row] == player) count++;
            else count = 0;
            if(count >= 4) return true;
        }
        
        //vertically
        count = 0;
        for(int i = 0 ; i < 6; i++){
            if(this.board[lastColumnPlayed][i] == player) count++;
            else count = 0;
            if(count >= 4) return true;
        }
        
        //diagonally
        int col = lastColumnPlayed;
        count = 0;
        while(row != -1 && col != -1 )
        {
            row--;
            col--;
        }
        row++;
        col++;
        while(row!=6 && col!=7 )
        {
            if(this.board[col][row] == player) count++;
            else count = 0;
            if(count >= 4) return true;
            row++;
            col++;
        }
        
        col = lastColumnPlayed;
        row = this.available[lastColumnPlayed] - 1;
        count = 0;
        while(row != 6 && col != -1 )
        {
            row++;
            col--;
        }
        row--;
        col++;
        while(row!=-1 && col!=7 )
        {
            if(this.board[col][row] == player) count++;
            else count = 0;
            if(count >= 4) return true;
            row--;
            col++;
        }
        
        return false; // DON'T FORGET TO CHANGE THE RETURN
    }
    
    public int canWinNextRound (int player){
        // ADD YOUR CODE HERE
        int flag = 0;
        for(int i = 0; i < 7; i++){
            if(this.available[i]!=6)
            {
                addDisk(i,player);
            }else continue;
            if(this.isWinning(i,player) == true)
            {
                flag = 1;
            }
            removeDisk(i);
            if(flag == 1) return i;
        }
        return -1; // DON'T FORGET TO CHANGE THE RETURN
    }
    
    public int canWinTwoTurns (int player){
        // ADD YOUR CODE HERE
        
        int result = -1;
        int flag = 0;
        int j;
        int p = 1;
        if(player == 1) p = 2;
        
        for(int i = 0 ; i < 7; i++){
            
            if(this.available[i]!=6) 
            {
                addDisk(i,player);
            }else continue;
            
            if(canWinNextRound(p) == -1){
                
                for(j = 0; j < 7; j++){
                    
                    if(this.available[j]!=6) 
                    {
                        addDisk(j,p);
                    }else continue;
                    
                    if(canWinNextRound(player) == -1){
                        flag = 1;
                        removeDisk(j);
                        break;
                    }
                    removeDisk(j);
                }	
                
                removeDisk(i);
                if(flag == 0){
                    return i;
                }
                
            }else{
                removeDisk(i);
                return result;
            }
            
            
            flag = 0;
            
        }	
        
        return result;// DON'T FORGET TO CHANGE THE RETURN
    }
    
    public void removeDisk(int index){
        int row = this.available[index];
        board[index][row-1] = 0;
        this.available[index] = row - 1;
        if(spaceLeft == false){
            spaceLeft = true;
        }
    }
    
}

