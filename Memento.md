# StageMemento
## Description
In order to allow users to save and restore the game state using key presses, 
the Memento pattern as outlined by the [Gang of Four](https://www.oodesign.com/memento-pattern.html) 
has been implemented. If a character presses ' ' (space), the state of the game is saved
in a StageMemento object, which is facilitated by the StageCaretaker object. We cannot 
simply store the current instance of Stage, because the object references for the 'saved' stage 
will point to the same memory pointers as the stage object in play. As such, only the 
locations of each character and the player are saved in the StageMemento. 

Saving the character locations is implemented by having the indexOfCell() method in the Grid class be accessible 
outside the class, and finding the current grid indices of each character's location. This 
is returned as a bos.Pair<T,T> of Integers corresponding to the x, y values of the character's location. Since the integer indices of the grid do not 
change between games (the grid dimensions remain 20 * 20), we can use this to re-place 
the characters in the stored positions when the player reloads the saved game.

## Classes
###### StageMemento
-ArrayList<bos.Pair<Integer,Integer>> characterLocations;

___ 

+setState(<bos.Pair<Integer,Integer>> shepherdIndices .... etc etc for all characters and players) : void
+getState() : ArrayList<bos.Pair<Integer,Integer>>

###### StageCaretaker _iKeyObserver_
-StageMemento savedMemento;
-Stage stage;

___

+notify(char c, Gameboard<Cell> gb) : void
(contains the logic for creating/restoring Mementos based on keys pressed)

###### Additions to Stage
+createMemento() : StageMemento (creates a Memento and calls its setState() method with the current location of each character)
+setMemento(StageMemento m) : void (sets the locations of each character from the passed-in StageMemento)

## Why it can't be used for general saves
Every time a new game is loaded, i.e. main() is run, Stage is initialised with the character 
locations in data/stage1.saw, and the player is given a random location. Because the implementation 
of saving / restoring happens dynamically and isn't written to a file, we cannot save the game state 
between games. This could be easily implemented by writing to a file e.g. 'save.saw' and extending 
the SAWReader and Stage classes to restore the saved game, if it exists. 