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

import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class Stock{
    public GridPane parent;
    public Pawn player;
    public Wall vert;
    public Wall hori;
    public GridPane stockpane;
    public Label count;

    public Stock(GridPane parent, Board plateau, BorderPane layout){
        this.parent = parent;
        stockpane = new GridPane();
        count = new Label("0");

        Wall wallh = new Wall(stockpane, plateau, new Vector(0, 0), "Horizontal");
        wallh.player = player;

        wallh.setOnDragDetected((MouseEvent event) -> {
            Dragboard db = wallh.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putImage(wallh.getImage());
            db.setContent(content);
            event.consume();
        });
        parent.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
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
        parent.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //The drag-and-drop gesture entered the target
                //show the user that it is an actual gesture target
                if(event.getGestureSource() != parent && event.getDragboard().hasImage()){
                    wallh.setVisible(false);
                    System.out.println("Drag entered");
                }
                event.consume();
            }
        });
        wallh.setOnDragOver(new EventHandler<DragEvent>(){
            public void handle(DragEvent event){
                if(event.getGestureSource()!= wallh && event.getDragboard().hasImage())
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                event.consume();
            }
        });
        parent.setOnDragExited(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //mouse moved away, remove graphical cues
                wallh.setVisible(true);
                parent.setOpacity(1);

                event.consume();
            }
        });
        parent.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //Data dropped
                //If there is an image on the dragboard, read it and use it
                Dragboard db = event.getDragboard();
                boolean success = false;
                if(db.hasImage()){
                    //target.setText(db.getImage()); --- must be changed to target.add(source, col, row)
                    //target.add(source, 5, 5, 1, 1);
                    //Places at 0,0 - will need to take coordinates once that is implemente
                    success = true;
                }
                //let the source know whether the image was successfully transferred and used
                event.setDropCompleted(success);

                event.consume();
            }
        });
        wallh.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                //the drag and drop gesture has ended
                //if the data was successfully moved, clear it
                if(event.getTransferMode() == TransferMode.MOVE){
                    wallh.setVisible(false);
                }
                event.consume();
            }
        });

        /*Wall wallv = new Wall(parent, plateau, new Vector(8, 10), "Vertical");
        wallv.setOnAction(null);
        stockpane.add(wallh, 0, 2 );*/

        Button wh = new Button("nsm");
        wh.setOnAction(null);

        stockpane.add(wh, 0, 0);
        stockpane.add(count, 3,1);

        layout.setBottom(stockpane);
    }

    public void usingWall(){
        player.stock_count--;
    }

    public void show(Pawn player){
        this.player = player;
        count.setText(Integer.toString(player.stock_count));
    }
}