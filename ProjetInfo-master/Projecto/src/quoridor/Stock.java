//author: Nathan Amorison
//based on https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane

package quoridor;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.scene.input.Dragboard;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.input.ClipboardContent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Stock extends GridPane{
    public GridPane parent;
    public Pawn player;
    public Wall vert;
    public Wall hori;
    public Label count;

    private int i_count = 1, i;
    private ArrayList<ArrayList<Rectangle>> tiles;
    private int x, y;
    private Board plateau;
    private Vector new_wall_pos;
    private Wall actif = null;
    private PathFinder p_path, e_path;

    public Stock(GridPane parent, Board plateau, BorderPane layout, ArrayList<ArrayList<Rectangle>> tiles){
        this.parent = parent;
        count = new Label("0");
        this.tiles = tiles;
        this.plateau = plateau;

        Wall wallh = new Wall(this, "Horizontal");
        wallh.setOnDragDetected((MouseEvent event) -> {
            if (i_count > 0){
                actif = wallh;
                Dragboard db = wallh.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(wallh.getImage());
                db.setContent(content);
            }
            event.consume();
        });

        Wall wallv = new Wall(this, "Vertical");
        wallv.setOnDragDetected((MouseEvent event) -> {
            if (i_count > 0){
                actif = wallv;
                Dragboard db = wallh.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putImage(wallv.getImage());
                db.setContent(content);
            }
            event.consume();
        });

        wallh.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                boolean found = false;
                if (i_count > 0){
                    if(actif == wallh){
                        if (!busy(x, y, "Horizontal")){
                            //placer un mur
                            for (i = 0; i < 3; i++){
                                plateau.setValue(new Vector(x+i, y), "w");
                            }
                            //verifier si chaque joueur peut gagner
                            for (i = 0; i < 17; i++){
                                if (player.color == "white"){
                                    p_path = new PathFinder(plateau, player.pos, new Vector(i, 0));
                                    e_path =new PathFinder(plateau, player.enemy.pos, new Vector(i, 17));
                                }
                                else{
                                    p_path = new PathFinder(plateau, player.pos, new Vector(i, 17));
                                    e_path =new PathFinder(plateau, player.enemy.pos, new Vector(i, 0));
                                }
                
                                if (p_path.run(null) != null && e_path.run(null) != null){
                                    //un chemin existe pour chaque pion, pour pouvoir gagner
                                    found = true;
                                    break;
                                }
                            }

                            //si NON: on retire le mur
                            if (!found)
                                for(i = 0; i < 3; i++)
                                    plateau.setValue(new Vector(x+i, y), null);
                            else
                                for(i = 0; i < 3; i++)
                                    tiles.get(x+i).get(y).setFill(Color.BROWN);
                        }
                    }
    
                    else if(actif == wallv){
                        if (!busy(x, y, "Vertical")){
                            for (i = 0; i < 3; i++){
                                //placer un mur
                            for (i = 0; i < 3; i++){
                                plateau.setValue(new Vector(x+i, y), "w");
                            }
                            //verifier si chaque joueur peut gagner
                            for (int i = 0; i < 17; i++){
                                if (player.color == "white"){
                                    p_path = new PathFinder(plateau, player.pos, new Vector(i, 0));
                                    e_path =new PathFinder(plateau, player.enemy.pos, new Vector(i, 17));
                                }
                                else{
                                    p_path = new PathFinder(plateau, player.pos, new Vector(i, 17));
                                    e_path =new PathFinder(plateau, player.enemy.pos, new Vector(i, 0));
                                }
                
                                if (p_path.run(null) != null && e_path.run(null) != null){
                                    //un chemin existe pour chaque pion, pour pouvoir gagner
                                    found = true;
                                    break;
                                }
                            }

                            //si NON: on retire le mur
                            if (!found)
                                for(i = 0; i < 3; i++)
                                    plateau.setValue(new Vector(x, y+i), null);
                            else
                                for(i = 0; i < 3; i++)
                                    tiles.get(x).get(y+i).setFill(Color.BROWN);
                            }
                        }
                    }
    
                    player.usingWall();
                    i_count = player.stock_count;
                    count.setText(Integer.toString(player.stock_count));
    
                    actif = null;
                }
                event.consume();
            }
        });


        parent.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //dragging walls over parent
                if(event.getGestureSource() != parent && event.getDragboard().hasImage() && i_count > 0){
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        parent.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //on Release on parent object
                if (i_count > 0){
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    Node node = (Node) event.getTarget();
                    if(db.hasImage()){
                        Integer cIndex = GridPane.getColumnIndex(node);
                        Integer rIndex = GridPane.getRowIndex(node);
                        x = cIndex == null ? 0 : cIndex;
                        y = rIndex == null ? 0 : rIndex;
                        success = true;
                    }
                    event.setDropCompleted(success);
    
                    event.consume();
                }
            }
        });

        this.add(wallh, 0, 0);
        this.add(new Label("  "), 1, 0);
        this.add(new Label("  "), 2, 0);
        this.add(count, 3, 0);
        this.add(new Label("  "), 4, 0);
        this.add(new Label("  "), 5, 0);
        this.add(wallv, 6, 0);
    }

    public void usingWall(){
        player.stock_count--;
    }

    public void show(Pawn player){
        this.player = player;
        i_count = player.stock_count;
        count.setText(Integer.toString(player.stock_count));
    }

    public boolean busy(int origin_x, int origin_y, String sens){
        String val = null;
        for (i = 0; i < 3; i++){
            if (sens == "Vertical"){
                //on recupere la valeur actuelle des 3 cases du mur et si on sort du plateau, alors on peut pas placer de mur
                if(origin_y%2==0){
                    if(origin_y + i > -1 && origin_y + i < 9)
                        val = plateau.getValue(new Vector(origin_x, origin_y + i));
                    else
                        return true;
                }
                else
                    return true;
            }
            else if (sens == "Horizontal"){
                //on recupere la valeur des 3 cases et si on sort du plateau, alors on peut pas placer de mur
                if(origin_x%2==0){
                    if(origin_x + i > -1 && origin_x + i < 9)
                        val = plateau.getValue(new Vector(origin_x + i, origin_y));
                    else
                        return true;
                }
                else
                    return true;
            }

            if ( val != null && val != "paw1win" &&  val != "pawn2win")
                return true; //si la case est occupee
        }

        return false;
    }
}