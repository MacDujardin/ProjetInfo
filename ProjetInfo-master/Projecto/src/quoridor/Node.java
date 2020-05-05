package quoridor;

public class Node{
	public Node parent;
	public Board grille;
	public Vector pos;
	public double gcost;
	public double hcost;
	public double cost;

	public Node(Board grille, PathFinder parent, Vector position, Node prec){
		this.parent = prec;
		this.grille = grille;
		pos = position;
		gcost = Math.distance(pos, parent.depart);
		hcost = Math.distance(pos, parent.arrivee);
		cost = gcost + hcost;
	}


	public Node(Board grille, PathFinder parent, Vector position){
		parent = null;
		this.grille = grille;
		pos = position;
		gcost = Math.distance(pos, parent.depart);
		hcost = Math.distance(pos, parent.arrivee);
		cost = gcost + hcost;
	}

	public boolean equals(Node node){
		if (pos.x == node.pos.x && pos.y == node.pos.y)
			return true;
		else
			return false;
	}

	public double get(){
		return cost;
	}

	public Node[] getTree(){
		int x = 2;
		Node[] tree = new Node[x];
		tree[0] = this;

		if (parent != null){
			Node[] parent_tree = parent.getTree();
			if (parent_tree.length > 1){
				x = x + parent_tree.length - 1;
				Node[] copy = tree;
				tree = new Node[x];
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