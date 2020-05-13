//author: Nathan Amorison

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

public class Stock extends GridPane{
    public GridPane parent;
    public Pawn player;
    public Wall vert;
    public Wall hori;
    public Label count;

    private Vector new_wall_pos;

    public Stock(GridPane parent, Board plateau, BorderPane layout){
        this.parent = parent;
        count = new Label("0");

        Wall wallh = new Wall(this, plateau, new Vector(), "Horizontal");

        wallh.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Drag detected wallh");
            Dragboard db = wallh.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(wallh.getImage());
            db.setContent(content);
            event.consume();
        });
        parent.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //The drag-and-drop gesture entered the target
                //show the user that it is an actual gesture target
                if(event.getGestureSource() != parent && event.getDragboard().hasImage()){
                    //wallh.setVisible(false);
                    System.out.println("Drag entered wallh");
                }
                event.consume();
            }
        });
        wallh.setOnDragOver(new EventHandler<DragEvent>(){
            public void handle(DragEvent event){
                if(event.getGestureSource()!= wallh && event.getDragboard().hasImage())
                    System.out.println("Drag over wallh");
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        parent.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("Drag exit wallh");
                //mouse moved away, remove graphical cues
                /*wallh.setVisible(true);
                parent.setOpacity(1);*/

                event.consume();
            }
        });
        wallh.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //the drag and drop gesture has ended
                //if the data was successfully moved, clear it
                if(event.getTransferMode() == TransferMode.MOVE){
                    Wall w = new Wall(parent, plateau, new_wall_pos, "Horizontal");

                    parent.add(w, new_wall_pos.x, new_wall_pos.y);
                }
                System.out.println("Drag done wallh");
                event.consume();
            }
        });

        Wall wallv = new Wall(this, plateau, new Vector(), "Vertical");

        wallv.setOnDragDetected((MouseEvent event) -> {
            System.out.println("Drag detected wallv");
            Dragboard db = wallh.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(wallv.getImage());
            db.setContent(content);
            event.consume();
        });
        parent.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //The drag-and-drop gesture entered the target
                //show the user that it is an actual gesture target
                if(event.getGestureSource() != parent && event.getDragboard().hasImage()){
                    //wallv.setVisible(false);
                    System.out.println("Drag entered wallv");
                }
                event.consume();
            }
        });
        wallv.setOnDragOver(new EventHandler<DragEvent>(){
            public void handle(DragEvent event){
                if(event.getGestureSource()!= wallv && event.getDragboard().hasImage())
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        parent.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //mouse moved away, remove graphical cues
                /*wallv.setVisible(true);
                parent.setOpacity(1);*/

                event.consume();
            }
        });
        wallv.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //the drag and drop gesture has ended
                //if the data was successfully moved, clear it
                if(event.getTransferMode() == TransferMode.MOVE){
                    Wall w = new Wall(parent, plateau, new_wall_pos, "Vertical");

                    parent.add(w, new_wall_pos.x, new_wall_pos.y);
                }
                event.consume();
            }
        });


        parent.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("Drag running");
                //data is dragged over to target
                //accept it only if it is not dragged from the same node
                //and if it has image data
                if(event.getGestureSource() != parent && event.getDragboard().hasImage()){
                    //allow for moving
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            }
        });
        parent.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //Data dropped
                //If there is an image on the dragboard, read it and use it
                Dragboard db = event.getDragboard();
                boolean success = false;
                Node node = (Node) event.getTarget();

                if (node != parent) {
                    Node p;
                    while ((p = node.getParent()) != parent) {
                        node = p;
                    }
                }

                System.out.println(node);
                if(db.hasImage()){//} && node == parent){
                    //getVectFromNode(node);
                    //System.out.println(node.getColumnConstraints());
                    //System.out.println(parent.getColumnConstraints());
                    Integer cIndex = GridPane.getColumnIndex(node);
                    Integer rIndex = GridPane.getRowIndex(node);
                    int x = cIndex == null ? 0 : cIndex;
                    int y = rIndex == null ? 0 : rIndex;
                    System.out.println("Drag dropped");

                    System.out.println(cIndex + ","+rIndex);

                    if (x%2!=0 && y%2!=0){
                        new_wall_pos = new Vector(x, y);
                        success = true;
                    }
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(success);

                event.consume();
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
        count.setText(Integer.toString(player.stock_count));
    }

    private void getVectFromNode(Node node1){
        for (Node node: parent.getChildren())
            if(GridPane.getColumnIndex(node) == 2 && GridPane.getRowIndex(node) == 2)
                System.out.println("2,2");
            else
                System.out.println(GridPane.getColumnIndex(node));
    }
}