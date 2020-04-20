
public class board {
	private final int size = 17; //9 cases de pions et 8 cases de murs
 private Tile [][] tiles;
 
 public void tileMaker() {
	 tiles = new Tile[size][size];
	 for (int i=0 ;  i< tiles.length ; i++) {
		 for (int j=0 ; j < tiles.length ; j++) {
			 tiles [i][j]= new Tile( i , j);
		 }
	 }
 }
 
}
