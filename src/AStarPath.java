import bos.*;

import java.util.ArrayList;
import java.util.Collections;

/*
* Based on the AStar algorithm found at http://www.cokeandcode.com/info/showsrc/showsrc.php?src=../../pathfinder/PathFindingTutorial/target/src/org/newdawn/pathexample/GameMap.java
* */

public class AStarPath {

    private ArrayList<Cell> open = new ArrayList<>();
    private ArrayList<Cell> closed = new ArrayList<>();
    private ArrayList<Cell> cellPath = new ArrayList<>();

    private Grid grid;

    private double getCost(int x, int y, int tx, int ty) {
        double dx = tx - x;
        double dy = ty - y;

        return (Math.sqrt((dx*dx)+(dy*dy)));
    }

    public AStarPath(Grid grid) {
        this.grid = grid;
    }

    public ArrayList<Cell> findPath(Cell from, Cell to, GamePiece<Cell> mover) {
        // x and y indices for the 'from' cell
        Pair<Integer, Integer> fromIndex = grid.findAmongstCells((c) -> c == from);
        // x and y indices for the destination cell
        Pair<Integer, Integer> toIndex = grid.findAmongstCells((c) -> c == to);

        // initial state for A*. The closed group is empty. Only the starting Cell
        // is in the open list
        from.cost = 0;
        from.heuristic = getCost(fromIndex.first, fromIndex.second, toIndex.first, toIndex.second);
        closed.clear();
        open.clear();
        open.add(from);
        to.parent = null;

        // while we haven't reached our destination (or run out of searches)
        while(open.size() != 0) {
            Cell current = open.get(0);
            if (current == to) {
                break;
            }

            open.remove(current);
            closed.add(current);

            // search the neighbours of the current Cell to evaluate them
            // as potential next steps

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if ((x != 0) && (y != 0)) {
                        continue;
                    }

                    bos.Pair<Integer, Integer> currentPos = grid.findAmongstCells((c) -> c == current);
                    int neighbourX = x + currentPos.first;
                    int neighbourY = y + currentPos.second;

                    if (neighbourX <= 19 && neighbourX >= 0 && neighbourY <= 19 && neighbourY >= 0) {

                        if(!grid.getCells()[neighbourX][neighbourY].checkIfBlock()) {

                            double nextStepCost = current.cost + 1;

                            Cell neighbour = grid.getCells()[neighbourX][neighbourY];

                            if (nextStepCost < neighbour.cost) {
                                if (open.contains(neighbour)) {
                                    open.remove(neighbour);
                                }
                                if (closed.contains(neighbour)) {
                                    closed.remove(neighbour);
                                }
                            }

                            if (!open.contains(neighbour) && !closed.contains(neighbour)) {
                                neighbour.cost = nextStepCost;
                                neighbour.heuristic = getCost(neighbourX, neighbourY, toIndex.first, toIndex.second);
                                neighbour.parent = current;
                                open.add(neighbour);
                                Collections.sort(open);
                            }

                        }

                    }

                }

            }

        }

        if (to.parent == null) {
            return null;
        }

        // find our way back using the parents
        Cell target = to;
        while (target != from) {
            cellPath.add(0, target);
            target = target.parent;
        }

        cellPath.add(0, from);

        return cellPath;
    }

}