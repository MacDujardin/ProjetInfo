package quoridor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;
import java.net.URL;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//author: Nathan Amorison [BACKEND]
//author: Igor Dujardin [FRONTEND]

public class Pawn{ //extends {
	private final String white = "white";
	private final String black = "black";
	private int click_num = 0;
	private Possibilitie[] possibilities;
	private Vector[] cases_possibilities;
	private Vector[] [] all_cases_possibilities;
	private int i;
	private Button pawnbutton;
    private ArrayList<ArrayList<Rectangle>> tiles;

	public Stock stock;
	public String playable;
	public int stock_count = 8;
	public GridPane parent;
	public Vector pos;
	public String color;
	public Pawn enemy = null;
	public Board plateau;

	public Pawn(GridPane parent, Board plateau, ArrayList<ArrayList<Rectangle>> tiles, Vector pos, String color, String playable){
        /*Constructeur pour un pion
         *parent est la variable qui correspond a l'objet de javafx qui contient le pion
         *plateau est le "pointeur" vers le tableau Board qui contient les donnees du jeu
         *tiles sont les cases qui peuvent accueillir un mur
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
        this.tiles = tiles;

        pawnbutton = new Button();
        parent.add(pawnbutton , pos.x , pos.y);
        pawnbutton.setOpacity(100);
        pawnbutton.setMinWidth(10.0);
        pawnbutton.setMinHeight(10.0);

		URL imageURL = null;

        if (this.color.equals(white)) {
        	imageURL = getClass().getResource("..\\..\\resources\\White.png");
            pawnbutton.setGraphic(new ImageView(new Image(imageURL.toExternalForm())));
        }
        else {
        	imageURL = getClass().getResource("..\\..\\resources\\Black.png");
            pawnbutton.setGraphic(new ImageView(new Image(imageURL.toExternalForm())));
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

			if (mur_case == "w"){
				all_cases_possibilities[i] = null;
				//on ne peut pas aller dans cette direction
			}

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
						sub_list[0] = mvmt.mul(2).sub(mvmt.mul(2).perpendiculaire());
					}
					else if (mur_case_left != "w" && mur_case_right == "w"){
						//ajouter le mvmt vers la gauche
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).add(mvmt.mul(2).perpendiculaire());
					}
					else if (mur_case_left != "w" && mur_case_right != "w"){
						//ajouter les 2 mvmts
						sub_list = new Vector[2];
						sub_list[0] = mvmt.mul(2).sub(mvmt.mul(2).perpendiculaire());
						sub_list[1] = mvmt.mul(2).add(mvmt.mul(2).perpendiculaire());
					}

					//pas de direction possible
					else
						sub_list = null;

					all_cases_possibilities[i] = sub_list;
				}
				else{
					Vector direction = pos.add(mvmt.mul(4));
					if(direction.x > 0 && direction.y < 17 && direction.x < 17 && direction.y > 0){
						Vector [] sub_list = {mvmt.mul(4)};
						all_cases_possibilities[i] = sub_list;
					}
				}
			}

			else if (mur_case != "w" && pawn_case != "p"){
				Vector [] sub_list = {mvmt.mul(2)};
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
		click_num = 0;

		disable();
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
			//le jeu est fini
			disable();
			enemy.disable();
			System.out.println("quitter");
		}
	}

	public void enable(){
		//rend le pion jouable
		stock.show(this);
		if (playable == "Player")
        	pawnbutton.setOnAction(e -> Clicked());
        else
        	runIA();
	}

	public void disable(){
		//rend le pion statique
        pawnbutton.setOnAction(null); 
	}
	
	public void usingWall() {
		disable();
		enemy.enable();
		stock_count--;
	}

	public void runIA(){
		Random generator = new Random();
		int wall_or_move;
		boolean free = false, found = false;
		Vector w_pos;
		PathFinder e_path, p_path;
		ArrayList<ArrayList<PFNode>> pfs = new ArrayList<ArrayList<PFNode>>();
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
					//chercher une position random qui soit correcte
					do{
						//choix de la position random
						do{
							x = generator.nextInt(17);
							y = generator.nextInt(17);
						}while ((y%2 == 1 && y%2 == 0) || (y%2 == 0 && y%2 == 1));
						chose_sens = generator.nextInt(2);
						w_pos = new Vector(x,y);
	
						free = !stock.busy(x,y, sens[chose_sens]);
					}while(!free);
					
					//mettre les murs
					for (i = 0; i < 3; i++){
						if(sens[chose_sens] == "Vertical")
							plateau.setValue(new Vector(x, y+i), "w");
						else if (sens[chose_sens] == "Horizontal")
							plateau.setValue(new Vector(x+i, y), "w");
					}
					
					//verifier si chaque joueur peut gagner
					for (i = 0; i < 17; i++){
						if (color == white){
							p_path = new PathFinder(plateau, this.pos, new Vector(i, 0));
							e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
						}
						else{
							p_path = new PathFinder(plateau, this.pos, new Vector(i, 17));
							e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 0));
						}
	
						if (p_path.run(null) != null && e_path.run(null) != null){
							//un chemin existe pour chaque pion, pour pouvoir gagner
							found = true;
							break;
						}
					}
					// si NON: on retire tout
					if (!found){
						for (i = 0; i < 3; i++){
							if(sens[chose_sens] == "Vertical")
								plateau.setValue(new Vector(x, y+i), null);
							else if (sens[chose_sens] == "Horizontal")
								plateau.setValue(new Vector(x+i, y), null);
						}
					}
					//si OUI: on indique le placement du mur, et on passe au joueur suivant
					else{
						for (i = 0; i < 3; i++){
							if(sens[chose_sens] == "Vertical")
								tiles.get(x).get(y+i).setFill(Color.BROWN);
							else if (sens[chose_sens] == "Horizontal")
								tiles.get(x+i).get(y).setFill(Color.BROWN);
						}
						usingWall();
					}
				}while(!found);
					
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
					for (int n = 0; n<pfs.get(i).size(); n++){
						if(less_cost == null){
							less_cost = pfs.get(i).get(n);
							node_list_id = i;
							node_id = n;
						}
						else if (less_cost.cost > pfs.get(i).get(n).cost){
							less_cost = pfs.get(i).get(n);
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
						if(pfs.get(node_list_id).size()>node_id_temp+a){
							delta = temp.pos.delta(pfs.get(node_list_id).get(node_id_temp+a).pos).truediv(2);
							w_pos = temp.pos.add(delta);
							//temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";

							//verifier que ca soit libre
							free = !stock.busy(w_pos.x, w_pos.y, sens);

							//placer un mur
							if (free){
								for (i = 0; i < 3; i++){
									if(sens == "Vertical")
										plateau.setValue(new Vector(w_pos.x, w_pos.y+i), "w");
									else if (sens == "Horizontal")
										plateau.setValue(new Vector(w_pos.x+i, w_pos.y), "w");
								}

								//verifier si chaque joueur peut gagner
								for (i = 0; i < 17; i++){
									if (color == white){
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 0));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
									}
									else{
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 17));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 0));
									}
				
									if (p_path.run(null) != null && e_path.run(null) != null){
										//un chemin existe pour chaque pion, pour pouvoir gagner
										found = true;
										break;
									}
								}

								//si NON: on retire les murs
								if (!found){
									for (i = 0; i < 3; i++){
										if(sens == "Vertical")
											plateau.setValue(new Vector(w_pos.x, w_pos.y+i), null);
										else if (sens == "Horizontal")
											plateau.setValue(new Vector(w_pos.x+i, w_pos.y), null);
									}
								}
								else{
									for (i = 0; i < 3; i++){
										if (sens == "Vertical")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
										else if (sens == "Horizontal")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
									}
									usingWall();
								}
							}

							//on met le 2eme node en node a regrader apres
							temp = pfs.get(node_list_id).get(node_id_temp+a);
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
							
							//verifier que ca soit libre
							free = !stock.busy(w_pos.x, w_pos.y, sens);

							//placer un mur
							if (free){
								for (i = 0; i < 3; i++){
									if(sens == "Vertical")
										plateau.setValue(new Vector(w_pos.x, w_pos.y+i), "w");
									else if (sens == "Horizontal")
										plateau.setValue(new Vector(w_pos.x+i, w_pos.y), "w");
								}

								//verifier si chaque joueur peut gagner
								for (i = 0; i < 17; i++){
									if (color == white){
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 0));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
									}
									else{
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 17));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 0));
									}
				
									if (p_path.run(null) != null && e_path.run(null) != null){
										//un chemin existe pour chaque pion, pour pouvoir gagner
										found = true;
										break;
									}
								}

								//si NON: on retire les murs
								if (!found){
									for (i = 0; i < 3; i++){
										if(sens == "Vertical")
											plateau.setValue(new Vector(w_pos.x, w_pos.y+i), null);
										else if (sens == "Horizontal")
											plateau.setValue(new Vector(w_pos.x+i, w_pos.y), null);
									}
								}
								else{
									for (i = 0; i < 3; i++){
										if (sens == "Vertical")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
										else if (sens == "Horizontal")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
									}
									usingWall();
								}
							}
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
					for (int j = 0; j < pfs.get(i).size(); j++)
						sommes_couts[i] = sommes_couts[i] +(int) pfs.get(i).get(j).cost;
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
				move(pfs.get(id).get(1).getVect());
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
					sommes_couts_p[i/2] = sommes_couts_p[i/2] + (int) pfs.get(i).get(j).cost;
			}
			for (i = 1; i < pfs.size(); i=i+2){
				sommes_couts_e[(i-1)/2] = 0;
				for (int j = 0; j < sommes_couts_e.length; j++)
					sommes_couts_e[(i-1)/2] = sommes_couts_e[(i-1)/2] +(int) pfs.get(i).get(j).cost;
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
				for (int i = 1; i < pfs.get(id_e*2+1).size(); i++){
					if (less_cost.cost > pfs.get(id_e*2+1).get(i).cost){
						less_cost = pfs.get(id_e*2+1).get(i);
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
						if(pfs.get(node_list_id).size()>node_id_temp+a){
							delta = temp.pos.delta(pfs.get(node_list_id).get(node_id_temp+a).pos).truediv(2);
							w_pos = temp.pos.add(delta);
							temp_pos = w_pos;
							if (delta.x == 0)
								sens = "Horizontal";
							else if (delta.y == 0)
								sens = "Vertical";
							
							//verifier que ca soit libre
							free = !stock.busy(w_pos.x, w_pos.y, sens);

							//placer un mur
							if (free){
								for (i = 0; i < 3; i++){
									if(sens == "Vertical")
										plateau.setValue(new Vector(w_pos.x, w_pos.y+i), "w");
									else if (sens == "Horizontal")
										plateau.setValue(new Vector(w_pos.x+i, w_pos.y), "w");
								}

								//verifier si chaque joueur peut gagner
								for (i = 0; i < 17; i++){
									if (color == white){
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 0));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
									}
									else{
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 17));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 0));
									}
				
									if (p_path.run(null) != null && e_path.run(null) != null){
										//un chemin existe pour chaque pion, pour pouvoir gagner
										found = true;
										break;
									}
								}

								//si NON: on retire les murs
								if (!found){
									for (i = 0; i < 3; i++){
										if(sens == "Vertical")
											plateau.setValue(new Vector(w_pos.x, w_pos.y+i), null);
										else if (sens == "Horizontal")
											plateau.setValue(new Vector(w_pos.x+i, w_pos.y), null);
									}
								}
								else{
									for (i = 0; i < 3; i++){
										if (sens == "Vertical")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
										else if (sens == "Horizontal")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
									}
									usingWall();
								}
							}
							//on met le 2eme node en node a regrader apres
							temp = pfs.get(node_list_id).get(node_id_temp+a);
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
							
							//verifier que ca soit libre
							free = !stock.busy(w_pos.x, w_pos.y, sens);

							//placer un mur
							if (free){
								for (i = 0; i < 3; i++){
									if(sens == "Vertical")
										plateau.setValue(new Vector(w_pos.x, w_pos.y+i), "w");
									else if (sens == "Horizontal")
										plateau.setValue(new Vector(w_pos.x+i, w_pos.y), "w");
								}

								//verifier si chaque joueur peut gagner
								for (i = 0; i < 17; i++){
									if (color == white){
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 0));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 17));
									}
									else{
										p_path = new PathFinder(plateau, this.pos, new Vector(i, 17));
										e_path =new PathFinder(plateau, enemy.pos, new Vector(i, 0));
									}
				
									if (p_path.run(null) != null && e_path.run(null) != null){
										//un chemin existe pour chaque pion, pour pouvoir gagner
										found = true;
										break;
									}
								}

								//si NON: on retire les murs
								if (!found){
									for (i = 0; i < 3; i++){
										if(sens == "Vertical")
											plateau.setValue(new Vector(w_pos.x, w_pos.y+i), null);
										else if (sens == "Horizontal")
											plateau.setValue(new Vector(w_pos.x+i, w_pos.y), null);
									}
								}
								else{
									for (i = 0; i < 3; i++){
										if (sens == "Vertical")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
										else if (sens == "Horizontal")
											tiles.get(w_pos.x).get(w_pos.y).setFill(Color.BROWN);
									}
									usingWall();
								}
							}
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
				move(pfs.get(id_p*2).get(1).getVect());
			}
		}
	}
}