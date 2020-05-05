//author: Nathan Amorison

package quoridor

public class Stock{
	public parent;
	private int stock;
	public Stock(parent, int n){
		this.parent = parent;
		stock = n;
	}

	public void usingWall(){
		stock--;
		//set visuellement le stock (le label)
	}
}