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

public class TestPawn extends Application{
	Stage quoridor;
	Scene window;
	public static int size = 5;
	static Board plateau;
	int i;
	public static Pawn player1;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		quoridor = primaryStage;
		plateau = new Board(size);

		GridPane game_window = new GridPane();
		game_window.setGridLinesVisible(true);

		for(i = 0; i < size; i++){
			if(i%2 == 0){
				ColumnConstraints colConst = new ColumnConstraints();
				colConst.setPercentWidth(100.0/size);
				game_window.getColumnConstraints().add(colConst);
			}
			else{
				ColumnConstraints colConst = new ColumnConstraints();
				colConst.setPercentWidth(20.0/size);
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

        BorderPane layout = new BorderPane();
        layout.setCenter(game_window);

        window = new Scene(layout, 800, 800);

        player1 = new Pawn(game_window, plateau, new Vector((int)(size/2),size-1), "white", "Player");

        quoridor.setScene(window);
        quoridor.setTitle("Quoridor - TEST Pawn");
        quoridor.show();
	}
}