package org.glencross.sudoku;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class SudokuSolverApp {

    public static void main(String[] args) throws IOException {

        String data = loadData(args);
        Board board = new Board();
        board.parse(data);

        SudokuSolver solver = new SudokuSolver(board);
        solver.solve();

    }

    private static String loadData(String[] args) throws IOException {
        String fileName;
        if (args.length == 0) {
            System.err.println("Usage: java " + SudokuSolverApp.class.getName() + " [<board file>]");
            System.err.println("Using default filename 'board.txt'.");
            fileName = "board.txt";
        } else {
            fileName = args[0];
        }
        System.err.println("Loading " + fileName);
        Path file = Paths.get(fileName);
        return Files.readAllLines(file).stream().collect(Collectors.joining("\n"));
    }

}
