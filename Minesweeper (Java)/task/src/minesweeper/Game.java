package minesweeper;

import java.util.Objects;
import java.util.Scanner;

public class Game {
    static Scanner scanner = new Scanner(System.in);
    int expectedMinesQuantity;
    Field field;
    int x;
    int y;
    String action;
    boolean firstExploreRound = true;
    boolean explode = false;

    void run() {
        System.out.print("How many mines do you want on the field? ");
        expectedMinesQuantity = scanner.nextInt();
        field = new Field(expectedMinesQuantity);

        while (true) {
            System.out.print("Set/unset mines marks or claim a cell as free: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
            action = scanner.next();

            switch (action) {
                case "mine" -> setOrUnsetMarks(x, y);
                case "free" -> explode = explore(x, y);
            }

            field.display();
            if (isMineSwept()) {
                System.out.println("Congratulations! You found all mines!");
                return;
            } else if (explode) {
                System.out.println("You stepped on a mine and failed!");
                return;
            }
        }

    }


    private void setOrUnsetMarks(int x, int y) {
        if (!field.getField()[y][x].isExplored()) {
            field.getField()[y][x].toggleMark();
        } else {
            System.out.println("There is a number here!");
        }
    }

    // return true if explored a mine
    private boolean explore(int x, int y) {
        Cell currentCell = field.getField()[y][x];
        if (firstExploreRound) {
            currentCell.setExplored(true);
            currentCell.setSymbol("/");
            field.placeMines();
            field.placeHints();
            firstExploreRound = !firstExploreRound;
        }

        if (!currentCell.isMinePlanted()) {
            currentCell.setExplored(true);
            if (currentCell.isHint()) {
                currentCell.revealHint();
            } else {
                currentCell.setSymbol("/");
                currentCell.exploreSurroundings(field.getField());
            }
        } else {
            field.revealMines();
            return true;
        }

        return false;
    }

    // return true if either planted mines == marked mines == marked cells
    // (Nb: marked cells is not to exceed marked mines quantity)
    // or explored cells == cells quantity minus planted mines
    private boolean isMineSwept() {
       int[] checkedResult = checkMarkedCells();
       int exploredCells = checkExploredCells();
        return (checkedResult[0] == expectedMinesQuantity && checkedResult[1] == expectedMinesQuantity) ||
                (exploredCells == (81 - expectedMinesQuantity) && checkedResult[1] == 0);
    }

    private int checkExploredCells() {
        int exploredCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell currentCell = field.getField()[i][j];
                if (currentCell.isExplored()) {
                    exploredCells++;
                }
            }
        }

        return exploredCells;
    }

    private int[] checkMarkedCells() {
        int markedMines = 0;
        int markedCells = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell currentCell = field.getField()[i][j];
                if (currentCell.isMinePlanted() && Objects.equals(currentCell.getSymbol(), "*")) {
                    markedMines++;
                }

                if (Objects.equals(currentCell.getSymbol(), "*")) {
                    markedCells++;
                }
            }
        }

        return new int[]{markedMines, markedCells};

    }


}
