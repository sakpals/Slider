import java.util.ArrayList;

public class PossibleState {
		public int value;
		char[][] board;
		Move move;
		ArrayList<PossibleState> children;
		
		PossibleState(int v, char[][] b, Move m){
			board=b;
			value=v;
			move=m;
			children = new ArrayList<PossibleState>();
		}
		
		
		
		public void addChild(PossibleState c) {
			children.add(c);
		}
	}