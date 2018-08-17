import java.awt.*;

public class Stage {
    private Grid grid;
    private Character sheep;
    private Character shepherd;
    private Character wolf;

    public Stage() {
        grid     = new Grid(10, 10);
        sheep    = new Sheep(grid.getRandomCell());
        shepherd = new Shepherd(grid.getRandomCell());
        wolf     = new Wolf(grid.getRandomCell());
    }

    public void paint(Graphics g, Point mouseLocation) {
        grid.paint(g, mouseLocation);
        sheep.paint(g);
        shepherd.paint(g);
        wolf.paint(g);
    }
}