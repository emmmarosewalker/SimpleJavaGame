import java.awt.*;
import java.util.*;
import java.time.*;
import bos.RelativeMove;

public class Stage {
    private Grid grid;
    private Character sheep;
    private Character shepherd;
    private Character wolf;

    private Instant timeOfLastMove = Instant.now();
    private java.util.List<RelativeMove> moves;

    public Stage() {
        grid     = new Grid(10, 10);
        sheep    = new Sheep(grid.getRandomCell());
        shepherd = new Shepherd(grid.getRandomCell());
        wolf     = new Wolf(grid.getRandomCell());

        moves    = new ArrayList<RelativeMove>();
        moves.add(new bos.MoveDown(grid, sheep));
        moves.add(new bos.MoveDown(grid, sheep));
        moves.add(new bos.MoveUp(grid, wolf));
        moves.add(new bos.MoveUp(grid, shepherd));

    }

    public void update(){
        if (moves.size() > 0 && timeOfLastMove.plus(Duration.ofSeconds(2)).isBefore(Instant.now())){
            timeOfLastMove = Instant.now();
            moves.remove(0).perform();
        } else if (moves.size() == 0  && timeOfLastMove.plus(Duration.ofSeconds(20)).isBefore(Instant.now())) {
            System.exit(0);
        }
    }

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g);
        shepherd.paint(g);
        wolf.paint(g);
    }
}