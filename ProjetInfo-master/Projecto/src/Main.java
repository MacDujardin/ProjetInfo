//author: Igor Dujardin

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
import quoridor.*;




public class Main extends Application  {

	Stage quoridor;
	Scene home, mode1v1 , modeIAhome , modeIAgood ,modeIAdumb ;
	public int i;
	public static int size = 17 ;
	static Board plateau;
	public static Pawn player1, player2;
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		quoridor = primaryStage;
		plateau= new Board(size);	
		
		Label label1 = new Label("Bienvenue sur Quoridor , veuillez choisir un mode de jeu");
		Button button1v1 = new Button("Joueur contre Joueur");
		Button buttonhomeIA = new Button("Joueur contre l'IA");
		button1v1.setOnAction(e -> quoridor.setScene(mode1v1));
		buttonhomeIA.setOnAction(e -> quoridor.setScene(modeIAhome));
		
		VBox layout1 = new VBox(20);
		layout1.getChildren().addAll(label1 , button1v1 , buttonhomeIA);
		home = new Scene (layout1 , 500 , 500);
		
		Button buttonhome = new Button("retour au menu");
		buttonhome.setOnAction(e -> quoridor.setScene(home));
		GridPane grid1v1 = new GridPane();
        grid1v1.setGridLinesVisible(true);
        
        for ( i = 0; i < size; i++) {
        	if (i%2 == 0) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / size);
            grid1v1.getColumnConstraints().add(colConst);
        	} 
        	else {
        		ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPercentWidth(20.0 / size);
                grid1v1.getColumnConstraints().add(colConst);
        	}
        }
        for ( i = 0; i < size; i++) {
        	if( i%2 == 0 ) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / size);
            grid1v1.getRowConstraints().add(rowConst);         
        }
        	else {
        		 RowConstraints rowConst = new RowConstraints();
                 rowConst.setPercentHeight(20.0 / size);
                 grid1v1.getRowConstraints().add(rowConst);  	
        	}
        }
        grid1v1.add(buttonhome, 1 , 0 ,1 ,1);
		BorderPane layout2 = new BorderPane();
		layout2.setCenter(grid1v1 );
		layout2.setTop(buttonhome);
		
		mode1v1 = new Scene (layout2 , 800 , 800);
		
		Label label3 = new Label("choisir une IA");
		Button buttonIAgood = new Button("Smart");
		Button buttonIAdumb = new Button("Dullard");
		buttonIAgood.setOnAction(e -> quoridor.setScene(modeIAgood));
		buttonIAdumb.setOnAction(e-> quoridor.setScene(modeIAdumb));
		
		VBox layout3 = new VBox(20);
		layout3.getChildren().addAll(label3 ,buttonIAgood , buttonIAdumb);
		modeIAhome = new Scene (layout3 , 500 , 500);
		
		Label label4 = new Label("Il est con");
		Button returnhome1 = new Button("Im out");
		returnhome1.setOnAction(e -> quoridor.setScene(home));
		
		VBox layout4 = new VBox();
		layout4.getChildren().addAll(label4 , returnhome1);
		modeIAdumb = new Scene(layout4 , 500 ,500);
		
		Label label5 = new Label("Malin");
		Button returnhome2 = new Button("Im out");
		returnhome2.setOnAction(e -> quoridor.setScene(home));
		
		VBox layout5 = new VBox();
		layout5.getChildren().addAll(label5 , returnhome2);
		modeIAgood = new Scene (layout5 ,500 ,500);

		//faire fonctionner le jeu ici
		player1 = new Pawn(grid1v1, plateau, new Vector((int)(size/2),size-1), "white", "Player");
		player2 = new Pawn(grid1v1, plateau, new Vector((int)(size/2),size-1), "white", "Player");
		
		quoridor.setScene(home);
		quoridor.setTitle("Quoridor");
		quoridor.show();
		
	}

	

}
