package org.glencross.sudoku;

/**
 * A set of 9 related cells on the Sudoku board. This can be a row, column or larger square.
 * Each cell on the board is in exactly three sets: one row, one column, and one grid of 9 cells.
 */
public class CellSet {

    private final Cell[] cells = new Cell[9];
    private final String name;

    public CellSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCell(int index, Cell cell) {
        cells[index] = cell;
    }

    public Cell getCell(int index) {
        return cells[index];
    }
}
