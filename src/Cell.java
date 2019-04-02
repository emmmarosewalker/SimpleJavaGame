import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Cell extends Rectangle implements Comparable {

    private static Random rand = new Random();
    Color c;

    double cost = 1;
    double heuristic = 0;
    Cell parent = null;

    private ArrayList<Cell> myPath;

    protected Boolean isBlock;

    public Cell(int x, int y) {
        super(x, y, 35, 35);
        c = new Color(rand.nextInt(30), rand.nextInt(155)+100, rand.nextInt(30));
        isBlock = false;
    }

    public void paint(Graphics g, Boolean highlighted) {
        g.setColor(c);
        g.fillRect(x, y, 35, 35);
        g.setColor(Color.BLACK);
        g.drawRect(x,y, 35, 35);
        if (highlighted) {
            g.setColor(Color.LIGHT_GRAY);
            for(int i = 0; i < 10; i++){
                g.drawRoundRect(x+1, y+1, 33, 33, i, i);
            }
        }
    }

    public Boolean checkIfBlock() {
        return isBlock;
    }

    public void setPath(ArrayList<Cell> path) {
        this.myPath = path;
    }

    public ArrayList<Cell> getPath() {
        return myPath;
    }

    @Override
    public boolean contains(Point target){
        if (target == null)
            return false;
        return super.contains(target);
    }

    public int getGrassHeight(){
        return c.getGreen()/50;
    }

    @Override
    public int compareTo(Object other) {
        Cell c = (Cell) other;

        double f = heuristic + cost;
        double of = c.heuristic + c.cost;

        if (f < of)
            return -1;
        else if (f > of)
            return 1;
        else
            return 0;
    }
}
