import javax.swing.*;
import java.util.*;
import java.awt.*;

public class GameGUI extends JFrame {
	
	int spacing =5;
	
      public GameGUI() {
    	  this.setTitle("Projo");
    	  this.setSize(1286 ,829);
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setVisible(true);
    	  this.setResizable(false);
    	  
    	  Board board = new Board();
    	  this.setContentPane(board);
    	  
      }
      
      public class Board extends JPanel {
    	  public void paintComponent(Graphics x) {
    		  x.setColor(Color.DARK_GRAY);
    		  x.fillRect(0 , 0 , 1280 , 800 );
    		  x.setColor(Color.gray);
    		  for (int i=0 ; i < 9 ; i++) {
    			  for (int j= 0 ; j < 9; j++) {
    				  x.fillRect(spacing+i*80+280, spacing+j*80+40, 80-2*spacing, 80-2*spacing);
    				
    			  }
    		  }
    		  x.setColor(Color.red);
    		  x.fillRect(240, 0, 10 , 800);
    		  x.fillRect(1030, 0, 10,800);
    		
    		  
    	  }
      }
}
