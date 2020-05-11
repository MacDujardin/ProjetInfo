package quoridor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;
//import quoridor.*;

//author: Nathan Amorison

public class Pawn{ //extends {
	private final String white = "white";
	private final String black = "black";
	private int click_num = 0;
	private Possibilitie[] possibilities;
	private Vector[] cases_possibilities;
	private Vector[] [] all_cases_possibilities;
	private Stock stock;
	private playable;

	public int stock_count = 8;
	public GridPane parent;
	public Vector pos;
	public String color;
	public Pawn enemy;
	public Board plateau;// = Main.plateau;

	public Pawn(GridPane parent, Vector pos, String color, String playable){
        /*Constructeur pour un pion
         *parent est la variable qui correspond ﾃ� l'objet de javafx qui contient le pion
         *pos correspond ﾃ� la position qui est donnﾃｩe au pion lors de sa construction
         *color est la variable qui permet de choisir la couleur du pion
         *playable permet de savoir si le pion est jouable ou pas
         *|-> non jouable dans le cas d'une IA
         */
        this.parent = parent;
        this.pos = pos;
        this.color = color;
        this.playable = playable;
        Image whitep = new Image("file:Black.png");
        Image blackp = new Image("file:Black.png");
        Button pawnbutton = new Button();
        parent.add(pawnbutton , pos.x , pos.y);
        pawnbutton.setOnAction(Clicked()); //a mettre dans enable
        pawnbutton.setOpacity(0);

        if (this.color.equals(white)) {
            pawnbutton.setGraphic(new ImageView(whitep));
        }
        else {
            pawnbutton.setGraphic(new ImageView(blackp));
        }
    }

	public void destroyPossibilities(){
		//on d�ｾ�ｽｩtruit les objets mis dans le tableau des possibilit�ｾ�ｽｩs

		for (int i = 0; i < possibilities.length; i++) {
			
		}
			//possibilities[i].destroy() //.hide()
	}

	public EventHandler<ActionEvent> Clicked(){ //�ｾ�ｽｩv�ｾ�ｽｩnement /!\
		click_num++;
		click_num %= 2;
		if (click_num > 1e-100){
			//si c'est la premi�ｾ�ｽｨre fois qu'on appuye sur le pion
			//alors on montre les possibilit�ｾ�ｽｩs de mouvement
			checkCase();
			checkWall();
			ShowPossibilities();
		}
		else{
			//c'est que c'est la deuxi�ｾ�ｽｨme fois qu'on appuie sur le pion
			//donc on cache les possibilit�ｾ�ｽｩs
			destroyPossibilities();
		}
		return null;
	}

	public void ShowPossibilities(){
		//on montre les possibilit�ｾ�ｽｩs sur le plateau, gr�ｾ�ｽ｢ce �ｾ�ｿｽ un tableau de possibilit�ｾ�ｽｩs
		
	}

	public void checkWall(){
		//mise a jour des possibilites de mouvement en prenant en compte les murs et le pion adverse

		all_cases_possibilities = new Vector[cases_possibilities.length][];
		for (int i = 0; i < cases_possibilities.length; i++){
			Vector mvmt = cases_possibilities[i];
			String mur_case = plateau.getValue(pos, mvmt);
			String pawn_case = plateau.getValue(pos, mvmt.mul(2));
			if (mur_case.equals("w"))
				all_cases_possibilities[i] = null;
				//on ne peut pas aller dans cette direction

			else if (pawn_case.equals("p")){
				//s'il y a un pion sur la case
				mur_case = plateau.getValue(pos, mvmt.mul(3));
				if (mur_case.equals("w")){
					Vector [] sub_list;
					//s'il y a un mur derri�ｾ�ｽｨre le pion, mvmts en L
					String mur_case_left = plateau.getValue(pos, mvmt.mul(2).add(mvmt.perpendiculaire()));
					String mur_case_right = plateau.getValue(pos, mvmt.mul(2).sub(mvmt.perpendiculaire()));
					// /!\ directions interchangees quand mvmt est horizontal => mur_case_right est en realite la verification du mouvement vers la gauche
					if (mur_case_left.equals("w") && !mur_case_right.equals("w")){
						//ajouter le mvmt vers la droite
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).sub(mvmt.perpendiculaire());
					}
					else if (!mur_case_left.equals("w") && mur_case_right.equals("w")){
						//ajouter le mvmt vers la gauche
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).add(mvmt.perpendiculaire());
					}
					else if (!mur_case_left.equals("w") && !mur_case_right.equals("w")){
						//ajouter les 2 mvmts
						sub_list = new Vector[2];
						sub_list[0] = mvmt.mul(2).sub(mvmt.perpendiculaire());
						sub_list[1] = mvmt.mul(2).add(mvmt.perpendiculaire());
					}

					//pas de direction possible
					sub_list = null;
				}

			Vector[] sub_list = {mvmt}; //juste le move de base
			all_cases_possibilities[i] = sub_list;
			}
		}
	}

	public void checkCase(){
		//v�ｾ�ｽｩrifie qu'il y ait bien une case pour les possibles mouvements
		boolean [] cases = new boolean[4];
		for (int i = 0; i < 4; i++)
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
			//voir si la case �ｾ�ｿｽ gauche existe
			cases[2] = true;
			num_of_possib++;
		}

		if ((pos.x+right.x*2) < 17 && (pos.y+right.y*2) < 17){
			//voir si la case �ｾ�ｿｽ droite existe
			cases[3] = true;
			num_of_possib++;
		}

		int count = 0;
		Vector []cases_possibilities = new Vector[num_of_possib];
		for (int i = 0; i < cases.length; i++){
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
		//bouge le pion �ｾ�ｿｽ la nouvelle position newpos pass�ｾ�ｽｩe en argument
		//v�ｾ�ｽｩrifie si le joueur a gagn�ｾ�ｽｩ la partie gr�ｾ�ｽ｢ce �ｾ�ｿｽ ce mouvement
		//une fois fini, on passe la main �ｾ�ｿｽ l'autre joueur

		if (color == white){
			if (pos.x == 16)
				plateau.setValue(pos, "bw");
			else
				plateau.setValue(pos, null);
		}
		else if (color == black){
			if (pos.x == 16)
				plateau.setValue(pos, "ww");
			else
				plateau.setValue(pos, null);
		}

		pos = newpos;
		win();
		plateau.setValue(pos, "p");
		//placer le pion, visuellement sur la nouvelle position
		disable();
		stock.player = enemy;
		enemy.enable();
	}

	public void win(){
		//v�ｾ�ｽｩrifie si la position actuelle est une case gagnante

		boolean stop = false;
		if (color == white && plateau.getValue(pos) == "ww"){
			System.out.println("winner is white");
			stop = true;
		}
		else if (color == black && plateau.getValue(pos) == "bw"){
			System.out.println("winner is black");
			stop = false;
		}

		if (stop){
			//action quitter
		}
	}

	public void enable(){
		//rend le pion jouable //configure une action
	}

	public void disable(){
		//rend le pion statique //configure une action
	}

	public void runIA(){
		Random generator = new Random();
		if (playable == "IAWeek"){
			//use random for mouvement
			int wall_or_move;
			Wall wall;
			boolean free = False;
			int x, y;
			String[] sens = {"Vertical", "Horizontal"};
			int chose_sens;
			Vector w_pos;

			//s'il reste des murs, on peut en poser, sinon c'est d'office un mouvement
			if (stock_count > 0)
				wall_or_move = generator.nextInt(2);
			else
				wall_or_move = 1;

			if (wall_or_move == 0){
				//mettre un wall à une position random
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

					free = wall.placeIA();
				}while(!free);
			}
			else if (wall_or_move = 1){
				//trouver tous les moves possibles
				//se déplacer dans un des mouvements trouvés (random)
				checkCase();
				checkWall();

				//utiliser aléatoirement un Vector dans all_cases_possibilities
				do{
					int chose_move = generator.nextInt(all_cases_possibilities.length);
				} while(all_cases_possibilities[chose_move]!=null);

				int chose_move2 = generator.nextInt(all_cases_possibilities[chose_move].length);

				move(all_cases_possibilities[chose_move][chose_move2]);
			}
		}

		else if (playable == "IAMedium"){
			//random entre mur ou move
			//PF pour savoir où le poser
			int wall_or_move;
			Wall wall;
			boolean free = False;
			ArrayList<PFNode[]> pfs = new ArrayList<PFNode[]>();
			PathFinder e_path, p_path;
			String sens;

			Vector w_pos;
			Vector delta;
			Vector temp_pos;

			PFNode less_cost = null;
			int node_list_id, node_id;

			//s'il reste des murs, on peut en poser, sinon c'est d'office un mouvement
			if (stock_count > 0)
				wall_or_move = generator.nextInt(2);
			else
				wall_or_move = 1;

			if (wall_or_move == 0){
				//trouver le pf ennemy
				for (int i = 0; i<17; i+2){
					if (color == white)
						e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 17));
					else
						e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 0));
					pfs.add(e_path.run());
				}

				//trouver le node avec les cost le plus petit dans e_nodes
				for(int i = 1; i < pfs.size(); i++){
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
				 *si plus de node suivant, on regarde dans les nodes précédents
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
								
								free = wall.placeIA();
							}while(b < 3 && !free);
							//on met le 2eme node en node à regrader après
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
								
								free = wall.placeIA();
							}while(b < 3 && !free);
						}
						else{
							wall_or_move = 1;
							free = True; //on n'a pas mis de mur, mais on sort du do while
						}
					}
				}while(!free);
			}

			//normalement on met simplement un else, mais si on est dans le cas qu'on ne trouvait pas d'endroit où
			//poser de mur dans le passage de l'ennemi, alors il vaut mieux bouger
			if (wall_or_move = 1){
				for (int i = 0; i<17; i+2){
					if (color == white)
						p_path = new PathFinder(plateau, pos, new Vector(i, 0));
					else
						p_path = new PathFinder(plateau, pos, new Vector(i, 17));
					pfs.add(p_path.run());
				}
				//on additionne les couts de tous les nodes dans chaque path pour savoir lequel a le plus petit
				int [] sommes_couts = new int[pfs.size()];
				for (int i = 0; i<pfs.size(); i++){
					sommes_couts[i] = 0;
					for (int j = 0; j < pfs.get(i).length; j++)
						sommes_couts[i] = sommes_couts[i] + pfs.get(i)[j].cost;
				}
				int less_cost = sommes_couts[0];
				int id = 0;
				//trouver l'index du chemin le plus intéressant pour le retrouver dans pfs
				for (int i = 1; i < sommes_couts.length; i++){
					if (less_cost > sommes_couts[i]){
						less_cost = sommes_couts[i];
						id = i;
					}
				}

				//on doit prendre le node qui nous intéresse, pour connaitre sa pos, et bouger dessus
				//ce node est à l'index 1 dans la liste de node du pathfinder
				move(pfs.get(id)[1].getVect());
			}
		}

		else if (playable == "IAHard"){
			//PF avec costs pour savoir quoi faire
			for (int i = 0; i<17; i+2){
				if (color = white){
					p_path = new PathFinder(plateau, pos, new Vector(i, 0));
					e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 17));
				}
				else{
					p_path = new PathFinder(plateau, pos, new Vector(i, 17));
					e_path = new PathFinder(plateau, enemy.pos, new Vector(i, 0));
				}
				pfs.add(p_path.run());
				pfs.add(e_path.run());
			}

			int [] sommes_couts_p = new int[pfs.size()/2];
			int [] sommes_couts_e = new int[pfs.size()/2];
			
			for (int i = 0; i < pfs.size(); i+2){
				sommes_couts_p[i/2] = 0;
				for (int j = 0; j < sommes_couts_p.length; j++)
					sommes_couts_p[i/2] = sommes_couts_p[i/2] + pfs.get(i)[j].cost;
			}
			for (int i = 1; i < pfs.size(); i+2){
				sommes_couts_e[(i-1)/2] = 0;
				for (int j = 0; j < sommes_couts_e.length; j++)
					sommes_couts_e[(i-1)/2] = sommes_couts_e[(i-1)/2] + pfs.get(i)[j];
			}

			int less_cost_p = sommes_couts_p[0];
			int id_p = 0;
			int less_cost_e = sommes_couts_e[0];
			int id_e = 0;
			for (int i = 1; i < sommes_couts_p.length; i++){
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
				PFNode less_cost = pfs.get(id_e*2+1)[0];
				int node_id = 0;
				for (int i = 1; i < pfs.get(id_e*2+1).length; i++){
					if (less_cost.cost > pfs.get(id_e*2+1)[i].cost){
						less_cost = pfs.get(id_e*2+1)[i].cost;
						node_id = i;
					}
				}

				PFNode temp = less_cost;
				int node_id_temp = node_id;
				int a = 1;
				int b;

				/*en partant de ce node au plus petit cout, on peut trouver le suivant
				 *tant qu'on ne peut pas placer le mur, on le place au node suivant
				 *si plus de node suivant, on regarde dans les nodes précédents
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
								
								free = wall.placeIA();
							}while(b < 3 && !free);
							//on met le 2eme node en node à regrader après
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
								
								free = wall.placeIA();
							}while(b < 3 && !free);
						}
						else{
							wall_or_move = 1;
							free = True; //on n'a pas mis de mur, mais on sort du do while
						}
					}
				}while(!free);
			}
			else{
				//bouger
				move(pfs.get(id_p*2)[1].getVect())
			}
		}
	}
}