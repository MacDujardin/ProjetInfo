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

	public Wall(GridPane parent, String sens){
		this.parent = parent;
		this.sens = sens;

		Image graphic = null;

		URL imageURL = null;

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

		setImage(graphic);
		setVisible(true);
	}
}