//author: Nathan Amorison

import quoridor.*;
import java.util.ArrayList;

public class TestPF{
    public static int size = 9;
    private static int i;
    private static Vector dep_p1 = new Vector((int)(size/2), size-1);
    private static Vector dep_p2 = new Vector((int)(size/2), 0);

	public static void main(String[] args){
		boolean found = false;
    	Board plateau;
    	PathFinder path_player1, path_player2;

    	//test1:
    	plateau = withoutWall();
    	for (i = 0; i<size; i++){
    		//System.out.println("P1:");
    		path_player1 = new PathFinder(plateau, dep_p1, new Vector(i, 0));
    		//System.out.println("P2:");
    		path_player2 = new PathFinder(plateau, dep_p2, new Vector(i, size-1));
    		//s'il existe au moins un chemin, afficher le chemin
    		ArrayList<PFNode> path_p1 = path_player1.run(null);
 			ArrayList<PFNode> path_p2 = path_player2.run(null);
    		if (path_p1 != null && path_p2 != null){
    			found = true;
    			System.out.println("PATH PLAYER1");
    			for (PFNode node: path_p1)
    				System.out.println("\t" + node.getVect().get());

    			System.out.println("");
    			System.out.println("PATH PLAYER2");
    			for (PFNode node: path_p2)
    				System.out.println("\t" + node.getVect().get());

    			break;
    		}
    		else
    			System.out.println("pas de chemin");
    	}
    	if (!found) System.out.println("C'est pas normal");
    	else System.out.println("Test1 OK!");

    	System.out.println("");

    	//test2:
    	found = false;
    	plateau = wallInMiddle();
    	for (i = 0; i<size; i++){
    		path_player1 = new PathFinder(plateau, dep_p1, new Vector(i, 0));
    		path_player2 = new PathFinder(plateau, dep_p2, new Vector(i, size-1));
    		//s'il existe au moins un chemin, afficher le chemin
    		ArrayList<PFNode> path_p1 = path_player1.run(null);
 			ArrayList<PFNode> path_p2 = path_player2.run(null);
    		if (path_p1 != null && path_p2 != null){
    			found = true;
    			System.out.println("PATH PLAYER1");
    			for (PFNode node: path_p1)
    				System.out.println("\t" + node.getVect().get());

    			System.out.println("");
    			System.out.println("PATH PLAYER2");
    			for (PFNode node: path_p2)
    				System.out.println("\t" + node.getVect().get());

    			break;
    		}
    		else
    			System.out.println("pas de chemin");
    	}
    	if (!found) System.out.println("C'est pas normal");
    	else System.out.println("Test2 OK!");
    	
    	System.out.println("");

    	//test2:
    	found = false;
    	plateau = wallStuck();
    	for (i = 0; i<size; i++){
    		path_player1 = new PathFinder(plateau, dep_p1, new Vector(i, 0));
    		path_player2 = new PathFinder(plateau, dep_p2, new Vector(i, size-1));
    		//s'il existe au moins un chemin, afficher le chemin
    		ArrayList<PFNode> path_p1 = path_player1.run(null);
 			ArrayList<PFNode> path_p2 = path_player2.run(null);
    		if (path_p1 != null && path_p2 != null){
    			found = true;
    			System.out.println("PATH PLAYER1");
    			for (PFNode node: path_p1)
    				System.out.println("\t" + node.getVect().get());

    			System.out.println("");
    			System.out.println("PATH PLAYER2");
    			for (PFNode node: path_p2)
    				System.out.println("\t" + node.getVect().get());

    			break;
    		}
    		else
    			System.out.println("pas de chemin");
    	}
    	if (!found) System.out.println("Test3 OK!");
    	else System.out.println("C'est pas normal");
	}

	private static Board withoutWall(){
		Board plateau = new Board(size);
		Vector pos;

		return plateau;
	}

	private static Board wallInMiddle(){
		Board plateau = new Board(size);
		Vector pos;

		//simulate a wall in the middle
		for (i = 0; i < 3; i++){
			pos = new Vector((int)(size/2 - 1 + i), (int)(size/2));
			plateau.setValue(pos, "w");
		}

		return plateau;
	}

	private static Board wallStuck(){
		Board plateau = new Board(size);
		Vector pos;

		//simulate Trump's Wall
		for (i = 0; i < size; i++){
			pos = new Vector(i, (int)(size/2) + 1);
			plateau.setValue(pos, "w");
		}

		return plateau;
	}
}
