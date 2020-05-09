//author: Nathan Amorison (Backend)

import quoridor;

public class HistoricButton{
	private parent;
	private String label;
	private Board grid;

	public HistoricButton(parent, String label, Board grid){
		this.parent = parent;
		this.label = label;
		this.grid = grid;

		//doit être un override d'un boutton
	}

	private clicked(ActionEvent e){
		//quand cliqué, alors on set tout le tableau
	}

	public void show(){
		//on montre le boutton
		//? parent.add(this);
	}

	public void clear(){
		//on supprime le boutton
		//? parent.remove(this);
	}
}