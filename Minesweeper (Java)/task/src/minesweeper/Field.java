package minesweeper;

import java.util.Objects;
import java.util.Random;

public class Field {
    Cell[][] field = new Cell[9][9];
    int expectedMinesQuantity;

    Field(int expectedMinesQuantity) {
        this.expectedMinesQuantity = expectedMinesQuantity;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                field[i][j] = new Cell(i, j);
            }
        }
        this.display();
    }

    public Cell[][] getField() {
        return field;
    }

    void placeMines() {
        Random random = new Random();
        int minesQuantity = 0;
        while (minesQuantity != expectedMinesQuantity) {
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            if (!field[x][y].isMinePlanted() && !Objects.equals(field[x][y].getSymbol(), "/")) {
                field[x][y].setMinePlanted(true);
                minesQuantity++;
            }
        }
    }


    // Place hint on number of mines in the surrounding
    void placeHints() {
        int minesCount = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!field[i][j].isMinePlanted()) {
                    minesCount = field[i][j].checkSurrounding(field);
                }

                if (minesCount != 0 && !field[i][j].isMinePlanted()) {
                    field[i][j].setHint(true);
                    field[i][j].setHintNumber(minesCount);
                }
            }
        }
    }


    void display() {
        System.out.println("\n |123456789|");
        System.out.println("-|---------|");
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < 9; j++) {
                System.out.print(field[i][j].getSymbol());
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-|---------|");
    }


    // Show all mines on the field
    void revealMines() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (field[i][j].isMinePlanted()) {
                    field[i][j].setSymbol("X");
                }

            }
        }
    }

}
