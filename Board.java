/**
 * Class represents the board state
 * @author Jane Hoh (hohj) and Sampada Sakpal (sakpals)
 *
 */

public class Board {
	
	/**
	 *  dimension size of the board
	 */
	private int N;
	
	/**
	 * the state of the board configuration
	 */
	public char[][] state;
	
	/**
	 * number of moves player H has
	 */
	private int numHMoves;
	
	/**
	 * number of moves player V has
	 */
	private int numVMoves;
	
	/**
	 * Creates an object of type Board, and initialises the board dimensions 
	 * and number of moves for each player is set to 0 initially.
	 * @param N dimension size of the board
	 */
	public Board(int N) {
		this.N = N;
		state = new char[N][N];
		this.numHMoves=0;
		this.numVMoves=0;
	}
	
	/**
	 * Fills a particular indexed tile of the board with value
	 * @param i row index
	 * @param j column index
	 * @param val value for tile
	 */
	public void fillBoard(int i, int j, char val) {
		state[i][j] = val;
	}
	
	/**
	 *
	 * @return The number of moves H can take
	 */
	
	public int getNumHMoves() {
		return numHMoves;
	}
	
	/**
	 * 
	 * @return The number of moves V can take 
	 */
	public int getNumVMoves() {
		return numVMoves;
	}
	
	/**
	 * Scans the board's current state and increments the number of moves players
	 * H and V can make based on their positions.
	 */
	public void scanBoard(){
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				char token=state[i][j];
				if(token=='H') {
					/* looking to the right */
					if(j!=N-1 && state[i][j+1]=='+'){
							this.numHMoves++;	
					}
					/* looking to move off board */
					if(j == N-1) {
						this.numHMoves ++;
					}
					/* looking up */
					if(i !=0 && state[i-1][j]=='+'){
						this.numHMoves++;
					}
					/* looking down */
					if(i !=N-1 && state[i+1][j]=='+'){
							this.numHMoves++;
					}
				}else if(token=='V'){
					/* looking to the right */
					if(j!=N-1 && state[i][j+1]=='+' ){
							this.numVMoves++;	
					}
					/* looking up */
					if(i !=0 && state[i-1][j]=='+'){
						this.numVMoves++;
					}
					/* looking to move off board */
					if(i==0) {
						this.numVMoves ++;
					}
					/* looking left */
					if(j != 0 && state[i][j-1]=='+'){
						this.numVMoves++;
					}
				}
			}
		}
	}
	
	/**
	 * Prints the board state
	 */
	public void printBoard() {
		for(int i=0; i < N; i++) {
			for(int j =0; j < N; j++) {
				System.out.print(state[i][j]);
			}
			System.out.println("");
		}
	}
}
