package quoridor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;

//author: Nathan Amorison [BACKEND]
//author: Igor Dujardin [FRONTEND]

public class Pawn{ //extends {
	private final String white = "white";
	private final String black = "black";
	private int click_num = 0;
	private Possibilitie[] possibilities;
	private Vector[] cases_possibilities;
	private Vector[] [] all_cases_possibilities;
	private Stock stock;
	private int i;
	private Button pawnbutton;

	public String playable;
	public int stock_count = 8;
	public GridPane parent;
	public Vector pos;
	public String color;
	public Pawn enemy;
	public Board plateau;

	public Pawn(GridPane parent, Board plateau, Vector pos, String color, String playable){
        /*Constructeur pour un pion
         *parent est la variable qui correspond a l'objet de javafx qui contient le pion
         *plateau est le "pointeur" vers le tableau Board qui contient les donnees du jeu
         *pos correspond a la position qui est donne au pion lors de sa construction
         *color est la variable qui permet de choisir la couleur du pion
         *playable permet de savoir si le pion est jouable ou pas
         *|-> non jouable dans le cas d'une IA
         */
        this.parent = parent;
        this.pos = pos;
        this.color = color;
        this.playable = playable;
        this.plateau = plateau;
        Image whitep = new Image("file:White.png");
        Image blackp = new Image("file:Black.png");
        pawnbutton = new Button();
        parent.add(pawnbutton , pos.x , pos.y);
        pawnbutton.setOpacity(100);
        pawnbutton.setMinWidth(10.0);
        pawnbutton.setMinHeight(10.0);

        if (this.color.equals(white)) {
            pawnbutton.setGraphic(new ImageView(whitep));
        }
        else {
            pawnbutton.setGraphic(new ImageView(blackp));
        }
    }

	public void destroyPossibilities(){
		//on detruit les objets mis dans le tableau des possibilites
		for (i = 0; i < possibilities.length; i++)
			possibilities[i].destroy();
	}

	public EventHandler<ActionEvent> Clicked(){
		click_num++;
		click_num %= 2;
		if (click_num > 1e-100){
			//si c'est la premiere fois qu'on appuye sur le pion
			//alors on montre les possibilites de mouvement
			checkCase();
			checkWall();
			ShowPossibilities();
		}
		else{
			//c'est que c'est la deuxieme fois qu'on appuie sur le pion
			//donc on cache les possibilites
			destroyPossibilities();
		}
		return null;
	}

	public void ShowPossibilities(){
		//on montre les possibilites sur le plateau, grace a un tableau de possibilites
		int a = 0;
		for (i = 0; i < all_cases_possibilities.length; i++)
			if (all_cases_possibilities[i] != null)
				for (int j = 0; j < all_cases_possibilities[i].length; j++)
					a++;

		possibilities = new Possibilitie[a];
		Possibilitie case_choice;
		a = 0;

		for (i = 0; i < all_cases_possibilities.length; i++){
			if (all_cases_possibilities[i] != null){
				for (int j = 0; j < all_cases_possibilities[i].length; j++){
					case_choice = new Possibilitie(parent, this, all_cases_possibilities[i][j]);
					possibilities[a] = case_choice;
					a++;
				}
			}
		}
	}

	public void checkWall(){
		//mise a jour des possibilites de mouvement en prenant en compte les murs et le pion adverse

		all_cases_possibilities = new Vector[cases_possibilities.length][];
		for (int i = 0; i < cases_possibilities.length; i++){
			Vector mvmt = cases_possibilities[i];
			String mur_case = plateau.getValue(pos, mvmt);
			String pawn_case = plateau.getValue(pos, mvmt.mul(2));
			if (mur_case == "w")
				all_cases_possibilities[i] = null;
				//on ne peut pas aller dans cette direction

			else if (pawn_case == "p"){
				//s'il y a un pion sur la case
				mur_case = plateau.getValue(pos, mvmt.mul(3));
				if (mur_case == "w"){
					Vector [] sub_list;
					//s'il y a un mur derriere le pion, mvmts en L
					String mur_case_left = plateau.getValue(pos, mvmt.mul(2).add(mvmt.perpendiculaire()));
					String mur_case_right = plateau.getValue(pos, mvmt.mul(2).sub(mvmt.perpendiculaire()));
					// /!\ directions interchangees quand mvmt est horizontal => mur_case_right est en realite la verification du mouvement vers la gauche
					if (mur_case_left == "w" && mur_case_right != "w"){
						//ajouter le mvmt vers la droite
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).sub(mvmt.perpendiculaire());
					}
					else if (mur_case_left != "w" && mur_case_right == "w"){
						//ajouter le mvmt vers la gauche
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).add(mvmt.perpendiculaire());
					}
					else if (mur_case_left != "w" && mur_case_right != "w"){
						//ajouter les 2 mvmts
						sub_list = new Vector[2];
						sub_list[0] = mvmt.mul(2).sub(mvmt.perpendiculaire());
						sub_list[1] = mvmt.mul(2).add(mvmt.perpendiculaire());
					}

					//pas de direction possible
					sub_list = null;
				}
			}

			else{
				Vector[] sub_list = {mvmt.mul(2)}; //juste le move de base
				all_cases_possibilities[i] = sub_list;
			}
		}
	}

	public void checkCase(){
		//verifie qu'il y ait bien une case pour les possibles mouvements
		boolean [] cases = new boolean[4];
		for (i = 0; i < 4; i++)
			cases[i] = false;
		int num_of_possib = 0;
		Vector down = new Vector(0,1);
		Vector right = new Vector(1,0);

		if ((pos.x-down.x*2) > -1 && (pos.y-down.y*2) > -1){
			//voir si la case au-dessus existe
			cases[0] = true;
			num_of_possib++;
		}

		if ((pos.x+down.x*2) < 17 && (pos.y+down.y*2) < 17){
			//voir si la case en-dessous existe
			cases[1] = true;
			num_of_possib++;
		}

		if ((pos.x-right.x*2) > -1 && (pos.y-right.y*2) > -1){
			//voir si la case a gauche existe
			cases[2] = true;
			num_of_possib++;
		}

		if ((pos.x+right.x*2) < 17 && (pos.y+right.y*2) < 17){
			//voir si la case a droite existe
			cases[3] = true;
			num_of_possib++;
		}

		int count = 0;
		cases_possibilities = new Vector[num_of_possib];
		for (i = 0; i < cases.length; i++){
			if (cases[i] == true){
				if (i-0 < 1e-100) //si i == 0
					cases_possibilities[count] = new Vector(-down.x, -down.y);
				else if (i-1 < 1e-100) //si i == 1
					cases_possibilities[count] = down;
				else if (i-2 < 1e-100) //si i == 2
					cases_possibilities[count] = new Vector(-right.x, -right.y);
				else if (i-3 < 1e-100) //si i == 3
					cases_possibilities[count] = right;

				count++;
			}
		}
	}

	public void move(Vector newpos){
		//bouge le pion a la nouvelle position newpos passee en argument
		//verifie si le joueur a gagne la partie grace a ce mouvement
		//une fois fini, on passe la main a l'autre joueur

		if (color == white){
			if (pos.y == 16)
				plateau.setValue(pos, "pawn2win");
			else
				plateau.setValue(pos, null);
		}
		else if (color == black){
			if (pos.y == 0)
				plateau.setValue(pos, "pawn1win");
			else
				plateau.setValue(pos, null);
		}

		pos = newpos;
		win();
		plateau.setValue(pos, "p");
		disable();
		click_num = 0;

		stock.player = enemy;
		if (enemy.playable != "Player")
			enemy.runIA();
		else
			enemy.enable();

		//placer le pion, visuellement sur la nouvelle position
		parent.getChildren().remove(pawnbutton);
		parent.add(pawnbutton, pos.x, pos.y);
	}

	public void win(){
		//verifie si la position actuelle est une case gagnante

		boolean stop = false;
		if (color == white && plateau.getValue(pos) == "pawn1win"){
			System.out.println("winner is white");
			stop = true;
		}
		else if (color == black && plateau.getValue(pos) == "pawn2win"){
			System.out.println("winner is black");
			stop = false;
		}

		if (stop){
			//action quitter
			System.out.println("quitter")
		}
	}

	public void enable(){
		//rend le pion jouable
        pawnbutton.setOnAction(e -> Clicked()); 
	}

	public void disable(){
		//rend le pion statique
        pawnbutton.setOnAction(e -> null); 
	}
	
	public void usingWall() {
		stock_count--;
	}

	public void runIA(){
		Random generator = new Random();
		int wall_or_move;
		Wall wall;
		boolean free = false;
		Vector w_pos;
		PathFinder e_path, p_path;
		ArrayList<PFNode[]> pfs = new ArrayList<PFNode[]>();
		Vector delta;
		Vector temp_pos;

		PFNode less_cost = null;
		int node_list_id = 0, node_id =0;
		
		if (playable == "IAWeek"){
			//use random for mouvement
			int x, y;
			String[] sens = {"Vertical", "Horizontal"};
			int chose_sens;

			//s'il reste des murs, on peut en poser, sinon c'est d'office un mouvement
			if (stock_count > 0)
				wall_or_move = generator.nextInt(2);
			else
				wall_or_move = 1;

			if (wall_or_move == 0){
				//mettre un wall a une position random
				do{
					do{
						x = generator.nextInt(17);
					}while (x%2 == 1);
					do{
						y = generator.nextInt(17);
					}while (y%2 == 1);
					chose_sens = generator.nextInt(2);
					w_pos = new Vector(x,y);

					wall = new Wall(parent, plateau, w_pos, sens[chose_sens]);
					wall.player = this;
					wall.enemy = enemy;

					free = wall.placeForIA();
				}while(!free);
			}
			else if (wall_or_move ==1) {
				//trouver tous les moves possibles
				//se daplacer dans un des mouvements trouves (random)
				checkCase();
				checkWall();
                 
				//utiliser aleatoirement un Vector dans all_cases_possibilities
				int chose_move;
				do{
					 chose_move = generator.nextInt(all_cases_possibilities.length);
				} while(all_cases_possibilities[chose_move]!=null);

				int chose_move2 = generator.nextInt(all_cases_possibilities[chose_move].length);

				move(all_cases_possibilities[chose_move][chose_move2]);
			}
		}


		else if (playable == "IAMedium"){
			//random entre mur ou move
			//PF pour savoir ou le poser
			String sens= "";

			//s'il reste des murs, on peut en poser, sinon c'est d'office un mouvement
			if (stock_count > 0)
				wall_or_move = generator.nextInt(2);
			else
				wall_or_move = 1;
		
			if (wall_or_move == 0){
				//trouver le pf ennemy
				for (int i = 0; i<17; i=i+2){
					if (color == white)
						e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 17));
					else
						e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 0));
					pfs.add(e_path.run(null));
				}

				//trouver le node avec les cost le plus petit dans e_nodes
				for(i = 1; i < pfs.size(); i++){
					for (int n = 0; n<pfs.get(i).length; n++){
						if(less_cost == null){
							less_cost = pfs.get(i)[n];
							node_list_id = i;
							node_id = n;
						}
						else if (less_cost.cost > pfs.get(i)[n].cost){
							less_cost = pfs.get(i)[n];
							node_list_id = i;
							node_id = n;
						}
					}
				}

				PFNode temp = less_cost;
				int node_id_temp = node_id;
				int a = 1;
				int b;

				/*en partant de ce node au plus petit cout, on peut trouver le suivant
				 *tant qu'on ne peut pas placer le mur, on le place au node suivant
				 *si plus de node suivant, on regarde dans les nodes precedents
				 */
				do{
					b = 0;
					if (a>0){
						//verif s'il y a un node apres
						if(pfs.get(node_list_id).length>node_id_temp+a){
							delta = temp.pos.delta(pfs.get(node_list_id)[node_id_temp+a].pos).truediv(2);
							w_pos = temp.pos.add(delta);
							temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";
							do{
								if (sens == "Horizontal")
									temp_pos.x = w_pos.x + b;
								else
									temp_pos.y = w_pos.y + b;
								b++;
								wall = new Wall(parent, plateau, temp_pos, sens);
								wall.player = this;
								wall.enemy = enemy;
								
								free = wall.placeForIA();
							}while(b < 3 && !free);
							//on met le 2eme node en node a regrader apres
							temp = pfs.get(node_list_id)[node_id_temp+a];
							node_id_temp++;
						}
						//sinon, il faudra regarder devant
						else{
							a = -1;
							temp = less_cost;
						}
					}
					else{
						//verif s'il y a un node avant
						if(temp.parent != null){
							delta = temp.pos.delta(temp.parent.pos).truediv(2);
							w_pos = temp.pos.add(delta);
							temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";
							do{
								if (sens == "Horizontal")
									temp_pos.x = w_pos.x + b;
								else
									temp_pos.y = w_pos.y + b;
								b++;
								wall = new Wall(parent, plateau, temp_pos, sens);
								wall.player = this;
								wall.enemy = enemy;
								
								free = wall.placeForIA();
							}while(b < 3 && !free);
						}
						else{
							wall_or_move = 1;
							free = true; //on n'a pas mis de mur, mais on sort du do while
						}
					}
				}while(!free);
			}

			//normalement on met simplement un else, mais si on est dans le cas qu'on ne trouvait pas d'endroit ou
			//poser de mur dans le passage de l'ennemi, alors il vaut mieux bouger
			if (wall_or_move == 1){
				for (i = 0; i<17; i=i+2){
					if (color == white)
						p_path = new PathFinder(plateau, pos, new Vector(i, 0));
					else
						p_path = new PathFinder(plateau, pos, new Vector(i, 17));
					pfs.add(p_path.run(null));
				}
				//on additionne les couts de tous les nodes dans chaque path pour savoir lequel a le plus petit
				int [] sommes_couts = new int[pfs.size()];
				for (i = 0; i<pfs.size(); i++){
					sommes_couts[i] = 0;
					for (int j = 0; j < pfs.get(i).length; j++)
						sommes_couts[i] = sommes_couts[i] +(int) pfs.get(i)[j].cost;
				}
				int less_cost_node = sommes_couts[0];
				int id = 0;
				//trouver l'index du chemin le plus intﾃｩressant pour le retrouver dans pfs
				for (i = 1; i < sommes_couts.length; i++){
					if (less_cost_node > sommes_couts[i]){
						less_cost_node = sommes_couts[i];
						id = i;
					}
				}

				//on doit prendre le node qui nous interesse, pour connaitre sa pos, et bouger dessus
				//ce node est a l'index 1 dans la liste de node du pathfinder
				move(pfs.get(id)[1].getVect());
			}
		}

		else if (playable == "IAHard"){
			String sens="";
			//PF avec costs pour savoir quoi faire
			for (i = 0; i<17; i=i+2){
				if (color == white){
					p_path = new PathFinder(plateau, pos, new Vector(i, 0));
					e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 17));
				}
				else{
					p_path = new PathFinder(plateau, pos, new Vector(i, 17));
					e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 0));
				}
				pfs.add(p_path.run(null));
				pfs.add(e_path.run(null));
			}

			int [] sommes_couts_p = new int[pfs.size()/2];
			int [] sommes_couts_e = new int[pfs.size()/2];
			
			for (i = 0; i < pfs.size(); i=i+2){
				sommes_couts_p[i/2] = 0;
				for (int j = 0; j < sommes_couts_p.length; j++)
					sommes_couts_p[i/2] = sommes_couts_p[i/2] + (int) pfs.get(i)[j].cost;
			}
			for (i = 1; i < pfs.size(); i=i+2){
				sommes_couts_e[(i-1)/2] = 0;
				for (int j = 0; j < sommes_couts_e.length; j++)
					sommes_couts_e[(i-1)/2] = sommes_couts_e[(i-1)/2] +(int) pfs.get(i)[j].cost;
			}

			int less_cost_p = sommes_couts_p[0];
			int id_p = 0;
			int less_cost_e = sommes_couts_e[0];
			int id_e = 0;
			for (i = 1; i < sommes_couts_p.length; i++){
				if (sommes_couts_p[i] < less_cost_p){
					less_cost_p = sommes_couts_p[i];
					id_p = i;
				}
			}
			for (int i = 1; i < sommes_couts_e.length; i++){
				if (sommes_couts_e[i] < less_cost_e){
					less_cost_e = sommes_couts_e[i];
					id_e = i;
				}
			}

			if (less_cost_p < less_cost_e && stock_count > 0){
				//placer un mur
				//trouver le node ennemi avec le cost le plus bas dans le path le plus court
				for (int i = 1; i < pfs.get(id_e*2+1).length; i++){
					if (less_cost.cost > pfs.get(id_e*2+1)[i].cost){
						less_cost = pfs.get(id_e*2+1)[i];
						node_id = i;
					}
				}

				PFNode temp = less_cost;
				int node_id_temp = node_id;
				int a = 1;
				int b;

				/*en partant de ce node au plus petit cout, on peut trouver le suivant
				 *tant qu'on ne peut pas placer le mur, on le place au node suivant
				 *si plus de node suivant, on regarde dans les nodes precedents
				 */
				do{
					b = 0;
					if (a>0){
						//verif s'il y a un node apres
						if(pfs.get(node_list_id).length>node_id_temp+a){
							delta = temp.pos.delta(pfs.get(node_list_id)[node_id_temp+a].pos).truediv(2);
							w_pos = temp.pos.add(delta);
							temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";
							do{
								if (sens == "Horizontal")
									temp_pos.x = w_pos.x + b;
								else
									temp_pos.y = w_pos.y + b;
								b++;
								wall = new Wall(parent, plateau, temp_pos, sens);
								wall.player = this;
								wall.enemy = enemy;
								
								free = wall.placeForIA();
							}while(b < 3 && !free);
							//on met le 2eme node en node a regrader apres
							temp = pfs.get(node_list_id)[node_id_temp+a];
							node_id_temp++;
						}
						//sinon, il faudra regarder devant
						else{
							a = -1;
							temp = less_cost;
						}
					}
					else{
						//verif s'il y a un node avant
						if(temp.parent != null){
							delta = temp.pos.delta(temp.parent.pos).truediv(2);
							w_pos = temp.pos.add(delta);
							temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";
							do{
								if (sens == "Horizontal")
									temp_pos.x = w_pos.x + b;
								else
									temp_pos.y = w_pos.y + b;
								b++;
								wall = new Wall(parent, plateau, temp_pos, sens);
								wall.player = this;
								wall.enemy = enemy;
								
								free = wall.placeForIA();
							}while(b < 3 && !free);
						}
						else{
							wall_or_move = 1;
							free = true; //on n'a pas mis de mur, mais on sort du do while
						}
					}
				}while(!free);
			}
			else{
				//bouger
				move(pfs.get(id_p*2)[1].getVect());
			}
		}
	}
}