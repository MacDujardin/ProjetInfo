package quoridor;

import java.util.ArrayList;

public class PathFinder {
	private Vector up = new Vector(0, -1);
	public Vector down = new Vector(0, 1);
	private final Vector right = new Vector(1, 0);
	private final Vector left = new Vector(-1, 0);

	public Board grille;
	public PFNode actif_node;
	public Vector depart;
	public Vector arrivee;
	public ArrayList<PFNode> nodelist;

	public PathFinder(Board parent, Vector depart, Vector arrivee){
		grille = parent;
		this.depart = depart;
		this.arrivee = arrivee;
		nodelist = new ArrayList<PFNode>();
	}

	public ArrayList<PFNode> run(PFNode pos){
		boolean found = false;
		boolean stop = false;
		Vector true_pos;
		PFNode dep_node;

		ArrayList<PFNode> local_node_list = new ArrayList<PFNode>();
		if (pos == null){
			true_pos = depart;
			dep_node = new PFNode(grille,this ,depart);
			nodelist.add(dep_node);
			local_node_list.add(dep_node);
		}
		else
			true_pos = pos.getVect();

		ArrayList<Vector> possibilities = possibilities(true_pos);
		for (Vector sens: possibilities){
			PFNode node;
			if (nodelist.size() == 0)
				node = new PFNode(grille, this, true_pos.add(sens));
			else{
				if (pos == null)
					node = new PFNode(grille, this, true_pos.add(sens), nodelist.get(0));
				else
					node = new PFNode(grille, this, true_pos.add(sens), pos);
			}

			boolean exist = false;
			for (PFNode n: nodelist){
				if (node.getVect().get().equals(n.getVect().get())){
					exist = true;
					break;
				}
			}

			if (exist == false){
				nodelist.add(node);
				local_node_list.add(node);
				if (node.hcost == 0){
					found = true;
					break;
				}
			}
		}

		if (local_node_list.size() == 0){
			stop = true;
		}

		if (!stop){
			if (!found){
				if (local_node_list.size() != 0){
					PFNode less = null;
					for (int a = 0; a < local_node_list.size(); a++){
						if (less == null){
							less = local_node_list.get(a);
						}
						else if (less.hcost > local_node_list.get(a).hcost) {
							less = local_node_list.get(a);
						}
					}

					ArrayList<PFNode> l = run(less);

					if (l != null){
						return l;
					}
				}
				else
					return null;
			}
			else{
				return nodelist.get(nodelist.size()-1).getTree();
			}
		}
		return null;
	}
	
	public ArrayList<Vector> possibilities(Vector pos) {
		ArrayList<Vector> possibilities_list = new ArrayList<Vector>();


		if (pos.add(up).y > -1 && pos.add(up).y < 9  && pos.add(up).x > -1 && pos.add(up).x < 9){
			if (grille.getValue(pos, up) != "w"){
				if (pos.add(up.mul(2)).y > -1 && pos.add(up.mul(2)).y < 9 && pos.add(up.mul(2)).x > -1 && pos.add(up.mul(2)).y < 9){
					if (grille.getValue(pos, up.mul(2)) == "p"){
						if (pos.add(up.mul(3)).y > -1 && pos.add(up.mul(3)).y < 9 && pos.add(up.mul(3)).x > -1 && pos.add(up.mul(3)).x < 9){
							if (grille.getValue(pos, up.mul(3)) != "w")
								if (pos.add(up.mul(4)).y > -1 && pos.add(up.mul(4)).y < 9 && pos.add(up.mul(4)).x > -1 && pos.add(up.mul(4)).y < 9)
									possibilities_list.add(up.mul(4));
							else{
								if (pos.add(up.mul(2)).add(left).y > -1 && pos.add(up.mul(2)).add(left).y < 9 && pos.add(up.mul(2)).add(left).x > -1 && pos.add(up.mul(2)).add(left).x <9 )
									if (grille.getValue(pos, up.mul(2).add(left)) != "w")
										if (pos.add(up.mul(2)).add(left.mul(2)).y > -1 && pos.add(up.mul(2)).add(left.mul(2)).y <9  && pos.add(up.mul(2)).add(left.mul(2)).x > -1 && pos.add(up.mul(2)).add(left.mul(2)).x <9)
											possibilities_list.add(up.mul(4));
								if (pos.add(up.mul(2)).add(right).y > -1 && pos.add(up.mul(2)).add(right).y <9  && pos.add(up.mul(2)).add(right).x > -1 && pos.add(up.mul(2)).add(right).x <9 )
									if (grille.getValue(pos, up.mul(2).add(right)) != "w")
										if (pos.add(up.mul(2)).add(right.mul(2)).y > -1 && pos.add(up.mul(2)).add(right.mul(2)).y <9  && pos.add(up.mul(2)).add(right.mul(2)).x > -1 && pos.add(up.mul(2)).add(right.mul(2)).x <9)
											possibilities_list.add(up.mul(4));
							}
						}
					}
					else{
						possibilities_list.add(up.mul(2));
					}
				}
			}
		}

		if (pos.add(down).y > -1 && pos.add(down).y < 9 && pos.add(down).x > -1 && pos.add(down).x <9 ){
			if (grille.getValue(pos, down) != "w"){
				if (pos.add(down.mul(2)).y > -1 && pos.add(down.mul(2)).y < 9 && pos.add(down.mul(2)).x > -1 && pos.add(down.mul(2)).y <9 ){
					if (grille.getValue(pos, down.mul(2)) == "p"){
						if (pos.add(down.mul(3)).y > -1 && pos.add(down.mul(3)).y < 9 && pos.add(down.mul(3)).x > -1 && pos.add(down.mul(3)).x <9 ){
							if (grille.getValue(pos, down.mul(3)) != "w")
								if (pos.add(down.mul(4)).y > -1 && pos.add(down.mul(4)).y < 9 && pos.add(down.mul(4)).x > -1 && pos.add(down.mul(4)).y <9 )
									possibilities_list.add(down.mul(4));
							else{
								if (pos.add(down.mul(2)).add(left).y > -1 && pos.add(down.mul(2)).add(left).y < 9 && pos.add(down.mul(2)).add(left).x > -1 && pos.add(down.mul(2)).add(left).x <9 )
									if (grille.getValue(pos, down.mul(2).add(left)) != "w")
										if (pos.add(down.mul(2)).add(left.mul(2)).y > -1 && pos.add(down.mul(2)).add(left.mul(2)).y <9  && pos.add(down.mul(2)).add(left.mul(2)).x > -1 && pos.add(down.mul(2)).add(left.mul(2)).x <9)
											possibilities_list.add(down.mul(4));
								if (pos.add(down.mul(2)).add(right).y > -1 && pos.add(down.mul(2)).add(right).y < 9 && pos.add(down.mul(2)).add(right).x > -1 && pos.add(down.mul(2)).add(right).x < 9)
									if (grille.getValue(pos, down.mul(2).add(right)) != "w")
										if (pos.add(down.mul(2)).add(right.mul(2)).y > -1 && pos.add(down.mul(2)).add(right.mul(2)).y <9  && pos.add(down.mul(2)).add(right.mul(2)).x > -1 && pos.add(down.mul(2)).add(right.mul(2)).x <9)
											possibilities_list.add(down.mul(4));
							}
						}
					}
					else
						possibilities_list.add(down.mul(2));
				}
			}
		}

		if (pos.add(right).y > -1 && pos.add(right).y <9  && pos.add(right).x > -1 && pos.add(right).x <9 ){
			if (grille.getValue(pos, right) != "w"){
				if (pos.add(right.mul(2)).y > -1 && pos.add(right.mul(2)).y <9  && pos.add(right.mul(2)).x > -1 && pos.add(right.mul(2)).y <9 ){
					if (grille.getValue(pos, right.mul(2)) == "p"){
						if (pos.add(right.mul(3)).y > -1 && pos.add(right.mul(3)).y < 9 && pos.add(right.mul(3)).x > -1 && pos.add(right.mul(3)).x < 9){
							if (grille.getValue(pos, right.mul(3)) != "w")
								if (pos.add(right.mul(4)).y > -1 && pos.add(right.mul(4)).y < 9 && pos.add(right.mul(4)).x > -1 && pos.add(right.mul(4)).y <9)
									possibilities_list.add(right.mul(4));
							else{
								if (pos.add(right.mul(2)).add(up).y > -1 && pos.add(right.mul(2)).add(up).y <9  && pos.add(right.mul(2)).add(up).x > -1 && pos.add(right.mul(2)).add(up).x <9 )
									if (grille.getValue(pos, right.mul(2).add(up)) != "w")
										if (pos.add(right.mul(2)).add(up.mul(2)).y > -1 && pos.add(right.mul(2)).add(up.mul(2)).y <  9&& pos.add(right.mul(2)).add(up.mul(2)).x > -1 && pos.add(right.mul(2)).add(up.mul(2)).x <9)
											possibilities_list.add(right.mul(4));
								if (pos.add(right.mul(2)).add(down).y > -1 && pos.add(right.mul(2)).add(down).y <9  && pos.add(right.mul(2)).add(down).x > -1 && pos.add(right.mul(2)).add(down).x < 9)
									if (grille.getValue(pos, right.mul(2).add(down)) != "w")
										if (pos.add(right.mul(2)).add(down.mul(2)).y > -1 && pos.add(right.mul(2)).add(down.mul(2)).y < 9 && pos.add(right.mul(2)).add(down.mul(2)).x > -1 && pos.add(right.mul(2)).add(down.mul(2)).x <97)
											possibilities_list.add(right.mul(4));
							}
						}
					}
					else
						possibilities_list.add(right.mul(2));
				}
			}
		}

		if (pos.add(left).y > -1 && pos.add(left).y < 9 && pos.add(left).x > -1 && pos.add(left).x < 9){
			if (grille.getValue(pos, left) != "w"){
				if (pos.add(left.mul(2)).y > -1 && pos.add(left.mul(2)).y < 9 && pos.add(left.mul(2)).x > -1 && pos.add(left.mul(2)).y <9 ){
					if (grille.getValue(pos, left.mul(2)) == "p"){
						if (pos.add(left.mul(3)).y > -1 && pos.add(left.mul(3)).y < 9 && pos.add(left.mul(3)).x > -1 && pos.add(left.mul(3)).x <9 ){
							if (grille.getValue(pos, left.mul(3)) != "w")
								if (pos.add(left.mul(4)).y > -1 && pos.add(left.mul(4)).y < 9 && pos.add(left.mul(4)).x > -1 && pos.add(left.mul(4)).y < 9)
									possibilities_list.add(left.mul(4));
							else{
								if (pos.add(left.mul(2)).add(right).y > -1 && pos.add(left.mul(2)).add(right).y <9  && pos.add(left.mul(2)).add(right).x > -1 && pos.add(left.mul(2)).add(right).x < 9)
									if (grille.getValue(pos, left.mul(2).add(right)) != "w")
										if (pos.add(left.mul(2)).add(right.mul(2)).y > -1 && pos.add(left.mul(2)).add(right.mul(2)).y < 9 && pos.add(left.mul(2)).add(right.mul(2)).x > -1 && pos.add(left.mul(2)).add(right.mul(2)).x <9)
											possibilities_list.add(left.mul(4));
								if (pos.add(left.mul(2)).add(down).y > -1 && pos.add(left.mul(2)).add(down).y < 9 && pos.add(left.mul(2)).add(down).x > -1 && pos.add(left.mul(2)).add(down).x < 9)
									if (grille.getValue(pos, left.mul(2).add(down)) != "w")
										if (pos.add(left.mul(2)).add(down.mul(2)).y > -1 && pos.add(left.mul(2)).add(down.mul(2)).y <9 && pos.add(left.mul(2)).add(down.mul(2)).x > -1 && pos.add(left.mul(2)).add(down.mul(2)).x <9)
											possibilities_list.add(left.mul(4));
							}
						}
					}
					else
						possibilities_list.add(left.mul(2));
				}
			}
		}
		return possibilities_list;
	}
}
