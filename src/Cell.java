import java.awt.*;

public class Cell {

    int x;
    int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g, Boolean highlighted) {
        if (highlighted) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, 35, 35);
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, 35, 35);
    }

    public boolean contains(Point target){
        if (target == null)
            return false;
        return target.x > x && target.x < x + 35 && target.y > y && target.y < y +35;
    }
}
