//author: Nathan Amorison (Backend)

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import quoridor.Board;
import quoridor.Pawn;

public class Historic{
	private BorderPane parent;
	private Pawn p1;
	private Pawn p2;
	private int tour = 1;
	private GridPane historique;
	public static int sizehisto = 10;

	private ArrayList<HistoricButton> historic = new ArrayList<HistoricButton>();

	public Historic(BorderPane parent, Pawn p1, Pawn p2, Board grid){
		this.parent = parent;
		this.p1 = p1;
		this.p2 = p2;

		HistoricButton debut = new HistoricButton(this, "debut", grid);
		historic.add(debut);
		show();
		historique.setGridLinesVisible(true);
		for(int i = 0 ; i> sizehisto ; i ++ ) {
			RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / sizehisto);
            historique.getRowConstraints().add(rowConst);   
		} 
		  for (int i = 0; i < 1; i++) {
	        	
	            ColumnConstraints colConst = new ColumnConstraints();
	            colConst.setPercentWidth(100.0 / 1);
	            historique.getColumnConstraints().add(colConst);
	        	
		  }
		show();  
		parent.setRight(historique);
		
	}

	public void newMove(Pawn p, Board grid){
		if (historic.size() > 10)
			clear();

		HistoricButton move = new HistoricButton(this, p.color +":"+Integer.toString((int)((tour/2)+1)), grid);
		historic.add(move);
		tour++;
	}

	private void clear(){
		//on ne montre plus les bouttons
		for (HistoricButton b: historic)
			b.clear();

		//on retire le premier boutton de la liste
		historic.remove(0);
	}

	private void show(){
		//on affiche les bouttons
		for (HistoricButton b: historic)
			b.show();
	}
}