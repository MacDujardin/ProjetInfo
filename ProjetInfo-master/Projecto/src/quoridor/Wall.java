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

	public Pawn enemy;
	public Pawn player;
	public Vector pos;
	public Wall(GridPane parent, Board plateau, Vector position, String sens){
		this.parent = parent;
		this.sens = sens;
		this.plateau = plateau;
		Button Wallbutt = new Button();
		Image wallvert = new Image("");
		Image wallhor = new Image("");
		Wallbutt.setOpacity(0);
		Wallbutt.setOnAction(null);
		if (sens == "Vertical"){
			//cr�ｾ�ｽｩation du visuel pour un mur vertical
			Wallbutt.setGraphic(new ImageView(wallvert));
		}
		else if (sens == "Horizontal"){
			//cr�ｾ�ｽｩation du visuel pour un mur horizontal
			Wallbutt.setGraphic(new ImageView(wallhor));
		}

		pos = position;
	}

	public EventHandler<ActionEvent> onRelease(ActionEvent event){ // /!\ event 
		//place le mur �ｾ�ｿｽ la position donn�ｾ�ｽｩe lors du release
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
        PathFinder p_path,e_path;
		//V�ｾ�ｽｩrifier qu'il n'y a pas d�ｾ�ｽｩj�ｾ�ｿｽ un mur
		if (busy() == false){
			for (int i = 0; i<17;i++){
				p_path =new PathFinder(plateau, player.pos, new Vector(i, 1));
				e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
				if (p_path.run(null) != null && e_path.run(null) != null){
					//un chemin existe pour chaque pion, pour pouvoir gagner
					player.usingWall();
					Wall new_wall = new Wall(parent, pos, sens);
					new_wall.disable();
					new_wall.place();
					found = true;
					break;
				}
			}

			if (found == true){
				for (int i = 0; i < 3; i++){
					if (sens == "Vertical"){
						//on met un marqueur w (wall) dans le
						//tableau sur les 3 positions occup�ｾ�ｽｩes par le Wall
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

	private boolean busy(){
		String val= null;
		for (int i = 0; i < 3; i++){
			if (sens == "Vertical"){
				//on r�ｾ�ｽｩcup�ｾ�ｽｨre la valeur actuelle des 3 cases du mur
				val = plateau.getValue(new Vector(pos.x, pos.y + i));
			}
			else if (sens == "Horizontal"){
				//on r�ｾ�ｽｩcup�ｾ�ｽｨre la valeur des 3cases
				val = plateau.getValue(new Vector(pos.x + i, pos.y));
			}

			if ( val != null && val != "bw" &&  val != "ww")
				return true; //si la case est d�ｾ�ｽｩj�ｾ�ｿｽ occup�ｾ�ｽｩe
		}

		return false;
	}

	public boolean placeForIA(){
		//verif si on peut placer un mur ﾃ� cette position et dans le sens souhaitﾃｩ, sinon on demande de recommencer
		boolean found = false;
		PathFinder p_path , e_path ;
		

		if (busy() == false){
			//on vﾃｩrifie qu'il existe au moins un chemin par joueur pour gagner
			for (int i = 0; i<17; i++){
				p_path =new PathFinder(plateau, player.pos, new Vector(i, 1));
				e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
				if (p_path.run(null) != null && e_path.run(null) != null){
					//un chemin existe pour chaque pion, pour pouvoir gagner
					player.usingWall();
					disable();
					place();
					found = true;
					break;
				}
			}
			if (found == true){
				for (int i = 0; i < 3; i++){
					if (sens == "Vertical"){
						//on met un marqueur w (wall) dans le
						//tableau sur les 3 positions occup�ｾ�ｽｩes par le Wall
						plateau.setValue(new Vector(pos.x, pos.y+i), "w");
					}
					else if (sens == "Horizontal"){
						//on met un marqueur sur les 3 cases
						plateau.setValue(new Vector(pos.x+i, pos.y), "w");
					}
				}
				return true;
			}
			else
				return false; //on peut pas placer le mur lﾃ�
		}

		else
			return false;
	}

	private void disable(){
		//fait en sorte que l'objet ne soit visiblement plus utilisable
	}

	private void place(){
		//place le mur sur pos
	}
}