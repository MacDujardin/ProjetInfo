public class Board {
	public int size ;
	public String [] [] board;
	
	public  Board (int size) {	
      this.size = size; //9 cases de pions et 8 cases de murs
      board = new String [size] [size] ;
      for ( int row = 0; row<board.length; row ++ ) {
    	  board[row] [0]= "pawn1win" ;
      }
      for ( int row = 0; row<board.length ; row ++) { 
    	  board [row] [size] = "pawn2win";
      }

}
	public void setValue (Vector pos , String value) {
	//Assigne une valeur (value) à la position (pos) souhaitée
	  board [pos.y][pos.x] = value ; 
	} 
	public String getValue (Vector pos , Vector vect ) {
		return board [pos.y+vect.y][pos.x+vect.x];
	}
	public String getValue (Vector pos) {
		return board [pos.y][pos.x];
	}
	public Vector getPosByValue (String value) {
		for (int y=0 ; y<size ; y ++ ) {
			for (int x=0; x<size ;x++) {
				if (board [y][x] == value )
					return Vector(x,y);	
			}
		}
	}
}
