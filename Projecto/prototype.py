import gclasses
from gclasses import PathFinder as PF
import tkinter
from PIL import Image, ImageTk

#
#
#		onMove du mur + limiter la récursion du pathfinding ou changer de technique
#
#

plateau = gclasses.Grille(15, 15) # pas le 0 0, pas le 0 14, pas le 14 0, pas le 14 14 #14 pts
for i in range(15):
	for j in range(15):
		if (i == 0 or i == 14):
			plateau.grid[i][j] = "0"
		elif (j == 0 or j == 14):
			plateau.grid[i][j] = "0"
		elif i == 1:
			plateau.grid[i][j] = "ww"
		elif i == 13:
			plateau.grid[i][j] = "bw"


def getWindowPos(case): #case number on the plate
	"""return the position on the window in pixels."""
	if case == 0:
		return 0
	elif gclasses.Math.isPair(case):
		return (case/2)*35
	else:
		return (int(case/2)+1)*5+int(case/2)*30

def getWallCase(x,y):
	"""return the position for plateau.grid to set walls."""
	_x = int(x/35)*2
	_y = int(y/35)*2
	return gclasses.Vect(_x, _y)

class Possibilitie(tkinter.Canvas):
	def __init__(self, parent, pion, vect):
		"""Create a green case you can click on to move your pawn at the place.\n\nparent is the window.\npion is the pawn that can move on the possibility.\nvect is the direction vector from the pawn to the case."""
		self.parent = parent
		self.pion = pion
		self.dir = vect
		super(Possibilitie, self).__init__(parent, width = 29, height = 29, bg = "green")
		self.bind("<Button-1>", self.makemove)

	def makemove(self, event):
		"""Move the pawn to the chosen possibility then destroy all possibilities objects that have been created."""
		newpos = self.pion.pos + self.dir

		if (gclasses.Math.isPair(newpos[0]) | gclasses.Math.isPair(newpos[1])):
			pass
		else:
			self.pion.move(newpos)
			self.pion.destroyPossibilities()

class Plate(tkinter.Canvas):
	def __init__(self, parent):
		"""Create the plate fro the game with the image."""
		#peut être changé et rester un objet classique de tkinter
		super(Plate, self).__init__(parent, width = 250, height = 250)
		
		img = Image.open("plateau.png")
		self.img = ImageTk.PhotoImage(img)
		self.bg = self.create_image(0,0, anchor = tkinter.NW, image = self.img)

class Pawn(tkinter.Canvas):
	#value for enemy: opposite color of your's
	_white = "white"
	_black = "black"

	def __init__(self, parent, pos = gclasses.Vect(), color = None, player = True): #parent
		"""Create the Pawn object\n\nparent is the object where the Pawn will be placed.\npos is a vector witch define the place where the Pawn will be placed.\ncolor defines if the Pawn is white or brown.\nplayer defines if the Pawn is the first player or not. Not needed if player vs player mode"""
		super(Pawn, self).__init__(parent, width = 30, height = 30)
		self.pos = pos
		self.parent = parent
		self.color = color
		self.possibilities = []
		self.enemy = None

		if color == Pawn._white:
			img = Image.open("White.png")
		else:
			img = Image.open("Black.png")
		self.img = ImageTk.PhotoImage(img)
		self.bg = self.create_image(1,1, anchor = tkinter.NW, image = self.img)
		if(player):
			self.enable()

	def destroyPossibilities(self):
		"""Destroy all possibilities cases that have been created and stored in self.possibilities list."""
		for i in range(len(self.possibilities)):
			self.possibilities[i].destroy()

		self.possibilities = []

	def isClickedAgain(self, event):
		"""Destroy possibilities that have been created and activate the fact that if pawn is clicked, possibilities are showed again."""
		self.bind("<Button-1>", self.isClicked)
		self.destroyPossibilities()

	def isClicked(self, event):
		"""Verify witch possibilities are available around the Pawn and create the green possibilities. If Pawn is clicked again, it destroys possibilities that have been created."""
		self.bind("<Button-1>", self.isClickedAgain)
		self.checkCase()
		self.checkWall()
		self.createPossibilities()

	def createPossibilities(self):
		"""Place the possibilities on the plate."""
		for i in range(len(self.possibilities)):
			case = Possibilitie(self.parent, self, self.possibilities[i])
			_x = getWindowPos(self.pos[0] + self.possibilities[i][0])
			_y = getWindowPos(self.pos[1] + self.possibilities[i][1])
			case.place(x = _x-1, y = _y-1)
			self.possibilities[i] = case

	def checkWall(self):
		"""Verify if there is a wall around the Pawn and remove the theoretic possible movements where there is a wall."""
		toremove = []
		toappend = []
		for mvmt in self.possibilities:
			mur = plateau.getValue(self.pos, mvmt*0.5)
			pawn = plateau.getValue(self.pos, mvmt)
			if mur == 'w':
				toremove.append(mvmt)
			elif pawn == 'p':
				mur = plateau.getValue(self.pos, mvmt*1.5)
				if mur == 'w':
					#verifier les mvmt en L
					mur1 = plateau.getValue(self.pos, mvmt + (mvmt*0.5).perp())
					mur2 = plateau.getValue(self.pos, mvmt - (mvmt*0.5).perp())
					if mur1 == 'w' and mur2 == 'w':
						toremove.append(mvmt)
					elif mur1 == 'w' and mur2 != 'w':
						toremove.append(mvmt)
						toappend.append(mvmt + mvmt.perp())
					elif mur1 != 'w' and mur2 == 'w':
						toremove.append(mvmt)
						toappend.append(mvmt - mvmt.perp())
					elif mur1 != 'w' and mur2 != 'w':
						toremove.append(mvmt)
						toappend.append(mvmt - mvmt.perp())
						toappend.append(mvmt + mvmt.perp())

				else:
					i = self.possibilities.index(mvmt)
					self.possibilities[i] *= 2
					direction = self.possibilities[i]
					if ((self.pos + direction)[0] < 1 or (self.pos + direction)[0] > 14 or (self.pos + direction)[1] < 1 or (self.pos + direction)[1] > 14):
						#on vérifie qu'on ne sorte pas du tableau, sinon on retire la possibilité de mouvement
						toremove.append(direction)

		for mvmt in toremove:
			self.possibilities.remove(mvmt)

		for mvmt in toappend:
			self.possibilities.append(mvmt)

	def checkCase(self):
		"""Verify if there is a case around the Pawn where it can move."""
		self.possibilities = []
		down = gclasses.Vect(0, 1)
		right = gclasses.Vect(1, 0)

		if ((self.pos-down*2)[0] > -1 and (self.pos-down*2)[1] > -1):
			self.possibilities.append((-down)*2)

		if ((self.pos+down*2)[0] < 14 and (self.pos+down*2)[1] < 14):
			self.possibilities.append(down*2)

		if ((self.pos-right*2)[0] > -1 and (self.pos-right*2)[1] > -1):
			self.possibilities.append((-right)*2)

		if ((self.pos+right*2)[0] < 14 and (self.pos+right*2)[1] < 14):
			self.possibilities.append(right*2)

	def move(self, newpos):
		"""Moves the Pawn to [newpos], verify if the game is won and change the playable Pawn"""
		#self.bind("<Button-1>", self.isClicked)
		if self.color == Pawn._white:
			if self.pos[1] == 13:
				plateau.setValue(self.pos, "bw")
			else:
				plateau.setValue(self.pos, None)
		elif self.color == Pawn._black:
			if self.pos[1] == 1:
				plateau.setValue(self.pos, "ww")
			else:
				plateau.setValue(self.pos, None)

		self.pos = newpos
		self.winning()
		plateau.setValue(self.pos, 'p')
		super(Pawn, self).place(x = getWindowPos(self.pos[0]), y = getWindowPos(self.pos[1]))
		self.disable()
		self.enemy.enable()

	def winning(self):
		"""Verify if player is winning the game."""
		#verification si la partie a été gagnée
		stop = False
		if (self.color == "white" and plateau.getValue(self.pos) == 'ww'):
			print("winner is white")
			stop = True
		elif (self.color == "black" and plateau.getValue(self.pos) == 'bw'):
			print("winner is black")
			stop = True
		if stop:
			self.bind("<Button-1>", quit)
			exit()

	def place(self):
		"""Place Pawn at the desired place."""
		_x = getWindowPos(self.pos[0])
		_y = getWindowPos(self.pos[1])
		super(Pawn, self).place(x = _x-2, y = _y-2)

	def enable(self):
		"""Enable the Pawn to be played."""
		self.bind("<Button-1>", self.isClicked)

	def disable(self):
		"""The Pawn cannot be played anymore."""
		self.bind("<Button-1>", lambda event: None)

class Wall(tkinter.Canvas):
	def __init__(self, parent, pos = gclasses.Vect(), sens = None):
		"""Create a wall object.\n\nparent is the object where it's placed.\npos is the desired plate position.\nsens is horizontal or vertical."""
		self.parent = parent
		self.sens = sens
		if sens == "vertical":
			super(Wall, self).__init__(parent, width = 4, height = 64, bg = "brown")
		elif sens == "horizontal":
			super(Wall, self).__init__(parent, width = 64, height = 4, bg = "brown")

		#self._drag_data = {"x": 0, "y": 0}
		self.pos = pos
		self.bind("<Button1-Motion>", self.onMove)
		self.bind("<ButtonRelease-1>", self.onRelease)

	def onMove(self, event):
		"""Track the wall on the screen while dragged"""
		#print(event.x, event.y)
		"""if self.sens == "vertical":
			absolute_x = event.x + 170
			absolute_y = event.y + 265
		elif self.sens == "horizontal":
			absolute_x = event.x + 40
			absolute_y = event.y + 295
						
		super(Wall, self).place(x = absolute_x, y = absolute_y)"""
		pass

	def onRelease(self, event):
		"""Place the wall at the desired position (released position)."""
		found = False
		x,y = (event.x, event.y)
		if self.sens == "vertical":
			x += 170
			y += 270
		elif self.sens == "horizontal":
			x += 40
			y += 295

		pos = getWallCase(x, y)
		if self.sens == "vertical":
			pos += gclasses.Vect(0, 1)
		elif self.sens == "horizontal":
			pos += gclasses.Vect(1, 0)

		#vérifier si déjà un mur
		if not self.busy(pos):
			for j in range(3):
				if self.sens == "vertical":
					plateau.setValue(gclasses.Vect(pos[0], pos[1]+j), "w")
				elif self.sens == "horizontal":
					plateau.setValue(gclasses.Vect(pos[0]+j, pos[1]), "w")

			for a in range(1, 13):
				#vérifier s'il existe un chemin possible pour que les 2pions puissent gagner.
				ppath = PF(plateau, player.pos, gclasses.Vect(a, 1))
				epath = PF(plateau, enemy.pos, gclasses.Vect(a, 13))
				if (ppath.run() != None and epath.run() != None):
					print("ways exists")
					self.parent.usingWall()
					newWall = Wall(self.parent.parent, pos = pos, sens = self.sens)
					newWall.disable()
					newWall.place()
					found = True
					break

			if not found:
				for j in range(3):
					if self.sens == "vertical":
						plateau.setValue(gclasses.Vect(pos[0], pos[1]+j), None)
					elif self.sens == "horizontal":
						plateau.setValue(gclasses.Vect(pos[0]+j, pos[1]), None)



	def busy(self, pos):
		"""Verify if there is no other wall or if the wall to be placed won't get out of the plate."""
		for i in range(3):
			if self.sens == "vertical":
				val = plateau.getValue((pos[0], pos[1] + i))
			elif self.sens == "horizontal":
				val = plateau.getValue((pos[0] + i, pos[1]))

			if (val != None and val != 'bw' and val != 'ww'):
				return True

		return False

	def disable(self):
		"""Disable the possibility to move the wall if it has been placed on the plate."""
		self.bind("<Button1-Motion>", lambda event: None)
		self.bind("<ButtonRelease-1>", lambda event: None)

	def place(self):
		"""Place Wall at the desired place."""
		self.disable()
		_x = getWindowPos(self.pos[0])
		_y = getWindowPos(self.pos[1])
		super(Wall, self).place(x = _x-1, y = _y-1)

class Stock(tkinter.Canvas):
	def __init__(self, parent, n):
		"""Create the stock where you can chose horizontal or vertical walls and where there is a countdown of you're walls into this stock."""
		super(Stock, self).__init__(parent, width = 250, height = 95)
		self.parent = parent
		#quand drag and drop, création d'un wall au drop, à l'endroit souhaité
		#quand drop, vérifier que possibilité de déplacement pour chaque joueur
		self.horizontal = Wall(self, pos = gclasses.Vect(40, 45), sens = "horizontal")
		self.horizontal.configure(cursor = "hand2")
		self.vertical = Wall(self, pos = gclasses.Vect(210, 15), sens = "vertical")
		self.vertical.configure(cursor = "hand2")
		super(Wall, self.horizontal).place(x = 40 , y = 45)
		super(Wall, self.vertical).place(x = 170 , y = 15)

		self.stock = tkinter.IntVar()
		self.stock.set(n)
		self.label = tkinter.Label(self, textvariable = self.stock)
		self.label.place(x = 130, y = 35)

	def usingWall(self):
		"""Update the countdown and if empty, disable the walls"""
		self.stock.set(self.stock.get()-1)
		if self.stock.get() < 1:
			self.horizontal.disable()
			self.horizontal.configure(cursor = "arrow")
			self.vertical.disable()
			self.vertical.configure(cursor = "arrow")

if __name__ == '__main__':
	win = tkinter.Tk() 
	win.geometry("250x345")
	win["bg"] = "blue"
	win.resizable(False,False)
	plate = Plate(win)
	plate.place(x = 0, y = 0)

	stock = Stock(win, 8)
	stock.place(x = 0, y = 250)

	player = Pawn(win, pos = gclasses.Vect(7, 13), color = "white", player = True)
	player.place()
	plateau.setValue(player.pos, 'p')
	enemy = Pawn(win, pos = gclasses.Vect(7, 1), color = "black", player = False)
	enemy.place()
	plateau.setValue(enemy.pos, 'p')

	player.enemy = enemy
	enemy.enemy = player

	win.mainloop()