/*
 * Created on 05-Jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.glencross.sudoku;

import java.util.BitSet;

/**
 * A single cell of the Sudoku board.
 */
public class Cell {

    private final int x;
    private final int y;

    private int actualValue = -1;
    private BitSet possibleValues = new BitSet(9);

    private CellSet rowSet;
    private CellSet columnSet;
    private CellSet squareSet;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        possibleValues.set(0, 9);
    }

    public int getValue() {
        return actualValue;
    }

    public void setValue(int value) {
        this.actualValue = value;
        possibleValues.clear(0, 9);
        possibleValues.set(value - 1);
    }

    public void clearPossibleValue(int value) {
        possibleValues.clear(value - 1);

        int remainingValue = -1;
        for (int i = 0; i < 9; i++) {
            if (possibleValues.get(i)) {
                if (remainingValue != -1) {
                    return;
                } else {
                    remainingValue = i + 1;
                }
            }
        }
        if (remainingValue != -1) {
            System.out.println("By process of elimination cell " + x + "," + y + " has value " + remainingValue);
            this.actualValue = remainingValue;
        }

    }

    public boolean hasPossibleValue(int value) {
        return possibleValues.get(value - 1);
    }

    public void setColumnSet(CellSet columnSet) {
        this.columnSet = columnSet;
    }

    public void setRowSet(CellSet rowSet) {
        this.rowSet = rowSet;
    }

    public void setSquareSet(CellSet squareSet) {
        this.squareSet = squareSet;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public Cell clone() {
        Cell newCell = new Cell(x, y);
        newCell.actualValue = this.actualValue;
        newCell.possibleValues = (BitSet) this.possibleValues.clone();
        return newCell;
    }

    public BitSet getPossibleValues() {
        return this.possibleValues;
    }

    public boolean removePossibleValues(BitSet values) {

        boolean dirty = false;
        for (int i = 0; i < 9; i++) {
            if (values.get(i) && possibleValues.get(i)) {
                System.out.println("Cell " + x + "," + y + " cannot have value " + (i + 1));
                clearPossibleValue(i + 1);
                dirty = true;
            }
        }
        return dirty;

    }

    @Override
    public String toString() {
        return "Cell " + (x + 1) + "," + (y + 1);
    }
}
