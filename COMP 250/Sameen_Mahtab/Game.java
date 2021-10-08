package assignment4Game;

import java.io.*;

public class Game {
	
	public static int play(InputStreamReader input){
		BufferedReader keyboard = new BufferedReader(input);
		Configuration c = new Configuration();
		int columnPlayed = 3; int player;
		
		// first move for player 1 (played by computer) : in the middle of the grid
		c.addDisk(firstMovePlayer1(), 1);
		int nbTurn = 1;
		
		while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
			player = nbTurn %2 + 1;
			if (player == 2){
				columnPlayed = getNextMove(keyboard, c, 2);
			}
			if (player == 1){
				columnPlayed = movePlayer1(columnPlayed, c);
			}
			System.out.println(columnPlayed);
			c.addDisk(columnPlayed, player);
			if (c.isWinning(columnPlayed, player)){
				c.print();
				System.out.println("Congrats to player " + player + " !");
				return(player);
			}
			nbTurn++;
		}
		return -1;
	}
	
	public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
		// ADD YOUR CODE HERE
		try {
			System.out.println("\n--- Welcome to Connect 4! ---");
			c.print();
			System.out.println("Enter desired column to place your token:");
			String columnInput = keyboard.readLine();
			
			if(columnInput.equals("0") ||
			   columnInput.equals("1") ||
			   columnInput.equals("2") ||
			   columnInput.equals("3") ||
			   columnInput.equals("4") ||
			   columnInput.equals("5") ||
			   columnInput.equals("6")){
			
			  int input = Integer.parseInt(columnInput);
			  
			      if(c.available[input] == 6){
				      System.out.println("There's no more space in this column.");
				      return getNextMove(keyboard,c,player);
			      }else{  
			    	  return input;
			      }
			}else{
				System.out.println("The column input is not valid, please try again.");
				return getNextMove(keyboard,c,player);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // DON'T FORGET TO CHANGE THE RETURN
		
		return -1;
	}
	
	public static int firstMovePlayer1 (){
		return 3;
	}
	
	public static int movePlayer1 (int columnPlayed2, Configuration c){
		// ADD YOUR CODE HERE
		int moves = 0;
		int leftSide = columnPlayed2 - 1;
		int rightSide = columnPlayed2 + 1;
		int readyPlayerOne = 1;
		int canWinNextRound = c.canWinNextRound(readyPlayerOne);
		int canWinTwoTurns = c.canWinTwoTurns(readyPlayerOne);
		if(canWinNextRound != -1) {
			moves = canWinNextRound;
			return moves;
		}
		
		else if(canWinTwoTurns!=-1) {
			moves = canWinTwoTurns;
			return moves;
		}
		
		else {
			while(c.spaceLeft) {
				if(c.available[columnPlayed2]<6) {
					moves = columnPlayed2;
					return moves;
				}
				if(leftSide>=0) {
					if(c.available[leftSide]<6){
						moves = leftSide;
						return moves;
					}else {
						leftSide--;
					}
				}
				if(rightSide<7) {
					if(c.available[rightSide]<6) {
						moves = columnPlayed2+1;
						return moves;
					}else {
						rightSide++;
					}
				}
			}
		}
		return moves; // DON'T FORGET TO CHANGE THE RETURN
	}
	
}