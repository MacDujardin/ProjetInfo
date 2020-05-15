//author: Nathan Amorison

package quoridor;
import java.util.ArrayList;

public class PFNode{
	public Vector pos;
	
	public PFNode parent;
	public Board grille;
	public double gcost;
	public double hcost;
	public double cost;

	public PFNode(Board grille, PathFinder parent, Vector position, PFNode prec){
		this.parent = prec;
		this.grille = grille;
		pos = position;
		gcost = MMath.distance(pos, parent.depart);
		hcost = MMath.distance(pos, parent.arrivee);
		cost = gcost + hcost;
	}


	public PFNode(Board grille, PathFinder parent, Vector position){
		this.parent = null;
		this.grille = grille;
		pos = position;
		gcost = MMath.distance(pos, parent.depart);
		hcost = MMath.distance(pos, parent.arrivee);
		cost = gcost + hcost;
	}

	public boolean equals(PFNode node){
		if (pos.x == node.pos.x && pos.y == node.pos.y)
			return true;
		else
			return false;
	}

	public double get(){
		return cost;
	}

	public ArrayList<PFNode> getTree(){
		ArrayList<PFNode> tree = new ArrayList<PFNode>();
		tree.add(this);

		if (parent != null){
			for (PFNode n: parent.getTree())
				tree.add(n);
		}
		return tree;
	}

	public Vector getVect(){
		return pos;
	}
}