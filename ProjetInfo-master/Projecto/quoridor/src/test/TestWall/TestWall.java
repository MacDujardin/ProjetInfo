//author: Nathan Amorison

import quoridor.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class TestWall extends Application{
    Stage quoridor;
    Scene window;
    public int i;
    public static int size = 9;
    static Board plateau;
    Pawn player1, player2;

    ArrayList<ArrayList<Rectangle>> tiles = new ArrayList<ArrayList<Rectangle>>();
 
    public static void main(String[] args){
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception{
        quoridor = primaryStage;
        plateau = new Board(size);
  
        GridPane game_window = new GridPane();
        game_window.setGridLinesVisible(true);


 
        for ( i = 0; i < size; i++) {
            if (i%2 == 0) {
    	        ColumnConstraints colConst = new ColumnConstraints();
    	        colConst.setPercentWidth(100.0 / size);
    	        game_window.getColumnConstraints().add(colConst);
           } 
           else {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(20.0 / size);
                game_window.getColumnConstraints().add(colConst);
           }
        }
        for ( i = 0; i < size; i++) {
            if( i%2 == 0 ) {
                RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(100.0 / size);
                game_window.getRowConstraints().add(rowConst);         
            }
            else {
            	RowConstraints rowConst = new RowConstraints();
                rowConst.setPercentHeight(20.0 / size);
                game_window.getRowConstraints().add(rowConst);  	
            }
        }
 
        for (i = 0; i < size; i++){
            tiles.add(new ArrayList<Rectangle>());
            for (int j = 0; j < size; j++){
                Rectangle rect = null;
                if(i%2 == 0 && j%2 != 0)
                    rect = new Rectangle(0,0,20,5);
                else if(i%2 != 0 && j%2 == 0)
                    rect = new Rectangle(0,0,5,20);
                else if(i%2 != 0 && j%2 != 0)
                    rect = new Rectangle(0,0,5,5);

                if (rect != null)
                    game_window.add(rect, i, j, 1, 1);

                tiles.get(i).add(rect);
            }
        }

        BorderPane layout = new BorderPane();
        layout.setLeft(game_window);
 
        window = new Scene(layout, 400, 400);


        
        //GridPane test = new GridPane();
 
        Stock stock = new Stock(game_window, plateau, layout, tiles);
 
        player1 = new Pawn(game_window, plateau, new Vector((int)(size/2),size-1), "white", "Player");
        player2 = new Pawn(game_window, plateau, new Vector((int)(size/2),0), "black", "Player");
        player1.enemy = player2;
        player1.stock = stock;
        player2.enemy = player1;
        player2.stock = stock;
 
        player1.enable();

        //stock.show(player1);

        /*Wall test_h = new Wall(test, plateau, new Vector(), "Horizontal");
        Wall test_v = new Wall(test, plateau, new Vector(), "Vertical");

        test.add(test_h, 0, 0);
        test.add(test_v, 0, 0);*/

        layout.setRight(stock);
 
        quoridor.setScene(window);
        quoridor.setTitle("Quoridor - TEST Stock + Wall");
        quoridor.show();
    }
}