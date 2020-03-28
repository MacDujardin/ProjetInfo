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
