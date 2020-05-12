//author: Nathan Amorison

package quoridor;

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
		gcost = Math.distance(pos, parent.depart);
		hcost = Math.distance(pos, parent.arrivee);
		cost = gcost + hcost;
	}


	public PFNode(Board grille, PathFinder parent, Vector position){
		parent = null;
		this.grille = grille;
		pos = position;
		gcost = Math.distance(pos, parent.depart);
		hcost = Math.distance(pos, parent.arrivee);
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

	public PFNode[] getTree(){
		int x = 2;
		PFNode[] tree = new PFNode[x];
		tree[0] = this;

		if (parent != null){
			PFNode[] parent_tree = parent.getTree();
			if (parent_tree.length > 1){
				x = x + parent_tree.length - 1;
				PFNode[] copy = tree;
				tree = new PFNode[x];
				for (int i = 0; i < copy.length; i++)
					tree[i] = copy[i];
			}
			for (int i = x-1; i > -1; i--){
				tree[x-i] = parent_tree[x-i-1];
			}
		}

		return tree;
	}

	public Vector getVect(){
		return pos;
	}
}