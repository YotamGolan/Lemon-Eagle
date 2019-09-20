JAVASRC    = NQueens.java Queen.java
MAINCLASS  = NQueens
CLASSES    = NQueens.class Queen.class
JARFILE    = NQueens.jar

all: $(JARFILE)

$(JARFILE): $(CLASSES)
	echo Main-class: $(MAINCLASS) > Manifest
	jar cvfm $(JARFILE) Manifest $(CLASSES)
	rm Manifest
	
$(CLASSES): $(JAVASRC)
	javac -Xlint $(JAVASRC)
	
clean:
	rm $(CLASSES) $(JARFILE)