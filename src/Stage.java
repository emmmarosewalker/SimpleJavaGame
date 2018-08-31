import java.awt.*;
import java.util.*;
import java.time.*;
import java.util.List;

import bos.RelativeMove;

public class Stage {
    protected Grid grid;
    protected Character sheep;
    protected Character shepherd;
    protected Character wolf;
    private List<Character> allCharacters;

    private Instant timeOfLastMove = Instant.now();

    public Stage() {
        grid     = new Grid(10, 10);
        sheep    = new Sheep(grid.getRandomCell());
        shepherd = new Shepherd(grid.getRandomCell());
        wolf     = new Wolf(grid.getRandomCell());

        allCharacters = new ArrayList<Character>();
        allCharacters.add(sheep); allCharacters.add(shepherd); allCharacters.add(wolf);

    }

    public void update(){
        if (timeOfLastMove.plus(Duration.ofSeconds(1)).isBefore(Instant.now())) {
            if (sheep.location == shepherd.location) {
                System.out.println("The sheep is safe :)");
                System.exit(0);
            } else if (sheep.location == wolf.location){
                System.out.println("The sheep is dead :(");
                System.exit(1);
            } else {
                allCharacters.forEach((c) -> c.aiMove(this).perform());
                timeOfLastMove = Instant.now();
            }
        }
    }

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g);
        shepherd.paint(g);
        wolf.paint(g);
    }
}