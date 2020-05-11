//author: Igor Dujardin
//code basé sur une recherche internet: https://stackoverflow.com/questions/41088095/javafx-drag-and-drop-to-gridpane
package quoridor;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;

public class DragDrop {
	


public GridPane TheWalls;
public Button ReadyButton;
public Button QuitButton;
public ImageView[][] water;
public ImageView[] walls;
public ImageView[][] walls2d;

public void initialize(GridPane board){
    
    //Adds walls
    walls = new ImageView[2];
    walls[0] = new ImageView("");
    walls[1] = new ImageView("");
 
    for(int i=0; i < 1; i++){
        walls[i].setPreserveRatio(true);
    }
    walls[0].setFitWidth(80);
    walls[1].setFitWidth(80);
    //TheWalls.add(walls[0], 0, 0);
    TheWalls.add(walls[1], 0, 1);
    TheWalls.add(walls[2], 0, 2);

    //First attempt at drag and drop
    ImageView source = new ImageView ("walls/ship2.png");
    source.setPreserveRatio(true);
    source.setFitWidth(80);
    TheWalls.add(source, 0, 0);
    final GridPane target = board;

    //Drag detected event handler is used for adding drag functionality to the boat node
    source.setOnDragDetected(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            //Drag was detected, start drap-and-drop gesture
            //Allow any transfer node
            Dragboard db = source.startDragAndDrop(TransferMode.ANY);

            //Put ImageView on dragboard
            ClipboardContent cbContent = new ClipboardContent();
            cbContent.putImage(source.getImage());
            //cbContent.put(DataFormat.)
            db.setContent(cbContent);
            source.setVisible(false);
            event.consume();
        }
    });

    //Drag over event handler is used for the receiving node to allow movement
    target.setOnDragOver(new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            //data is dragged over to target
            //accept it only if it is not dragged from the same node
            //and if it has image data
            if(event.getGestureSource() != target && event.getDragboard().hasImage()){
                //allow for moving
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        }
    });

    //Drag entered changes the appearance of the receiving node to indicate to the player that they can place there
    target.setOnDragEntered(new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            //The drag-and-drop gesture entered the target
            //show the user that it is an actual gesture target
            if(event.getGestureSource() != target && event.getDragboard().hasImage()){
                source.setVisible(false);
                target.setOpacity(0.7);
                System.out.println("Drag entered");
            }
            event.consume();
        }
    });

    //Drag exited reverts the appearance of the receiving node when the mouse is outside of the node
    target.setOnDragExited(new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            //mouse moved away, remove graphical cues
            source.setVisible(true);
            target.setOpacity(1);

            event.consume();
        }
    });

    //Drag dropped draws the image to the receiving node
    target.setOnDragDropped(new EventHandler<DragEvent>() {
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

    source.setOnDragDone(new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            //the drag and drop gesture has ended
            //if the data was successfully moved, clear it
            if(event.getTransferMode() == TransferMode.MOVE){
                source.setVisible(false);
            }
            event.consume();
        }
    });
}
}