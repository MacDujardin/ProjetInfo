//author: Nathan Amorison

package quoridor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.net.URL;

public class Wall extends ImageView{
	private GridPane parent;
	private String sens;
	private Board plateau;

	public Vector pos;

	public Wall(GridPane parent, Board plateau, Vector position, String sens){
		this.parent = parent;
		this.sens = sens;
		this.plateau = plateau;

		Image graphic = null;

		URL imageURL = null;

		System.out.println(sens);
		if (sens == "Vertical"){
			//creation du visuel pour un mur vertical
			imageURL = getClass().getResource("images\\wallv.png");

			setFitWidth(10);
			setFitHeight(50);
		}
		else if (sens == "Horizontal"){
			//creation du visuel pour un mur horizontal
			imageURL = getClass().getResource("images\\wallh.png");

			setFitWidth(50);
			setFitHeight(10);
		}

		graphic = new Image(imageURL.toExternalForm());

		System.out.println("Loading Image ERROR: " + graphic.isError());

		setImage(graphic);

		setVisible(true);

		pos = position;
	}
}