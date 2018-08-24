import bos.GameBoard;

import java.awt.*;
import java.util.Optional;

public class Grid implements GameBoard<Cell> {

    private Cell[][] cells  = new Cell[20][20];

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
    }

    public Cell getRandomCell(){
        java.util.Random rand = new java.util.Random();
        return cells[rand.nextInt(20)][rand.nextInt(20)];
    }

    private bos.Pair<Integer, Integer> indexOfCell(Cell c){
        for(int y = 0; y < 20; ++y) {
            for(int x = 0; x < 20; ++x) {
                if (cells[y][x] == c) {
                    return new bos.Pair(y, x);
                }
            }
        }
        return null;
    }

    @Override
    public Optional<Cell> below(Cell relativeTo) {
        bos.Pair<Integer, Integer> loc = indexOfCell(relativeTo);
        if (loc.first < 19)
            return Optional.of(cells[loc.first+1][loc.second]);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Cell> above(Cell relativeTo) {
        bos.Pair<Integer, Integer> loc = indexOfCell(relativeTo);
        if (loc.first > 0)
            return Optional.of(cells[loc.first - 1][loc.second]);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Cell> rightOf(Cell relativeTo) {
        bos.Pair<Integer, Integer> loc = indexOfCell(relativeTo);
        if (loc.second < 19)
            return Optional.of(cells[loc.first][loc.second + 1]);
        else
            return Optional.empty();
    }

    @Override
    public Optional<Cell> leftOf(Cell relativeTo) {
        bos.Pair<Integer, Integer> loc = indexOfCell(relativeTo);
        if (loc.second > 0)
            return Optional.of(cells[loc.first][loc.second - 1]);
        else
            return Optional.empty();
    }
}
