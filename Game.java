
import java.util.Scanner;
/**
 * Driver program for slider game. Reads the input for board configuration 
 * from standard input and uses methods from Board class to return the number
 * of moves for each player.
 * @author Jane Hoh (hohj) and Sampada Sakpal (sakpals)
 *
 */
public class Game {	
	
	public static void main(String[] args) {
		
		Board board;
		/* initialised to 4, since N > 3*/
		int N = 4;
	
		/* scanner object that reads board specifications from standard input */
		Scanner scanner = new Scanner(System.in);
		
	
		/* reads the size of the NxN board */
		if(scanner.hasNextInt()) {
			N = scanner.nextInt();
		}
		
		/* creates an object of type Board, representing the board configuration */
		board = new Board(N);
		
		/* variables to index board */
		int i=0;
		int j=0;
		
		/* reads characters for board tiles */
		while(scanner.hasNext()) {
			char token=scanner.next().charAt(0);
			board.fillBoard(i, j, token);
			
			j++;
			if(j==N){
				j=0;
				i++;
			}
			if(i == N) {
				break;
			}
			
		}
		/* scans the board counting the number of moves for each player */
		board.scanBoard();
	
		/* returns the number of moves for H and V, respectively */
		System.out.println(board.getNumHMoves());
		System.out.println(board.getNumVMoves());
		
	}
	
	
	
}
