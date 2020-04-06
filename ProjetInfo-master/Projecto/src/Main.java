
public class Main implements Runnable {

	GameGUI gui = new GameGUI();
	
	
	public static void main(String[] args) {
		new Thread(new Main()).start();
		
	}

	@Override
	public void run() {
		while(true) {
			gui.repaint();
		}
	}

}
