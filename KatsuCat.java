import java.util.ArrayList;
import java.util.Scanner;



public class KatsuCat implements SliderPlayer{
	
	private char[][] katsuBoard;
	private Scanner boardReader;
	private char katsuPlayer;
	private char opponent;
	private int N;
	private int numPlayerTokens;
	
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
		numPlayerTokens = dimension-1;
	
		int x=0;
		int y=0;
		
		/* initialises the board according to given cartesian coordinate system 
		 * (0,0) corresponds to the bottom left corner */
		while(boardReader.hasNext()) {
			katsuBoard[dimension-1-y][x] = boardReader.next().charAt(0);
			//System.out.print(katsuBoard[dimension-1-y][x]+" ");
			x++;
			if(x==dimension){
				x=0;
				y++;
				//System.out.println();
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
		
		// to account for the case in which the opponent passes, or hasn't
		// made a move yet
		if(move == null) {
			return oldBoard;
		}
				int x=move.i;//move.j;
				int y=move.j;//move.i;
				char[][] board = copyBoard(oldBoard);
				
				
				board[y][x] = '+';
				
				switch(move.d) 
				{
					//all can go up(y++) and right(x++)
					//pick up the piece and move it (free the spot)
					
					case UP:
						if(y!=oldBoard.length-1) {
							board[y+1][x] = player;
						}
						/* else it moves off the board - Goal:(*/
						break;
					case RIGHT:
						if(x!=oldBoard.length-1) {
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
		//printBoard(board);
		return board;
		
	}
	// inner class :O for the possible states of the board and its details
	/*public class PossibleState {
		private int value;
		char[][] board;
		Move move;
		PossibleState parent;
		
		PossibleState(int v, char[][] b, Move m, PossibleState p){
			board=b;
			value=v;
			move=m;
			parent = p;
		}
	}*/
	@Override
	public Move move() {
		int currMax;
		Move bestMove;
		int val = evaluation(katsuBoard);
		PossibleState parent = new PossibleState(val, katsuBoard, null);
		ArrayList<PossibleState> katsuPlayerMoves;
		// looking at first depth only currently
		// expand parent
		katsuPlayerMoves = expand(parent); 
		
		// for each katsuPlayer move, look at its 
		for(PossibleState katsuMove: katsuPlayerMoves) {
			//System.out.println("possible moves player "+katsuPlayer+ " can"
				///	+ "make is : "+katsuMove.move.toString());
			ArrayList<PossibleState> opponentMoves = expand(katsuMove);
			for(PossibleState opponentMove: opponentMoves) {
				if(opponentMove.value < katsuMove.value) {
					katsuMove.value = opponentMove.value;
				}
			}
		}
		
		// check max
		if(!katsuPlayerMoves.isEmpty()) {
			// just for now, pretend that the first move is the
			// best till we find a better one
			currMax = katsuPlayerMoves.get(0).value;
			bestMove = katsuPlayerMoves.get(0).move;
			for(PossibleState katsuMove: katsuPlayerMoves) {
				if(katsuMove.value > currMax) {
					currMax = katsuMove.value;
					bestMove = katsuMove.move;
				}
			}
			// update the board to reflect the move your about to make
			//System.out.println("best move is ... "+bestMove.toString());
			katsuBoard = updateBoard(katsuBoard, bestMove, katsuPlayer);
		//	printBoard(katsuBoard);
			return bestMove;
			
		}
		return null;
		
		// find the most profitable move
		
		
	}
	
	public ArrayList<PossibleState> expand(PossibleState parent){
		ArrayList<Move> possibleMoves = findMoves(parent.board);
		
		for(Move m: possibleMoves) {
			char[][] newBoard = copyBoard(parent.board);
			
			newBoard = updateBoard(newBoard, m, katsuPlayer);
			int val = evaluation(newBoard);
			
		
			//PossibleState newState = new PossibleState(val, newBoard, m);
			//parent.addChild(newState);
			parent.addChild(new PossibleState(val, newBoard, m));
		}
		return parent.children;
	}
	
	public void printBoard(char[][] board) {
		for(int y = 0; y < board.length; y++) {
			for(int x = 0; x < board.length; x++) {
				//System.out.print(board[board.length-1-y][x]+" ");
				System.out.print(board[y][x]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	public void maxUpdate(PossibleState parent) {
		for(PossibleState child: parent.children) {
			if(child.value > parent.value) {
				parent.value = child.value;
			}
		}	
	}
	
	
	
	
	public void minUpdate(PossibleState parent) {
		for(PossibleState child: parent.children) {
			if(child.value < parent.value) {
				parent.value = child.value;
			}
		}	
	}
	
	public ArrayList<Move> findMoves(char[][] board) {
		ArrayList<Move> possibleMoves = new ArrayList<Move>();
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				if(board[y][x] == 'H' && katsuPlayer == 'H') {
					// it can go up, right, down
					//System.out.println("found h");
					// if can go up
					if((y != N-1) && (board[y+1][x] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.UP));
					}
					// if can go right
					if((x == N-1) || (board[y][x+1] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.RIGHT));
					}
						
					// if can go down
					if((y != 0) && (board[y-1][x] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.DOWN));
					}
				}
				else if(board[y][x] == 'V'  && katsuPlayer == 'V'){
					// it can go up, right, left
					//System.out.println("found v");
					// if can go up, if its moving off the board, or theres a
					// free spot above
					if((y == N-1) || (board[y+1][x] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.UP));
					}
					// if can go right
					if((x != N-1) && (board[y][x+1] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.RIGHT));
					}
					
					// if can go left
					if((x != 0) && (board[y][x-1] == '+')) {
						possibleMoves.add(new Move(x,y,Move.Direction.LEFT));
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
	
	public int evaluation(char[][] board) {
		/* if H is the player, then the value stays the same,
		 * or else its the opposite */
		int distanceWeight = 3; //12+9+6+4//9+6+12
		int blockedWeight = 2;
		int tokenWeight = (board.length-1)*distanceWeight+blockedWeight;
		
		if(numPlayerTokens==1){
			//last token! - get off the board!
			tokenWeight = (board.length-1)*distanceWeight+1000;
		}
		
		if(katsuPlayer == 'H') {
			return distanceWeight*distanceEvaluation(board)
					+ blockedWeight*blockedEvaluation(board)
					+ tokenWeight*numTokens(board);
		}
		else {
			return -(distanceWeight*distanceEvaluation(board)
					+ blockedWeight*blockedEvaluation(board)
					+ tokenWeight*numTokens(board));
		}
		
	}
	
	/** 
	 * Counts the number of blocked tokens for a given player
	 * @param player the player that is currently being evaluated
	 * @return the blocked evaluation w/r/t H
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
	
	/** 
	 * Calculates the desirability of the distance left to move off the board
	 * @param board
	 * @return the distance evaluation w/r/t H
	 */
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
	
	/**
	 * Calculates the number of player tokens left, the less for H
	 * or the more for V, the better
	 * @param board
	 * @return the number of tokens evaluation w/r/t H
	 */
	private int numTokens(char[][] board) {
		int numH = 0;
		int numV = 0;
		/* calculates the evaluation for H */
		for(int y = 0; y < N; y ++) {
			for(int x = 0; x < N; x ++) {
				if(board[y][x] == 'H') {
					numH++;
				}
				else if(board[y][x] == 'V') {
					numV++;
				}
			}
		}
		
		if(katsuPlayer=='H'){
			numPlayerTokens = numH;
		}else{
			numPlayerTokens = numV;
		}
		
		return numV-numH;
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
