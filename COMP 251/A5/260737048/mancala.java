import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class mancala {

	private static final int numOfCavities = 12;
	private static int Problems;
	private static int[][] board;
	private static int[] sol;

	public static void main(String args[]) throws FileNotFoundException {

		try {

			Scanner f = new Scanner(new File("testMancala.txt"));

			Problems = Integer.parseInt(f.nextLine());

			board = new int[Problems][numOfCavities];
			sol = new int[Problems];
			for (int i = 0; i < sol.length; i++)
				sol[i] = 12;

			int index = 0;

			while (f.hasNext()) {

				String[] l = f.nextLine().split(" ");

				int[] b = new int[numOfCavities];
				for (int i = 0; i < b.length; i++) {
					b[i] = Integer.parseInt(l[i]);

				}

				board[index] = b;
				index += 1;
			}

			f.close();

			BufferedReader reader = null;
			File fileOut = new File("testMancala_solution.txt");

			try {

				if (!fileOut.exists()) {
					fileOut.createNewFile();
				} else {
					fileOut.delete();
					fileOut.createNewFile();
				}

				FileWriter writer = new FileWriter(fileOut.getAbsoluteFile(), true);
				BufferedWriter bw = new BufferedWriter(writer);

				for (int n = 0; n < Problems; n++) {
					solveOneBoard(board[n], n);
				}
				for (int i = 0; i < Problems; i++) {
					bw.write("" + sol[i]);
					if (i < Problems - 1) {
						bw.write("\n");

					}

				}

				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (reader != null)
						reader.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
			System.exit(1);
		}

	}

	public static int[] move(int[] b, int startIndex) {

		int[] moved = new int[b.length]; 
		
		for(int i=0; i<b.length; i++ ) {
			
			moved[i]=b[i]; }

		if (moved[startIndex] == 0) {

			moved[startIndex] = 1;

		} else {
			moved[startIndex] = 0;
		}

		if (moved[startIndex + 2] == 0) {

			moved[startIndex + 2] = 1;
		} else {
			moved[startIndex + 2] = 0;
		}

		moved[startIndex + 1] = 0;
		return moved;
	}

	public static int countOnes(int[] b) {

		int numOne = 0;
		for (int i : b) {

			if (i == 1)
				numOne = numOne + 1;
		}
		return numOne;
	}

	public static void solveOneBoard(int[] b, int boardIndex) {

		int remainingOne = 0;

		boolean movable = false;
		for (int i = 0; i < numOfCavities - 2; i++) {
			if ((b[i] == 0 && b[i + 1] == 1 && b[i + 2] == 1) || (b[i] == 1 && b[i + 1] == 1 && b[i + 2] == 0)) {

				solveOneBoard(move(b, i), boardIndex);
				movable = true;
			}
		}

		int numOne = countOnes(b);
		if (numOne < sol[boardIndex])
			sol[boardIndex] = numOne;

	}

}