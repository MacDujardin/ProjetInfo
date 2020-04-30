class Possibilitie{ //extends {
	public parent;
	public Pawn pion;
	public Vector dir;

	public Possibilitie(parent, Pawn pion, Vector vect){
		/*Constructeur d'une case signifiant que le pion (pion) peut se déplacer vers sa position (vect)*/
		this.parent = parent;
		this.pion = pion;
		dir = vect;
		//si cliqué, le pion bouge vers la position de la case Possibilitie choisie
	}

	public void makemove(){ //événement /!\
		//fait bouger le pion quand la case Possibilitie est cliquée
		Vector newpos = pion.pos.add(dir);

		if (Math.isPair(newpos.y) || Math.isPair(newpos.y)){
			pion.move(newpos);
			pion.destroyPossibilities();
		}
	}

}