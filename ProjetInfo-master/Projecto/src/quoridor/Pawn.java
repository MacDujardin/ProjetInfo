package quoridor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

	public int stock_count = 8;
	public GridPane parent;
	public Vector pos;
	public String color;
	public Pawn enemy;
	public Board plateau;// = Main.plateau;

	public Pawn(GridPane parent, Vector pos, String color, boolean playable){
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
        Image whitep = new Image("file:Black.png");
        Image blackp = new Image("file:Black.png");
        Button pawnbutton = new Button();
        parent.add(pawnbutton , pos.x , pos.y);
        pawnbutton.setOnAction(Clicked());
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

			Vector[] sub_list = null;
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
}