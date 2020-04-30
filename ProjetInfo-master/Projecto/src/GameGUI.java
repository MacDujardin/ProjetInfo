import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GameGUI extends JFrame {
	
	int spacing =5;
	
	int mx = -100;
	int my = -100;
	
      public GameGUI() {
    	  this.setTitle("Projo");
    	  this.setSize(1286 ,829);
    	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	  this.setVisible(true);
    	  this.setResizable(false);
    	  
    
    	  
    	  Move move = new Move();
    	  this.addMouseMotionListener(move);
    	  
    	  Click click = new Click();
    	  this.addMouseListener(click);
    	  
      }
      
      
      
      public class Move implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
		
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		mx=e.getX();
		my=e.getY();
			System.out.println(mx +" et " +my);
		}
    	  
      }
      public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
    	  
      }
}

