package minesweeper;

import java.util.ArrayList;
import java.util.Objects;

public class Cell {
    boolean minePlanted;
    boolean explored;
    boolean hint;
    boolean exploredRecursively = false;
    int hintNumber;
    int x; // horizontal coordinates
    int y; // vertical coordinates
    StringBuilder symbol = new StringBuilder(".");
    ArrayList<int[]> surroundingCoordinates = new ArrayList<>();

    Cell(int x, int y) {
        this.x = x;
        this.y = y;

        if (x == 0 && y == 0) {
            surroundingCoordinates.add(new int[]{x, y + 1}); // bottom
            surroundingCoordinates.add(new int[]{x + 1, y}); // right
            surroundingCoordinates.add(new int[]{x + 1, y + 1}); // bottom right
        } else if (x == 0 && y == 8) {
            surroundingCoordinates.add(new int[]{x, y - 1});
            surroundingCoordinates.add(new int[]{x + 1, y});
            surroundingCoordinates.add(new int[]{x + 1, y - 1});
        } else if (x == 8 && y == 0) {
            surroundingCoordinates.add(new int[]{x - 1, y});
            surroundingCoordinates.add(new int[]{x - 1, y + 1});
            surroundingCoordinates.add(new int[]{x, y + 1});
        } else if (x == 8 && y == 8) {
            surroundingCoordinates.add(new int[]{x - 1, y}); // left
            surroundingCoordinates.add(new int[]{x - 1, y - 1}); // top left
            surroundingCoordinates.add(new int[]{x, y - 1}); // top
        } else if (x == 0) {
            surroundingCoordinates.add(new int[]{x, y - 1});
            surroundingCoordinates.add(new int[]{x, y + 1});
            surroundingCoordinates.add(new int[]{x + 1, y - 1});
            surroundingCoordinates.add(new int[]{x + 1, y});
            surroundingCoordinates.add(new int[]{x + 1, y + 1});
        } else if (x == 8) {
            surroundingCoordinates.add(new int[]{x - 1, y + 1});
            surroundingCoordinates.add(new int[]{x - 1, y});
            surroundingCoordinates.add(new int[]{x - 1, y - 1});
            surroundingCoordinates.add(new int[]{x, y - 1});
            surroundingCoordinates.add(new int[]{x, y + 1});
        } else if (y == 0) {
            surroundingCoordinates.add(new int[]{x - 1, y + 1});
            surroundingCoordinates.add(new int[]{x - 1, y});
            surroundingCoordinates.add(new int[]{x, y + 1});
            surroundingCoordinates.add(new int[]{x + 1, y});
            surroundingCoordinates.add(new int[]{x + 1, y + 1});
        } else if (y == 8) {
            surroundingCoordinates.add(new int[]{x - 1, y}); // left
            surroundingCoordinates.add(new int[]{x - 1, y - 1}); // top left
            surroundingCoordinates.add(new int[]{x, y - 1}); // top
            surroundingCoordinates.add(new int[]{x + 1, y - 1}); // top right
            surroundingCoordinates.add(new int[]{x + 1, y}); // right
        } else {
            surroundingCoordinates.add(new int[]{x - 1, y + 1}); // bottom left
            surroundingCoordinates.add(new int[]{x - 1, y}); // left
            surroundingCoordinates.add(new int[]{x - 1, y - 1}); // top left
            surroundingCoordinates.add(new int[]{x, y - 1}); // top
            surroundingCoordinates.add(new int[]{x, y + 1}); // bottom
            surroundingCoordinates.add(new int[]{x + 1, y - 1}); // top right
            surroundingCoordinates.add(new int[]{x + 1, y}); // right
            surroundingCoordinates.add(new int[]{x + 1, y + 1}); // bottom right
        }
    }

    void toggleMark() {
        if (Objects.equals(symbol.toString(), ".")) {
            symbol.replace(0, 1, "*");
        } else {
            symbol.replace(0, 1, ".");
        }
    }

    public boolean isExploredRecursively() {
        return exploredRecursively;
    }

    public void setExploredRecursively(boolean exploredRecursively) {
        this.exploredRecursively = exploredRecursively;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public String getSymbol() {
        return symbol.toString();
    }
    public void setSymbol(String s) {
        symbol.replace(0, 1, s);
    }

    public boolean isMinePlanted() {
        return minePlanted;
    }

    public void setMinePlanted(boolean minePlanted) {
        this.minePlanted = minePlanted;
    }

    public void setHintNumber(int hintNumber) {
        this.hintNumber = hintNumber;
    }

    public void revealHint() {
       if (hintNumber > 0) {
            symbol.replace(0, 1, Integer.toString(hintNumber));
        }
    }

    int checkSurrounding(Cell[][] field) {
        int minesCount = 0;
        for (int[] ints : surroundingCoordinates) {
            if (field[ints[0]][ints[1]].isMinePlanted()) {
                minesCount++;
            }
        }

        return minesCount;
    }

    void exploreSurroundings(Cell[][] field) {
        for (int[] ints : surroundingCoordinates) {
            Cell currentCell = field[ints[0]][ints[1]];
            if (!currentCell.isHint() && !currentCell.isExplored()) {
                currentCell.setExplored(true);
                currentCell.setSymbol("/");
            } else {
                currentCell.revealHint();
                currentCell.setExplored(true);
            }
        }

        for (int[] ints : surroundingCoordinates) {
            Cell currentCell = field[ints[0]][ints[1]];
            if (!currentCell.isHint()) {
                currentCell.exploreRecursive(field);
            }
        }

    }

    void exploreRecursive(Cell[][] field) {
        for (int[] ints : surroundingCoordinates) {
            Cell currentCell = field[ints[0]][ints[1]];
            if (!currentCell.isHint() && !currentCell.isExplored()) {
                currentCell.setExplored(true);
                currentCell.setSymbol("/");
            } else if (currentCell.isHint() && !currentCell.isExplored()){
                currentCell.revealHint();
                currentCell.setExplored(true);
            }
        }

        for (int[] ints : surroundingCoordinates) {
            Cell currentCell = field[ints[0]][ints[1]];
            if (!currentCell.isHint() && !currentCell.isExploredRecursively()) {
                currentCell.setExploredRecursively(true);
                currentCell.exploreRecursive(field);
            }
        }
    }



}
