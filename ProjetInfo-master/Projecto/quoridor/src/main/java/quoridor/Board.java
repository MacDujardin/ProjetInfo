package quoridor;

public class Board {	
	public int size ;
	public String [] [] board;
	
	public Board (int size) {
	      this.size = size; //9 cases de pions et 8 cases de murs
	      board = new String [size][size];
	      for ( int row = 0; row<size; row ++ )
	          board[0] [row]= "pawn1win" ;
	      for ( int row = 0; row<size ; row ++) 
	          board [size-1] [row] = "pawn2win";
	    }


	public void setValue (Vector pos , String value) {
	//Assigne une valeur (value) � la position (pos) souhait馥
	  board [pos.y][pos.x] = value ; 
	} 
	public String getValue (Vector pos , Vector vect ) {
		if (pos.x+vect.x > size-1 || pos.y+vect.y > size-1 || pos.x+vect.x < 0 || pos.y+vect.y < 0)
			return "out";
		return board [pos.y+vect.y][pos.x+vect.x];
	}
	public String getValue (Vector pos) {
		return board [pos.y][pos.x];
	}
	public Vector getPosByValue (String value) {
		for (int y=0 ; y<size ; y ++ ) {
			for (int x=0; x<size ;x++) {
				if (board [y][x] == value ) {
					return new Vector (x,y);
			    }
				
			}
		}
		return null;
	}

	public void debug(){
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				System.out.print(board[i][j]);
				System.out.print("\t");
			}
			System.out.println();
		}
	}
}
