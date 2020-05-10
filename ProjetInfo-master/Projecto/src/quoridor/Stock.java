//author: Nathan Amorison

package quoridor;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class Stock{
    public BorderPane parent;
    public Pawn player;
    public Wall vert;
    public Wall hori;
    public GridPane stockpane

    public Stock(BorderPane parent, int n){
        this.parent = parent;
        tockpane = new GridPane();

        Button wallh = new Button();
        wallh.setOnAction(null);
        Button wallv = new Button();
        wallv.setOnAction(null);

        stockpane.add(wallv, 0, 0);
        stockpane.add(wallh, 0, 2 );

        parent.setBottom(stockpane);
    }

    public void usingWall(){
        player.stock--;
    }
}