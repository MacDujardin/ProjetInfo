//author: Igor Dujardin

package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Possibilitie{ //extends {
    public GridPane parent;
    public Pawn pion;
    public Vector dir;
    private Button possibuton;

    public Possibilitie(GridPane parent, Pawn pion, Vector vect){
        //Constructeur d'une case signifiant que le pion (pion) peut se dÃ©placer vers sa position (vect)/
        this.parent = parent;
        this.pion = pion;
        dir = vect;
        possibuton = new Button();
        possibuton.setOpacity(100);
        possibuton.setMinWidth(20.0);
        possibuton.setMinHeight(20.0);
        possibuton.setStyle("-fx-background-color: #00ff00");
        parent.add(possibuton ,pion.pos.x+ vect.x ,pion.pos.y+vect.y);
        possibuton.setOnAction(e -> makemove());
    }
    
        //si clique, le pion bouge vers la position de la case Possibilitie choisie
    public EventHandler<ActionEvent> makemove(){
        //fait bouger le pion quand la case Possibilitie est cliquﾃｩe
        Vector newpos = pion.pos.add(dir);

        if (Math.isPair(newpos.x) && Math.isPair(newpos.y)){
            pion.move(newpos);
            pion.destroyPossibilities();
        }
        return null;
    }

    public void destroy(){
        parent.getChildren().remove(possibuton);
    }

}