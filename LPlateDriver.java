import java.util.Scanner;

public class LPlateDriver {

	public static void main(String[] args) {
		char[][] katsuBoard;
		Scanner boardReader;
		int dimension = 4;
		String board = "+ H V +\n+ H V +\n+ H V +\n+ + + +\n";
		katsuBoard = new char[dimension][dimension];
		boardReader = new Scanner(board);
		KatsuCat cat = new KatsuCat();
		Move move = new Move(1,1,Move.Direction.DOWN);
		
		/*cat init*/
		 cat.init(dimension, board, 'H');
		
		
		/*for(int i = 0; i < dimension; i++){
			for(int j = 0; j <dimension; j++) {
				boardReader.useDelimiter("\n");
				if(boardReader.hasNext()) {
					for(int k = 0; k < dimension; k++) {
						katsuBoard[i][j] = boardReader.next().charAt(k);
						System.out.println(boardReader.next());
					}
					
					
					
				}
				
			}
		}*/
		
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
		
		
		System.out.println("printing out katsu board");
		for(int ik = 0; ik < dimension; ik++){
			for(int jk = 0; jk <dimension; jk++) {
				System.out.print(katsuBoard[ik][jk] + " ");
				
			}
			System.out.println();
		}
		
		
		
		char[][] newBoard = cat.copyBoard(katsuBoard);
		System.out.println("printing out new board");
		for(int ik = 0; ik < dimension; ik++){
			for(int jk = 0; jk <dimension; jk++) {
				System.out.print(newBoard[ik][jk] + " ");
				
			}
			System.out.println();
		}
		
		cat.updateBoard(katsuBoard,move,'H');
		
		System.out.println("printing out katsu board");
		for(int ik = 0; ik < dimension; ik++){
			for(int jk = 0; jk <dimension; jk++) {
				System.out.print(katsuBoard[ik][jk] + " ");
				
			}
			System.out.println();
		}
		
		System.out.println("printing out new board");
		for(int ik = 0; ik < dimension; ik++){
			for(int jk = 0; jk <dimension; jk++) {
				System.out.print(newBoard[ik][jk] + " ");
				
			}
			System.out.println();
		}
		

		
		
		
		
		
		
		
	}
}
