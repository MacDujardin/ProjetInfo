package quoridor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.Random;

//author: Nathan Amorison

public class Pawn{ //extends {
	private final String white = "white";
	private final String black = "black";
	private int click_num = 0;
	private Possibilitie[] possibilities;
	private Vector[] cases_possibilities;
	private Vector[] [] all_cases_possibilities;
	private String playable;
	private int i;
	private Button pawnbutton;

	public GridPane parent;
	public Vector pos;
	public String color;
	public Board plateau;

	public Pawn(GridPane parent, Board plateau, Vector pos, String color, String playable){
        /*Constructeur pour un pion
         *parent est la variable qui correspond a l'objet de javafx qui contient le pion
         *pos correspond a la position qui est donnee au pion lors de sa construction
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
        enable();
        pawnbutton.setOpacity(100);
        pawnbutton.setMinWidth(200.0);
        pawnbutton.setMinHeight(200.0);

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
		//on montre les possibilites sur le plateau, grece e un tableau de possibilites
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
		for (i = 0; i < cases_possibilities.length; i++){
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
					//s'il y a un mur derri�ｿｽ�ｽｾ�ｿｽ�ｽｽ�ｽｨre le pion, mvmts en L
					String mur_case_left = plateau.getValue(pos, mvmt.mul(2).add(mvmt.perpendiculaire()));
					String mur_case_right = plateau.getValue(pos, mvmt.mul(2).sub(mvmt.perpendiculaire()));
					// /!\ directions interchangees quand mvmt est horizontal => mur_case_right est en realite la verification du mouvement vers la gauche
					if (mur_case_left == "w" && mur_case_right != "w"){
						//ajouter le mvmt vers la droite
						sub_list = new Vector[1];
						sub_list[0] = mvmt.mul(2).sub(mvmt.perpendiculaire());
					}
					else if (mur_case_left !="w" && mur_case_right == "w"){
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
		//v�ｿｽ�ｽｾ�ｿｽ�ｽｽ�ｽｩrifie qu'il y ait bien une case pour les possibles mouvements
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

		if ((pos.x+down.x*2) < 5 && (pos.y+down.y*2) < 5){
			//voir si la case en-dessous existe
			cases[1] = true;
			num_of_possib++;
		}

		if ((pos.x-right.x*2) > -1 && (pos.y-right.y*2) > -1){
			//voir si la case a gauche existe
			cases[2] = true;
			num_of_possib++;
		}

		if ((pos.x+right.x*2) < 5 && (pos.y+right.y*2) < 5){
			//voir si la case a droite existe
			cases[3] = true;
			num_of_possib++;
		}

		int count = 0;
		cases_possibilities = new Vector[num_of_possib];
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
		//bouge le pion a la nouvelle position newpos passee en argument
		if (color == white){
			if (pos.y == 4)
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
		plateau.setValue(pos, "p");
		click_num = 0;

		parent.getChildren().remove(pawnbutton);
		parent.add(pawnbutton , pos.x , pos.y);
	}

	public void enable(){
		//rend le pion jouable //configure une action
        pawnbutton.setOnAction(e -> Clicked());
	}
}