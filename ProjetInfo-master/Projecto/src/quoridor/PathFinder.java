package quoridor;

public class PathFinder(){
	private Vector up = new Vector(0, -1);
	public Vector down = new Vector(0, 1);
	private final Vector right = new Vector(1, 0);
	private final Vector left = new Vector(-1, 0);
	private int x = 1;

	public Board grille;
	public Node actif_node;
	public Vector depart;
	public Vector arrivee;
	public Node [] nodelist;

	public static PathFinder(Board parent, Vector depart, Vector arrivee){
		grille = parent;
		this.depart = depart;
		this.arrivee = arrivee;
		nodelist = new Node[x];
	}

	public static Node[] run(Node pos){
		boolean found = false;
		boolean stop = false;
		Node true_pos = null;
		Node dep_node;
		if (pos != null){
			true_pos = depart;
			dep_node = new Node(grille, null);
			nodelist[0] = dep_node;
		}

		Node [] local_node_list = new Node[x];

		Node [] possibilities = possibilities(true_pos);
		for (int i = 0; i < possibilities.length(); i++){
			Node sens = possibilities[i];

			Node node = Node(grille, this, true_pos.add(sens));

			boolean exist = false;
			for (int j = 0; j < nodelist.length(); j++){
				if (node.get() == nodelist[j].get())
					exist = true;
			}

			if (exist == false){
				nodelist[i] = node;
				local_node_list[i] = node;
				if (node.hcost == 0){
					found = true;
					break;
				}
			}
		}

		if (local_node_list.length() == 0)
			stop = true;

		if (stop == false){
			if (found == false){
				if (local_node_list.length() != 0){
					for (int i = 0; i < local_node_list.length(); i++){
						Node [] l = run(local_node_list[i].pos)

						//rallonger la liste
						Node[] copy = nodelist;
						x++;
						nodelist = new Node[x];
						for (int i = 0; i < copy.length(); i++)
							nodelist[i] = copy[i];

						if (l[0] != null){
							return l;
						}
					}
				}
				else
					return null
			}
			else
				return nodelist;
		}
	}

	/*
	public static Node[] get(){
		return nodelist;
	}
	*/

	public static Vector possibilities(Vector pos){
		int a = 1;
		Vector [] possibilities_list = new Vector[a];

		if (pos.add(up).y > -1 && pos.add(up).y <  && pos.add(up).x > -1 && pos.add(up).x < ){
			if (grille.getValue(pos, up) != "w"){
				if (pos.add(up.mul(2)).y > -1 && pos.add(up.mul(3)).y <  && pos.add(up.mul(3)).x > -1 && pos.add(up.mul(3)) < ){
					if (grille.getValue(pos, up.mul(3)) != "w"){
						if (pos.add(up.mul(2)).y > -1 && pos.add(up.mul(2)).y <  && pos.add(up.mul(2)).x > -1 && pos.add(up.mul(2)).x < ){
							if (grille.getValue(pos, up.mul(2)) == "p"){
								if (pos.add(up.mul(3)).y > -1 && pos.add(up.mul(3)).y <  && pos.add(up.mul(3)).x > -1 && pos.add(up.mul(3)).x < ){
									if (grille.getValue(pos, up.mul(3)) != "w"){
										if (pos.add(up.mul(4)).y > -1 && pos.add(up.mul(4)).y <  && pos.add(up.mul(4)).x > -1 && pos.add(up.mul(4)) < ){
											possibilities_list[a-1] = up.mul(4);

											a++;
											Vector[] copy = possibilities_list;
											possibilities_list = new Vector[a];
											for (int i = 0; i < a-2; i++)
												possibilities_list[i] = copy[i];
										}
									}
									else{
										if (pos.add(up.mul(2)).add(left).y > -1 && pos.add(up.mul(2)).add(left).y <  && pos.add(up.mul(2)).add(left).x > -1 && pos.add(up.mul(2)).add(left).x < ){
											if (grille.getValue(pos, up.mul(2).add(left) != "w"){
												if (pos.add(up.mul(2)).add(left.mul(2)).y > -1 && pos.add(up.mul(2)).add(left.mul(2)).y <  && pos.add(up.mul(2)).add(left.mul(2)).x > -1 && pos.add(up.mul(2)).add(left.mul(2)).x <){
													possibilities_list[a-1] = up.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
										if (pos.add(up.mul(2)).add(right).y > -1 && pos.add(up.mul(2)).add(right).y <  && pos.add(up.mul(2)).add(right).x > -1 && pos.add(up.mul(2)).add(right).x < ){
											if (grille.getValue(pos, up.mul(2).add(right) != "w"){
												if (pos.add(up.mul(2)).add(right.mul(2)).y > -1 && pos.add(up.mul(2)).add(right.mul(2)).y <  && pos.add(up.mul(2)).add(right.mul(2)).x > -1 && pos.add(up.mul(2)).add(right.mul(2)).x <){
													possibilities_list[a-1] = up.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
									}
								}
							}
							else{
								possibilities_list[a-1] = up.mul(2);

								a++;
								Vector[] copy = possibilities_list;
								possibilities_list = new Vector[a];
								for (int i = 0; i < a-2; i++)
									possibilities_list[i] = copy[i];
							}
						}
					}
				}
			}
		}

		if (pos.add(down).y > -1 && pos.add(down).y <  && pos.add(down).x > -1 && pos.add(down).x < ){
			if (grille.getValue(pos, down) != "w"){
				if (pos.add(down.mul(2)).y > -1 && pos.add(down.mul(3)).y <  && pos.add(down.mul(3)).x > -1 && pos.add(down.mul(3)) < ){
					if (grille.getValue(pos, down.mul(3)) != "w"){
						if (pos.add(down.mul(2)).y > -1 && pos.add(down.mul(2)).y <  && pos.add(down.mul(2)).x > -1 && pos.add(down.mul(2)).x < ){
							if (grille.getValue(pos, down.mul(2)) == "p"){
								if (pos.add(down.mul(3)).y > -1 && pos.add(down.mul(3)).y <  && pos.add(down.mul(3)).x > -1 && pos.add(down.mul(3)).x < ){
									if (grille.getValue(pos, down.mul(3)) != "w"){
										if (pos.add(down.mul(4)).y > -1 && pos.add(down.mul(4)).y <  && pos.add(down.mul(4)).x > -1 && pos.add(down.mul(4)) < ){
											possibilities_list[a-1] = down.mul(4);

											a++;
											Vector[] copy = possibilities_list;
											possibilities_list = new Vector[a];
											for (int i = 0; i < a-2; i++)
												possibilities_list[i] = copy[i];
										}
									}
									else{
										if (pos.add(down.mul(2)).add(left).y > -1 && pos.add(down.mul(2)).add(left).y <  && pos.add(down.mul(2)).add(left).x > -1 && pos.add(down.mul(2)).add(left).x < ){
											if (grille.getValue(pos, down.mul(2).add(left) != "w"){
												if (pos.add(down.mul(2)).add(left.mul(2)).y > -1 && pos.add(down.mul(2)).add(left.mul(2)).y <  && pos.add(down.mul(2)).add(left.mul(2)).x > -1 && pos.add(down.mul(2)).add(left.mul(2)).x <){
													possibilities_list[a-1] = down.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
										if (pos.add(down.mul(2)).add(right).y > -1 && pos.add(down.mul(2)).add(right).y <  && pos.add(down.mul(2)).add(right).x > -1 && pos.add(down.mul(2)).add(right).x < ){
											if (grille.getValue(pos, down.mul(2).add(right) != "w"){
												if (pos.add(down.mul(2)).add(right.mul(2)).y > -1 && pos.add(down.mul(2)).add(right.mul(2)).y <  && pos.add(down.mul(2)).add(right.mul(2)).x > -1 && pos.add(down.mul(2)).add(right.mul(2)).x <){
													possibilities_list[a-1] = down.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
									}
								}
							}
							else{
								possibilities_list[a-1] = down.mul(2);

								a++;
								Vector[] copy = possibilities_list;
								possibilities_list = new Vector[a];
								for (int i = 0; i < a-2; i++)
									possibilities_list[i] = copy[i];
							}
						}
					}
				}
			}
		}

		if (pos.add(right).y > -1 && pos.add(right).y <  && pos.add(right).x > -1 && pos.add(right).x < ){
			if (grille.getValue(pos, right) != "w"){
				if (pos.add(right.mul(2)).y > -1 && pos.add(right.mul(3)).y <  && pos.add(right.mul(3)).x > -1 && pos.add(right.mul(3)) < ){
					if (grille.getValue(pos, right.mul(3)) != "w"){
						if (pos.add(right.mul(2)).y > -1 && pos.add(right.mul(2)).y <  && pos.add(right.mul(2)).x > -1 && pos.add(right.mul(2)).x < ){
							if (grille.getValue(pos, right.mul(2)) == "p"){
								if (pos.add(right.mul(3)).y > -1 && pos.add(right.mul(3)).y <  && pos.add(right.mul(3)).x > -1 && pos.add(right.mul(3)).x < ){
									if (grille.getValue(pos, right.mul(3)) != "w"){
										if (pos.add(right.mul(4)).y > -1 && pos.add(right.mul(4)).y <  && pos.add(right.mul(4)).x > -1 && pos.add(right.mul(4)) < ){
											possibilities_list[a-1] = right.mul(4);

											a++;
											Vector[] copy = possibilities_list;
											possibilities_list = new Vector[a];
											for (int i = 0; i < a-2; i++)
												possibilities_list[i] = copy[i];
										}
									}
									else{
										if (pos.add(right.mul(2)).add(up).y > -1 && pos.add(right.mul(2)).add(up).y <  && pos.add(right.mul(2)).add(up).x > -1 && pos.add(right.mul(2)).add(up).x < ){
											if (grille.getValue(pos, right.mul(2).add(up) != "w"){
												if (pos.add(right.mul(2)).add(up.mul(2)).y > -1 && pos.add(right.mul(2)).add(up.mul(2)).y <  && pos.add(right.mul(2)).add(up.mul(2)).x > -1 && pos.add(right.mul(2)).add(up.mul(2)).x <){
													possibilities_list[a-1] = right.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
										if (pos.add(right.mul(2)).add(down).y > -1 && pos.add(right.mul(2)).add(down).y <  && pos.add(right.mul(2)).add(down).x > -1 && pos.add(right.mul(2)).add(down).x < ){
											if (grille.getValue(pos, right.mul(2).add(down) != "w"){
												if (pos.add(right.mul(2)).add(down.mul(2)).y > -1 && pos.add(right.mul(2)).add(down.mul(2)).y <  && pos.add(right.mul(2)).add(down.mul(2)).x > -1 && pos.add(right.mul(2)).add(down.mul(2)).x <){
													possibilities_list[a-1] = right.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
									}
								}
							}
							else{
								possibilities_list[a-1] = right.mul(2);

								a++;
								Vector[] copy = possibilities_list;
								possibilities_list = new Vector[a];
								for (int i = 0; i < a-2; i++)
									possibilities_list[i] = copy[i];
							}
						}
					}
				}
			}
		}

		if (pos.add(left).y > -1 && pos.add(left).y <  && pos.add(left).x > -1 && pos.add(left).x < ){
			if (grille.getValue(pos, left) != "w"){
				if (pos.add(left.mul(2)).y > -1 && pos.add(left.mul(3)).y <  && pos.add(left.mul(3)).x > -1 && pos.add(left.mul(3)) < ){
					if (grille.getValue(pos, left.mul(3)) != "w"){
						if (pos.add(left.mul(2)).y > -1 && pos.add(left.mul(2)).y <  && pos.add(left.mul(2)).x > -1 && pos.add(left.mul(2)).x < ){
							if (grille.getValue(pos, left.mul(2)) == "p"){
								if (pos.add(left.mul(3)).y > -1 && pos.add(left.mul(3)).y <  && pos.add(left.mul(3)).x > -1 && pos.add(left.mul(3)).x < ){
									if (grille.getValue(pos, left.mul(3)) != "w"){
										if (pos.add(left.mul(4)).y > -1 && pos.add(left.mul(4)).y <  && pos.add(left.mul(4)).x > -1 && pos.add(left.mul(4)) < ){
											possibilities_list[a-1] = left.mul(4);

											a++;
											Vector[] copy = possibilities_list;
											possibilities_list = new Vector[a];
											for (int i = 0; i < a-2; i++)
												possibilities_list[i] = copy[i];
										}
									}
									else{
										if (pos.add(left.mul(2)).add(right).y > -1 && pos.add(left.mul(2)).add(right).y <  && pos.add(left.mul(2)).add(right).x > -1 && pos.add(left.mul(2)).add(right).x < ){
											if (grille.getValue(pos, left.mul(2).add(right) != "w"){
												if (pos.add(left.mul(2)).add(right.mul(2)).y > -1 && pos.add(left.mul(2)).add(right.mul(2)).y <  && pos.add(left.mul(2)).add(right.mul(2)).x > -1 && pos.add(left.mul(2)).add(right.mul(2)).x <){
													possibilities_list[a-1] = left.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
										if (pos.add(left.mul(2)).add(down).y > -1 && pos.add(left.mul(2)).add(down).y <  && pos.add(left.mul(2)).add(down).x > -1 && pos.add(left.mul(2)).add(down).x < ){
											if (grille.getValue(pos, left.mul(2).add(down) != "w"){
												if (pos.add(left.mul(2)).add(down.mul(2)).y > -1 && pos.add(left.mul(2)).add(down.mul(2)).y <  && pos.add(left.mul(2)).add(down.mul(2)).x > -1 && pos.add(left.mul(2)).add(down.mul(2)).x <){
													possibilities_list[a-1] = left.mul(4);

													a++;
													Vector[] copy = possibilities_list;
													possibilities_list = new Vector[a];
													for (int i = 0; i < a-2; i++)
														possibilities_list[i] = copy[i];
												}
											}
										}
									}
								}
							}
							else{
								possibilities_list[a-1] = left.mul(2);

								a++;
								Vector[] copy = possibilities_list;
								possibilities_list = new Vector[a];
								for (int i = 0; i < a-2; i++)
									possibilities_list[i] = copy[i];
							}
						}
					}
				}
			}
		}

		return possibilities_list;
	}
}
}