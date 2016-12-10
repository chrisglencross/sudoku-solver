package org.glencross.sudoku;

/**
 * The sudoku board.
 */
public class Board {

    private Cell[][] board = new Cell[9][9];

    private CellSet[] rows = new CellSet[9];
    private CellSet[] columns = new CellSet[9];
    private CellSet[] squares = new CellSet[9];

    public Board() {

        // Set up the board
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell cell = new Cell(x, y);
                board[x][y] = cell;
            }
        }

        init();

    }

    private void init() {

        // Create all the cell sets
        for (int i = 0; i < 9; i++) {
            rows[i] = new CellSet("Row " + i);
            columns[i] = new CellSet("Column " + i);
            squares[i] = new CellSet("Square " + i);
        }


        // Set up rows and columns
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                Cell cell = board[x][y];
                rows[y].setCell(x, cell);
                cell.setRowSet(rows[y]);
                columns[x].setCell(y, cell);
                cell.setColumnSet(columns[x]);
            }
        }
        // Set up the squares
        for (int ySquare = 0; ySquare < 3; ySquare++) {
            for (int xSquare = 0; xSquare < 3; xSquare++) {
                for (int y = 0; y < 3; y++) {
                    for (int x = 0; x < 3; x++) {
                        Cell cell = board[xSquare * 3 + x][ySquare * 3 + y];
                        squares[ySquare * 3 + xSquare].setCell(y * 3 + x, cell);
                        cell.setSquareSet(squares[ySquare * 3 + xSquare]);
                    }
                }
            }
        }
    }

    public void setCellValue(int x, int y, int value) {
        board[x][y].setValue(value);
    }

    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    public CellSet getRow(int i) {
        return rows[i];
    }

    public CellSet getColumn(int i) {
        return columns[i];
    }

    public CellSet getSquare(int i) {
        return squares[i];
    }

    public void parse(String board) {
        String[] rows = board.split("\n");
        if (rows.length != 9) {
            throw new IllegalArgumentException("Board does not have 9 rows");
        }
        for (int y = 0; y < 9; y++) {
            String row = rows[y];
            if (row.length() != 9) {
                throw new IllegalArgumentException("Row does not have 9 columns: " + row);
            }
            for (int x = 0; x < 9; x++) {
                char c = row.charAt(x);
                if (c != ' ' && c != '.') {
                    int value = Integer.parseInt(Character.toString(c));
                    if (value < 1 || value > 9) {
                        throw new IllegalArgumentException("Invalid character " + c);
                    }
                    setCellValue(x, y, value);
                }
            }
        }
    }
}
