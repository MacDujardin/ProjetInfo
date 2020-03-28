import math

class Vect:
	#a position could also be defined as a Vect bc it's also a (x,y) tupple
	def __init__(self, x = 0, y = 0):
		self.base = (x, y)

	def __mul__(self, n):
		return Vect(int(n*self.base[0]),int(n*self.base[1]))

	def __rmul__(self, n):
		self.base = (int(n*self.base[0]),int(n*self.base[1]))
		return self

	def __add__(self, vect):
		return Vect(self.base[0]+vect[0],self.base[1]+vect[1])

	def __truediv__(self, n):
		return Vect(int(self.base[0]/n), int(self.base[1]/n))

	"""def __radd__(self, vect):
					self.base = (self.base[0]+vect[0],self.base[1]+vect[1])
					return self
			"""
	def __sub__(self, vect):
		return Vect(self.base[0]-vect[0],self.base[1]-vect[1])

	"""def __isub__(self, vect):
					self.base = (self.base[0]-vect[0],self.base[1]-vect[1])
					return self"""

	def __repr__(self):
		return str(self.base)

	def __neg__(self):
		return Vect(-self.base[0], -self.base[1])

	def __str__(self):
		return str(self.base)

	def __getitem__(self, i):
		return self.base[i]

	def __eq__(self, v):
		if v:
			return self.base == v.base
		else:
			return False

	def perp(self):
		return Vect(self.base[1], self.base[0])

class Grille:
	def __init__(self, rows, columns):
		self.rowsnumber = rows
		self.columnsnumber = columns

		self.grid = []
		for row in range(0, rows):
			self.grid.append([])
			for column in range(0, columns):
				self.grid[row].append(None)

	def setValue(self, npos, value):
		#x = column, y = raw
		#To remove, value has to be set as None
		self.grid[npos[1]][npos[0]] = value

	def getValue(self, pos, vect = Vect(0,0)):
		return self.grid[pos[1]+vect[1]][pos[0]+vect[0]]

	def getPosByVal(self, value):
		for row in range(0, self.rowsnumber):
			for column in range(0, self.columnsnumber):
				if self.grif[row][column] == value:
					return (row, column)

class Math:
	def intSup(x):
		return int(x)+1

	def isPair(x):
		return x//2 == x/2

	def distance(a, b):
		dx = b[0] - a[0]
		dy = b[1] - a[1]
		dist = math.sqrt(math.pow(dx, 2) + math.pow(dy, 2))
		return dist

class PathFinder:
	up = Vect(0, -1)
	down = Vect(0, 1)
	right = Vect(1, 0)
	left = Vect(-1, 0)

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
			print()
			for n in self.nodelist:
				print(n.getVect())
			print()

			if node in self.nodelist:
				print(node.getVect(), "already in nodelist")
				pass
			else:
				self.nodelist.append(node)
				localnodelist.append(node)
				if node.hcost == 0:
					print("found:", node)
					found = True
					break

		if len(localnodelist) == 0:
			stop = True

		if not stop:
			if not found:
				if len(localnodelist) != 0:
					for n in localnodelist:
						print("\ngoing for ", n.getVect())
						l = self.run(n.pos)
						if l:
							return l
				else:
					print("rien trouvé")
					#self.nodelist = []
					return None
			else:
				return self.nodelist

	def get(self):
		return self.nodelist

	def possibilities(self, pos):
		possibilities_list = []

		print("position:",pos)

		if ((pos + PathFinder.up)[0] > -1 and (pos + PathFinder.up)[0] < 14 and (pos + PathFinder.up)[1] > -1 and (pos + PathFinder.up)[1] < 14):
			if self.grille.getValue(pos, PathFinder.up) != "w" and self.grille.getValue(pos, PathFinder.up) != "0":
				if ((pos + PathFinder.up*2)[0] > -1 and (pos + PathFinder.up*2)[0] < 14 and (pos + PathFinder.up*2)[1] > -1 and (pos + PathFinder.up*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.up*2) == "p":
						if ((pos + PathFinder.up*3)[0] > -1 and (pos + PathFinder.up*3)[0] < 14 and (pos + PathFinder.up*3)[1] > -1 and (pos + PathFinder.up*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.up*3) != "w" and self.grille.getValue(pos, PathFinder.up*3) != "0":
								if ((pos + PathFinder.up*4)[0] > -1 and (pos + PathFinder.up*4)[0] < 14 and (pos + PathFinder.up*4)[1] > -1 and (pos + PathFinder.up*4)[1] < 14):
									possibilities_list.append(PathFinder.up*4)
							else:
								if ((pos + PathFinder.up*2+PathFinder.left)[0] > -1 and (pos + PathFinder.up*2+PathFinder.left)[0] < 14 and (pos + PathFinder.up*2+PathFinder.left)[1] > -1 and (pos + PathFinder.up*2+PathFinder.left)[1] < 14):
									if self.grille.getValue(pos, PathFinder.up*2+PathFinder.left) != "w" and self.grille.getValue(pos, PathFinder.up*2+PathFinder.left) != "0":
										if ((pos + PathFinder.up*2+PathFinder.left*2)[0] > -1 and (pos + PathFinder.up*2+PathFinder.left*2)[0] < 14 and (pos + PathFinder.up*2+PathFinder.left*2)[1] > -1 and (pos + PathFinder.up*2+PathFinder.left*2)[1] < 14):
											possibilities_list.append(PathFinder.up*2+PathFinder.left*2)
								if ((pos + PathFinder.up*2+PathFinder.right)[0] > -1 and (pos + PathFinder.up*2+PathFinder.right)[0] < 14 and (pos + PathFinder.up*4+PathFinder.right)[1] > -1 and (pos + PathFinder.up*2+PathFinder.right)[1] < 14):
									if self.grille.getValue(pos, PathFinder.up*2+PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.up*2+PathFinder.right) != "0":
										if ((pos + PathFinder.up*2+PathFinder.right*2)[0] > -1 and (pos + PathFinder.up*2+PathFinder.right*2)[0] < 14 and (pos + PathFinder.up*2+PathFinder.right*2)[1] > -1 and (pos + PathFinder.up*2+PathFinder.right*2)[1] < 14):
											possibilities_list.append(PathFinder.up*2+PathFinder.right*2)
					else:
						possibilities_list.append(PathFinder.up*2)

		if ((pos + PathFinder.down)[0] > -1 and (pos + PathFinder.down)[0] < 14 and (pos + PathFinder.down)[1] > -1 and (pos + PathFinder.down)[1] < 14):
			if self.grille.getValue(pos, PathFinder.down) != "w" and self.grille.getValue(pos, PathFinder.down) != "0":
				if ((pos + PathFinder.down*2)[0] > -1 and (pos + PathFinder.down*2)[0] < 14 and (pos + PathFinder.down*2)[1] > -1 and (pos + PathFinder.down*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.down*2) == "p":
						if ((pos + PathFinder.down*3)[0] > -1 and (pos + PathFinder.down*3)[0] < 14 and (pos + PathFinder.down*3)[1] > -1 and (pos + PathFinder.down*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.down*3) != "w":
								if ((pos + PathFinder.down*4)[0] > -1 and (pos + PathFinder.down*4)[0] < 14 and (pos + PathFinder.down*4)[1] > -1 and (pos + PathFinder.down*4)[1] < 14):
									possibilities_list.append(PathFinder.down*4)
							else:
								if ((pos + PathFinder.down*2+PathFinder.left)[0] > -1 and (pos + PathFinder.down*2+PathFinder.left)[0] < 14 and (pos + PathFinder.down*2+PathFinder.left)[1] > -1 and (pos + PathFinder.down*2+PathFinder.left)[1] < 14):
									if self.grille.getValue(pos, PathFinder.down*2+PathFinder.left) != "w" and self.grille.getValue(pos, PathFinder.down*2+PathFinder.left) != "0":
										if ((pos + PathFinder.down*2+PathFinder.left*2)[0] > -1 and (pos + PathFinder.down*2+PathFinder.left*2)[0] < 14 and (pos + PathFinder.down*2+PathFinder.left*2)[1] > -1 and (pos + PathFinder.down*2+PathFinder.left*2)[1] < 14):
											possibilities_list.append(PathFinder.down*2+PathFinder.left*2)
								if ((pos + PathFinder.down*2+PathFinder.right)[0] > -1 and (pos + PathFinder.down*2+PathFinder.right)[0] < 14 and (pos + PathFinder.down*2+PathFinder.right)[1] > -1 and (pos + PathFinder.down*2+PathFinder.right)[1] < 14):
									if self.grille.getValue(pos, PathFinder.down*2+PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.down*2+PathFinder.right) != "0":
										if ((pos + PathFinder.down*2+PathFinder.right*2)[0] > -1 and (pos + PathFinder.down*2+PathFinder.right*2)[0] < 14 and (pos + PathFinder.down*2+PathFinder.right*2)[1] > -1 and (pos + PathFinder.down*2+PathFinder.right*2)[1] < 14):
											possibilities_list.append(PathFinder.down*2+PathFinder.right*2)
					else:
						possibilities_list.append(PathFinder.down*2)

		if ((pos + PathFinder.right)[0] > -1 and (pos + PathFinder.right)[0] < 14 and (pos + PathFinder.right)[1] > -1 and (pos + PathFinder.right)[1] < 14):
			if self.grille.getValue(pos, PathFinder.right) != "w" and self.grille.getValue(pos, PathFinder.right) != "0":
				if ((pos + PathFinder.right*2)[0] > -1 and (pos + PathFinder.right*2)[0] < 14 and (pos + PathFinder.right*2)[1] > -1 and (pos + PathFinder.right*2)[1] < 14):
					if self.grille.getValue(pos, PathFinder.right*2) == "p":
						if ((pos + PathFinder.right*3)[0] > -1 and (pos + PathFinder.right*3)[0] < 14 and (pos + PathFinder.right*3)[1] > -1 and (pos + PathFinder.right*3)[1] < 14):
							if self.grille.getValue(pos, PathFinder.right*3) != "w":
								if ((pos + PathFinder.right*4)[0] > -1 and (pos + PathFinder.right*4)[0] < 14 and (pos + PathFinder.right*4)[1] > -1 and (pos + PathFinder.right*4)[1] < 14):
									possibilities_list.append(PathFinder.right*4)
							else:
								if ((pos + PathFinder.right*2+PathFinder.up)[0] > -1 and (pos + PathFinder.right*2+PathFinder.up)[0] < 14 and (pos + PathFinder.right*2+PathFinder.up)[1] > -1 and (pos + PathFinder.right*2+PathFinder.up)[1] < 14):
									if self.grille.getValue(pos, PathFinder.right*2+PathFinder.up) != "w" and self.grille.getValue(pos, PathFinder.right*2+PathFinder.up) != "0":
										if ((pos + PathFinder.right*2+PathFinder.up*2)[0] > -1 and (pos + PathFinder.right*2+PathFinder.up*2)[0] < 14 and (pos + PathFinder.right*2+PathFinder.up*2)[1] > -1 and (pos + PathFinder.right*2+PathFinder.up*2)[1] < 14):
											possibilities_list.append(PathFinder.right*2+PathFinder.up*2)
								if ((pos + PathFinder.right*2+PathFinder.down)[0] > -1 and (pos + PathFinder.right*4+PathFinder.down)[0] < 14 and (pos + PathFinder.right*2+PathFinder.down)[1] > -1 and (pos + PathFinder.right*2+PathFinder.down)[1] < 14):
									if self.grille.getValue(pos, PathFinder.right*4+PathFinder.down) != "w" and self.grille.getValue(pos, PathFinder.right*2+PathFinder.down) != "0":
										if ((pos + PathFinder.right*2+PathFinder.down*2)[0] > -1 and (pos + PathFinder.right*2+PathFinder.down*2)[0] < 14 and (pos + PathFinder.right*2+PathFinder.down*2)[1] > -1 and (pos + PathFinder.right*2+PathFinder.down*2)[1] < 14):
											possibilities_list.append(PathFinder.right*2+PathFinder.down*2)
					else:
						possibilities_list.append(PathFinder.right*2)

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
		self.gcost = Math.distance(self.pos, parent.depart)
		self.hcost = Math.distance(self.pos, parent.arrivee)
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