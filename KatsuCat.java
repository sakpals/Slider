import java.util.ArrayList;
import java.util.Scanner;



public class KatsuCat implements SliderPlayer{
	
	private char[][] katsuBoard;
	private Scanner boardReader;
	private char katsuPlayer;
	private char opponent;
	private int N;
	
	public KatsuCat() {
		
	}
	@Override
	public void init(int dimension, String board, char player) {
		// TODO Auto-generated method stub
		katsuBoard = new char[dimension][dimension];
		boardReader = new Scanner(board);
		katsuPlayer = player;
		opponent = getOpponent();
		N = dimension;
	
		int x=0;
		int y=0;
		
		/* initialises the board according to given cartesian coordinate system 
		 * (0,0) corresponds to the bottom left corner */
		while(boardReader.hasNext()) {
			katsuBoard[dimension-1-y][x] = boardReader.next().charAt(0);
			System.out.print(katsuBoard[dimension-1-y][x]+" ");
			x++;
			if(x==dimension){
				x=0;
				y++;
				System.out.println();
			}
		}
		
	}

	@Override
	public void update(Move move) {
		
		katsuBoard = updateBoard(katsuBoard, move, opponent);
		
		
	}
	public char[][] updateBoard(char[][] oldBoard, Move move, char player) {
		// TODO Auto-generated method stub
		// i == y
				// j == x
				int x=move.j;
				int y=move.i;
				char[][] board = copyBoard(oldBoard);
				
				
				board[y][x] = '+';
				
				switch(move.d) 
				{
					//all can go up(y++) and right(x++)
					//pick up the piece and move it (free the spot)
					
					case UP:
						if(player != 'V') {
							board[y+1][x] = player;
						}
						/* else it moves off the board - Goal:(*/
						break;
					case RIGHT:
						if(player != 'H') {
							board[y][x+1] = player;
						}
						/* else it moves off the board - Goal:(*/
						break;
					case DOWN:
						if(player == 'H') {
							board[y-1][x] = player;
						}
						else {
							System.out.println("Bad move "+player);
						}
				
						break;
					case LEFT:
						
						if(player == 'V') {
							board[y][x-1] = player;
						}
						else {
							System.out.println("Bad move "+player);
						}
						
						break;
					default: 
						System.out.println("Bad move bro...");
						break;
				}
				
		return board;
		
	}
	// inner class :O for the possible states of the board and its details
	private class PossibleState {
		private int value;
		char[][] board;
		Move move;
		
		PossibleState(int v, char[][] b, Move m){
			board=b;
			value=v;
			move=m;
		}
	}
	@Override
	public Move move() {
		
		
		return null;
	}
	
	public PossibleState maxUpdate(char[][] givenBoard) {
		char[][] board;
		ArrayList<Move> possibleMoves = findMoves(givenBoard);
		PossibleState bestState=null;
		
		// make a replica of the board
		board = copyBoard(givenBoard);
		for(Move m: possibleMoves) {
			
			//add state constructor
			char[][] updatedBoard= updateBoard(board,m,katsuPlayer);
			int value = evaluation(updatedBoard);
			
			// updating the best State if its better
			if(bestState==null){
				bestState = new PossibleState(value, updatedBoard, m);
			}
			else {
				if(value > bestState.value) {
					bestState.value = value;
					bestState.board = updatedBoard;
					bestState.move = m;
				}
			}
			
		}
		return bestState;
		
	}
	
	
	
	
	public PossibleState minUpdate(char[][] givenBoard) {
		char[][] board;
		ArrayList<Move> possibleMoves = findMoves(givenBoard);
		PossibleState bestState=null;
		
		// make a replica of the board
		board = copyBoard(givenBoard);
		for(Move m: possibleMoves) {
			
			//add state constructor
			char[][] updatedBoard= updateBoard(board,m,opponent);
			int value = evaluation(updatedBoard);
			
			// updating the best State if its better
			if(bestState==null){
				bestState = new PossibleState(value, updatedBoard, m);
			}
			else {
				if(value < bestState.value) {
					bestState.value = value;
					bestState.board = updatedBoard;
					bestState.move = m;
				}
			}
			
		}
		return bestState;
		
	}
	
	public ArrayList<Move> findMoves(char[][] board) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				if(board[y][x] == 'H' && katsuPlayer == 'H') {
					// it can go up, right, down
					System.out.println("found h");
					// if can go up
					if((y != N-1) && (board[y+1][x] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.UP));
					}
					// if can go right
					if((x == N-1) || (board[y][x+1] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.RIGHT));
					}
						
					// if can go down
					if((y != 0) && (board[y-1][x] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.DOWN));
					}
				}
				else if(board[y][x] == 'V'  && katsuPlayer == 'V'){
					// it can go up, right, left
					System.out.println("found v");
					// if can go up
					if((y == N-1) || (board[y+1][x] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.UP));
					}
					// if can go right
					if((x != N-1) && (board[y][x+1] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.RIGHT));
					}
					
					// if can go left
					if((x != 0) && (board[y][x-1] == '+')) {
						possibleMoves.add(new Move(y,x,Move.Direction.LEFT));
					}
					
				}
				
			}
		}
		return possibleMoves;
	}
	
	
	private char getOpponent() {
		if(katsuPlayer == 'H') {
			return 'V';
		}
		else {
			return 'H';
		}
	}
	
	private int evaluation(char[][] board) {
		/* if H is the player, then the value stays the same,
		 * or else its the opposite */
		if(katsuPlayer == 'H') {
			return distanceEvaluation(board)
					+ blockedEvaluation(board)
					+ numTokens(board);
		}
		else {
			return -(distanceEvaluation(board)
					+ blockedEvaluation(board)
					+ numTokens(board));
		}
		
	}
	
	/** 
	 * Counts the number of blocked tokens for a given player
	 * @param player the player that is currently being evaluated
	 * @return 
	 */
	private int blockedEvaluation(char[][] board) {
		int value = 0;
		/* calculates the evaluation for H */
		for(int y = 0; y < N; y ++) {
			for(int x = 0; x < N; x ++) {
				/* checking the right side for H */
				if(board[y][x] == 'H') {
					if(x != N-1) {
						if(board[y][x+1] != '+') {
							value--;
						}
					}
				}
				/* checking the top for V */
				else if (board[y][x] == 'V'){
					if(y != N-1) {
						if(board[y+1][x] != '+') {
							value++;
						}
					}
				}
			}
		}
		
		return value;
	}
	
	private int distanceEvaluation(char[][] board) {
		int value = 0; 
		/* calculates the evaluation for H */
		for(int y = 0; y < N; y ++) {
			for(int x = 0; x < N; x ++) {
				/* increments the value by H's x position*/
				if(board[y][x] == 'H') {
					value += x;
				}
				/* decrements the value by V's y position */
				else if(board[y][x] == 'V'){
					value -= y;
				}
			}
		}
		return value;
		
	}
	
	private int numTokens(char[][] board) {
		int value = 0; 
		/* calculates the evaluation for H */
		for(int y = 0; y < N; y ++) {
			for(int x = 0; x < N; x ++) {
				if(board[y][x] == 'H') {
					value --;
				}
				else if(board[y][x] == 'V') {
					value ++;
				}
			}
		}
		return value;
	}
	
	
	public char[][] copyBoard(char[][] board){
		char[][] newBoard= new char[board.length][board.length];
		
			for(int i=0;i<board.length;i++){
				for(int j=0;j<board[i].length;j++){
					newBoard[i][j]=board[i][j];
				}
			}
		
		
		return newBoard;
	}

}
