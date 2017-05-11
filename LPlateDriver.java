import java.util.ArrayList;
import java.util.Scanner;


public class LPlateDriver {

	public static void main(String[] args) {
		
		char[][] katsuBoard;
		Scanner boardReader;
		int dimension = 4;
		String board = "H + + +\nH + + +\nH + + +\n+ V V V\n";
		katsuBoard = new char[dimension][dimension];
		boardReader = new Scanner(board);
		KatsuCat cat = new KatsuCat();
		Move move = new Move(1,1,Move.Direction.DOWN);
		
		/*cat init*/
		 cat.init(dimension, board, 'V');

		 
		System.out.println("storing the board");
		int y=0;
		int x=0;
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
		
		System.out.println();
		
		int val = cat.evaluation(katsuBoard);
		
		PossibleState parent = new PossibleState(val, katsuBoard, null);
		ArrayList<PossibleState> n = cat.expand(parent);
		System.out.println(parent.value );
		System.out.println(n.size());
		System.out.println("printing out possible states");
		
		for(int i = 0; i < n.size(); i ++){
			System.out.println("printing for "+i);
			System.out.println(n.get(i).value );

			System.out.println("printing out exp board");
			for(int ik = 0; ik < dimension; ik++){
				for(int jk = 0; jk <dimension; jk++) {
					System.out.print(n.get(i).board[dimension-1-ik][jk] + " ");
					
				}
				System.out.println();
			}
			
		}

		
		
		
		
		
		
		
	}
}
