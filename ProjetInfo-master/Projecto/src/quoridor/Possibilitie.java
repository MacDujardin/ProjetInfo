package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Possibilitie{ //extends {
	public GridPane parent;
	public Pawn pion;
	public Vector dir;

	public Possibilitie(GridPane parent, Pawn pion, Vector vect){
        //Constructeur d'une case signifiant que le pion (pion) peut se dÃ©placer vers sa position (vect)/
        this.parent = parent;
        this.pion = pion;
        dir = vect;
        Button possibuton = new Button();
        parent.add(possibuton ,pion.pos.x+ vect.x ,pion.pos.y+vect.y);
      possibuton.setOnAction(makemove(null));

    }
	
		//si cliquﾃｩ, le pion bouge vers la position de la case Possibilitie choisie
	

	public EventHandler<ActionEvent> makemove(ActionEvent event){ //ﾃｩvﾃｩnement /!\
        //fait bouger le pion quand la case Possibilitie est cliquﾃｩe
        Vector newpos = pion.pos.add(dir);

        if (Math.isPair(newpos.y) || Math.isPair(newpos.y)){
            pion.move(newpos);
            pion.destroyPossibilities();
        }
        return null;

    }

}