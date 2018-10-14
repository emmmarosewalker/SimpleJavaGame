import bos.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Grid implements GameBoard<Cell> {

    private Cell[][] cells = new Cell[20][20];

    private int x;
    private int y;

    private Point lastSeenMousePos;
    private long stillMouseTime;

    public Grid(int x, int y) {
        this.x = x;
        this.y = y;

        SAWReader sr = new SAWReader("data/stage1.saw");

        ArrayList<bos.Pair<Integer, Integer>> blocks = sr.getBlockLocs();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                cells[i][j] = new Cell(x + j * 35, y + i * 35);
                for (bos.Pair<Integer, Integer> block : blocks) {
                    if (block.first == i && block.second == j) {
                        cells[i][j] = new Block(x + j * 35, y + i * 35);
                    }
                }
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void paint(Graphics g, Point mousePosition) {
        if (lastSeenMousePos != null && lastSeenMousePos.equals(mousePosition)) {
            stillMouseTime++;
        } else {
            stillMouseTime = 0;
        }
        doToEachCell((c) -> {
            c.paint(g, c.contains(mousePosition));
        });
        doToEachCell((c) -> {
            if (c.contains(mousePosition)){
                if (stillMouseTime > 20){
                    if (c.getPath() != null) {
                        for (Cell p : c.getPath()) {
                            g.setColor(Color.MAGENTA);
                            p.paint(g, true);
                        }
                    }
                    g.setColor(Color.YELLOW);
                    g.fillRoundRect(mousePosition.x + 20, mousePosition.y + 20, 50, 15, 3, 3);
                    g.setColor(Color.BLACK);
                    g.drawString("grass: "+ c.getGrassHeight(), mousePosition.x + 20, mousePosition.y + 32);
                }
            }
        });
        lastSeenMousePos = mousePosition;
    }

    public Cell getRandomCell() {
        java.util.Random rand = new java.util.Random();
        return cells[rand.nextInt(20)][rand.nextInt(20)];
    }

    public bos.Pair<Integer, Integer> indexOfCell(Cell c) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (cells[y][x] == c) {
                    return new bos.Pair(y, x);
                }
            }
        }
        return null;
    }

    public Pair<Integer, Integer> findAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (predicate.test(cells[y][x]))
                    return new Pair(y, x);
            }
        }
        return null;
    }

    public Optional<Pair<Integer, Integer>> safeFindAmongstCells(Predicate<Cell> predicate) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                if (predicate.test(cells[y][x]))
                    return Optional.of(new Pair(y, x));
            }
        }
        return Optional.empty();

    }

    private void doToEachCell(Consumer<Cell> func) {
        for (int y = 0; y < 20; ++y) {
            for (int x = 0; x < 20; ++x) {
                func.accept(cells[y][x]);
            }
        }
    }

    @Override
    public Optional<Cell> below(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first < 19)
                .map((pair) -> cells[pair.first + 1][pair.second]);
    }

    @Override
    public Optional<Cell> above(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.first > 0)
                .map((pair) -> cells[pair.first - 1][pair.second]);
    }

    @Override
    public Optional<Cell> rightOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second < 19)
                .map((pair) -> cells[pair.first][pair.second + 1]);
    }

    @Override
    public Optional<Cell> leftOf(Cell relativeTo) {
        return safeFindAmongstCells((c) -> c == relativeTo)
                .filter((pair) -> pair.second > 0)
                .map((pair) -> cells[pair.first][pair.second - 1]);
    }

    public Cell cellAtRowCol(Integer row, Integer col) {
        return cells[row][col];
    }


    @Override
    public java.util.List<RelativeMove> movesBetween(Cell from, Cell to, GamePiece<Cell> mover) {
        Pair<Integer, Integer> fromIndex = findAmongstCells((c) -> c == from);
        Pair<Integer, Integer> toIndex = findAmongstCells((c) -> c == to);

        List<RelativeMove> result = new ArrayList<RelativeMove>();

        // horizontal movement
//        if (fromIndex.second <= toIndex.second) {
//            for (int i = fromIndex.second; i < toIndex.second; i++) {
//                result.add(new MoveRight(this, mover));
//            }
//        } else {
//            for (int i = toIndex.second; i < fromIndex.second; i++) {
//                result.add(new MoveLeft(this, mover));
//            }
//        }
//
//        // vertical movement
//        if (fromIndex.first <= toIndex.first) {
//            for (int i = fromIndex.first; i < toIndex.first; i++) {
//                result.add(new MoveDown(this, mover));
//            }
//        } else {
//            for (int i = toIndex.first; i < fromIndex.first; i++) {
//                result.add(new MoveUp(this, mover));
//            }
//        }

        AStarPath aStarPath = new AStarPath(this, mover);
        ArrayList<Cell> cellPath = aStarPath.findPath(from, to, mover);

        for (int i = 1; i < cellPath.size(); i++) {
            if (cellPath.get(i).x < cellPath.get(i-1).x) {
                result.add(new MoveLeft(this, mover));
            } else if (cellPath.get(i).x > cellPath.get(i-1).x) {
                result.add(new MoveRight(this, mover));
            } else if (cellPath.get(i).y < cellPath.get(i - 1).y) {
                result.add(new MoveUp(this, mover));
            } else if (cellPath.get(i).y > cellPath.get(i - 1).y) {
                result.add(new MoveDown(this, mover));
            }
        }

        return result;
    }}