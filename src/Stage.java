import java.awt.*;
import java.util.*;
import java.time.*;
import java.util.List;

import bos.*;

public class Stage extends KeyObservable {
    protected Grid grid;
    protected Character sheep;
    protected Character shepherd;
    protected Character wolf;
    private List<Character> allCharacters;
    protected Player player;

    private Instant timeOfLastMove = Instant.now();

    public Stage() {
        SAWReader sr = new SAWReader("data/stage1.saw");
        grid     = new Grid(10, 10);
        shepherd = new Shepherd(grid.cellAtRowCol(sr.getShepherdLoc().first, sr.getShepherdLoc().second), new StandStill());
        sheep    = new Sheep(grid.cellAtRowCol(sr.getSheepLoc().first, sr.getSheepLoc().second), new MoveTowards(shepherd));
        wolf     = new Wolf(grid.cellAtRowCol(sr.getWolfLoc().first, sr.getWolfLoc().second), new MoveTowards(sheep));

        player = new Player(grid.getRandomCell());
        this.register(player);

        allCharacters = new ArrayList<>();
        allCharacters.add(sheep); allCharacters.add(shepherd); allCharacters.add(wolf);

        // the Caretaker needs the instance of Stage in order to call Stage's create/setMemento methods.
        StageCaretaker caretaker = new StageCaretaker(this);

        // Register the caretaker as an Observer of the key presses.
        this.register(caretaker);

        // Initialise the current Memento to be the start of the game to avoid NullPointerException, which would happen
        // if user pressed 'r' (restore) prior to saving.
        caretaker.notify(' ', grid);
    }

    public StageMemento createMemento() {

        // Create instance of a Memento so we can call its setState method
        StageMemento m = new StageMemento();

        // Tell the Memento the locations of all the characters so we can restore them if user presses 'r'.
        m.setState(
                new bos.Pair<>(grid.indexOfCell(shepherd.getLocationOf()).first, grid.indexOfCell(shepherd.getLocationOf()).second),
                new bos.Pair<>(grid.indexOfCell(sheep.getLocationOf()).first, grid.indexOfCell(sheep.getLocationOf()).second),
                new bos.Pair<>(grid.indexOfCell(wolf.getLocationOf()).first, grid.indexOfCell(wolf.getLocationOf()).second),
                new bos.Pair<>(grid.indexOfCell(player.getLocationOf()).first, grid.indexOfCell(player.getLocationOf()).second)
        );

        // Return the Memento with the saved locations so Caretaker can keep track.
        return m;

    }

    public void setMemento(StageMemento m) {
        // Get the locations back from the Memento so we can override the current game state with the saved state.
        ArrayList<bos.Pair<Integer, Integer>> characterLocations = m.getState();

        // Set all the character's locations to those which were saved in the Memento.
        shepherd.setLocationOf(grid.cellAtRowCol(characterLocations.get(0).first, characterLocations.get(0).second));
        sheep.setLocationOf(grid.cellAtRowCol(characterLocations.get(1).first, characterLocations.get(1).second));
        wolf.setLocationOf(grid.cellAtRowCol(characterLocations.get(2).first, characterLocations.get(2).second));
        player.setLocationOf(grid.cellAtRowCol(characterLocations.get(3).first, characterLocations.get(3).second));

    }

    public void update(){
        sheep.getLocationOf().setPath(new AStarPath(grid, sheep).findPath(sheep.getLocationOf(), shepherd.getLocationOf(), sheep));
        wolf.getLocationOf().setPath(new AStarPath(grid, wolf).findPath(wolf.getLocationOf(), sheep.getLocationOf(), wolf));
        shepherd.getLocationOf().setPath(new AStarPath(grid, shepherd).findPath(shepherd.getLocationOf(), sheep.getLocationOf(), shepherd));

        if (!player.inMove()) {
            if (sheep.location == shepherd.location) {
                System.out.println("The sheep is safe :)");
                System.exit(0);
            } else if (sheep.location == wolf.location) {
                System.out.println("The sheep is dead :(");
                System.exit(1);
            } else {
                if (sheep.location.x == sheep.location.y) {
                    sheep.setBehaviour(new StandStill());
                    shepherd.setBehaviour(new MoveTowards(sheep));
                }

                // Implements blocking by checking which direction the character is about to move it, and checking if
                // the cell they will move to is a block, and if so, change their behaviour to stand still.
                for (Character c : allCharacters) {
                    if (c.aiMove(this) instanceof MoveDown && grid.below(c.getLocationOf()).get().isBlock) {
                        c.setBehaviour(new StandStill());
                    } else if (c.aiMove(this) instanceof MoveUp && grid.above(c.getLocationOf()).get().isBlock) {
                        c.setBehaviour(new StandStill());
                    } else if (c.aiMove(this) instanceof MoveLeft && grid.leftOf(c.getLocationOf()).get().isBlock) {
                        c.setBehaviour(new StandStill());
                    } else if (c.aiMove(this) instanceof MoveRight && grid.rightOf(c.getLocationOf()).get().isBlock) {
                        c.setBehaviour(new StandStill());
                    }
                }
                player.startMove();
                allCharacters.forEach((c) -> c.aiMove(this).perform());
                timeOfLastMove = Instant.now();
            }
        }
    }

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g, mouseLocation);
        shepherd.paint(g, mouseLocation);
        wolf.paint(g, mouseLocation);
        player.paint(g);
    }
}