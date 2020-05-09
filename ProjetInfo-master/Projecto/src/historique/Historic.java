//author: Nathan Amorison (Backend)

import java.util.ArrayList;
import quoridor;

public class Historic{
	private GridPane parent;
	private Pawn p1;
	private Pawn p2;
	private int tour = 1;

	private ArrayList<HistoricButton> historic = new ArrayList<HistoricButton>();

	public Historic(GridPane parent, Pawn p1, Pawn p2, Board grid){
		this.parent = parent;
		this.p1 = p1;
		this.p2 = p2;

		HistoricButton debut = new HistoricButton(this, "debut", grid);
		historic.add(debut);
		show()
	}

	public void newMove(Pawn p, Board grid){
		if (historic.size() > 10)
			clear();

		HistoricButton move = new HistoricButton(this, p.color +":"+(String)(int)(tour/2+1), grid);
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