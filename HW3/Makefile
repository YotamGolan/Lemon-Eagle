JAVASRC    = ChessMoves.java LinkedList.java Chesspiece.java node.java King.java Queen.java Bishop.java Knight.java Rook.java Pawn.java
MAINCLASS  = ChessMoves
CLASSES    = ChessMoves.class LinkedList.class Chesspiece.class node.class King.class Queen.class Bishop.class Knight.class Rook.class Pawn.class
JARFILE    = ChessMoves.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)
	
clean:
	rm $(CLASSES) $(JARFILE)