import java.awt.*;

public class Cell extends Rectangle {

    public Cell(int x, int y) {
        super(x, y, 35, 35);
    }

    public void paint(Graphics g, Boolean highlighted) {
        if (highlighted) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(x, y, 35, 35);
        } else {
            g.setColor(Color.DARK_GRAY);
            g.fillRect(x, y, 35, 35);
        }

        g.setColor(Color.BLACK);
        g.drawRect(x, y, 35, 35);
    }

    @Override
    public boolean contains(Point target){
        if (target == null)
            return false;
        return super.contains(target);
    }
}
