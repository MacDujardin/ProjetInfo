//author: Nathan Amorison

package quoridor

public class Wall{
	private parent;
	private String sens;

	public Vector pos;
	public Wall(parent, Vector position, String sens){
		this.parent = parent;
		this.sens = sens;
		if (sens == "Vertical"){
			//création du visuel pour un mur vertical
		}
		else if (sens == "Horizontal"){
			//création du visuel pour un mur horizontal
		}

		pos = position;
	}

	public void onRelease(){ // /!\ event 
		//place le mur à la position donnée lors du release
		boolean found = False;
		int x = event.x;
		int y = event.y;

		if (sens == "Vertical"){
			//corrections pour le positionnement
		}
		else if (sens == "Horizontal"){
			//corrections pour le positionnement
		}

		//trouver les index pour positionner le mur

		//Vérifier qu'il n'y a pas déjà un mur
		if (busy(pos) == False){
			for (int i = 0; i<size){//static size
				p_path = PathFinder(plateau, player.pos, new Vector(i, 1));
				e_path = PathFinder(plateau, ennemy.pos, new Vector(i, size));
				if (p_path.run() != null && e_path.run() != null){
					//un chemin existe pour chaque pion, pour pouvoir gagner
					paretn.usingWall()
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
						//tableau sur les 3 positions occupées par le Wall
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
				//on récupère la valeur actuelle des 3 cases du mur
				val = plateau.getValue(new Vector(pos.x, pos.y + i));
			}
			else if (sens == "Horizontal"){
				//on récupère la valeur des 3cases
				val = plateau.getValue(new Vector(pos.x + i, pos.y));
			}

			if (val != null && val != "bw" &&  val != "ww")
				return True; //si la case est déjà occupée
		}

		return False;
	}

	private void disable(){
		//fait en sorte que l'objet ne soit visiblement plus utilisable
	}

	private void place(){
		//place le mur sur pos
	}
}