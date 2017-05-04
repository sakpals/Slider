import java.util.Scanner;

public class KatsuCat implements SliderPlayer{
	
	private char[][] katsuBoard;
	private Scanner boardReader;
	private char katsuPlayer;
	
	@Override
	public void init(int dimension, String board, char player) {
		// TODO Auto-generated method stub
		katsuBoard = new char[dimension][dimension];
		boardReader = new Scanner(board);
		katsuPlayer = player;
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
		// i == y
		// j == x
		int x=move.j;
		int y=move.i;
		
		char opponent = katsuBoard[y][x];
		katsuBoard[y][x] = '+';
		
		switch(move.d) 
		{
			//all can go up(y++) and right(x++)
			//pick up the piece and move it (free the spot)
			
			case UP:
				katsuBoard[y+1][x] = opponent;
				break;
			case RIGHT:
				katsuBoard[y][x+1] = opponent;
				break;
			case DOWN:
				if(opponent == 'H') {
					katsuBoard[y-1][x] = opponent;
				}
				else {
					System.out.println("Bad move "+opponent);
				}
		
				break;
			case LEFT:
				
				if(opponent == 'V') {
					katsuBoard[y][x-1] = opponent;
				}
				else {
					System.out.println("Bad move "+opponent);
				}
				
				break;
			default: 
				System.out.println("Bad move bro...");
				break;
		}
		
		
	}

	@Override
	public Move move() {
		// TODO Auto-generated method stub
		return null;
	}

}
