/*
 * Created on 05-Jun-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.glencross.sudoku;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SudokuSolver {

    private Board board;

    public SudokuSolver(Board board) {
        this.board = board;
    }

    public boolean removeUsedValues(CellSet set) {
        boolean dirty = false;
        for (int i = 0; i < 9; i++) {
            Cell cell = set.getCell(i);
            int value = cell.getValue();
            if (value != -1) {
                for (int j = 0; j < 9; j++) {
                    if (i == j) {
                        continue;
                    }
                    Cell otherCell = set.getCell(j);
                    if (otherCell.hasPossibleValue(value)) {
                        System.out.println("Cell " + otherCell.getX() + "," + otherCell.getY() + " is not " + value +
                                " (because of cell " + cell.getX() +
                                "," + cell.getY() + " with value " + cell.getValue() + " in set " + set.getName() + ")");
                        set.getCell(j).clearPossibleValue(value);
                        dirty = true;
                    }
                }
            }
        }
        return dirty;
    }

    public boolean findByElimination(CellSet set) {
        boolean dirty = false;
        for (int value = 1; value <= 9; value++) {
            int pos = -1;
            for (int j = 0; j < 9; j++) {
                if (set.getCell(j).hasPossibleValue(value)) {
                    if (pos == -1) {
                        pos = j;
                    } else {
                        // Have got more than one possible value
                        pos = -1;
                        break;
                    }
                }
            }
            if (pos != -1) {
                Cell cell = set.getCell(pos);
                if (cell.getValue() == -1) {
                    // There is only one cell with this possible value
                    System.out.println("Cell " + cell.getX() + "," + cell.getY() + " has value " + value + " because no other item in set " + set.getName() + " can possibly have this value");
                    cell.setValue(value);
                    dirty = true;
                }
            }
        }
        return dirty;
    }

    public void solve() {

        System.out.println("Starting Position:");
        printBoard();
        System.out.println();

        System.out.println("Solving...");
        int iteration = 0;
        boolean dirty;
        do {
            iteration++;
            System.out.println("Iteration " + iteration + "...");

            dirty = false;

            for (int i = 0; i < 9; i++) {
                dirty |= removeUsedValues(board.getRow(i));
                dirty |= removeUsedValues(board.getColumn(i));
                dirty |= removeUsedValues(board.getSquare(i));
                dirty |= findByElimination(board.getRow(i));
                dirty |= findByElimination(board.getColumn(i));
                dirty |= findByElimination(board.getSquare(i));
            }

            if (!dirty) {
                System.out.println("CHECKING FOR COMBI GROUPS");
                for (int i = 0; i < 9; i++) {
                    dirty |= checkCombiGroups(board.getRow(i));
                    dirty |= checkCombiGroups(board.getColumn(i));
                    dirty |= checkCombiGroups(board.getSquare(i));
                }
            }

            System.out.println("Iteration " + iteration + " complete");
            if (!dirty) {
                System.out.println("SOLUTION:");
            }
            printBoard();

        } while (dirty);
    }

    /**
     * @param set
     * @return
     */
    private boolean checkCombiGroups(CellSet set) {

        // Create a list of all cells which are not definite
        List<Cell> cells = new ArrayList<Cell>();
        for (int i = 0; i < 9; i++) {
            Cell cell = set.getCell(i);
            if (cell.getValue() == -1) {
                cells.add(cell);
            }
        }

        // Get all combinations of these cells
        List<List<Cell>> combis = combinations(cells);

        boolean dirty = false;

        // For each combination, check to see if there are N-tuples with N possible values
        for (List<Cell> combi : combis) {
            if (combi.size() == 1 || combi.size() == cells.size()) {
                continue;
            }
            BitSet values = new BitSet(9);
            for (Cell cell : combi) {
                values.or(cell.getPossibleValues());
            }
            if (values.cardinality() == combi.size()) {
                // Remove the N possible values from any other cells
                System.out.print("Found a combi group " + combi + " in " + set.getName() + " containing values [");
                for (int bit = values.nextSetBit(0); bit >= 0; bit = values.nextSetBit(bit + 1)) {
                    System.out.print((1 + bit) + " ");
                }
                System.out.println("]");
                for (Cell remove : cells) {
                    if (combi.contains(remove)) {
                        // Don't touch any of the cells in the comi
                        continue;
                    }
                    dirty |= remove.removePossibleValues(values);
                }
            }
        }

        return dirty;

    }

    public void printBoard() {
        for (int y = 0; y < 9; y++) {
            for (int y1 = 0; y1 < 3; y1++) {
                for (int x = 0; x < 9; x++) {
                    Cell cell = board.getCell(x, y);
                    for (int x1 = 0; x1 < 3; x1++) {
                        int value = y1 * 3 + x1 + 1;
                        if (cell.hasPossibleValue(value)) {
                            System.out.print(value);
                        } else {
                            System.out.print(" ");
                        }
                    }
                    if (x != 8) {
                        if ((x % 3) == 2) {
                            System.out.print("#");
                        } else {
                            System.out.print("|");
                        }
                    }
                }
                System.out.print("\n");
            }
            String separator = ((y % 3) == 2) ? "#" : "-";
            for (int i = 0; i < 4 * 9 - 1; i++) {
                System.out.print(separator);
            }
            System.out.print("\n");
        }
    }

    /**
     * Returns all combinations of the items containing at least 2 elements.
     *
     * @param items
     */
    public List<List<Cell>> combinations(List<Cell> items) {
        List<List<Cell>> result = new ArrayList<List<Cell>>();
        combinations(new ArrayList<Cell>(), items, result);
        return result;
    }

    private void combinations(List<Cell> prefix, List<Cell> items, List<List<Cell>> result) {
        if (items.size() > 0) {
            ArrayList<Cell> newPrefix = new ArrayList<Cell>(prefix);
            newPrefix.add(items.get(0));
            result.add(newPrefix);
            List<Cell> newItems = items.subList(1, items.size());
            combinations(newPrefix, newItems, result);
            combinations(prefix, newItems, result);
        }
    }

}
