import java.awt.*;

public class Block extends Cell {
    public Block(int x, int y) {
        super(x, y);
        isBlock = true;
    }

    @Override
    public void paint(Graphics g, Boolean highlighted) {
        g.setColor(new Color(139,69,19));
        g.fillRect(x, y, 35, 35);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, 35, 35);
        for (int i = 0; i < 35; i+= 5) {
            g.drawLine(x + i, y, x, y + i);
            g.drawLine(x + 35, y + i, x + i, y + 35);
        }
    }
}
