
LIB = lib
SRCDIR = src
BINDIR = bin
TESTDIR = test
DOCDIR = doc


JAVAC = javac
JFLAGS = -g -d $(BINDIR) -cp $(BINDIR)


vpath %.java $(SRCDIR)
vpath %.class $(BINDIR)

# define general build rule for java sources
.SUFFIXES:  .java  .class

.java.class:
	$(JAVAC)  $(JFLAGS)  $<

#default rule - will be invoked by make


classes: 
	javac $(JFLAGS) $(SRCDIR)/*.java

run:
	java -ea -cp $(BINDIR) Simulator

# Rules for generating documentation
doc:
	javadoc -d $(DOCDIR) $(SRCDIR)/simulator/*.java

clean:
	@rm -f  $(BINDIR)/*.class
	@rm -f $(BINDIR)/*/*.class
	@rm -Rf doc
