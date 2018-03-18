cd tictactoe/src &&

echo "Compiling.." &&
javac core/*.java &&

echo "generating stub and skeleton.." &&
rmic core.GameServer &&

rmiregistry
#start rmiregistry


