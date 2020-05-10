//author: Nathan Amorison

package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Wall{
	private GridPane parent;
	private String sens;
	private Board plateau;

	public Vector pos;
	public Wall(GridPane parent,Board plateau, Vector position, String sens){
		this.parent = parent;
		this.sens = sens;
		this.plateau = plateau;
		Button Wallbutt = new Button();
		Image wallvert = new Image("");
		Image wallhor = new Image("");
		Wallbutt.setOpacity(0);
		Wallbutt.setOnAction(null);
		if (sens == "Vertical"){
			//crﾃｩation du visuel pour un mur vertical
			Wallbutt.setGraphic(new ImageView(wallvert));
		}
		else if (sens == "Horizontal"){
			//crﾃｩation du visuel pour un mur horizontal
			Wallbutt.setGraphic(new ImageView(wallhor));
		}

		pos = position;
	}

	public EventHandler<ActionEvent> onRelease(ActionEvent event){ // /!\ event 
		//place le mur ﾃ� la position donnﾃｩe lors du release
		boolean found = false;
		int x = event.x;
		int y = event.y;

		if (sens == "Vertical"){
			//corrections pour le positionnement
		}
		else if (sens == "Horizontal"){
			//corrections pour le positionnement
		}

		//trouver les index pour positionner le mur

		//Vﾃｩrifier qu'il n'y a pas dﾃｩjﾃ� un mur
		if (busy(pos) == False){
			for (int i = 0; i<size){//static size
				p_path = PathFinder(plateau, player.pos, new Vector(i, 1));
				e_path = PathFinder(plateau, ennemy.pos, new Vector(i, size));
				if (p_path.run() != null && e_path.run() != null){
					//un chemin existe pour chaque pion, pour pouvoir gagner
					parent.usingWall()
					Wall new_wall = new Wall(parent.parent, pos, sens);
					new_wall.disable();
					new_wall.place();
					found = True;
					break;
				}
			}

			if (found == True){
				for (int i = 0; i < 3; i++){
					if (sens == "Vertical"){
						//on met un marqueur w (wall) dans le
						//tableau sur les 3 positions occupﾃｩes par le Wall
						plateau.setValue(new Vector(pos.x, pos.y+i), "w");
					}
					else if (sens == "Horizontal"){
						//on met un marqueur sur les 3 cases
						plateau.setValue(new Vector(pos.x+i, pos.y), "w");
					}
				}
			}
		}
	}

	private boolean busy(Vector pos){
		String val;
		for (int i = 0; i < 3; i++){
			if (sens == "Vertical"){
				//on rﾃｩcupﾃｨre la valeur actuelle des 3 cases du mur
				val = plateau.getValue(new Vector(pos.x, pos.y + i));
			}
			else if (sens == "Horizontal"){
				//on rﾃｩcupﾃｨre la valeur des 3cases
				val = plateau.getValue(new Vector(pos.x + i, pos.y));
			}

			if (val != null && val != "bw" &&  val != "ww")
				return true; //si la case est dﾃｩjﾃ� occupﾃｩe
		}

		return false;
	}

	private void disable(){
		//fait en sorte que l'objet ne soit visiblement plus utilisable
	}

	private void place(){
		//place le mur sur pos
	}
}