#coding: utf-8

import gclasses

class PathFinder:
	up = gclasses.Vect(0, -1)
	down = gclasses.Vect(0, 1)
	right = gclasses.Vect(1, 0)
	left = gclasses.Vect(-1, 0)

	def __init__(self, parent, depart, arrivee):
		self.grille = parent
		self.actif_node = None
		self.depart = depart
		self.arrivee = arrivee
		self.nodelist = []

	def run(self, pos = None):
		found = False
		stop = False
		true_pos = pos
		if pos == None:
			true_pos = self.depart
			dnode = Node(self.grille, self, true_pos)
			self.nodelist.append(dnode)

		localnodelist = []
		
		#print("position:", self.possibilities(true_pos))
		for sens in self.possibilities(true_pos):
			print("vers: ", sens)
			node = Node(self.grille, self, true_pos + sens)
			print(node.getVect())
			for n in self.nodelist:
				print(n.getVect())
			if node in self.nodelist:
				print(node.getVect(), "already in nodelist")
				pass
			else:
				self.nodelist.append(node)
				localnodelist.append(node)
				if node.hcost == 0:
					found = True
					break

		if len(localnodelist) == 0:
			stop = True

		if not stop:
			if not found:
				if len(localnodelist) != 0:
					for n in localnodelist:
						return self.run(n.pos)
				else:
					return None
			else:
				return self.nodelist

	#def get(self):
	#	return self.nodelist

	def possibilities(self, pos):
		possibilities_list = []

		#merdier pour vérifier si on peut aller en haut, avec tous les cas possibles
		if ((pos + PathFinder.up)[0] > 0 and (pos + PathFinder.up)[0] < 14 and (pos + PathFinder.up)[1] > 0 and (pos + PathFinder.up)[1] < 14):
			if self.grille.getValue(pos, PathFinder.up) != "w" and self.grille.getValue(pos, PathFinder.up) != "0":
				if ((pos + PathFinder.up*2)[0] > 0 and (pos + PathFinder.up*2)[0] < 14 and (pos + PathFinder.up*2)[1] > 0 and (pos + PathFinder.up*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.up*2) == "p":
						if ((pos + PathFinder.up*3)[0] > 0 and (pos + PathFinder.up*3)[0] < 14 and (pos + PathFinder.up*3)[1] > 0 and (pos + PathFinder.up*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.up*3) != "w" and self.grille.getValue(pos, PathFinder.up*3) != "0":
								if ((pos + PathFinder.up*4)[0] > 0 and (pos + PathFinder.up*4)[0] < 14 and (pos + PathFinder.up*4)[1] > 0 and (pos + PathFinder.up*4)[1] < 14):
									possibilities_list.append(PathFinder.up*4)
							else:
								if ((pos + PathFinder.up*2+PathFinder.left)[0] > 0 and (pos + PathFinder.up*2+PathFinder.left)[0] < 14 and (pos + PathFinder.up*2+PathFinder.left)[1] > 0 and (pos + PathFinder.up*2+PathFinder.left)[1] < 14):
									if self.grille.getValue(pos, PathFinder.up*2+PathFinder.left) != "w" and self.grille.getValue(pos, PathFinder.up*2+PathFinder.left) != "0":
										if ((pos + PathFinder.up*2+PathFinder.left*2)[0] > 0 and (pos + PathFinder.up*2+PathFinder.left*2)[0] < 14 and (pos + PathFinder.up*2+PathFinder.left*2)[1] > 0 and (pos + PathFinder.up*2+PathFinder.left*2)[1] < 14):
											possibilities_list.append(PathFinder.up*2+PathFinder.left*2)
								if ((pos + PathFinder.up*2+PathFinder.right)[0] > 0 and (pos + PathFinder.up*2+PathFinder.right)[0] < 14 and (pos + PathFinder.up*4+PathFinder.right)[1] > 0 and (pos + PathFinder.up*2+PathFinder.right)[1] < 14):
									if self.grille.getValue(pos, PathFinder.up*2+PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.up*2+PathFinder.right) != "0":
										if ((pos + PathFinder.up*2+PathFinder.right*2)[0] > 0 and (pos + PathFinder.up*2+PathFinder.right*2)[0] < 14 and (pos + PathFinder.up*2+PathFinder.right*2)[1] > 0 and (pos + PathFinder.up*2+PathFinder.right*2)[1] < 14):
											possibilities_list.append(PathFinder.up*2+PathFinder.right*2)
					else:
						possibilities_list.append(PathFinder.up*2)

		#pareil pour down
		if ((pos + PathFinder.down)[0] > 0 and (pos + PathFinder.down)[0] < 14 and (pos + PathFinder.down)[1] > 0 and (pos + PathFinder.down)[1] < 14):
			if self.grille.getValue(pos, PathFinder.down) != "w" and self.grille.getValue(pos, PathFinder.down) != "0":
				if ((pos + PathFinder.down*2)[0] > 0 and (pos + PathFinder.down*2)[0] < 14 and (pos + PathFinder.down*2)[1] > 0 and (pos + PathFinder.down*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.down*2) == "p":
						if ((pos + PathFinder.down*3)[0] > 0 and (pos + PathFinder.down*3)[0] < 14 and (pos + PathFinder.down*3)[1] > 0 and (pos + PathFinder.down*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.down*3) != "w":
								if ((pos + PathFinder.down*4)[0] > 0 and (pos + PathFinder.down*4)[0] < 14 and (pos + PathFinder.down*4)[1] > 0 and (pos + PathFinder.down*4)[1] < 14):
									possibilities_list.append(PathFinder.down*4)
							else:
								if ((pos + PathFinder.down*2+PathFinder.left)[0] > 0 and (pos + PathFinder.down*2+PathFinder.left)[0] < 14 and (pos + PathFinder.down*2+PathFinder.left)[1] > 0 and (pos + PathFinder.down*2+PathFinder.left)[1] < 14):
									if self.grille.getValue(pos, PathFinder.down*2+PathFinder.left) != "w" and self.grille.getValue(pos, PathFinder.down*2+PathFinder.left) != "0":
										if ((pos + PathFinder.down*2+PathFinder.left*2)[0] > 0 and (pos + PathFinder.down*2+PathFinder.left*2)[0] < 14 and (pos + PathFinder.down*2+PathFinder.left*2)[1] > 0 and (pos + PathFinder.down*2+PathFinder.left*2)[1] < 14):
											possibilities_list.append(PathFinder.down*2+PathFinder.left*2)
								if ((pos + PathFinder.down*2+PathFinder.right)[0] > 0 and (pos + PathFinder.down*2+PathFinder.right)[0] < 14 and (pos + PathFinder.down*2+PathFinder.right)[1] > 0 and (pos + PathFinder.down*2+PathFinder.right)[1] < 14):
									if self.grille.getValue(pos, PathFinder.down*2+PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.down*2+PathFinder.right) != "0":
										if ((pos + PathFinder.down*2+PathFinder.right*2)[0] > 0 and (pos + PathFinder.down*2+PathFinder.right*2)[0] < 14 and (pos + PathFinder.down*2+PathFinder.right*2)[1] > 0 and (pos + PathFinder.down*2+PathFinder.right*2)[1] < 14):
											possibilities_list.append(PathFinder.down*2+PathFinder.right*2)
					else:
						possibilities_list.append(PathFinder.down*2)

		#pareil pour right
		if ((pos + PathFinder.right)[0] > 0 and (pos + PathFinder.right)[0] < 14 and (pos + PathFinder.right)[1] > 0 and (pos + PathFinder.right)[1] < 14):
			if self.grille.getValue(pos, PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.right) != "0":
				if ((pos + PathFinder.right*2)[0] > 0 and (pos + PathFinder.right*2)[0] < 14 and (pos + PathFinder.right*2)[1] > 0 and (pos + PathFinder.right*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.right*2) == "p":
						if ((pos + PathFinder.right*3)[0] > 0 and (pos + PathFinder.right*3)[0] < 14 and (pos + PathFinder.right*3)[1] > 0 and (pos + PathFinder.right*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.right*3) != "w":
								if ((pos + PathFinder.right*4)[0] > 0 and (pos + PathFinder.right*4)[0] < 14 and (pos + PathFinder.right*4)[1] > 0 and (pos + PathFinder.right*4)[1] < 14):
									possibilities_list.append(PathFinder.right*4)
							else:
								if ((pos + PathFinder.right*2+PathFinder.up)[0] > 0 and (pos + PathFinder.right*2+PathFinder.up)[0] < 14 and (pos + PathFinder.right*2+PathFinder.up)[1] > 0 and (pos + PathFinder.right*2+PathFinder.up)[1] < 14):
									if self.grille.getValue(pos, PathFinder.right*2+PathFinder.up) != "w" and self.grille.getValue(pos, PathFinder.right*2+PathFinder.up) != "0":
										if ((pos + PathFinder.right*2+PathFinder.up*2)[0] > 0 and (pos + PathFinder.right*2+PathFinder.up*2)[0] < 14 and (pos + PathFinder.right*2+PathFinder.up*2)[1] > 0 and (pos + PathFinder.right*2+PathFinder.up*2)[1] < 14):
											possibilities_list.append(PathFinder.right*2+PathFinder.up*2)
								if ((pos + PathFinder.right*2+PathFinder.down)[0] > 0 and (pos + PathFinder.right*4+PathFinder.down)[0] < 14 and (pos + PathFinder.right*2+PathFinder.down)[1] > 0 and (pos + PathFinder.right*2+PathFinder.down)[1] < 14):
									if self.grille.getValue(pos, PathFinder.right*4+PathFinder.down) != "w" and self.grille.getValue(pos, PathFinder.right*2+PathFinder.down) != "0":
										if ((pos + PathFinder.right*2+PathFinder.down*2)[0] > 0 and (pos + PathFinder.right*2+PathFinder.down*2)[0] < 14 and (pos + PathFinder.right*2+PathFinder.down*2)[1] > 0 and (pos + PathFinder.right*2+PathFinder.down*2)[1] < 14):
											possibilities_list.append(PathFinder.right*2+PathFinder.down*2)
					else:
						possibilities_list.append(PathFinder.right*2)

		#pareil pour left
		if ((pos + PathFinder.left)[0] > -1 and (pos + PathFinder.left)[0] < 14 and (pos + PathFinder.left)[1] > -1 and (pos + PathFinder.left)[1] < 14):
			if self.grille.getValue(pos, PathFinder.left) != "w" and self.grille.getValue(pos, PathFinder.left) != "0":
				if ((pos + PathFinder.left*2)[0] > -1 and (pos + PathFinder.left*2)[0] < 14 and (pos + PathFinder.left*2)[1] > -1 and (pos + PathFinder.left*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.left*2) == "p":
						if ((pos + PathFinder.left*3)[0] > -1 and (pos + PathFinder.left*3)[0] < 14 and (pos + PathFinder.left*3)[1] > -1 and (pos + PathFinder.left*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.left*3) != "w":
								if ((pos + PathFinder.left*4)[0] > -1 and (pos + PathFinder.left*4)[0] < 14 and (pos + PathFinder.left*4)[1] > -1 and (pos + PathFinder.left*4)[1] < 14):
									possibilities_list.append(PathFinder.left*4)
							else:
								if ((pos + PathFinder.left*2+PathFinder.up)[0] > -1 and (pos + PathFinder.left*2+PathFinder.up)[0] < 14 and (pos + PathFinder.left*2+PathFinder.up)[1] > -1 and (pos + PathFinder.left*2+PathFinder.up)[1] < 14):
									if self.grille.getValue(pos, PathFinder.left*2+PathFinder.up) != "w" and self.grille.getValue(pos, PathFinder.left*2+PathFinder.up) != "0":
										if ((pos + PathFinder.left*2+PathFinder.up*2)[0] > -1 and (pos + PathFinder.left*2+PathFinder.up*2)[0] < 14 and (pos + PathFinder.left*2+PathFinder.up*2)[1] > -1 and (pos + PathFinder.left*2+PathFinder.up*2)[1] < 14):
											possibilities_list.append(PathFinder.left*2+PathFinder.up*2)
								if ((pos + PathFinder.left*2+PathFinder.down)[0] > -1 and (pos + PathFinder.left*2+PathFinder.down)[0] < 14 and (pos + PathFinder.left*2+PathFinder.down)[1] > -1 and (pos + PathFinder.left*2+PathFinder.down)[1] < 14):
									if self.grille.getValue(pos, PathFinder.left*2+PathFinder.down) != "w" and self.grille.getValue(pos, PathFinder.left*2+PathFinder.down) != "0":
										if ((pos + PathFinder.left*2+PathFinder.down*2)[0] > -1 and (pos + PathFinder.left*2+PathFinder.down*2)[0] < 14 and (pos + PathFinder.left*2+PathFinder.down*2)[1] > -1 and (pos + PathFinder.left*2+PathFinder.down*2)[1] < 14):
											possibilities_list.append(PathFinder.left*2+PathFinder.down*2)
					else:
						possibilities_list.append(PathFinder.left*2)

		return possibilities_list

class Node:
	def __init__(self, grille, parent, position, prec = None):
		self.parent = prec
		self.grille = grille
		self.pos = position
		self.gcost = gclasses.Math.distance(self.pos, parent.depart)
		self.hcost = gclasses.Math.distance(self.pos, parent.arrivee)
		self.cost = self.gcost + self.hcost

	def __eq__(self, node):
		if self.pos == node.pos:
			return True
		else:
			return False

	def __get__(self):
		print(self.cost)

	def __str__(self):
		return str(self.cost)

	def getTree(self):
		tree = [self]
		if self.parent:
			for n in self.parent.getTree():
				tree.append(n)
		return tree

	def getVect(self):
		return self.pos