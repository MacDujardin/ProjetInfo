//author: Nathan Amorison (Backend)

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import quoridor.Board;

public class HistoricButton{
	private Historic parent;
	private String label;
	private Board grid;

	public HistoricButton(Historic parent, String label, Board grid){
		this.parent = parent;
		this.label = label;
		this.grid = grid;
		Button histobut = new Button();
        histobut.setOnAction(clicked(null));
		//doit être un override d'un boutton
	}

	private EventHandler<ActionEvent> clicked(ActionEvent e){
		return null;
		//quand cliqué, alors on set tout le tableau
	}

	public void show(){
		//on montre le boutton
		//? parent.add(this);
	}

	public void clear(){
		//on supprime le boutton
		parent.remove(this);
	}
}