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

	private boolean busy(){
		String val= null;
		for (int i = 0; i < 3; i++){
			if (sens == "Vertical"){
				//on r�ｾ�ｽｩcup�ｾ�ｽｨre la valeur actuelle des 3 cases du mur
				val = plateau.getValue(new Vector(pos.x, pos.y + i));
			}
			else if (sens == "Horizontal"){
				//on r�ｾ�ｽｩcup�ｾ�ｽｨre la valeur des 3cases
				val = plateau.getValue(new Vector(pos.x + i, pos.y));
			}

			if ( val != null && val != "bw" &&  val != "ww")
				return true; //si la case est d�ｾ�ｽｩj�ｾ�ｿｽ occup�ｾ�ｽｩe
		}

		return false;
	}

	public String getSens(){
		return sens;
	}

	/*private void enable(){

	}

	private void disable(){
		//fait en sorte que l'objet ne soit visiblement plus utilisable
	}

	private void place(){
		//place le mur sur pos
	}*/
}