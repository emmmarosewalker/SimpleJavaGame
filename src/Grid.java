import java.awt.*;

public class Grid {

    private Cell[][] cells  = new Cell[20][20];;

    private int x;
    private int y;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;

        for(int i = 0; i < 20; i++) {
            for(int j = 0; j < 20; j++) {
                cells[i][j] = new Cell(x + j * 35, y + i * 35);
            }
        }
    }

    public void paint(Graphics g, Point mousePosition) {
        for(int y = 0; y < 20; ++y) {
            for(int x = 0; x < 20; ++x) {
                Cell thisCell = cells[x][y];
                thisCell.paint(g, thisCell.contains(mousePosition));
            }
        }
    }}
